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

import com.ics.likeplayer.Model.AllVideos;
import com.ics.likeplayer.R;
import com.ics.likeplayer.SearchPackage.Searchpackageadapters.AllSearchMusicAdapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.ics.likeplayer.FurtherActivity.Searchwhatactivity.search_all_music;
import static com.ics.likeplayer.FurtherActivity.Searchwhatactivity.mypos;

public class MusicSearchTabFragment extends Fragment {
    RecyclerView all_mp3search_rec;

    AllSearchMusicAdapter allSearchMusicAdapter;
    ArrayList<String> AllMusicArraylist = new ArrayList<>();
    private Cursor cursor;
    private String song_name;
    private ArrayList<AllVideos> searchedmp3List= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.searchmainmp3java , container ,false);
        all_mp3search_rec = view.findViewById(R.id.all_mp3search_rec);
//        mypos = 0;
        GetAllMyMp3("None");
        search_all_music.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getActivity(), "Searching", Toast.LENGTH_SHORT).show();
//                searchedmp3List.clear();
                if(mypos ==0) {
                    try {
                        searchedmp3List.clear();
//                    all_mp3search_rec.setVisibility(View.GONE);
                    } catch (NullPointerException e) {
                        Log.e("adapter", "is null");
                    }
                    GetAllMyMp3(newText);
                }
//                filter(newText);
                return false;
            }
        });
        return  view;
//        return super.onCreateView(inflater, container, savedInstanceState);
    }

    private void filter(String newText) {
        ArrayList<AllVideos> temp = new ArrayList();
        for (AllVideos d : searchedmp3List) {
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            String name = d.getName();
            if (new String(name).contains(new String(newText.toLowerCase())) || new String(name).toUpperCase().contains(new String(newText.toUpperCase()))) {
                temp.add(d);
            }
        }
        //update recyclerview
//        searchedmp3List.clear();
//        all_mp3search_rec.getAdapter().notifyDataSetChanged();
        allSearchMusicAdapter.updateList(temp);

        //    }
    }

    private void GetAllMyMp3(String song_names) {

        Uri allsongsuri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";

        cursor = this.getContext().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, "is_music != 0", null, null);

        if (cursor != null) {
            try {
                searchedmp3List.clear();
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
                                searchedmp3List.add(new AllVideos(song_name, 0, "", fullpath, file.getParent().toString()));
                                allSearchMusicAdapter = new AllSearchMusicAdapter(getActivity(), searchedmp3List);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                all_mp3search_rec.setLayoutManager(linearLayoutManager);
                                all_mp3search_rec.setAdapter(allSearchMusicAdapter);
                                Log.e("full path", "" + fullpath);
                            } else if (song_names.equals("None")) {
                                File file = new File(fullpath);
                                searchedmp3List.add(new AllVideos(song_name, 0, "", fullpath, file.getParent().toString()));
                                allSearchMusicAdapter = new AllSearchMusicAdapter(getActivity(), searchedmp3List);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                                all_mp3search_rec.setLayoutManager(linearLayoutManager);
                                all_mp3search_rec.setAdapter(allSearchMusicAdapter);
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
                    int artist_id = cursor.getInt(cursor
                            .getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));


                } while (cursor.moveToNext());

            }
            cursor.close();
//            db.closeDatabase();
        }
//        File dir =new File(String.valueOf(Environment.getExternalStorageDirectory()));
//        if (dir.exists()&&dir.isDirectory()){
//            File[] files=dir.listFiles(new FilenameFilter(){
//                @Override
//                public boolean accept(File dir,String name){
//                    Log.e("Names are" , ""+dir.getAbsolutePath()+"/"+name);
//                    AllMusicArraylist.add(dir.getAbsolutePath()+"/"+name);
//                    if(name.contains(".mp3"))
//                    {
//                        Log.e("Names324 are" , ""+dir.getAbsolutePath()+"/"+name);
//                    }
//                    return name.contains(".mp3");
//                }
//            });
//            for(int i=0;i<files.length;i++) {
//                File[] files1 = files[i].listFiles();
//            }
////            for (int i =0 ;i<AllMusicArraylist.size();i++)
////            {
////                File file = new File(AllMusicArraylist.get(i));
////                if(file.exists())
////                {
////
//////                    File[] files2=dir.listFiles(new FilenameFilter(){
//////                        @Override
//////                        public boolean accept(File dir,String name){
////////                            Log.e("Names are" , ""+dir.getAbsolutePath()+"/"+name);
//////                            if(name.contains(".mp3"))
//////                            {
//////                                Log.e("mp3s are" , ""+dir.getAbsolutePath()+"/"+name);
//////                            }
//////                            return name.contains(".mp3");
//////                        }
//////                    });
////                    Toast.makeText(getActivity(), "exist", Toast.LENGTH_SHORT).show();
////                }
////
////            }
//        }
    }
}
