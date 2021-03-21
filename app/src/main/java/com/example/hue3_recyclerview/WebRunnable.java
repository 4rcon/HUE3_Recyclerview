package com.example.hue3_recyclerview;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.LongBinaryOperator;
import java.util.logging.LogRecord;

public class WebRunnable implements Runnable
{
    private static final String LOG_WB = WebRunnable.class.getCanonicalName();
    public URL url;
    Context context;
    Handler mainHandler = new Handler(Looper.getMainLooper());

    public WebRunnable(String url, Context appContext)
    {
        try
        {
            this.url = new URL(url);
            this.context = appContext;
            Log.d(LOG_WB, "URL = " + this.url);
        }
        catch (MalformedURLException e)
        {
            //e.printStackTrace();
            Log.d(LOG_WB, "Encountered Malformed URL - Check URL");
            mainHandler.post(() -> MainActivity.btn_loadcards.setEnabled(true));
        }
    }


    @Override
    public void run()
    {
        try
        {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            InputStream incoming = urlConnection.getInputStream();

            Scanner scanner = new Scanner(incoming);
            scanner.useDelimiter("\\A");

            if (scanner.hasNext())
            {
                JSONObject root = new JSONObject(scanner.next());
                JSONArray cards = root.getJSONArray("cards");

                for (int i = 0; i < cards.length(); i++)
                {
                    JSONObject card = cards.getJSONObject(i);
                    MagicCard mc = new MagicCard(card);
                    mainHandler.post(() -> MainActivity.CardsList.add(mc));
                }
            }
            mainHandler.post(() -> MainActivity.page++);
            mainHandler.post(() -> MainActivity.myAdapter.notifyDataSetChanged());
            Log.d(LOG_WB, "Load Cards Button activated");
        }
        catch (IOException e)
        {
            //e.printStackTrace();
            Log.d(LOG_WB, "Server unreachable, check Inet Connection!");
        }
        catch (JSONException e)
        {
            //e.printStackTrace();
            Log.d(LOG_WB, "Wrong JSON Format");
        }
        finally
        {
            mainHandler.post(() -> MainActivity.btn_loadcards.setEnabled(true));
        }
    }
}
