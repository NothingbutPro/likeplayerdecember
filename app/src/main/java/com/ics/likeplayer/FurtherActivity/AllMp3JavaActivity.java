package com.ics.likeplayer.FurtherActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.ics.likeplayer.Adapter.AllMp3Adapters;
import com.ics.likeplayer.Model.AllVideos;
import com.ics.likeplayer.R;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class AllMp3JavaActivity extends AppCompatActivity {
    private AllMp3Adapters AallMp3Adapters;
    private AllMp3Adapters AallVideotoMp3Adapters;
    com.google.android.exoplayer2.ui.PlaybackControlView hidemp3controls;
    LinearLayout moreopt;
    AutoCompleteTextView searchsongs;
    com.github.florent37.shapeofview.shapes.DottedEdgesCutCornerView videoonly,mp3only;
    private String RootDirname;
    public  ImageView backplaypause;
    public LinearLayout searchmemp3;
    public  RecyclerView videoasmp3;
    private RecyclerView allmp3eorec;
    public ArrayList<AllVideos> CustomList = new ArrayList<>();
    private File File;
    private ArrayList<AllVideos> AllVideosList = new ArrayList();
    private ArrayList<AllVideos> AllVideosAsMp3List = new ArrayList();

    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMp3BroadcastRecevier = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String mp3datafileurl = intent.getStringExtra("mp3file_url");
            Log.d("receiver", "Got message: " + mp3datafileurl);
//            hidemp3controls.setVisibility(View.VISIBLE);
            prepareExoplayermp3(mp3datafileurl);
        }
    };
    private SimpleExoPlayer simpleExoplayer;
    private File _videofile;
    private TextView song_namewa;
    private TextView backsongname;
    private boolean ppplaybackState = false;

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
        simpleExoplayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {

                } else if (playbackState == Player.STATE_READY) {
                    Toast.makeText(AllMp3JavaActivity.this, "Player is ready with ", Toast.LENGTH_LONG).show();
//
//                        simpleExoplayer.seekTo(currentindex)

                } else if (playbackState == Player.STATE_ENDED) {
                    Toast.makeText(
                            AllMp3JavaActivity.this,
                            "Your video has been ended",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            }
        });
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(AllMp3JavaActivity.this, "Exoplayer-local")
        ).createMediaSource(uri);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.mainmp3java);
        super.onCreate(savedInstanceState);
        RootDirname = getIntent().getStringExtra("dirpath");
        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance(this).registerReceiver(mMp3BroadcastRecevier,
                new IntentFilter("mp3eventclicked"));
        Setallviews();
        Filtertsongwith("sortatoz");

    }

    private void Filtertsongwith(String sorting) {
        if (!RootDirname.isEmpty() || !RootDirname.isEmpty()) {
            Toast.makeText(this, "Directory Name" + RootDirname, Toast.LENGTH_LONG).show();
//            File = Uri.fromFile(File(RootDirname.toUri())
            File = new File(RootDirname);
            if (File.exists()) {
                String[] stringArrayListpath = File.list();
                Log.e("stringArrayListpath", "" + stringArrayListpath.length);
                Log.e("RootDirname", "" + RootDirname);
                for (int i = 0; i < stringArrayListpath.length; i++) {
                    String namepath = stringArrayListpath[i];
                    File = new File(RootDirname);
                    Log.e("path is", "file:");

                    if (namepath != null && (namepath.endsWith(".mp3"))) {
                        File fx = new File(RootDirname + "/" + stringArrayListpath[i]);
//                            File fx2 = new File(RootDirname + "/" + stringArrayListpath[i+1]);

                        if(fx.exists())
                        {
                            Uri uri = Uri.parse(fx.getAbsolutePath());
                            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                            mmr.setDataSource(AllMp3JavaActivity.this,uri);
                            String METADATA_KEY_DURATION = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                            String METADATA_KEY_ARTIST = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                            String METADATA_KEY_ALBUM = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                            String METADATA_KEY_YEAR = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
                            int millSecond = Integer.parseInt(METADATA_KEY_DURATION);
                            AllVideos allVideos = new AllVideos(fx.getName(),millSecond,"",fx.getAbsolutePath() ,METADATA_KEY_ARTIST,METADATA_KEY_ALBUM,METADATA_KEY_YEAR) ;
//                                AllVideos allVideos2 = new AllVideos(fx2.getName(),"","",fx2.getAbsolutePath()) ;
//                            Toast.makeText(this, "Yes existyyhui"+fx.getName(), Toast.LENGTH_SHORT).show();
                            AllVideosList.add(allVideos);
                        }
                    }

                    if (namepath != null && (namepath.endsWith(".mp4"))) {
                        File fx = new File(RootDirname + "/" + stringArrayListpath[i]);
//                            File fx2 = new File(RootDirname + "/" + stringArrayListpath[i+1]);
                        if(fx.exists())
                        {
                            Uri uri = Uri.parse(fx.getAbsolutePath());
                            MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                            mmr.setDataSource(AllMp3JavaActivity.this,uri);
                            String METADATA_KEY_DURATION = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
                            String METADATA_KEY_ARTIST = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);
                            String METADATA_KEY_ALBUM = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ALBUM);
                            String METADATA_KEY_YEAR = mmr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_YEAR);
                            int millSecond = Integer.parseInt(METADATA_KEY_DURATION);
                            long timeInMillisec = Long.parseLong(METADATA_KEY_DURATION );

                            Toast.makeText(this, "millis secs"+ Long.parseLong(METADATA_KEY_DURATION ), Toast.LENGTH_LONG).show();
                            if( TimeUnit.MILLISECONDS.toSeconds(timeInMillisec) <=600) {
                                AllVideos allVideos = new AllVideos(fx.getName(), millSecond, "", fx.getAbsolutePath(), METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_YEAR);
//                                AllVideos allVideos2 = new AllVideos(fx2.getName(),"","",fx2.getAbsolutePath()) ;
//                            Toast.makeText(this, "Yes existyyhui"+fx.getName(), Toast.LENGTH_SHORT).show();
                                AllVideosAsMp3List.add(allVideos);
                            }else {
                                Toast.makeText(this, "Found video greater than 10min", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                }
                //++++++++++++++++++++++++++++++++++FOR ATOZ++++++++++++++++++++++++++++++++++++
                if(sorting.equals("sortatoz"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
                            public int compare(AllVideos s1, AllVideos s2) {
                                // notice the cast to (Integer) to invoke compareTo

                                return (s1.getName().compareTo(s2.getName()));
                            }
                        });
//                                    AllVideosList = CustomList.stream().sorted().collect(Collectors.toCollection(Collectors.toList()));
                    }
                    AallMp3Adapters = new AllMp3Adapters(this, AllVideosList);
                    //  mLayoutManager = LinearLayoutManager(context)
                    allmp3eorec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    allmp3eorec.setItemAnimator(new DefaultItemAnimator());
                    allmp3eorec.setAdapter(AallMp3Adapters);
                    AallVideotoMp3Adapters = new AllMp3Adapters(this, AllVideosAsMp3List);
                    //  mLayoutManager = LinearLayoutManager(context)
                    videoasmp3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    videoasmp3.setItemAnimator(new DefaultItemAnimator());
                    videoasmp3.setAdapter(AallVideotoMp3Adapters);
                }
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //++++++++++++++++++++++++++++++++FOR ARTIST+++++++++++++++++++++++++++++++++++++++++++++++++++
                if(sorting.equals("artisttxt"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
                            public int compare(AllVideos s1, AllVideos s2) {
                                // notice the cast to (Integer) to invoke compareTo
                                try {
                                    return (s2.getArtist().compareTo(s1.getArtist()));
                                }catch (Exception e)
                                {
                                    Toast.makeText(AllMp3JavaActivity.this, "Valid Artist are not found in files", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                    return 0;
                                }

                            }
                        });
//                                    AllVideosList = CustomList.stream().sorted().collect(Collectors.toCollection(Collectors.toList()));
                    }
                    AallMp3Adapters = new AllMp3Adapters(this, AllVideosList);
                    //  mLayoutManager = LinearLayoutManager(context)
                    allmp3eorec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    allmp3eorec.setItemAnimator(new DefaultItemAnimator());
                    allmp3eorec.setAdapter(AallMp3Adapters);
                }
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //++++++++++++++++++++++++++++++++++++++++FOR ZTOA+++++++++++++++++++++++
                if(sorting.equals("ztoatxt"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
                            public int compare(AllVideos s1, AllVideos s2) {
                                // notice the cast to (Integer) to invoke compareTo
                                return (s2.getName().compareTo(s1.getName()));
                            }
                        });
//                                    AllVideosList = CustomList.stream().sorted().collect(Collectors.toCollection(Collectors.toList()));
                    }
                    AallMp3Adapters = new AllMp3Adapters(this, AllVideosList);
                    //  mLayoutManager = LinearLayoutManager(context)
                    allmp3eorec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    allmp3eorec.setItemAnimator(new DefaultItemAnimator());
                    allmp3eorec.setAdapter(AallMp3Adapters);
                }
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                //++++++++++++++++++++++++++++++++++++++++++++++++FOR YEAR_++++++++++++++++++++++
                if(sorting.equals("yeartxt"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
                            public int compare(AllVideos s1, AllVideos s2) {
                                // notice the cast to (Integer) to invoke compareTo
                                try {
                                    return (s1.getYear().compareTo(s2.getYear()));
                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(AllMp3JavaActivity.this, "Valid Years are not found in files", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                    return 0;
                                }

                            }
                        });
//                                    AllVideosList = CustomList.stream().sorted().collect(Collectors.toCollection(Collectors.toList()));
                    }
                    AallMp3Adapters = new AllMp3Adapters(this, AllVideosList);
                    //  mLayoutManager = LinearLayoutManager(context)
                    allmp3eorec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    allmp3eorec.setItemAnimator(new DefaultItemAnimator());
                    allmp3eorec.setAdapter(AallMp3Adapters);
                }
                //+++++++++++++++++++++++++++++++++++++++++++++++++FOR ARTIST++++++++++++++++++++++++++++++++++++++++
                if(sorting.equals("artisttxt"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
                            public int compare(AllVideos s1, AllVideos s2) {
                                // notice the cast to (Integer) to invoke compareTo
                                try {
                                    return (s1.getArtist().compareTo(s2.getArtist()));
                                }catch (Exception e)
                                {
                                    Toast.makeText(AllMp3JavaActivity.this, "Valid artist not found in files", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });
//                                    AllVideosList = CustomList.stream().sorted().collect(Collectors.toCollection(Collectors.toList()));
                    }
                    AallMp3Adapters = new AllMp3Adapters(this, AllVideosList);
                    //  mLayoutManager = LinearLayoutManager(context)
                    allmp3eorec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    allmp3eorec.setItemAnimator(new DefaultItemAnimator());
                    allmp3eorec.setAdapter(AallMp3Adapters);
                }
                //+++++++++++++++++++++++++++++++++++++++FOR ALBUM++++++++++++++++++++++++++++++++++
                if(sorting.equals("albumtxt"))
                {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
                            public int compare(AllVideos s1, AllVideos s2) {
                                // notice the cast to (Integer) to invoke compareTo
                                try {
                                    return (s1.getAlbum().compareTo(s2.getAlbum()));
                                }catch (Exception e)
                                {
                                    Toast.makeText(AllMp3JavaActivity.this, "Valid artist not found in files", Toast.LENGTH_SHORT).show();
                                    e.printStackTrace();
                                    return 0;
                                }
                            }
                        });
