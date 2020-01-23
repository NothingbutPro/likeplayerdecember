package com.ics.likeplayer.FurtherActivity.PlalyistViews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.ics.likeplayer.Adapter.SeperatePlaylistAdapter;
import com.ics.likeplayer.Database.DatabaseHelper;
import com.ics.likeplayer.Database.Model.Database_players_play;
import com.ics.likeplayer.FurtherActivity.AllMp3JavaActivity;
import com.ics.likeplayer.MycustomListeners.RecyclerTouchListener;
import com.ics.likeplayer.R;

import java.io.File;
import java.util.ArrayList;

public class AllPlayListViewActivity extends AppCompatActivity {
    RecyclerView playlistsongsrec;
    TextView play_name;
    DatabaseHelper databaseHelper;
    String play_type;
    com.google.android.exoplayer2.ui.PlaybackControlView hidemp3controls;
    Cursor cursor;
    SeperatePlaylistAdapter seperatePlaylistAdapter;
    ArrayList<Database_players_play> Playlist_Songs = new ArrayList<>();
    private SimpleExoPlayer simpleExoplayer;
    private File _videofile;
    private TextView song_namewa;
    private TextView backsongname;
    public ImageView backplaypause;
    public ImageView cancelback;
    private boolean ppplaybackState = false;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++PLay SONG+++++++++++++++++++++++++++++++++++++++


    //+++++++++++++++++++++++++++
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_play_list_view);
        playlistsongsrec = findViewById(R.id.playlistsongsrec);
        play_name = findViewById(R.id.play_name);
        hidemp3controls = findViewById(R.id.hidemp3controls);
        backplaypause = findViewById(R.id.backplaypause);
        cancelback = findViewById(R.id.cancelback);
//        backsongname = findViewById(R.id.backsongname);
        databaseHelper = new DatabaseHelper(this );
        databaseHelper.getReadableDatabase();
        play_type = getIntent().getStringExtra("play_type") ;
        play_name.setText(getIntent().getStringExtra("play_name"));
        playlistsongsrec.addOnItemTouchListener(new RecyclerTouchListener(this, playlistsongsrec, new RecyclerTouchListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                String mp3datafileurl = Playlist_Songs.get(position).getSong_url();
            Log.d("receiver", "Got message: " + mp3datafileurl);
//            hidemp3controls.setVisibility(View.VISIBLE);
            try {
                simpleExoplayer.stop();
                simpleExoplayer.release();
            }catch (Exception e)
            {
                e.printStackTrace();
            }

            prepareExoplayermp3(mp3datafileurl);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }
        }));
        if(getIntent().getStringExtra("play_type").equals("fav"))
        {
            cursor =  databaseHelper.getPlaylistCountnSongs("fav");
        }else {
            cursor =  databaseHelper.getPlaylistCountnSongs(play_type);
        }
        if(getIntent().getStringExtra("play_type").equals("fav"))
        {
            if (cursor.moveToFirst()) {
                do {
                    Database_players_play database_players_play_Playlist = new Database_players_play(
                            cursor.getInt ( cursor.getColumnIndex(Database_players_play.SONG_COLUMN_ID) )
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME) )
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_URL) ),
                            cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_TIME) )
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NAME))
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
//                note.setId(cursor.getInt(cursor.getColumnIndex(Database_players_play.COLUMN_ID)));
//                note.setNote(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)));
//                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
                    Toast.makeText(this, "song name is"+cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)) , Toast.LENGTH_SHORT).show();
                    Log.e("url is" , ""+cursor.getColumnIndex(Database_players_play.COLUMN_URL));
                    Playlist_Songs.add(database_players_play_Playlist);
                } while (cursor.moveToNext());

            }
        }else {
            if (cursor.moveToFirst()) {
                do {
                    Database_players_play database_players_play_Playlist = new Database_players_play(
                            cursor.getInt ( cursor.getColumnIndex(Database_players_play.SONG_COLUMN_ID) )
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME) )
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_URL) ),
                            cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_TIME) )
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_PLAYLIST_NAME))
                            ,cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
//                note.setId(cursor.getInt(cursor.getColumnIndex(Database_players_play.COLUMN_ID)));
//                note.setNote(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)));
//                note.setTimestamp(cursor.getString(cursor.getColumnIndex(Database_players_play.COLUMN_TIMESTAMP)));
                    Toast.makeText(this, "song name is"+cursor.getString ( cursor.getColumnIndex(Database_players_play.COLUMN_SONGNAME)) , Toast.LENGTH_SHORT).show();
                    Log.e("url is" , ""+cursor.getColumnIndex(Database_players_play.COLUMN_URL));
                    Playlist_Songs.add(database_players_play_Playlist);
                } while (cursor.moveToNext());

            }
        }

        cursor.close();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        seperatePlaylistAdapter = new SeperatePlaylistAdapter(this ,Playlist_Songs);
        playlistsongsrec.setLayoutManager(linearLayoutManager);
        playlistsongsrec.setAdapter(seperatePlaylistAdapter);

    }

    private void prepareExoplayermp3(String mp3datafileurl) {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
                this,
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(),new DefaultLoadControl()
        );
        prepareExoplayer(mp3datafileurl);
    }

    private void prepareExoplayer(String filepath) {
        if (hidemp3controls.getVisibility() == View.GONE) {
            hidemp3controls.setVisibility(View.VISIBLE);
        } else {
            hidemp3controls.setVisibility(View.GONE);
        }
        _videofile = new File(filepath);
        Uri uri = Uri.parse(filepath);
//        MediaSource mediaSource = buildMediaSource(uri);
        //        MediaSource mediaSource = buildMediaSource(uri);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoplayer.prepare(mediaSource, true, true);
        simpleExoplayer.setPlayWhenReady(true);
        simpleExoplayer.setPlayWhenReady(true);
        hidemp3controls.setPlayer(simpleExoplayer);
        song_namewa.setText(_videofile.getName());
//         currentindex = get.getLongExtra("currentwindow" ,-1);
//        simpleExoplayer.seekTo(currentindex);
        backsongname.setText(_videofile.getName());
        backplaypause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (simpleExoplayer != null) {
                    if (ppplaybackState) {
                        simpleExoplayer.setPlayWhenReady(false);
                        ppplaybackState = false;
                    } else {
                        simpleExoplayer.setPlayWhenReady(true);
                        ppplaybackState = true;
                    }
                }
            }
        });
        cancelback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpleExoplayer.release();
                simpleExoplayer.stop(true);
                hidemp3controls.setVisibility(View.GONE);
            }
        });
        simpleExoplayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {

                } else if (playbackState == Player.STATE_READY) {
                    Toast.makeText(AllPlayListViewActivity.this, "Player is ready with ", Toast.LENGTH_LONG).show();
//
//                        simpleExoplayer.seekTo(currentindex)

                } else if (playbackState == Player.STATE_ENDED) {
                    Toast.makeText(
                            AllPlayListViewActivity.this,
                            "Your video has been ended",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });


    }
    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(AllPlayListViewActivity.this, "Exoplayer-local")
        ).createMediaSource(uri);
    }

}
