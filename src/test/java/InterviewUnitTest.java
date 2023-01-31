/**
 * User:Sunil GC
 * Date: 1/31/2023
 */
import org.json.JSONObject;
import org.junit.Test;
import utils.DBOperation;
import utils.ProjectUtils;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Properties;

public class InterviewUnitTest extends ProjectUtils {


    @Test
    public void getCredentialsTest() throws InterruptedException, IOException, SQLException {

        Properties prop = getCredentials();
        String userName = prop.getProperty("Username");
        String password = prop.getProperty("Password");
        URL url = new URL("https://wings.it.ndsu.edu/iam/tree.json");

        JSONObject jsonObj =  new JSONObject(connectUrl(userName,password,url));
         HashSet<String> guidSet = getListNameGuid(jsonObj);
        System.out.println("::::::::::::::"+guidSet);

        // get list of guid from url
        String dbName = "ndsuguiddb";
        String dbUsername = "root";
        String dbPassword = "12345";

        DBOperation db = new DBOperation();
        Connection con = db.getDBConnection(dbName,dbUsername,dbPassword);
        System.out.println(con);

        for (String guid:guidSet){
            URL url1 = new URL("https://wings.it.ndsu.edu/iam/"+guid+".json");
            String jsnString = connectUrl(userName,password,url1);
            System.out.println("sgc::::\n\n");

            db.writeToDB(jsnString,con);
        }

    }

}
