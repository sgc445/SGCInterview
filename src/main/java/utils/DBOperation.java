package utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;

public class DBOperation {


    public Connection getDBConnection(String dbName,String dbUsername,String dbPassword){
        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
             con= DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/"+dbName,dbUsername,dbPassword);
        }catch(Exception e){ System.out.println(e);}
        return con;

       }

    public void writeToDB( String jsonString,Connection con) throws SQLException {

        // connect to database
        JSONArray jsonArray = new JSONArray(jsonString);
        for(Object object:jsonArray) {
            //System.out.println(object);
            //System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
            JSONObject jsonObject = (JSONObject) object;
            int number = Integer.parseInt(jsonObject.get("number").toString());
            String given = jsonObject.get("given").toString();
            String guid = jsonObject.get("guid").toString();
            String sn = jsonObject.get("sn").toString();
            String username = jsonObject.get("username").toString();

            //System.out.println(guid);
            System.out.println("sgc");
            PreparedStatement stmt = con.prepareStatement("INSERT INTO listnameTable(number,given, guid, sn, username) VALUES (?, ?, ?,?,?)");
            System.out.println(stmt);
            stmt.setInt(1, number);
            stmt.setString(2, given);
            stmt.setString(3, guid);
            stmt.setString(4, sn);
            stmt.setString(5, username);

            stmt.executeUpdate();

        }

    }
}
