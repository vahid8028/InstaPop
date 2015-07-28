package com.cryfin.vukickresimir.instapop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *  Class for downloading JSON data from given URL.
 */
//todo: inspect class
public class GetJson {

    static String response = null;
    InputStream inputStream = null;

    public GetJson() {}

    public String get(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            inputStream = con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputStream));

            StringBuilder sb = new StringBuilder();

            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

            response = sb.toString();

            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

        return response;
    }
}