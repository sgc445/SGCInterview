package utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Properties;
import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONObject;

public class ProjectUtils {
    HashSet<String> guidSet = new HashSet<>();

    public Properties getCredentials(){
        Properties prop = new Properties();
        try {
            prop.load(this.getClass().getResourceAsStream("credentials.properties"));

        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        return prop;
    }

    public void authorization(String username, String password,HttpURLConnection con) throws IOException {
        String userCredentials = username+":"+password;
        String basicAuth = "Basic " + new String(new Base64().encode(userCredentials.getBytes()));
        con.setRequestProperty ("Authorization", basicAuth);
        con.setDoOutput(true);
        int responseCode = con.getResponseCode();
        System.out.println("Response Code : " + responseCode);
    }


    public  String connectUrl(String username, String password, URL url) throws IOException {

        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        authorization(username,password,con);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
       return response.toString();

    }
    public HashSet<String> getListNameGuid(JSONObject jsonObj){

        handleValue(jsonObj);
        return guidSet;
    }
    public void handleValue(Object value) {
        if (value instanceof JSONObject) {
            handleJSONObject((JSONObject) value);
        } else if (value instanceof JSONArray) {
            handleJSONArray((JSONArray) value);
        } else {
            //System.out.println();;
        }
    }
    void handleJSONArray(JSONArray jsonArray) {
        jsonArray.iterator().forEachRemaining(this::handleValue);
    }
    void handleJSONObject(JSONObject jsonObject) {
        jsonObject.keys().forEachRemaining(key -> {
            Object value = jsonObject.get(key);
            if ("listName".equalsIgnoreCase(key)){
                String guid = jsonObject.get("guid").toString();
                System.out.println(guid);
                guidSet.add(guid);
            }
            handleValue(value);
        });
    }

}
