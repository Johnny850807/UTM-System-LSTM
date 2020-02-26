package app.service;

import app.geotools.FeatureCollectionBuilder;
import app.geotools.GeoJsonTool;
import app.dto.FeatureCollectionDTO;
import app.model.uav.UavFlightPath;
import app.repository.UavFlightPathRepository;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.mapbox.geojson.Point;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UavDataService {

    @Autowired
    UavFlightPathRepository uavFlightPathRepository;

    public FeatureCollectionDTO getForbidAreaJson(){
        return getResourceFile("static/forbid_area.json");
    }

    public FeatureCollectionDTO getAllowsFlyAreaJson(){
        return getResourceFile("static/allows_fly_area.json");
    }

    public FeatureCollectionDTO getAirportCampLimitAreaJson(){
        return getResourceFile("static/airport_camp_limit.json");
    }

    private FeatureCollectionDTO getResourceFile(String path){
        FeatureCollectionDTO featureCollectionDTO = null;
        Resource resource = new ClassPathResource(path);
        try {
            JsonReader jsonReader = new JsonReader(new FileReader(resource.getFile()));
            featureCollectionDTO = new Gson().fromJson(jsonReader, FeatureCollectionDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return featureCollectionDTO;
    }


    public int saveUavFlightPath(UavFlightPath uavFlightPath){
        return uavFlightPathRepository.save(uavFlightPath).getRecordIndex();
    }


    public List<FeatureCollectionDTO> getUavPathDataByPilot(String pilotId) {
        List<UavFlightPath> uavFlightPaths = uavFlightPathRepository.findAllByPilotId(pilotId);
        List<List<UavFlightPath>> clusterUavFlightPath = clusterUavFlightPathByPilotId(uavFlightPaths, pilotId);

        FeatureCollectionBuilder pathFeatureCollection = GeoJsonTool.buildFeatureCollection();//軌跡資料一個GeoJson
        FeatureCollectionBuilder headIconFeatureCollection = GeoJsonTool.buildFeatureCollection();//icon位置一個GeoJson

        List<Point> headIconList = new ArrayList<>();

        for (List<UavFlightPath> uavFlightPathList : clusterUavFlightPath) {
            List<Point> coordinateList = new ArrayList<>();
            uavFlightPathList.forEach(uavFlightPath -> {
                coordinateList.add(Point.fromLngLat(uavFlightPath.getLongitude(), uavFlightPath.getLatitude()));
            });
         pathFeatureCollection.addLineString(coordinateList);
            headIconList.add(coordinateList.get(coordinateList.size() - 1));
        }
        headIconFeatureCollection.addMultiPoints(headIconList);

        FeatureCollectionDTO headIconJsonString = headIconFeatureCollection.buildJsonObject();
        FeatureCollectionDTO pathJsonString = pathFeatureCollection.buildJsonObject();
        return Arrays.asList(pathJsonString, headIconJsonString);
    }


    //待改，等pilot驗證開放
    private List<String> getBelongToUavByPilot(String pilotId){
        return uavFlightPathRepository.findUavIdByPilotId(pilotId);
    }

    private List<List<UavFlightPath>> clusterUavFlightPathByPilotId(List<UavFlightPath> uavFlightPaths, String pilotId){
        List<String> pilotOwnedUavList = getBelongToUavByPilot(pilotId);
        List<List<UavFlightPath>> lists = new ArrayList<>();
        for(String uavId: pilotOwnedUavList) {
            List<UavFlightPath> uavFlightPAthByUavId = uavFlightPaths.stream()
                    .filter(uavFlightPath -> uavFlightPath.getUavId().equals(uavId))
                    .collect(Collectors.toList());
            lists.add(uavFlightPAthByUavId);
        }
        return lists;
    }

//    private void clusterUavFlightPathByPilotId(String pilotId){
//        List<UavFlightPath> uavFlightPaths = uavFlightPathRepository.findAllByPilotId(pilotId);
//        Map<String, ArrayList<UavFlightPath>> uavIdDictionary = new HashMap<>();
//
//        for(UavFlightPath uavFlightPath: uavFlightPaths) {
//            String key = uavFlightPath.getUavId();
//            if (!uavIdDictionary.containsKey(key)) {
//                uavIdDictionary.put(key, new ArrayList<>());
//            }else{
//                ArrayList<UavFlightPath> uavGroup = uavIdDictionary.get(key);
//                uavGroup.add(uavFlightPath);
//            }
//        }
//
//    }


}