//                                    AllVideosList = CustomList.stream().sorted().collect(Collectors.toCollection(Collectors.toList()));
                    }
                    AallMp3Adapters = new AllMp3Adapters(this, AllVideosList);
                    //  mLayoutManager = LinearLayoutManager(context)
                    allmp3eorec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    allmp3eorec.setItemAnimator(new DefaultItemAnimator());
                    allmp3eorec.setAdapter(AallMp3Adapters);
                }
                //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                else
                    if(sorting.equals("durationtxt"))
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
//                            public int compare(AllVideos s1, AllVideos s2) {
//                                Toast.makeText(AllMp3JavaActivity.this, "length is"+s1.getLength(), Toast.LENGTH_SHORT).show();
//                                Toast.makeText(AllMp3JavaActivity.this, "length is"+s2.getLength(), Toast.LENGTH_SHORT).show();
//                                // notice the cast to (Integer) to invoke compareTo
//                                return (String.valueOf(s1.getLength()).compareTo(String.valueOf(s2.getLength())));
//                            }
//
//                        });
                        Collections.sort(AllVideosList, new Comparator<AllVideos>() {
                            @Override
                            public int compare(AllVideos o1, AllVideos o2) {
                                return Integer.compare(o1.getLength(), o2.getLength());
                            }
                        });

