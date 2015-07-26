package com.cryfin.vukickresimir.instapop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by CryFin on 7/24/2015.
 */
public class GetJsonTask {

    static String response = null;
    InputStream inputStream = null;

    public GetJsonTask() {}

    public String makeServiceCall(String urlString) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            inputStream = con.getInputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    inputStream));

            StringBuffer sb = new StringBuffer();

            String line = "";
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