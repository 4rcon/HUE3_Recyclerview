package com.example.hue3_recyclerview;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;

public class DetailActivity extends AppCompatActivity
{

    public static final String KEY_CARD = "CARD";
    private TextView tv_Info, tv_Value;
    private Button btn_toast;
    private ImageView iv_image;
    private LinearLayout ll_detailActivity;
    Bitmap bmp;
    String imgLink;
    Context context = this;
    
    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);


        MagicCard mc = getIntent().getParcelableExtra(KEY_CARD);

        tv_Info = findViewById(R.id.tv_Info);
        tv_Value = findViewById(R.id.tv_Value);
        iv_image = findViewById(R.id.iv_image);
        btn_toast = findViewById(R.id.btn_toast);
        ll_detailActivity = findViewById(R.id.ll_detailActivity);

        int colorFromCard = 0;
        switch (mc.getColors()[0])
        {
            case "White":
                colorFromCard = R.color.White;
                break;
            case "Black":
                colorFromCard = R.color.Black;
                tv_Info.setTextColor(Color.parseColor("#EFEFEF"));
                tv_Value.setTextColor(Color.parseColor("#EFEFEF"));
                break;
            case "Blue":
                colorFromCard = R.color.Blue;
                break;
            case "Red":
                colorFromCard = R.color.Red;
                break;
            case "Green":
                colorFromCard = R.color.Green;
                break;
            default:
                colorFromCard = R.color.Violet;
                break;
        }
        ll_detailActivity.setBackgroundResource(colorFromCard);



        tv_Info.setText("Name:\n" +
                "Mana Cost:\n" +
                "Type:\n" +
                "Rarity:\n" +
                "Number:\n" +
                "Power:\n" +
                "Text:");

        tv_Value.setText(mc.getName()+"\n" +
                mc.getManaCost()+"\n"+
                mc.getType()+"\n"+
                mc.getRarity()+"\n"+
                mc.getNumber()+"\n"+
                mc.getPower()+"\n"+
                mc.getText());


        btn_toast.setOnClickListener(view ->
        {
            Toast.makeText(context,"Card ID: "+mc.getId(),Toast.LENGTH_SHORT).show();
        });

        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    String imgLinkHTTP = mc.getImageUrl();
                    imgLink = imgLinkHTTP.replace("http","https");
                    InputStream in = new java.net.URL(imgLink).openStream();
                    bmp = BitmapFactory.decodeStream(in);
                    Handler mainHandler = new Handler(Looper.getMainLooper());
                    mainHandler.post(() -> iv_image.setImageBitmap(bmp));
                    if(!imgLinkHTTP.equals(""))
                        Log.d("IMGLink",""+imgLink);
                    else
                        Log.d("IMGLink","No IMGLink");
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}