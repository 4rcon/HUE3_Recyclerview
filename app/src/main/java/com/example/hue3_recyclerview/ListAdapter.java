package com.example.hue3_recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ItemViewHolder>
{
    private Context myContext;
    private List<MagicCard> myCards;
    private ListItemClickListener myListItemClickListener;

    interface ListItemClickListener
    {
        void onListItemClick(MagicCard item);
    }

    public ListAdapter(List<MagicCard> myCards)
    {
        this.myCards = myCards;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        myContext = context;

        LayoutInflater inflater = LayoutInflater.from(myContext);
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position)
    {
        holder.bind(position);
        Log.d("BINDLOG", String.valueOf(position));
    }

    @Override
    public int getItemCount()
    {
        return (myCards == null) ? 0 : myCards.size();
    }

    public void setMyListItemClickListener(ListItemClickListener listItemClickListener)
    {
        myListItemClickListener = listItemClickListener;
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private TextView tvName, tvRarity;
        private LinearLayout llItem;

        public ItemViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvRarity = itemView.findViewById(R.id.tv_rarity);
            llItem = itemView.findViewById(R.id.ll_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            if (myListItemClickListener != null)
            {
                int clickedIndex = getAdapterPosition();
                MagicCard mc = myCards.get(clickedIndex);
                myListItemClickListener.onListItemClick(mc);
            }
        }

        @SuppressLint("ResourceAsColor")
        public void bind(int position)
        {
            tvName.setText(myCards.get(position).getName());
            tvRarity.setText(myCards.get(position).getRarity());
            int colorFromCard = 0;
            switch (myCards.get(position).getColors()[0])
            {
                case "White":
                    colorFromCard = R.color.White;
                    break;
                case "Black":
                    colorFromCard = R.color.Black;
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
            llItem.setBackgroundResource(colorFromCard);
        }
    }


}
