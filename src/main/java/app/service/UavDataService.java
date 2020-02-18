package app.service;

import app.geotools.FeatureCollectionBuilder;
import app.geotools.GeoJsonTool;
import app.model.uav.UavFlightPath;
import app.repository.UavFlightPathRepository;
import com.mapbox.geojson.Point;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UavDataService {

    @Resource
    UavFlightPathRepository uavFlightPathRepository;

    public JSONObject getForbidAreaJson(){
        return readJsonFile("src/main/java/app/static/forbid_area.json");
    }

    public JSONObject getAllowsFlyAreaJson(){
        return readJsonFile("src/main/java/app/static/allows_fly_area.json");
    }

    public JSONObject getAirportCampLimitAreaJson(){
        return readJsonFile("src/main/java/app/static/airport_camp_limit.json");
    }

    private JSONObject readJsonFile(String path){
        JSONObject jsonObject = null;
        try {
            JSONParser parser = new JSONParser();
            jsonObject = (JSONObject)parser.parse(new FileReader(path));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }


    public int saveUavFlightPath(UavFlightPath uavFlightPath){
        return uavFlightPathRepository.save(uavFlightPath).getRecordIndex();
    }


    public List<JSONObject> getUavPathDataByPilot(String pilotId) {
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

        JSONObject headIconJsonString = headIconFeatureCollection.buildJsonObject();
        JSONObject pathJsonString = pathFeatureCollection.buildJsonObject();
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
