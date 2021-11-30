package ch.solarplus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonReaderTest {

    private static String readAll(Reader reader) throws IOException {
        StringBuilder strBld = new StringBuilder();
        int cp;
        while ((cp = reader.read()) != -1) {
            strBld.append((char) cp);
        }
        return strBld.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(reader);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static String getJson() throws JSONException, IOException {
        // URL for API, current weather (in London)
        String currentWeatherUrl = "https://api.openweathermap.org/data/2.5/weather?q=London,uk&units=metric&appid=006ce03ad05f36426003d8aed3407a9c";

        return currentWeatherUrl;

    }

    public static String getForecastJson() throws JSONException, IOException {
        // URL for API, current weather (in London)
        String hourlyForecastUrl = "https://api.openweathermap.org/data/2.5/onecall?lat=51.5085&lon=-0.1257&exclude=hourly&&units=metric&appid=006ce03ad05f36426003d8aed3407a9c";

        return hourlyForecastUrl;

    }

    public static String getCoordination() throws JSONException, IOException {

        JSONObject jsonObj = readJsonFromUrl(getJson());

        // Parse the coord: lon & lat
        double longitude = jsonObj.getJSONObject("coord").getDouble("lon");
        double latitude = jsonObj.getJSONObject("coord").getDouble("lat");

        String coord = longitude + ", " + latitude;

        return coord;
    }

    public static String getCityname() throws JSONException, IOException {
        JSONObject jsonObj = readJsonFromUrl(getJson());
        String city = jsonObj.getString("name");
        return city;
    }

    public static String getWeather() throws JSONException, IOException {

        JSONObject jsonObj = readJsonFromUrl(getForecastJson());

        // Dateformatter: from UNIX to this format
        DateTimeFormatter formatter = 
            DateTimeFormatter.ofPattern("dd.MM.yyyy, HH:mm");


        JSONArray arr = jsonObj.getJSONArray("daily");
        
        for (int i = 0; i < arr.length(); i++){
            Long dateTime = arr.getJSONObject(i).getLong("dt");
            String formattedDate = Instant.ofEpochSecond(dateTime)
                .atZone(ZoneId.of("GMT-4"))
                .format(formatter);
            Double temp = arr.getJSONObject(i).getJSONObject("temp").getDouble("day");
            System.out.println(formattedDate + ": "+ temp + "°C");
        }
        
        return " ";
    }

}
