package com.ics.likeplayer.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.Database.Model.Database_players_play;
import com.ics.likeplayer.FurtherActivity.AllVideoActivity;
import com.ics.likeplayer.Model.DIrectories_Folders;
import com.ics.likeplayer.R;

import java.util.ArrayList;

public class Allplaylistadapter extends RecyclerView.Adapter<Allplaylistadapter.ViewHolder>  {

    private Context context;
    private ArrayList<Database_players_play> pojoClassArrayList;

    public Allplaylistadapter(Context context, ArrayList<Database_players_play> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
    }
    @Override
    public Allplaylistadapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directories, parent, false);

        return new Allplaylistadapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(Allplaylistadapter.ViewHolder holder, int position) {
//        Log.e("Dire namne is",""+pojoClassArrayList.get(position).getNote());
//        pojoClassArrayList.set(position , )
//        DirectoriesList.set
//        holder.playname.setText(pojoClassArrayList.get(position).getName() +" "+ pojoClassArrayList.get(position).getNo_of_SOngs());

//        holder.singer_name.setText(pojoClassArrayList.get(position).getSinger_name());
//        holder.time.setText(pojoClassArrayList.get(position).getTime());

    }

    @Override
    public int getItemCount()   {
        return pojoClassArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView playname,singer_name,time;

        public ViewHolder(View view) {
            super(view);

            playname =  view.findViewById(R.id.playname);
//            singer_name =  view.findViewById(R.id.singer_name);
//            time =  view.findViewById(R.id.time);
        }
    }
}