//                                    AllVideosList = CustomList.stream().sorted().collect(Collectors.toCollection(Collectors.toList()));
                    }
                    AallMp3Adapters = new AllMp3Adapters(this, AllVideosList);
                    //  mLayoutManager = LinearLayoutManager(context)
                    allmp3eorec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
                    //    songrec.setLayoutManager(mLayoutManager)
                    allmp3eorec.setItemAnimator(new DefaultItemAnimator());
                    allmp3eorec.setAdapter(AallMp3Adapters);
                }


//            }
            } else {
                Toast.makeText(this, "Invalid Directory", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void Setallviews() {
        allmp3eorec = findViewById(R.id.allmp3eorec);
        moreopt = findViewById(R.id.moreopt);
        hidemp3controls = findViewById(R.id.hidemp3controls);
        backsongname = findViewById(R.id.exo_position);
        backplaypause = findViewById(R.id.backplaypause);
        videoonly = findViewById(R.id.videoonly);
        mp3only = findViewById(R.id.mp3only);
        searchmemp3 = findViewById(R.id.searchmemp3);
        song_namewa = findViewById(R.id.song_namewa);
        videoasmp3 = findViewById(R.id.videoasmp3);
        searchsongs = findViewById(R.id.searchsongs);

        videoonly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                allmp3eorec.setVisibility(View.GONE);
                videoasmp3.setVisibility(View.VISIBLE);
            }
        });
        mp3only.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoasmp3.setVisibility(View.GONE);
                allmp3eorec.setVisibility(View.VISIBLE);
            }
        });
        searchsongs.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        moreopt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(AllMp3JavaActivity.this);
                AlertDialog alertDialog = new AlertDialog.Builder(AllMp3JavaActivity.this).create();
                LayoutInflater inflater = getLayoutInflater();
                alertDialog.setTitle("Please select one");


                View dialogLayout = inflater.inflate(R.layout.filterdialog, null);
