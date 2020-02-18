package app.utility;

import java.util.Random;

public class FakeLocationData {

    public static double tempValue = 0;
    public static double tempValue2 = 0;

    private static Random random = new Random();

    public static double getFakeLatitudeData(double latitude){

        double value = random.nextDouble() / 5000;

        tempValue += value;
        return latitude+tempValue;
    }

    public static double getFakeLongitudeData(double longitude){
        double value = random.nextDouble() / 5000;
        tempValue2 += value;
        return longitude-tempValue2;
    }



    public static Double[] getTainanFakeCoordinateData(){
        Double testLatitude = getFakeLatitudeData(22.625236);
        Double testLongitude = getFakeLongitudeData(120.285776);
        return new Double[]{testLongitude, testLatitude};
    }

    public static Double[] getOtherFakeCoordinateData(){
        Double testLatitude = getFakeLatitudeData(22.894197);
        Double testLongitude = getFakeLongitudeData(120.245112);
        return new Double[]{testLongitude, testLatitude};
    }

    public static Double[] getOther2FakeCoordinateData(){
        Double testLatitude = FakeLocationData.getFakeLatitudeData(22.897905);
        Double testLongitude = FakeLocationData.getFakeLongitudeData(120.245251);
        return new Double[]{testLongitude, testLatitude};
    }

}
