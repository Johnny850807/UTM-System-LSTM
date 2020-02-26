var serverUrl;
var pilotId;
function init(){
    pilotId = getToken('pilot-id');
    document.getElementById('pilot-id').textContent = pilotId;
    serverUrl = getServerUrl();
}

init();



var iconHeads = {
    'type': 'geojson',
    'data': {
        'type': 'FeatureCollection',
        'features': []
        }
}

var iconFeature = {
    'type': 'Feature',
    'geometry': {
    'type': 'Point',
    'coordinates': []
    }
}




mapboxgl.accessToken = 'pk.eyJ1Ijoic2hhd253dXBsdXMiLCJhIjoiY2p3bDd0MmljMDJuNjN5bWM0amE4Zjh1dyJ9.egOLl8lhG8LhBFktHTuIhw';
var map = new mapboxgl.Map({
    container: 'map',
    style: 'mapbox://styles/mapbox/outdoors-v11',
    center: [119.885922, 23.543487],
    zoom: 7.4
});




map.on('load', function () {

    var geocoder = new MapboxGeocoder({
            
        accessToken: mapboxgl.accessToken,
        marker: {
            color: 'orange'
        },
        mapboxgl: mapboxgl
        });
         
        map.addControl(geocoder);

    axios.get(serverUrl + '/uav/flightData/limitArea/forbid')
    .then((response) => {
        let limit_area = response.data;
        map.addLayer({
            'id': 'limit',
            'type': 'fill',
            'source': {
            'type': 'geojson',
            'data': limit_area
        },
            'layout': {},
            'paint': {
                'fill-color': '#f22929',
                'fill-opacity': 0.5
            }
        });
    });




    axios.get(serverUrl + '/uav/flightData/limitArea/allowFly')
    .then((response) => {
        let allows_fly_area = response.data;
        map.addLayer({
            'id': 'allows_area',
            'type': 'fill',
            'source': {
            'type': 'geojson',
            'data': allows_fly_area
        },
            'layout': {},
            'paint': {
                'fill-color': '#f2f233',
                'fill-opacity': 0.4
            }
        });
    });

    axios.get(serverUrl + '/uav/flightData/limitArea/airportCamp')
    .then((response) => {
        let airport_camp_area = response.data;
        map.addLayer({
            'id': 'airport_camp',
            'type': 'fill',
            'source': {
            'type': 'geojson',
            'data': airport_camp_area
        },
            'layout': {},
            'paint': {
                'fill-color': '#00ff1d',
                'fill-opacity': 0.4
            }
        });
    });


                
    d3.json(serverUrl + '/uav/flightData/location/' + pilotId, function(err, data) {
        if (err) throw err;
        var pathGeoJson = data[0];
        var headIconGeoJson = data[1];
            map.addSource('trace', { type: 'geojson', data: pathGeoJson });
            map.addLayer({
                "id": "trace",
                "type": "line",
                "source": "trace",
                "paint": {
                "line-color": "red",
                "line-opacity": 0.75,
                "line-width": 10
            }
            });

            map.loadImage('https://i.imgur.com/Q8dRejz.png', function(error, image) {
                if (error) throw error;
                map.addImage('uav', image);
                map.addLayer({
                    "id": "drone",
                    "type": "symbol",
                    "source": "drone",
                    "layout": {
                    "icon-image": "uav"
                }
                });
                });
            map.addSource('drone', { type: 'geojson', data: headIconGeoJson});

    });

    

    d3.json(serverUrl+'/flightPlan/'+ pilotId, function(err, planPaths) {

        map.addSource('plan-path', { type: 'geojson', data: planPaths });
        map.addLayer({
            "id": "plan-path",
            "type": "line",
            "source": "plan-path",
            "paint": {
            "line-color": "#5e5e5e",
            "line-opacity": 0.8,
            "line-width": 7
            }
            });

    });
});


    map.on('mouseenter', 'plan-path', function() {
        map.getCanvas().style.cursor = 'pointer';
    });
    map.on('mouseleave', 'plan-path', function() {
        map.getCanvas().style.cursor = '';
    });  
    map.on('click', 'plan-path', function(e) {
        var coordinates = e.features[0].geometry.coordinates.slice();

        //format data and time
        var pathProperties = e.features[0].properties;
        var planExpectedTakeoffTime = new Date(pathProperties['expected-takeoff-time']);
        var planDate = planExpectedTakeoffTime.getUTCFullYear()+'/'+planExpectedTakeoffTime.getMonth()+'/'+planExpectedTakeoffTime.getDate();
        var planTime = planExpectedTakeoffTime.getHours()+':'+planExpectedTakeoffTime.getMinutes();
        //format data and time
        
        //show dialog location
        while (Math.abs(e.lngLat.lng - coordinates[0]) > 180) {
        coordinates[0] += e.lngLat.lng > coordinates[0] ? 360 : -360;
        }
        console.log(e);
        var describedDialog = []
        describedDialog[0] = e.lngLat.lng;
        describedDialog[1] = e.lngLat.lat;
        //show dialog location


        new mapboxgl.Popup()
        .setLngLat(describedDialog)
        .setHTML('<h4>Flight Plan</h4>' +
                    `<table style="width:100%">
                    <tr>
                        <th>Plan ID:</th>
                        <th>${pathProperties['plan-id']}</th> 
                    </tr>

                    <tr>
                    <th>UAV ID:</th>
                    <th>${pathProperties['uav-id']}</th> 
                    </tr>

                    <tr>
                    <td>Takeoff Date:</td>
                    <td>${planDate}</td>
                    </tr>

                    <tr>
                    <td>Takeoff Time:</td>
                    <td>${planTime}</td>
                    </tr>

                    <tr>
                        <td>Flying Height:</td>
                        <td>${pathProperties['fly-height']}m</td>
                    </tr>

                    <tr>
                    <td>Description:</td>
                    <td>${pathProperties['description']}</td>
                    </tr>
                </table>`)
        .addTo(map);
        });


window.setInterval(function(){
    axios.get(serverUrl + '/uav/flightData/location/'+ pilotId)
    .then((response) => {
        let newLocationData = response.data;
        console.log(newLocationData);

        var pathGeoJson = newLocationData[0];
        var headIconGeoJson = newLocationData[1];

        console.log(pathGeoJson);
        

        map.getSource('trace').setData(pathGeoJson);    
        map.getSource('drone').setData(headIconGeoJson);

    },(error) => {
        
    }
    );
}, 5000)





function goRigisterPlan(){
    window.location.replace('/src/html/registered-flight-plan.html');
}