package com.example.hue3_recyclerview;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MagicCard implements Parcelable
{
    private String name, manaCost, type, rarity, text, number, power,
            id, imageUrl;
    private String[] colors;

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getManaCost()
    {
        return manaCost;
    }

    public void setManaCost(String manaCost)
    {
        this.manaCost = manaCost;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getRarity()
    {
        return rarity;
    }

    public void setRarity(String rarity)
    {
        this.rarity = rarity;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public String getNumber()
    {
        return number;
    }

    public void setNumber(String number)
    {
        this.number = number;
    }

    public String getPower()
    {
        return power;
    }

    public void setPower(String power)
    {
        this.power = power;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        this.imageUrl = imageUrl;
    }

    public String[] getColors()
    {
        return colors;
    }

    public void setColors(String[] colors)
    {
        this.colors = colors;
    }

    public MagicCard(String name, String manaCost, String[] colors, String type, String rarity, String text, String number, String power, String id, String  imageUrl)
    {
        this.name = name;
        this.manaCost = manaCost;
        this.colors = colors;
        this.type = type;
        this.rarity = rarity;
        this.text = text;
        this.number = number;
        this.power = power;
        this.id = id;
        this.imageUrl = imageUrl;
    }

    public MagicCard(JSONObject magicCard)
    {
        //Try Parsing all the Strings:
        try
        {

            if(!magicCard.isNull("name"))
                this.name = magicCard.getString("name");
            else
                this.name = "";

            if(!magicCard.isNull("manaCost"))
                this.manaCost = magicCard.getString("manaCost");
            else
                this.manaCost = "";

            if(!magicCard.isNull("type"))
                this.type = magicCard.getString("type");
            else
                this.type = "";

            if(!magicCard.isNull("rarity"))
                this.rarity = magicCard.getString("rarity");
            else
                this.rarity = "";

            if(!magicCard.isNull("text"))
                this.text = magicCard.getString("text");
            else
                this.text = "";

            if(!magicCard.isNull("number"))
                this.number = magicCard.getString("number");
            else
                this.number = "";

            if(!magicCard.isNull("power"))
                this.power = magicCard.getString("power");
            else
                this.power = "";

            if (!magicCard.isNull("id"))
                this.id = magicCard.getString("id");
            else
                this.id = "";

            if(!magicCard.isNull("imageUrl"))
                this.imageUrl = magicCard.getString("imageUrl");
            else
                this.imageUrl = "";
        }
        catch (JSONException e)
        {
            //e.printStackTrace();
            Log.d("MagicCard","one or more Values were not added");
        }

        try
        {
            if (!magicCard.isNull("colors"))
            {
                JSONArray jsColors = magicCard.getJSONArray("colors");
                this.colors = new String[jsColors.length()];
                for (int i = 0; i<jsColors.length(); i++)
                {
                    this.colors[i] = jsColors.getString(i);
                    //Log.d("Magiccard","contains now: " + colors[i]);
                }
            }
            else
                this.colors = new String[]{"No Color"};
        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Log.d("MagicCard","one or more color from String[] were not added");
        }

    }

    protected MagicCard(Parcel in)
    {
        name = in.readString();
        manaCost = in.readString();
        colors = in.createStringArray();
        type = in.readString();
        rarity = in.readString();
        text = in.readString();
        number = in.readString();
        power = in.readString();
        id = in.readString();
        imageUrl = in.readString();
    }

    public static final Creator<MagicCard> CREATOR = new Creator<MagicCard>()
    {
        @Override
        public MagicCard createFromParcel(Parcel in)
        {
            return new MagicCard(in);
        }

        @Override
        public MagicCard[] newArray(int size)
        {
            return new MagicCard[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(name);
        dest.writeString(manaCost);
        dest.writeStringArray(colors);
        dest.writeString(type);
        dest.writeString(rarity);
        dest.writeString(text);
        dest.writeString(number);
        dest.writeString(power);
        dest.writeString(id);
        dest.writeString(imageUrl);
    }
}
