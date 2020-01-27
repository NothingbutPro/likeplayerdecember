package com.ics.likeplayer.SearchPackage;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ics.likeplayer.Adapter.MyAllVideosAdpter;
import com.ics.likeplayer.Model.AllVideos;
import com.ics.likeplayer.MycustomListeners.RecyclerTouchListener;
import com.ics.likeplayer.R;
import com.ics.likeplayer.SearchPackage.Searchpackageadapters.AllSearchMusicAdapter;

import java.io.File;
import java.util.ArrayList;

import static com.ics.likeplayer.FurtherActivity.Searchwhatactivity.mypos;
import static com.ics.likeplayer.FurtherActivity.Searchwhatactivity.search_all_music;

public class AllSearchVideosFragment extends Fragment {
    RecyclerView allsearchvideorec;
    MyAllVideosAdpter allSearchMusicAdapter;
    ArrayList<String> AllMusicArraylist = new ArrayList<>();
    private Cursor cursor;
    private String song_name;
    private ArrayList<AllVideos> searchedVideoList= new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.allsearchvideo , container,false);
        allsearchvideorec = view.findViewById(R.id.allsearchvideorec);
        GetAllMyVideos("None");
//        mypos =1;
        search_all_music.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity(), "Searching", Toast.LENGTH_SHORT).show();
//                searchedmp3List.clear();
                if(mypos ==1) {
                    try {
                        searchedVideoList.clear();
//                    all_mp3search_rec.setVisibility(View.GONE);
                    } catch (NullPointerException e) {
                        Log.e("adapter", "is null");
                    }
                    GetAllMyVideos(newText);
                }
//                filter(newText);
                return false;
            }
        });
        return view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void GetAllMyVideos(String song_names) {

        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        cursor = this.getContext().getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, null, null, null, null);

        if (cursor != null) {
            try {
                searchedVideoList.clear();
//                all_mp3search_rec.setAdapter(null);
                allSearchMusicAdapter.notifyDataSetChanged();
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            if (cursor.moveToFirst()) {
                do {
                    song_name = cursor
                            .getString(cursor
                                    .getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    int song_id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media._ID));

                    String fullpath = cursor.getString(cursor
                            .getColumnIndex(MediaStore.Audio.Media.DATA));
//                        if(searchedmp3List.isEmpty()) {
//                            Toast.makeText(getActivity(), "yes it's null", Toast.LENGTH_SHORT).show();
                    if (song_name.startsWith(song_names)) {
                        File file = new File(fullpath);
                        searchedVideoList.add(new AllVideos(song_name, 0, "", fullpath, file.getParent().toString()));
                        allSearchMusicAdapter = new MyAllVideosAdpter(getActivity(), searchedVideoList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        allsearchvideorec.setLayoutManager(linearLayoutManager);
                        allsearchvideorec.setAdapter(allSearchMusicAdapter);
                        Log.e("full path", "" + fullpath);
                    } else if (song_names.equals("None")) {
                        File file = new File(fullpath);
                        searchedVideoList.add(new AllVideos(song_name, 0, "", fullpath, file.getParent().toString()));
                        allSearchMusicAdapter = new MyAllVideosAdpter(getActivity(), searchedVideoList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                        allsearchvideorec.setLayoutManager(linearLayoutManager);
                        allsearchvideorec.setAdapter(allSearchMusicAdapter);
                        Log.e("full path", "" + fullpath);
                    }
//                        }
//                    album_name = cursor.getString(cursor
//                            .getColumnIndex(MediaStore.Audio.Media.ALBUM));
//                    int album_id = cursor.getInt(cursor
//                            .getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
//
//                    artist_name = cursor.getString(cursor
//                            .getColumnIndex(MediaStore.Audio.Media.ARTIST));
//                    int artist_id = cursor.getInt(cursor
//                            .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));


                } while (cursor.moveToNext());

            }
            cursor.close();
//            db.closeDatabase();
        }

    }
}
