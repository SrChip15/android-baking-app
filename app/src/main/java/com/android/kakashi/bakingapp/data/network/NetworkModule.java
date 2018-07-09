package com.android.kakashi.bakingapp.data.network;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;

import com.android.kakashi.bakingapp.data.model.Recipe;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public final class NetworkModule {

    private static final String REQUEST_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/baking.json";
    private List<Recipe> recipes;
    @SuppressLint("StaticFieldLeak") private static NetworkModule networkModule;

    public static NetworkModule getInstance() {
        if (networkModule == null) {
            networkModule = new NetworkModule();
        }

        return networkModule;
    }

    private NetworkModule() {
    }

    public void cacheData(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    @Nullable
    public List<Recipe> getRecipes() {
        return recipes;
    }

    @SuppressWarnings("unused")
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

    public static List<Recipe> fetchRecipes() {
        List<Recipe> recipes = new ArrayList<>();
        try {
            String requestString = getRequestString(REQUEST_URL);
            try {
                JSONArray requestArray = new JSONArray(requestString);
                Type listType = new TypeToken<ArrayList<Recipe>>(){}.getType();
                recipes = new Gson().fromJson(requestArray.toString(), listType);
            } catch (JSONException e) {
                Timber.e("Failed to parse JSON");
            }
        } catch (IOException ioe) {
            Timber.e(ioe, "Failed to fetch data from %s", REQUEST_URL);
        }

        return recipes;
    }

    private static byte[] getRequestBytes(String requestUrl) throws IOException {
        URL url = new URL(requestUrl); // malformed exception is an extension of IOException
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();

            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                throw new IOException(connection.getResponseMessage() + ": with " + requestUrl);
            } else {
                Timber.i("Connection successful! Response Code = %d, Response Message = %s",
                        connection.getResponseCode(), connection.getResponseMessage());
            }

            @SuppressWarnings("UnusedAssignment") int bytesRead = 0;
            byte[] buffer = new byte[1024];

            while ((bytesRead = in.read(buffer)) > 0) { // 0 when there are no bytes to read,
                // -1 when end of line is reached
                out.write(buffer, 0, bytesRead);
            }

            out.close(); // all data read
            return out.toByteArray();
        } finally {
            connection.disconnect();
        }
    }

    private static String getRequestString(String requestUrl) throws IOException {
        return new String(getRequestBytes(requestUrl));
    }
}
