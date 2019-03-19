package com.example.graduationproject_2019_1.Manager;

import android.content.ContentValues;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpManager {

    private static HttpManager instance = null;

    public static HttpManager getInstance() {
        return (instance == null ? instance = new HttpManager() : instance);
    }

    public String request(String _url) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(_url);

            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setConnectTimeout(3000);

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String line;
            String page = "";

            while ((line = reader.readLine()) != null) {
                page += line;
            }
            return page;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }


        return null;
    }

    public String request(String _url, ContentValues params) {

        HttpURLConnection connection = null;
        StringBuffer sbParams = new StringBuffer();

        if (params == null) {
            sbParams.append("");
        } else {
            boolean isAnd = false;

            _url += params.get("msrrgn") + "/" + params.get("msrste");
        }

        try {
            URL url = new URL(_url);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept-Charset", "UTF-8");
            connection.setRequestProperty("Context_Type", "application/json");
            connection.setConnectTimeout(3000);

            String strParams = sbParams.toString();
            OutputStream os = connection.getOutputStream();
            os.write(strParams.getBytes("UTF-8"));
            os.flush();
            os.close();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return null;
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));

            String line;
            String page = "";

            while ((line = reader.readLine()) != null) {
                page += line;
            }

            return page;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }
}


