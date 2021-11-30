package ch.solarplus;

import java.io.IOException;

import org.json.JSONException;

public class APIReader {

    public static void main(String[] args) throws IOException, JSONException {

        System.out.println(JsonReaderTest.getCoordination()); 
        System.out.println(JsonReaderTest.getCityname());
        System.out.println(JsonReaderTest.getWeather());

    }

}