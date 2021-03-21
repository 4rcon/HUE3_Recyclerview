package com.example.hue3_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{
    private static final String LOG_TAG = MainActivity.class.getCanonicalName();
    public static Button btn_loadcards;
    public static int page = 1;
    Context context;
    public static ListAdapter myAdapter;
    public static ArrayList<MagicCard> CardsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView list = findViewById(R.id.rv_list);

        btn_loadcards = findViewById(R.id.btn_load_cards);
        context = getApplicationContext();
        btn_loadcards.setOnClickListener(view -> {
            CardsList.clear();
            myAdapter.notifyDataSetChanged();
            loadWebResults();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);

        myAdapter = new ListAdapter(CardsList);
        list.setAdapter(myAdapter);

        myAdapter.setMyListItemClickListener(new ListAdapter.ListItemClickListener()
        {
            @Override
            public void onListItemClick(MagicCard item)
            {
                Intent i = new Intent(MainActivity.this,DetailActivity.class);
                i.putExtra(DetailActivity.KEY_CARD, item);
                startActivity(i);
            }
        });

    }

    private void loadWebResults()
    {

        Log.d(LOG_TAG, "Card Loading, disabling loading button.");
        btn_loadcards.setEnabled(false);
        Toast.makeText(context, "Loading Cards - Page: "+page,Toast.LENGTH_SHORT).show();

        Log.d(LOG_TAG, "Loading new Webrunnable");
        Handler mainHandler = new Handler(Looper.getMainLooper());
        WebRunnable webRunnable = new WebRunnable ("https://api.magicthegathering.io/v1/cards?page="+page, this);
        if (webRunnable.url != null)
            new Thread(webRunnable).start();
        Log.d(LOG_TAG, "Next page = " + page);


    }



}