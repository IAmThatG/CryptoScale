package com.example.android.cryptoscale.Utils;

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

import javax.net.ssl.HttpsURLConnection;

import static android.content.ContentValues.TAG;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by GABRIEL on 11/12/2017.
 */

public class ApiUtils
{
    private static final String BASE_URL = "https://min-api.cryptocompare.com/data/price?";

    public static double makeCurrencyConversionCall(String currency, String btcSymbol)
    {
        // Declare url parameters
        String tsym = currency;
        String fsym = btcSymbol;

        // Create URL object
        URL url = createUrl(String.format("%sfsym=%s&tsyms=%s", BASE_URL, fsym, tsym));

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try
        {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e)
        {
            Log.e(TAG, "makeHttpRequest: Error closing input stream. ", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object

        // Return the {@link Event}
        return extractFeatureFromJson(jsonResponse, tsym);
    }

    /**
     * Returns new URL object from the given string URL.
     */
    private static URL createUrl(String stringUrl)
    {
        URL url = null;
        try
        {
            url = new URL(stringUrl);
        } catch (MalformedURLException e)
        {
            Log.e(TAG, "createUrl: Error creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null)
        {
            return jsonResponse;
        }

        HttpsURLConnection urlConnection = null;
        InputStream inputStream = null;
        try
        {
            urlConnection = (HttpsURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200)
            {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else
            {
                Log.e(TAG, "makeHttpRequest: Error code " + urlConnection.getResponseCode());
            }
        } catch (IOException e)
        {
            Log.e(TAG, "makeHttpRequest: Problem retrieving JSON results.", e);
        } finally
        {
            if (urlConnection != null)
            {
                urlConnection.disconnect();
            }
            if (inputStream != null)
            {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException
    {
        StringBuilder output = new StringBuilder();
        if (inputStream != null)
        {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null)
            {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static double extractFeatureFromJson(String jsonResult, String tsym)
    {
        double amount = 0;

        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(jsonResult))
        {
            return amount;
        }

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try
        {
            JSONObject baseJsonResponse = new JSONObject(jsonResult);
            amount = baseJsonResponse.getDouble(tsym);
        } catch (JSONException e)
        {
            Log.e(TAG, "Problem parsing the earthquake JSON results ", e);
        }

        Log.v(TAG, "extractFeatureFromJson() returned: " + amount);
        return amount;
    }
}
