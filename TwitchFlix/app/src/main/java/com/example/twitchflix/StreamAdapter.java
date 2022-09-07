package com.example.twitchflix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StreamAdapter extends RecyclerView.Adapter<StreamAdapter.ViewHolder> {

    private List<Stream> sList;
    private Context context;

    public StreamAdapter(Context context, List<Stream> sList) {
        this.context = context;
        this.sList = sList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stream_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txt_StreamTitleCardValue.setText(sList.get(position).getTitle());
        holder.txt_StreamUsernameCardValue.setText(sList.get(position).getUsername());
        Bundle b = new Bundle();
        b.putSerializable("streamData",sList.get(position));

        holder.vholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, StreamPlayer.class);
                intent.putExtras(b);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return sList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView txt_StreamUsernameCardValue, txt_StreamTitleCardValue;
        View vholder;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txt_StreamUsernameCardValue = itemView.findViewById(R.id.txt_StreamUsernameCardValue);
            txt_StreamTitleCardValue = itemView.findViewById(R.id.txt_StreamTitleCardValue);

            vholder = itemView;

        }
    }
}
