package com.depplenny.tennews;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class NetworkUtils {
    /** Tag for the log messages */
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    public static List<News> getNewsList(String queryString) {

        URL url = null;
        try {
            url = new URL(queryString);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }

        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractNewsListFromJson(jsonResponse);

    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static List<News> extractNewsListFromJson(String jsonResponse) {
        List<News> newsList = new ArrayList<>();

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResponse)) {
            return null;
        }

        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);


            jsonObject = jsonObject.getJSONObject("response");

            JSONArray jsonArray = jsonObject.getJSONArray("results");

            // If there are results in the array
            if (jsonArray.length() > 0) {
                // Extract out the first element
                for (int i=0; i<jsonArray.length(); i++) {
                    JSONObject arrayElement = jsonArray.getJSONObject(i);
                    String time = arrayElement.getString("webPublicationDate");
                    String title = arrayElement.getString("webTitle");
                    String content = arrayElement.getJSONObject("fields").getString("body");
                    String thumbnail = arrayElement.getJSONObject("fields").getString("thumbnail");

                    News news = new News(time, title, content, thumbnail);

                    Bitmap bitmap = null;
                    try {
                        InputStream in = new URL(news.getThumbnail()).openStream();
                        bitmap = BitmapFactory.decodeStream(in);
                    } catch (Exception e) {
                        Log.e("Error", e.getMessage());
                        e.printStackTrace();
                    }

                    news.setBitmap(bitmap);

                    newsList.add(news);
                }
                return newsList;
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the JSON response", e);
        }
        return null;
    }

}
