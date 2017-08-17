package com.example.joker.pru;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by joker on 17/8/17.
 */

public final class HttpHandler {

    public static final String TAG = HttpHandler.class.getSimpleName();


    //to Fetch the response from url.
    private static String getResponseFromHttpUrl(InputStream in) throws IOException {
        StringBuilder buffer = null;
        try {
            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            buffer = new StringBuilder();
            while (scanner.hasNext()) {
                buffer.append(scanner.next());
            }

        } catch (Exception e){
            Log.e(TAG," Exception "+e);
        }

        return buffer.toString();
    }


    //checking if url is correct , and making calls to getResponseFromHttpUrl()
    public static String makeServiceCall(String reqUrl) {
        String response = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(reqUrl);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            //reade the response
            InputStream in = connection.getInputStream();
            response = getResponseFromHttpUrl(in);

        } catch (MalformedURLException e) {
            Log.e(TAG, " MalformedURLException " + e);
        } catch (ProtocolException e) {
            Log.e(TAG, " ProtocolException " + e);
        } catch (IOException e) {
            Log.e(TAG, " IOException " + e);
        } catch (Exception e) {
            Log.e(TAG, " Exception " + e);
        } finally {
            connection.disconnect();
        }

        return response;

    }


}
