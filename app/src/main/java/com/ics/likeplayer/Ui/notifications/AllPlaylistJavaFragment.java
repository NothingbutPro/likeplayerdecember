package com.ics.likeplayer.Ui.notifications;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.Adapter.PlayListAdapter;
import com.ics.likeplayer.Database.DatabaseHelper;
import com.ics.likeplayer.Database.Model.Database_players_play;
import com.ics.likeplayer.R;

import java.util.ArrayList;

public class AllPlaylistJavaFragment extends Fragment {
    RecyclerView playlist_recs;
    TextView fav_count_txt;
    public DatabaseHelper db;
    ArrayList<Database_players_play> database_players_playArrayList = new ArrayList<>();
    ArrayList<Database_players_play> database_favorites_players_playArrayList = new ArrayList<>();

    public AllPlaylistJavaFragment() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_allplaylist ,container,false);
        playlist_recs = view.findViewById(R.id.playlist_recs);
        fav_count_txt = view.findViewById(R.id.fav_count_txt);
        db = new DatabaseHelper(getActivity());
       if( (database_players_playArrayList = db.getAllNotesPlaylist() ).size() !=0)
       {
           for (int i = 0; i < database_players_playArrayList.size(); i++)
           {
               Toast.makeText(getActivity(), "playlists type"+database_players_playArrayList.get(i).getPlay_list_type(), Toast.LENGTH_SHORT).show();
               if(database_players_playArrayList.get(i).getPlay_list_type().equals("Favorites"))
               {
                   database_players_playArrayList.remove(i);
               }
           }
           LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
           linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
           PlayListAdapter playListAdapter = new PlayListAdapter(getActivity(),database_players_playArrayList);
           playlist_recs.setLayoutManager(linearLayoutManager);
           playlist_recs.setAdapter(playListAdapter);
       }else {
           Toast.makeText(getActivity(), "No playlist found", Toast.LENGTH_SHORT).show();
       }
       if( (database_favorites_players_playArrayList =db.getAllNotesFavorites()).size() !=0)
       {
//           for(int i=0;i<database_favorites_players_playArrayList.size();i++) {

               fav_count_txt.setText(String.valueOf(database_favorites_players_playArrayList.size()));
//           }
       }else {
           Toast.makeText(getActivity(), "No favorites found", Toast.LENGTH_SHORT).show();
       }
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }
}