package com.ics.likeplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.Database.DatabaseHelper;
import com.ics.likeplayer.Database.Model.Database_players_play;
import com.ics.likeplayer.FurtherActivity.PlalyistViews.AllPlayListViewActivity;
import com.ics.likeplayer.R;

import java.util.ArrayList;

import static com.ics.likeplayer.Adapter.MyAllVideosAdpter._Song_name;
import static com.ics.likeplayer.Adapter.MyAllVideosAdpter._Song_time;
import static com.ics.likeplayer.Adapter.MyAllVideosAdpter._Song_url;

public class PlaylistFragemtnAdapter extends RecyclerView.Adapter<PlaylistFragemtnAdapter.ViewHolder>  {
    private DatabaseHelper db;
    private Context context;
    private ArrayList<Database_players_play> pojoClassArrayList;

    public PlaylistFragemtnAdapter(Context context, ArrayList<Database_players_play> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;
        db = new DatabaseHelper(context);
    }
    @Override
    public PlaylistFragemtnAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.directories, parent, false);

        return new PlaylistFragemtnAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(PlaylistFragemtnAdapter.ViewHolder holder, int position) {
        Log.e("Dire namne is",""+pojoClassArrayList.get(position).getPlay_list_name());
//        pojoClassArrayList.set(position , )
//        DirectoriesList.set
        holder.Dir_Name.setText(pojoClassArrayList.get(position).getPlay_list_name() +" "+ pojoClassArrayList.get(position).getPlaylist_no_of_songs());
        holder.Dir_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(v.getContext() , AllPlayListViewActivity.class);
                intent.putExtra("play_name" , pojoClassArrayList.get(position).getPlay_list_name());
                intent.putExtra("play_type" , String.valueOf( pojoClassArrayList.get(position).getPlay_id()));
                v.getContext().startActivity(intent);
            }
        });
//        holder.Dir_Name.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext() , AllVideoActivity.class);
//                intent.putExtra("dirpath" , ""+pojoClassArrayList.get(position).getBaseAddress());
//                intent.putExtra("sectionvid" , ""+pojoClassArrayList.get(position).getName());
//                intent.putExtra("nofsongs" , ""+pojoClassArrayList.get(position).getNo_of_SOngs());
//                v.getContext().startActivity(intent);
//                ((Activity)context).finish();
//                Log.e("aleardy " , "called");
//            }
//        });
//        holder.singer_name.setText(pojoClassArrayList.get(position).getSinger_name());
//        holder.time.setText(pojoClassArrayList.get(position).getTime());

    }

    @Override
    public int getItemCount()   {
        return pojoClassArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView Dir_Name;

        public ViewHolder(View view) {
            super(view);

            Dir_Name =  view.findViewById(R.id.dir_name);
//            singer_name =  view.findViewById(R.id.singer_name);
//            time =  view.findViewById(R.id.time);
        }
    }
}

