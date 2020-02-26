var tokenPilotId = getToken('pilot-id');
var serverUrl;
function init(){
    console.log(document.getElementById('register-pilot-id'));
    document.getElementById('register-pilot-id').value = tokenPilotId;
    serverUrl = getServerUrl();
}


init();


function goToMap(){
    window.location.replace('/src/html/main-map.html');
}



mapboxgl.accessToken = 'pk.eyJ1Ijoic2hhd253dXBsdXMiLCJhIjoiY2p3bDd0MmljMDJuNjN5bWM0amE4Zjh1dyJ9.egOLl8lhG8LhBFktHTuIhw';
var map = new mapboxgl.Map({
    container: 'select-flight-path-map',
    style: 'mapbox://styles/mapbox/outdoors-v11',
    center: [120.870817, 23.679199],
    zoom: 7
});


var selectPathGeoJson = {
    'type': 'Feature',
    'properties': { 
        'icon':{
            'type': 'Feature',
            'geometry': {
            'type': 'Point',
            'coordinates': []
            }
    }},
    'geometry': {
        'type': 'LineString',
        'coordinates': []
    }

}





map.on('load', function () {

    var geocoder = new MapboxGeocoder({
            
        accessToken: mapboxgl.accessToken,
        marker: {
            color: 'orange'
        },
        mapboxgl: mapboxgl
        });
         
        map.addControl(geocoder);

        axios.get(serverUrl+'/uav/flightData/limitArea/forbid')
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


        map.addSource('palnPath', {
            'type': 'geojson',
            'data': selectPathGeoJson});
        map.addLayer({
            'id': 'palnPath',
            'type': 'line',
            'source': 'palnPath',
            'layout': {
            'line-join': 'round',
            'line-cap': 'round'
            },
            'paint': {
            'line-color': '#666666',
            'line-width': 6
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
                "icon-image": 'uav',
                'visibility': 'visible'
            }
            });
            });
        map.addSource('drone', { type: 'geojson', data: selectPathGeoJson.properties.icon});







        axios.get(serverUrl+'/uav/flightData/limitArea/allowFly')
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

        axios.get(serverUrl+'/uav/flightData/limitArea/airportCamp')
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

});


function initUavListByPilot(){
    var url = serverUrl+'/uav/uavlist/' + tokenPilotId;
    axios.get(url)
    .then((response) => {
        let data = response.data;
        for(var i=0; i< data.length;i++){
            document.getElementById('uav-list').options[i] = new Option(data[i], data[i]);
        }
    },(error) => {
        
    }
    );
}

initUavListByPilot();



//左鍵選擇航點
var planPathArray = new Array(); 
map.on('click', function(e) {
    var thisLngLat = JSON.parse(JSON.stringify(e.lngLat.wrap()));
    var lng = thisLngLat.lng;
    var lat = thisLngLat.lat;
    var coordinate = [lng, lat];
    planPathArray.push(coordinate);
    updateSelectPathMap(planPathArray);

});

//右鍵移除最新選的一點
map.on('contextmenu', function(e) {
    if(planPathArray.length != 0){
        planPathArray.pop();
        updateSelectPathMap(planPathArray);
    }
});

//每次選完新地點就更新
function updateSelectPathMap(planPathArray){
    selectPathGeoJson.geometry.coordinates = planPathArray;
    selectPathGeoJson.properties.icon.geometry.coordinates = planPathArray[planPathArray.length - 1];

    map.getSource('palnPath').setData(selectPathGeoJson); 
    map.getSource('drone').setData(selectPathGeoJson.properties.icon); 

    if(planPathArray.length == 0){
        map.setLayoutProperty('drone', 'visibility', 'none');
    }else{
        map.setLayoutProperty('drone', 'visibility', 'visible');
    }

    if(planPathArray.length == 0){
        document.getElementById('flight-plan-path').value = "Flight Plan Path";
    }else{
        if(planPathArray.length > 1){
            document.getElementById('flight-plan-path').value = planPathArray.length + ' waypoints';
        }else{
            document.getElementById('flight-plan-path').value = JSON.stringify(planPathArray);
        }
    }
}


const registerPlanFormElement = document.getElementById("register-paln-form");

var pathCoordinate = {"coordinate":[]};


var signUpStatus;
registerPlanFormElement.addEventListener('submit', function(e) {
    e.preventDefault();

    const registerPlanFormData = new FormData(this);

    var pilotId = tokenPilotId;
    var uavId = registerPlanFormData.get('uav-id');
    var expectedTakeoffTime = registerPlanFormData.get('expected-takeoff-time');
    var expectedArrivalsTime = registerPlanFormData.get('expected-arrivals-time');
    var expectedFlyingHeight = registerPlanFormData.get('expected-flying-height');
    pathCoordinate.coordinate = planPathArray;
    var description = registerPlanFormData.get('flight-description');

    axios.post(serverUrl+'/flightPlan', 
    {
        "pilot-id":pilotId,
        "uav-id":uavId,
        "ads-b":"1234",
        "expected-takeoff-time":expectedTakeoffTime,
        "expected-arrivals-time":expectedArrivalsTime,
        "expected-flying-height":expectedFlyingHeight,
        "flight-plan-path":pathCoordinate,
        "flight-description":description,
    }
    ).then(function (response) {
        signUpStatus = response.status;
        $("#Signup-Plan-Status-Modal").modal('show');
        $("#status-message").text(response.data.message);
      }).catch(function (error) {


      });
});
function done(){
    if(signUpStatus == 200){
        window.location.reload();
    }
}