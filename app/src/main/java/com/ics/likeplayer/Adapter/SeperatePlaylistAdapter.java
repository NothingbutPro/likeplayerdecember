package com.ics.likeplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.Database.Model.Database_players_play;
import com.ics.likeplayer.FurtherActivity.PlayJavaVideoActivity;
import com.ics.likeplayer.R;

import java.util.ArrayList;

public class SeperatePlaylistAdapter  extends RecyclerView.Adapter<SeperatePlaylistAdapter.ViewHolder>  {

    private Context context;
    private ArrayList<Database_players_play> pojoClassArrayList;

    public SeperatePlaylistAdapter(Context context, ArrayList<Database_players_play> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }
    @Override
    public SeperatePlaylistAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directories, parent, false);

        return new SeperatePlaylistAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SeperatePlaylistAdapter.ViewHolder holder, int position) {
//        holder.playname.setText(pojoClassArrayList.get(position).getPlay_list_name());
        holder.playname.setText(pojoClassArrayList.get(position).getSong_name());
        Log.e("play name" , ""+pojoClassArrayList.get(position).getSong_name());
        holder.simpledirimg.setVisibility(View.GONE);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(pojoClassArrayList.get(position).getSong_name().endsWith(".mp3")) {
                        Toast.makeText(context, "It is mp3", Toast.LENGTH_SHORT).show();
                    }else {
                        Intent intent = new Intent(v.getContext(), PlayJavaVideoActivity.class);
                        intent.putExtra("playplaylist", pojoClassArrayList);
                        v.getContext().startActivity(intent);
                    }
                }
            });
    }

    @Override
    public int getItemCount()   {
        return pojoClassArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView playname,time;
        ImageView simpledirimg;

        public ViewHolder(View view) {
            super(view);

            playname =  view.findViewById(R.id.dir_name);
            simpledirimg =  view.findViewById(R.id.simpledirimg);
//            singer_name =  view.findViewById(R.id.singer_name);
//            time =  view.findViewById(R.id.time);
        }
    }
}
