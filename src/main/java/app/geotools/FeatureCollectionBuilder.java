package app.geotools;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mapbox.geojson.*;
import com.mapbox.geojson.gson.GeometryGeoJson;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FeatureCollectionBuilder {

    private List<Feature> featureList = new ArrayList<>();
    private Feature feature;


    public FeatureCollectionBuilder addLineString(List<Point> linestring, Properties properties){
        addToFeatureCollection(LineString.fromLngLats(linestring), properties);
        return this;
    }

    public FeatureCollectionBuilder addLineString(List<Point> linestring){
        addToFeatureCollection(LineString.fromLngLats(linestring));
        return this;
    }

    public FeatureCollectionBuilder addLineString(String lineStringJson){
        addToFeatureCollection(LineString.fromJson(lineStringJson));
        return this;
    }

    public FeatureCollectionBuilder addLineString(String lineStringJson, Properties properties){
        addToFeatureCollection(LineString.fromJson(lineStringJson), properties);
        return this;
    }

    public FeatureCollectionBuilder addPoint(double log, double lat, Properties properties){
        addToFeatureCollection(Point.fromLngLat(log, lat), properties);
        return this;
    }

    public FeatureCollectionBuilder addPoint(double log, double lat){
        addToFeatureCollection(Point.fromLngLat(log, lat));
        return this;
    }

    public FeatureCollectionBuilder addPoint(Point point){
        addToFeatureCollection(point);
        return this;
    }

    public FeatureCollectionBuilder addPoint(String pointJson){
        addToFeatureCollection(Point.fromJson(pointJson));
        return this;
    }

    public FeatureCollectionBuilder addPoint(String pointJson, Properties properties){
        addToFeatureCollection(Point.fromJson(pointJson), properties);
        return this;
    }


    public FeatureCollectionBuilder addPolygon(List<List<Point>> coordinates, Properties properties) throws PolygonFormatException {
        if(!isPolygonFormatCorrect(Polygon.fromLngLats(coordinates)))
            throw new PolygonFormatException();
        addToFeatureCollection(Polygon.fromLngLats(coordinates), properties);
        return this;
    }


    public FeatureCollectionBuilder addPolygon(List<List<Point>> coordinates) throws PolygonFormatException {
        if(!isPolygonFormatCorrect(Polygon.fromLngLats(coordinates)))
            throw new PolygonFormatException();
        addToFeatureCollection(Polygon.fromLngLats(coordinates));
        return this;
    }

    public FeatureCollectionBuilder addPolygon(String json, Properties properties) throws PolygonFormatException {
        Polygon polygon = Polygon.fromJson(json);
        if(!isPolygonFormatCorrect(polygon))
            throw new PolygonFormatException();
        addToFeatureCollection(polygon, properties);
        return this;
    }

    public FeatureCollectionBuilder addPolygon(String json) throws PolygonFormatException {
        Polygon polygon = Polygon.fromJson(json);
        if(!isPolygonFormatCorrect(polygon))
            throw new PolygonFormatException();
        addToFeatureCollection(polygon);
        return this;
    }



    public FeatureCollectionBuilder addMultiLineString(List<List<Point>> linestring, Properties properties){
        addToFeatureCollection(MultiLineString.fromLngLats(linestring), properties);
        return this;
    }

    public FeatureCollectionBuilder addMultiLineString(List<List<Point>> linestring){
        addToFeatureCollection(MultiLineString.fromLngLats(linestring));
        return this;
    }


    public FeatureCollectionBuilder addMultiLineString(String linestringJson, Properties properties){
        addToFeatureCollection(MultiLineString.fromJson(linestringJson), properties);
        return this;
    }

    public FeatureCollectionBuilder addMultiLineString(String linestringJson){
        addToFeatureCollection(MultiLineString.fromJson(linestringJson));
        return this;
    }


    public FeatureCollectionBuilder addMultiPoints(List<Point> pointLists, Properties properties){
        addToFeatureCollection( MultiPoint.fromLngLats(pointLists), properties);
        return this;
    }

    public FeatureCollectionBuilder addMultiPoints(List<Point> pointLists){
        addToFeatureCollection( MultiPoint.fromLngLats(pointLists));
        return this;
    }

    public FeatureCollectionBuilder addMultiPoints(String multiPointsJson, Properties properties){
        addToFeatureCollection( MultiPoint.fromJson(multiPointsJson), properties);
        return this;
    }


    public FeatureCollectionBuilder addMultiPoints(String multiPointsJson){
        addToFeatureCollection( MultiPoint.fromJson(multiPointsJson));
        return this;
    }


    public FeatureCollectionBuilder addMultiPolygon(ArrayList<Polygon> polygons, Properties properties){
        validatePolygonCorrectFormat(polygons);
        addToFeatureCollection(MultiPolygon.fromPolygons(polygons), properties);
        return this;
    }

    public FeatureCollectionBuilder addMultiPolygon(ArrayList<Polygon> polygons){
        validatePolygonCorrectFormat(polygons);
        addToFeatureCollection(MultiPolygon.fromPolygons(polygons));
        return this;
    }

    public FeatureCollectionBuilder addMultiPolygon(String multiPolygonJson, Properties properties){
        MultiPolygon multiPolygon = MultiPolygon.fromJson(multiPolygonJson);
        validatePolygonCorrectFormat(multiPolygon.polygons());
        addToFeatureCollection(multiPolygon, properties);
        return this;
    }

    public FeatureCollectionBuilder addMultiPolygon(String multiPolygonJson){
        MultiPolygon multiPolygon = MultiPolygon.fromJson(multiPolygonJson);
        validatePolygonCorrectFormat(multiPolygon.polygons());
        addToFeatureCollection(multiPolygon);
        return this;
    }

    private void validatePolygonCorrectFormat(List<Polygon> polygons){
        for (Polygon polygon: polygons) {
            if(!isPolygonFormatCorrect(polygon))
                throw new PolygonFormatException();
        }
    }

    private boolean isPolygonFormatCorrect(Polygon polygon){
        int size = polygon.coordinates().get(0).size();
        List<Double> firstPoint = polygon.coordinates().get(0).get(0).coordinates();
        List<Double> lastPoint = polygon.coordinates().get(0).get(size-1).coordinates();
        double firstPointLat = firstPoint.get(0);
        double firstPointLog = firstPoint.get(1);
        double lastPointLat = lastPoint.get(0);
        double lastPointLog = lastPoint.get(1);
        return (firstPointLat == lastPointLat) && (firstPointLog == lastPointLog);
    }


    public String buildToString(){
        FeatureCollection featureCollection = FeatureCollection.fromFeatures(featureList);
        return featureCollection.toJson();
    }

    public JSONObject buildJsonObject(){
        FeatureCollection featureCollection = FeatureCollection.fromFeatures(featureList);
        JSONObject jsonObject = null;
        try {
            jsonObject = (JSONObject) new JSONParser().parse(featureCollection.toJson());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void addToFeatureCollection(CoordinateContainer coordinateContainer){
        feature = Feature.fromGeometry(coordinateContainer);
        featureList.add(feature);
    }


    private void addToFeatureCollection(CoordinateContainer coordinateContainer, Properties properties){
        feature = Feature.fromGeometry(coordinateContainer);
        Map<String, String> map = properties.getProperties();
        for (String key : map.keySet()) {
            feature.addStringProperty(key, map.get(key));
        }
        featureList.add(feature);
    }



}