//                alertDialog.
//                alertDialog = builder.create();
                TextView sortatoz = dialogLayout.findViewById(R.id.sortatoz);
                TextView artisttxt = dialogLayout.findViewById(R.id.artisttxt);
                TextView albumtxt = dialogLayout.findViewById(R.id.albumtxt);
                TextView yeartxt = dialogLayout.findViewById(R.id.yeartxt);
                TextView ztoatxt = dialogLayout.findViewById(R.id.ztoatxt);
                TextView durationtxt = dialogLayout.findViewById(R.id.durationtxt);
                searchmemp3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(searchsongs.getVisibility() == View.GONE)
                        {
                            searchsongs.setVisibility(View.VISIBLE);
                        }
                        else
                            {
                            searchsongs.setVisibility(View.GONE);
                        }
                    }
                });

                //+++++++++++++++++++++++++++++++FOr ATOZ++++++++++++++++++++++++++
                sortatoz.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllVideosList.clear();
                        Filtertsongwith("sortatoz");
                        alertDialog.dismiss();
                    }
                });
                //+++++++++++++++++++++++++++++FOR DURATION+++++++++++++++++++++++++++++
                durationtxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllVideosList.clear();
                        Filtertsongwith("durationtxt");
                        alertDialog.dismiss();
//                        finish();
                    }
                });
                //+++++++++++++++++++++++++++++FOR ZTOA+++++++++++++++++++++++++++++
                ztoatxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllVideosList.clear();
                        alertDialog.dismiss();
                        Filtertsongwith("ztoatxt");

//                        finish();
                    }
                });
                //+++++++++++++++++++++++++++++FOR ARTIST+++++++++++++++++++++++++++++
                artisttxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllVideosList.clear();
                        alertDialog.dismiss();
                        Filtertsongwith("artisttxt");

//                        finish();
                    }
                });
                //+++++++++++++++++++++++++++++FOR YEAR+++++++++++++++++++++++++++++
                yeartxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllVideosList.clear();
                        alertDialog.dismiss();
                        Filtertsongwith("yeartxt");

//                        finish();
                    }
                });
                //+++++++++++++++++++++++++++++FOR ALBUM+++++++++++++++++++++++++++++
                albumtxt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AllVideosList.clear();
                        alertDialog.dismiss();
                        Filtertsongwith("albumtxt");

//                        finish();
                    }
                });
                alertDialog.setView(dialogLayout);
                alertDialog.show();


            }
        });
    }

    @Override
    protected void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMp3BroadcastRecevier);
        super.onDestroy();
    }
}
