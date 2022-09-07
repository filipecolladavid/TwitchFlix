package com.example.twitchflix;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {

    private List<Movie> mList;
    private Context context;

    public VideoAdapter(Context context, List<Movie> mList) {
        this.context = context;
        this.mList = mList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(mList.get(position).getLink_pic().isEmpty()) holder.img_thumbnail.setImageResource(R.drawable.ic_thumbnaildefault_background);
        else Picasso.get().load(mList.get(position).getLink_pic()).into(holder.img_thumbnail);
        holder.txt_TitleValue.setText(mList.get(position).getTitle());
        String year = String.valueOf(mList.get(position).getRelease_year());
        holder.txt_YearValue.setText(year);
        String duration = String.valueOf(mList.get(position).getDuration());
        holder.txt_DurationValue.setText(duration);
        Bundle b = new Bundle();
        b.putSerializable("movieData",mList.get(position));

        holder.vholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, VideoPlayer.class);
                intent.putExtras(b);
                v.getContext().startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView txt_TitleValue, txt_YearValue, txt_GenreValue, txt_DurationValue;
        ImageView img_thumbnail;
        View vholder;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            txt_TitleValue = itemView.findViewById(R.id.txt_TitleValue);
            txt_YearValue = itemView.findViewById(R.id.txt_YearValue);
            txt_GenreValue = itemView.findViewById(R.id.txt_GenreValue);
            txt_DurationValue = itemView.findViewById(R.id.txt_DurationValue);
            img_thumbnail = itemView.findViewById(R.id.img_thumbnail);

           vholder = itemView;

        }
    }
}
