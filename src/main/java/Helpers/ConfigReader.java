package Helpers;

import java.io.*;
import java.sql.*;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {
   static  String CsvfilePath;
    static FileWriter fileWriter;
    static BufferedWriter bufferedWriter;


      Properties properties;
    String relativePath = "config.properties";
    String filePath = System.getProperty("user.dir") + "/src/main/resources/" + relativePath;//--src\test\resources\testdata
    public ConfigReader() {
        properties = new Properties();
        try (InputStream inputStream = new FileInputStream(filePath)) {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, "Updated property: " + key);
            System.out.println("Property " + key + " set to " + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void saveProperty(String key, String value) {
        properties.setProperty(key, value);
        try (FileOutputStream output = new FileOutputStream(filePath)) {
            properties.store(output, "Updated property: " + key);
            System.out.println("Property " + key + " saved with value: " + value);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    }


