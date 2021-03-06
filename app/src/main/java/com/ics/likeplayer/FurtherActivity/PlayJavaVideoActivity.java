package com.ics.likeplayer.FurtherActivity;
import android.app.Dialog;
import android.app.PictureInPictureParams;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.audiofx.Equalizer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;

import com.bullhead.equalizer.DialogEqualizerFragment;
import com.bullhead.equalizer.EqualizerFragment;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.hmomeni.verticalslider.VerticalSlider;
import com.ics.likeplayer.Database.Model.Database_players_play;
import com.ics.likeplayer.Model.AllVideos;
import com.ics.likeplayer.R;
import com.ics.likeplayer.ScreenshotManager;
import com.marcoscg.dialogsheet.DialogSheet;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


import static androidx.core.view.ViewCompat.LAYER_TYPE_HARDWARE;

public class PlayJavaVideoActivity extends AppCompatActivity {
    public SimpleExoPlayer simpleExoplayer;
    public PlayerView vidview;
    public PlaybackControlView controls;
    public LinearLayout hideli;
    public LinearLayout pipmode;
    public LinearLayout mainli;
    public ImageView imghideshow;
    public ImageView screenshot;
    public ImageView sleep_timer_btn;
    public TextView slevidname;
    public TextView exo_position;
    public TextView exo_duration;
    public LinearLayout backplayimg;
    ImageView nextthedamn;
    ImageView go_previous;
    public  File _videofile;
    //+++++++++++++++++++++++++++++++++++++++Windows postions for player for repeat and back play+++++++++++++
    int currentwindows;
    long currentwindowspositions;
    //+++++++++++++++++++++++++++++++++++++++++++++++GET PLAYLIST++++++++++++++++++++++++++++++
    ArrayList<Database_players_play> database_players_playArrayList = new ArrayList<>();
    ArrayList<AllVideos> _players_playArrayList = new ArrayList<>();
    int Playlist_position = 0;
    String Playlist_song_url;
    int play_fromwhere = 0;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++FOr timer++++++++++++++++
    int _1_min;
    int brightnesscount = 0;
    //++++++++++++++++++++++++++++++++++++For MEnu Options+++++++++++++++
    LinearLayout moreplayoptionsli;
//+++++++++++++++++++++++++++++++++For Variables++++++++++++++++++++++++
    public int REQUEST_ID = 1;
    public long videoPosition = 0;
    public int myVericalbrigrtness = 100;
    public Boolean StoporNot = false;
    public Boolean LockORNot = false;
    public Boolean ScreenLockORNot = false;
    public Context context;
    public ImageView fullthedamn;
//+++++++++++++++++++++++++++++++++++++++All Controls+++++++++++++++++++++++++++++++++++++++++++++++++
    ImageView ReverseBtn;
    ImageView nightmodeimg,abrepeatimg;
    ImageView NextBtn;
    ImageView FastForwardBtn;
    ImageView BackFastForwardBtn;
    ImageView RepeatBtn;
    ImageView Img_lockscreen;
    ImageView Img_lockscreen_hide;
    int clicks = 1;
    Boolean greysacle = false;
    boolean ppplaybackState;
    ImageView repeat_modes;
    //+++++++++++++++++++++++++++++++++++++++FOR GESTURES+++++++++++++++++
    Runnable r,r2;
    Handler mHandler;
    Handler mHandler2;
    private float brightness = (float) 225;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
    //++++++++++++++++++++++++++++++++++++++For Current video loop+++++++++++++++++++
    long Currenttimeseek;
    Handler seekdownHandler;
    Runnable seekrunnable;
    int repeat_mode_int = 0;
    long positionneedrepeata =-1,positionneedrepeatb=-1;
    //+++++++++++++++++++++++++++++++++++++++++++++++++++++
    //Window object, that will store a reference to the current window
    private Window window;
    private String myvideo;
    private ImageView PlaynPauseBTn;
    private ImageView img_rotate;
    private VerticalSlider verticalSlider,brightverticalSlider;
    TextView volprogressText;
    ImageView greyscalebtn;
    //+++++++++++++++++++++++++++++++++++++++End+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    private PictureInPictureParams.Builder mPictureInPictureParamsBuilder;
    private com.google.android.material.appbar.AppBarLayout tootwa;
    private Thread thread;
    private AudioManager audio;
    //    private TouchTypeDetector.TouchTypListener touchTypListener;
    private GestureDetectorCompat mvolumeDetector;
    private GestureDetectorCompat mvbrigtnessDetector;
    LinearLayout volumeview,brigtnessview;
    private com.google.android.exoplayer2.ui.DefaultTimeBar exo_progress;
    private TextView brightprogressText;
    private TextView timer_counttxt;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++Audio to video change+++++++++++++++++++++++
    CountDownTimer countDownTimersleep =null;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++++++
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        try {
            mHandler.removeCallbacks(r);
            mHandler2.removeCallbacks(r2);
            seekdownHandler.removeCallbacks(seekrunnable);
            mHandler.removeCallbacksAndMessages(null);
            seekdownHandler.removeCallbacksAndMessages(null);
            mHandler2.removeCallbacksAndMessages(null);
        }catch (Exception e)
        {
            e.printStackTrace();
        }

        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_video);
        super.onCreate(savedInstanceState);
        context = PlayJavaVideoActivity.this;
        //++++++++++++++++++++++++++++++++++++++++++++++++++Retrive the PLAYLIST++++++++++++++++++++

        //+++++++++++++++++++++++++++++++++++++
        //Get the content resolver
        cResolver = getContentResolver();
        window = getWindow();
        mvolumeDetector = new GestureDetectorCompat(this, new MyVolumeGestureListener());
        mvbrigtnessDetector = new GestureDetectorCompat(this, new MyBrightnessGestureListener());
        InitializeEverything();
        moreplayoptionsli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Creating the instance of PopupMenu
                PopupMenu popup = new PopupMenu(PlayJavaVideoActivity.this, moreplayoptionsli);
                //Inflating the Popup using xml file
                popup.getMenuInflater().inflate(R.menu.play_menu_full, popup.getMenu());

                //registering popup with OnMenuItemClickListener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
//                        Toast.makeText(PlayJavaVideoActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                        if(item.getItemId() != R.id.add_sub_titls) {
                            DialogEqualizerFragment fragment = DialogEqualizerFragment.newBuilder()
                                    .setAudioSessionId(simpleExoplayer.getAudioSessionId())
                                    .themeColor(ContextCompat.getColor(PlayJavaVideoActivity.this, R.color.color_black))
                                    .textColor(ContextCompat.getColor(PlayJavaVideoActivity.this, android.R.color.holo_blue_dark))
                                    .accentAlpha(ContextCompat.getColor(PlayJavaVideoActivity.this, android.R.color.holo_blue_light))
                                    .darkColor(ContextCompat.getColor(PlayJavaVideoActivity.this, android.R.color.holo_orange_light))
                                    .setAccentColor(ContextCompat.getColor(PlayJavaVideoActivity.this, android.R.color.holo_red_dark))
                                    .build();
                            fragment.show(getSupportFragmentManager(), "eq");
                        }else {
                            DialogSheet dialogSheet = new DialogSheet(v.getContext());
                            dialogSheet.setTitle("Add Subtitle Url")
                                    .setMessage("")
                                    .setColoredNavigationBar(true)
                                    .setCancelable(true)
                                    .setBackgroundColor(Color.WHITE) // Your custom background color
                                    .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                                    .show();
                            dialogSheet.setView(R.layout.add_subtitle_url);
                        }
                        return true;
                    }
                });

                popup.show();//showing popup menu
            }
        });
        if(!getIntent().toString().isEmpty()) {
            try {
                database_players_playArrayList = (ArrayList<Database_players_play>) getIntent().getSerializableExtra("playplaylist");
                Playlist_song_url = database_players_playArrayList.get(0).getSong_url();
                play_fromwhere =1;
                InitializePlayer();
                InitializePlayerControls();
            }catch (Exception e)
            {
                _players_playArrayList = (ArrayList<AllVideos>) getIntent().getSerializableExtra("playplaylist");
                Playlist_song_url = _players_playArrayList.get(0).getPath();
                InitializePlayer();
                play_fromwhere =2;
                InitializePlayerControls();
                e.printStackTrace();
            }
        }


        imghideshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hideli.getVisibility() == View.VISIBLE)
                {
                    hideli.setVisibility(View.GONE);
            }else {
                    hideli.setVisibility(View.VISIBLE );
                }
            }
        });

        simpleExoplayer.addListener(new Player.DefaultEventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {
//                Toast.makeText(context, "timeline touch"+timeline, Toast.LENGTH_SHORT).show();
                super.onTimelineChanged(timeline, manifest, reason);
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {
//                Toast.makeText(context, "is Loading", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                Toast.makeText(context, "is "+playbackState, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {
//                Toast.makeText(context, "is "+isPlaying, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
            @Override
            public void onSeekProcessed() {
//                Toast.makeText(context, "my currentwindows is"+currentwindows, Toast.LENGTH_SHORT).show();
//                Toast.makeText(context, "my currentwindowspositions is"+currentwindowspositions, Toast.LENGTH_SHORT).show();
                currentwindows = simpleExoplayer.getCurrentWindowIndex();
                currentwindowspositions = simpleExoplayer.getCurrentPosition();


            }

        });
        if(myVericalbrigrtness == 100)
        {
            myVericalbrigrtness = 100;
            brightness = 225;
            brightverticalSlider.setProgress(100);
            ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int) 225);
        }
//        if(checkSystemWritePermission()){

        try {
            // To handle the auto
            Settings.System.putInt(cResolver,
                    Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
            //Get the current system brightness
            brightness = Settings.System.getInt(cResolver, Settings.System.SCREEN_BRIGHTNESS);
        } catch (Settings.SettingNotFoundException e) {
            //Throw an error case it couldn't be retrieved
            Log.e("Error", "Cannot access system brightness");
            e.printStackTrace();
        }
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++FOR GESTURES++++++++++++++++++++++++++++++++++++++++++++++++++++++
        brigtnessview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                Toast.makeText(context, "brigtnessview touch", Toast.LENGTH_SHORT).show();
                try {
                    PlayJavaVideoActivity.this.mvbrigtnessDetector.onTouchEvent(event);
                }catch (Exception e)
                {
                    if(myVericalbrigrtness <=0)
                    {

                        brightverticalSlider.setProgress(0);
                    }else  if(myVericalbrigrtness >=100)
                    {
                        brightverticalSlider.setProgress(100);
                    }
                }
                return true;
            }
        });

        volumeview.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Toast.makeText(context, "volumeview touch", Toast.LENGTH_SHORT).show();
                PlayJavaVideoActivity.this.mvolumeDetector.onTouchEvent(event);
                return true;
            }
        });
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++FOR BAck Ground play++++++++++++++++++++++++++++
        backplayimg.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String sx = _videofile.getAbsolutePath();
                Intent intent = new Intent(v.getContext() ,AllVideoActivity.class);
                intent.putExtra("filepath" , sx);
                intent.putExtra("dirpath" , getIntent().getStringExtra("dirpath"));
                intent.putExtra("currentwindow" ,simpleExoplayer.getCurrentPosition() );
                intent.putExtra("currentposition" ,exo_position.getText());
                intent.putExtra("endposition" ,exo_duration.getText());
                intent.putExtra("_videofile" ,_videofile.getName() );
                startActivity(intent);
                simpleExoplayer.stop();
                simpleExoplayer.release();
//                finish();

//            AudioExtractor.Companion.with(v.getContext()).setFile(_videofile)//video FIle
//                .setOutputFileName(sx)
//                    .setOutputFileName("LikePlayeraudio_" + System.currentTimeMillis() + ".mp3")
//                    .setCallback(new FFMpegCallback() {
//                        @Override
//                        public void onProgress(@NotNull String progress) {
//                            Toast.makeText(context, "onProgress", Toast.LENGTH_SHORT).show();
//
//                        }
//                        @Override
//                        public void onSuccess(@NotNull File convertedFile, @NotNull String type) {
//                            Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFailure(@NotNull Exception error) {
//                            Toast.makeText(context, "onFailure", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onNotAvailable(@NotNull Exception error) {
//                            Toast.makeText(context, "onNotAvailable", Toast.LENGTH_SHORT).show();
//                        }
//
//                        @Override
//                        public void onFinish() {
//                            if(new File(_videofile.getAbsolutePath() +""+"LikePlayeraudio" + System.currentTimeMillis() + ".mp3").exists())
//                            {
//                                Toast.makeText(context, "onFinish", Toast.LENGTH_SHORT).show();
//                            }else {
//                                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
//                            }
//
//                        }
//                    }).extract();
//                if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    // Do something for lollipop and above versions
//                        Toast.makeText(context, "Trying with java", Toast.LENGTH_LONG).show();
//                        String audiofileplace =  sx+"like.mp3";
//                        simpleExoplayer.stop();

//                }

            }
        });        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++FOR Volume hide+++++++++++++++++++++++++++++++++++++++++++++++++++++
        mHandler = new Handler();
        r = new Runnable() {
            public void run() {
                Log.e("i volume ma", "in");
                if (verticalSlider.getVisibility() == View.VISIBLE && brightprogressText.getVisibility() == View.VISIBLE) {
                    verticalSlider.setVisibility(View.INVISIBLE);
                    volprogressText.setVisibility(View.INVISIBLE);
//                    Sensey.getInstance().stopTouchTypeDetection();
                }
                mHandler.postDelayed(this, 3000);
            }
        };
        mHandler.postDelayed(r, 3000);
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++For Brightness+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        mHandler2 = new Handler();
        r2 = new Runnable() {
            public void run() {
                Log.e("i bright ma", "in");
                if (brightverticalSlider.getVisibility() == View.VISIBLE) {
                    brightverticalSlider.setVisibility(View.INVISIBLE);
                    brightprogressText.setVisibility(View.INVISIBLE);
//                    Sensey.getInstance().stopTouchTypeDetection();
                }
                mHandler2.postDelayed(this, 3000);
            }
        };
        mHandler2.postDelayed(r2, 3000);
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
    }

//    private void ExtractAVideo(String sx) {
////        File originalVideo = new File(sx);
////        String filename = originalVideo.getAbsolutePath() + "Likesayshi" + ".mp3";
////        File copy = new File(filename);
////        int i = 0;
////        while (copy.exists()) {
////            copy = new File(String.format(Locale.getDefault(), filename));
////            i++;
////        }
////        try {
////            copy.createNewFile();
////            String cmd = "-y -v debug -i " + filename + " -r 15 -vf scale=w=320:h=320:force_original_aspect_ratio=increase -threads 2 -gifflags +transdiff -y " + copy.getPath();
////            String[] command = cmd.split(" ");
////            File finalCopy = copy;
////
////            fFmpeg.execute(command, new FFmpegExecuteResponseHandler() {
////                @Override
////                public void onSuccess(String message) {
////                    Log.i("FFmpeg", message);
////                }
////                @Override
////                public void onProgress(String message) {
////                    String[] strings = message.split("\n");
////                    for (String string : strings) {
////                        Log.i("FFmpeg", string);
////                    }
////                }
////                @Override
////                public void onFailure(String message) {
////                    String[] strings = message.split("\n");
////                    for (String string : strings) {
////                        Log.e("FFmpeg", string);
////                    }
////                }
////                @Override
////                public void onStart() {
////                    Log.i("FFmpeg", "on start");
////                }
////                @Override
////                public void onFinish() {
////                    Log.i("FFmpeg", "on finish");
////                }
////            });
////        } catch (IOException e) {
////            e.printStackTrace();
////        } catch (FFmpegCommandAlreadyRunningException e) {
////            e.printStackTrace();
////        }
//        // ffmpeg -i source_video.avi -vn -ar 44100 -ac 2 -ab 192 -f mp3 sound.mp3
//        String[] cmd =
//                new String[]{"-y", "-i", sx, "-vn", "-ar", "44100", "-c:a",
//                        "libmp3lame", "-f", "mp3", sx+"like.mp3"};
//        try {
//            FFmpeg.getInstance(PlayJavaVideoActivity.this).execute(cmd, new FFmpegExecuteResponseHandler() {
//                @Override
//                public void onSuccess(String message) {
//                    Toast.makeText(context,"onSuccess",Toast.LENGTH_LONG).show();
//                    String audiofileplace =  sx+"like.mp3";
//                    simpleExoplayer.stop();
//                    playSonginmp3(audiofileplace);
//                }
//
//                @Override
//                public void onProgress(String message) {
//                    Toast.makeText(context,"onProgress",Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onFailure(String message) {
//                    Toast.makeText(context,"onFailure",Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onStart() {
//                    Toast.makeText(context,"onStart",Toast.LENGTH_LONG).show();
//                }
//
//                @Override
//                public void onFinish() {
//                    if(new File(sx+"like.mp3").exists()) {
//                        Toast.makeText(context, "Finished", Toast.LENGTH_LONG).show();
//                        System.exit(0);
//                        SetOnNotification(simpleExoplayer.getPlaybackState(),sx+"like.mp3");
//                    }else {
//                        Toast.makeText(context, "Failed to do so", Toast.LENGTH_LONG).show();
//                        String audiofileplace =  sx+"like.mp3";
//                        simpleExoplayer.stop();
//                        try {
//                            convertToAudio(audiofileplace);
//                        } catch (EncoderException e) {
//                            Toast.makeText(context, "Java also failed", Toast.LENGTH_SHORT).show();
//                            e.printStackTrace();
//                        }
//                    }
//                }
//            });
//        } catch (FFmpegCommandAlreadyRunningException e) {
//            e.printStackTrace();
//        }
//    }
//++++++++++++++++++++++++++IF fails++++++++++++++++++++++++
//    private void convertToAudio(String audiofileplace) throws EncoderException {
//        File target = new File(audiofileplace);
//        AudioAttributes audio = new AudioAttributes();
//        audio.setCodec("libmp3lame");
//        audio.setBitRate(new Integer(128000));
//        audio.setChannels(new Integer(2));
//        audio.setSamplingRate(new Integer(44100));
//        EncodingAttributes attrs = new EncodingAttributes();
//        attrs.setFormat("mp3");
//        attrs.setAudioAttributes(audio);
//        Encoder encoder = new Encoder();
//        if(target.exists()) {
//            Toast.makeText(context, "Yes it's exist", Toast.LENGTH_SHORT).show();
//            encoder.encode(_videofile, target, attrs);
//
//        }else {
//
//            encoder.encode(_videofile.getAbsoluteFile(), target, attrs, new EncoderProgressListener() {
//                @Override
//                public void sourceInfo(MultimediaInfo multimediaInfo) {
//                    Toast.makeText(context, " multimediaInfo Failed it does not", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void progress(int i) {
//                    Toast.makeText(context, " i Failed it does not", Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void message(String s) {
//                    Toast.makeText(context, " i Failed "+s.toString() ,Toast.LENGTH_SHORT).show();
//                }
//            });
//
//        }
//        Toast.makeText(context, "Success ", Toast.LENGTH_SHORT).show();
//
//    }
//++++++++++++++++++++++++++++++++++++++++++++++
//    private void playSonginmp3(String audiofileplace) {
//        // Play song
//        try {
//            mp.reset();
//            mp.setDataSource(audiofileplace);
//            mp.prepare();
//            mp.start();
//            // Displaying Song title
//            String songTitle = "Backgroundwa";
//
//        } catch (IllegalArgumentException e) {
//            e.printStackTrace();
//        } catch (IllegalStateException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }


    private Boolean checkSystemWritePermission() {
        boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.d("TAG", "Can Write Settings: " + retVal);
            if (retVal) {
                Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();
                openAndroidPermissionsMenu();
            }
        }
        return retVal;
    }
    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData(Uri.parse("package:" + this.getPackageName()));
        startActivity(intent);
    }

    //    }
    private void InitializePlayerControls()
    {
        PlaynPauseBTn = findViewById(com.ics.likeplayer.R.id.playnpause);
//        PlaynPauseBTn = findViewById(R.id.playnpause);
        ReverseBtn = findViewById(R.id.exo_prev);
        nightmodeimg = findViewById(R.id.nightmodeimg);
        abrepeatimg = findViewById(R.id.abrepeatimg);
        NextBtn = findViewById(R.id.exo_next);
        FastForwardBtn = findViewById(R.id.exo_ffwd);
        nextthedamn = findViewById(R.id.nextthedamn);
        fullthedamn = findViewById(R.id.fullthedamn);
        brightverticalSlider = findViewById(R.id.brightverticalSlider);
        timer_counttxt = findViewById(R.id.timer_counttxt);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
        go_previous = findViewById(R.id.go_previous);
        verticalSlider = findViewById(R.id.verticalSlider);
//        VolumeBtn = findViewById(R.id.exo_);
        BackFastForwardBtn = findViewById(R.id.exo_rew);
        sleep_timer_btn = findViewById(R.id.sleep_timer_btn);
        exo_duration = findViewById(R.id.exo_duration);
        tootwa = findViewById(R.id.tootwa);
        Img_lockscreen = findViewById(com.ics.likeplayer.R.id.img_lockscreen);
        img_rotate = findViewById(com.ics.likeplayer.R.id.img_rotate);
        Img_lockscreen_hide = findViewById(com.ics.likeplayer.R.id.img_lockscreen_hide);
        exo_progress = findViewById(com.ics.likeplayer.R.id.exo_progress);
        volprogressText = findViewById(com.ics.likeplayer.R.id.volprogressText);
        brightprogressText = findViewById(com.ics.likeplayer.R.id.brightprogressText);
        greyscalebtn = findViewById(com.ics.likeplayer.R.id.greyscalebtn);
        backplayimg = findViewById(com.ics.likeplayer.R.id.backplayimg);
        exo_position = findViewById(R.id.exo_position);
        repeat_modes = findViewById(R.id.repeat_modes);
        // get the gesture detector
        //++++++++++++++++++++++++++++++++++++++++++++++++MAin Functions+++++++++++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++++++++++++++++++++++++++++++++++++++FOR PREIVIOUS ITEM++++++++++++++++++++++++++++++++++++++++++++
        //++++++++++++++++++++++++++++++++++For Repeate mode++++++++++++++++++++++++++++++++++++++++++
        repeat_modes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( repeat_mode_int ==1)
                {
                    Toast.makeText(context, "Repeat is on", Toast.LENGTH_SHORT).show();
                    repeat_mode_int++;

                }else if(repeat_mode_int ==2){
                    Toast.makeText(context, "Shuffle is on", Toast.LENGTH_SHORT).show();
                    repeat_mode_int++;
                }else  if(repeat_mode_int ==3)
                {
                    Toast.makeText(context, "Single Repeat is on", Toast.LENGTH_SHORT).show();
                    repeat_mode_int=0;
                }else {
                    Toast.makeText(context, "Normal Mode is on", Toast.LENGTH_SHORT).show();
                    repeat_mode_int++;
                }
            }
        });
        //++++++++++++++++++++++
        go_previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Playlist_position--;

                if(Playlist_position !=-1)
                {
                    if(play_fromwhere ==1) {
                        simpleExoplayer.release();
                        simpleExoplayer.stop();
                        Playlist_song_url = database_players_playArrayList.get(Playlist_position).getSong_url();
                        InitializePlayer();
                    }else {
                        simpleExoplayer.release();
                        simpleExoplayer.stop();
                        Playlist_song_url = _players_playArrayList.get(Playlist_position).getPath();
                        InitializePlayer();
                    }

                }else {
                    try {
                        simpleExoplayer.release();
                        simpleExoplayer.stop();
                        Playlist_position++;
                        if(play_fromwhere ==1) {
                            Playlist_song_url = database_players_playArrayList.get(Playlist_position).getSong_url();
                        }else {
                            Playlist_song_url = _players_playArrayList.get(Playlist_position).getPath();
                        }
                        InitializePlayer();
                    }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                }

            }
        });

        //+++++++++++++++++++++++++++++++++++++++++++++
        //+++++++++++++++++++++++++++++++++++++++++++++++For NExt item+++++++++++++++++++++++++
        nextthedamn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(play_fromwhere ==1) {
                        if (!database_players_playArrayList.get(Playlist_position).getSong_url().isEmpty()) {

                            Playlist_position++;
                            Playlist_song_url = database_players_playArrayList.get(Playlist_position).getSong_url();
                            simpleExoplayer.release();
                            simpleExoplayer.stop();
                            InitializePlayer();
//                        InitializePlayerControls();
                        }
                    }else {
                        if (!_players_playArrayList.get(Playlist_position).getPath().isEmpty()) {

                            Playlist_position++;
                            Playlist_song_url = _players_playArrayList.get(Playlist_position).getPath();
                            simpleExoplayer.release();
                            simpleExoplayer.stop();
                            InitializePlayer();
//                        InitializePlayerControls();
                        }
                    }
                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(context, "You are on the last in playlist", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //+++++++++++++++++++
        //++++++++++++++++++++++++++++++++++++++++++++FOR AUTO REPEAT+++++++++++++++++++++++++++++++

        abrepeatimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//             currentwindowspositions=  simpleExoplayer.getCurrentPosition();

                final Dialog dialog = new Dialog(v.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setCancelable(true);
//                dialog.setContentView(R.layout.seektorangedialog);\
                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.seektorangedialog, null);
                Drawable d = new ColorDrawable(Color.BLACK);
                d.setAlpha(130);
                //++++++++++++++++++++++++++++++++++++++++++++Time loop for a++++++++++++++++++
                ImageView cancel_act = view.findViewById(R.id.cancel_act_img);
                cancel_act.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button startaseekbtn = view.findViewById(R.id.startaseekbtn);
                TextView timeloopa = view.findViewById(R.id.timeloopa);
                startaseekbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        positionneedrepeata =simpleExoplayer.getCurrentPosition();
                        int positiona = (int) ((simpleExoplayer.getCurrentPosition() * 100) / simpleExoplayer.getDuration());
//                        Toast.makeText(PlayJavaVideoActivity.this, "value is" + positiona, Toast.LENGTH_SHORT).show();
                        long value_of_point = ((TimeUnit.MILLISECONDS.toSeconds(Currenttimeseek) )%60);
//                            value_of_point= TimeUnit.SECONDS.toMinutes(value_of_point);
                            timeloopa.setText(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(Currenttimeseek)) +":"+ value_of_point );

                    }
                });
                //++++++++++++++++++For time loop B++++++++++++++
                Button startbseekbtn = view.findViewById(R.id.startbseekbtn);
                TextView timeloopb = view.findViewById(R.id.timeloopb);
                startbseekbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(positionneedrepeata !=-1) {
                            positionneedrepeatb = -1;
                            int positiona = (int) ((simpleExoplayer.getCurrentPosition() * 100) / simpleExoplayer.getDuration());
//                            Toast.makeText(PlayJavaVideoActivity.this, "value is" + positiona, Toast.LENGTH_SHORT).show();
//                        timeloopb.setText(String.valueOf(positiona));
                            positionneedrepeatb = simpleExoplayer.getCurrentPosition();
                            long value_of_point = ((TimeUnit.MILLISECONDS.toSeconds(Currenttimeseek)) % 60);
//                        value_of_point= TimeUnit.SECONDS.toMinutes(value_of_point);
                            timeloopb.setText(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(Currenttimeseek)) + ":" + value_of_point);
                            try {
                                if (!timeloopa.getText().toString().isEmpty()) {
                                    simpleExoplayer.seekTo(positionneedrepeata);
                                    dialog.dismiss();
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }else {
                            Toast.makeText(PlayJavaVideoActivity.this, "Please select the A first", Toast.LENGTH_SHORT).show();
                        }
//                        timeloopb.setText(String.valueOf(TimeUnit.MILLISECONDS.toMinutes(Currenttimeseek)) +":"+TimeUnit.MILLISECONDS.toSeconds(Currenttimeseek));
                    }
                });
                //+++++++++++++++++++++++++
                // Setting dialogview
                Window window = dialog.getWindow();
                window.setGravity(Gravity.RIGHT);
                dialog.getWindow().setBackgroundDrawable(d);
                dialog.setContentView(view);
                dialog.show();
            }
        });
        //+++++++++++++++++++++++++++++++++++++++
        nightmodeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Working mode on", Toast.LENGTH_SHORT).show();
                //constrain the value of brightness
                if(brightnesscount ==0)
                {
                    ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
                    Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int) 0);
                    brightnesscount =1;
//                    Drawable drawable = new (R.drawable.nightmodeoff);
//                    nightmodeimg.setBackgroundResource(R.drawable.nightmodeoff);
                    nightmodeimg.setImageDrawable(getResources().getDrawable(R.drawable.nightmodeoff));
//                    nightmodeimg.setBackground(R.drawable.dialog_sheet_main_background);
                }else {
                    Toast.makeText(context, "Night mode off", Toast.LENGTH_SHORT).show();
                    ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
                    Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int) 255);
//                    Drawable drawable = new ColorDrawable(R.drawable.nightmode);
                    nightmodeimg.setImageDrawable(getResources().getDrawable(R.drawable.nightmode));
                    brightnesscount =0;
                }
//                if(brightness < 0) {
//                    brightness = 0;
//                    Toast.makeText(context, "Night mode on", Toast.LENGTH_SHORT).show();
//                }
//                else if(brightness > 255) {
//
//                    brightness = 255;
//                }
            }
        });
        PlaynPauseBTn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(simpleExoplayer !=null)
                {
                    if(ppplaybackState)
                    {
                        simpleExoplayer.setPlayWhenReady(false);
                        ppplaybackState =false;

                    }else {
                        simpleExoplayer.setPlayWhenReady(true);
                        ppplaybackState = true;

                    }
                }
            }
        });

        fullthedamn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clicks == 5) {
                    vidview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIT);
                    simpleExoplayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                    clicks = 1;
                } else if (clicks == 1) {
                    vidview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_WIDTH);
                    simpleExoplayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    clicks = clicks + 1;
                } else if (clicks == 2) {
                    vidview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FIXED_HEIGHT);
                    simpleExoplayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    clicks = clicks + 1;
                } else if (clicks == 3) {
                    vidview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);
                    simpleExoplayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT);
                    clicks = clicks + 1;
                } else if (clicks == 4) {
                    vidview.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_ZOOM);
                    simpleExoplayer.setVideoScalingMode(C.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                    clicks = clicks + 1;
                }
            }
        });
        screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takeScreenshot(vidview);
            }
        });
        img_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           if (ScreenLockORNot) {
               setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
               ScreenLockORNot = false;
            }
          else
         {
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                ScreenLockORNot = true;
                             }
                   }
               }
        );
        //+++++++++++++++++++++++++++++++++++++++++++For Sleep timer++++++++++++++++++++++++++++++++
        sleep_timer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    SleepTimerWay(v);
//                Settings.System.putInt(getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, 1000);

            }
        });

        //++++++++++++++++++++++++++++++
        Img_lockscreen_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainli.getVisibility() == View.VISIBLE && controls.getVisibility() == View.VISIBLE && Img_lockscreen_hide.getVisibility() == View.GONE &&
                        tootwa.getVisibility() == View.GONE) {
                    mainli.setVisibility(View.GONE);
                    controls.setVisibility(View.GONE);
                    Img_lockscreen_hide.setVisibility(View.VISIBLE);
                    tootwa.setVisibility(View.GONE);
                } else {
                    mainli.setVisibility(View.VISIBLE);
                    controls.setVisibility(View.VISIBLE);
                    tootwa.setVisibility(View.VISIBLE);
                    Img_lockscreen_hide.setVisibility(View.GONE);
                }
                LockORNot = false;
            }
        });

        Img_lockscreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mainli.getVisibility() == View.VISIBLE && controls.getVisibility() == View.VISIBLE && Img_lockscreen_hide.getVisibility() == View.GONE) {
                    mainli.setVisibility(View.GONE);
                    controls.setVisibility(View.GONE);
                    tootwa.setVisibility(View.GONE);
                    Img_lockscreen_hide.setVisibility(View.VISIBLE);
                } else {
                    mainli.setVisibility(View.VISIBLE);
                    controls.setVisibility(View.VISIBLE);
                    tootwa.setVisibility(View.VISIBLE);
                    Img_lockscreen_hide.setVisibility(View.GONE);
                }
                LockORNot = true;
            }
        });

        greyscalebtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(!greysacle) {
                    // Create a paint object with 0 saturation (black and white)
                    ColorMatrix cm = new ColorMatrix();
                    cm.setSaturation(0);
                    Paint greyscalePaint = new Paint();
                    greyscalePaint.setColorFilter(new ColorMatrixColorFilter(cm));
// Create a hardware layer with the greyscale paint
                    vidview.setLayerType(LAYER_TYPE_HARDWARE, greyscalePaint);
                    greysacle = true;
                }
                else
                    {
                    // Create a paint object with 0 saturation (black and white)
                    ColorMatrix cm = new ColorMatrix();
                    cm.setSaturation(1);
                    Paint greyscalePaint = new Paint();
                    greyscalePaint.setColorFilter(new ColorMatrixColorFilter(cm));
// Create a hardware layer with the greyscale paint
                    vidview.setLayerType(LAYER_TYPE_HARDWARE, greyscalePaint);
                    greysacle = false;
                }
                return false;
            }
        });
        //++++++++++++++++++++++++++++++++++++fdjg+++++++++++++++++++++++++++++++++
    }

    private void SleepTimerWay(View v) {
        Dialog dialog = new Dialog(v.getContext());
        dialog.setCancelable(true);
        View view = LayoutInflater.from(v.getContext()).inflate(R.layout.sleep_timer_xml, null);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        Drawable d = new ColorDrawable(Color.BLACK);
        d.setAlpha(130);
        dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(view);
        ImageView close_btn_timer = dialog.findViewById(R.id.close_btn_timer);
        TextView _30mins_txt = dialog.findViewById(R.id._30mins);
        TextView _45mins_txt = dialog.findViewById(R.id._45mins);
        TextView _60min_txt = dialog.findViewById(R.id._60min);
        TextView _15min_txt = dialog.findViewById(R.id._15min_txt);
        TextView offtimer = dialog.findViewById(R.id.offtimer);
        //+++++++++++++++++++++++++OFF timer++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        offtimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                timer_counttxt.setText("");
                timer_counttxt.setVisibility(View.GONE);
                sleep_timer_btn.setVisibility(View.VISIBLE);
                try {
                    countDownTimersleep.cancel();
                    timer_counttxt.setText("");
//                    countDownTimersleep=null;
                }catch (Exception e)
                {
                    Toast.makeText(PlayJavaVideoActivity.this, "timer is not set", Toast.LENGTH_SHORT).show();
                    timer_counttxt.setText("");
                    countDownTimersleep=null;
                    e.printStackTrace();
                }
            }
        });
        //++++++++++++++++++++++++++++++For 15 mins++++++++++++++++++++++++++++++++++++++
        _15min_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int __15_15min =  Integer.valueOf(1 * 60*1000);
                _1_min = 60;
                sleep_timer_btn.setVisibility(View.GONE);
                timer_counttxt.setVisibility(View.VISIBLE);
                dialog.dismiss();
                HandlerTimerWithHandler(v ,__15_15min ,_1_min ,countDownTimersleep);

            }
        });
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++30 mins++++++++++++++++++++++

        _30mins_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int __15_15min =  Integer.valueOf(30 * 60*1000);
                _1_min = 60;
                dialog.dismiss();
                HandlerTimerWithHandler(v ,__15_15min ,_1_min ,countDownTimersleep);

            }

        });

        //+++++++++++++++++++++++++++++++++++++++++++For 45 min+++++++++++++++++++++++++++
        _45mins_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int __15_15min =  Integer.valueOf(45 * 60*1000);
                _1_min = 60;
                sleep_timer_btn.setVisibility(View.GONE);
                timer_counttxt.setVisibility(View.VISIBLE);
//                try{
//                    countDownTimersleep.cancel();
////                    countDownTimersleep=null;
//                    timer_counttxt.setText("");
//                }catch (Exception e)
//                {
//                    Toast.makeText(PlayJavaVideoActivity.this, "This is first", Toast.LENGTH_SHORT).show();
//                    timer_counttxt.setText("");
//                    countDownTimersleep=null;
//
//                }
//                countDownTimersleep =  new CountDownTimer(__15_15min, 1000) {
//
//                    public void onTick(long millisUntilFinished) {
//                        if(_1_min ==0)
//                        {
//                            _1_min = 60;
//                        }
//                        timer_counttxt.setText(String.valueOf(String.valueOf((millisUntilFinished  /60 )/1000)+":"+ _1_min--));
//                        timer_counttxt.setTextColor(Color.GREEN);
//                        //here you can have your logic to set text to edittext
//                        dialog.dismiss();
//                    }
//
//                    public void onFinish() {
//                        sleep_timer_btn.setVisibility(View.VISIBLE);
//                        timer_counttxt.setVisibility(View.GONE);
//                        simpleExoplayer.setPlayWhenReady(false);
//                    }
//
//                }.start();
                dialog.dismiss();
                HandlerTimerWithHandler(v ,__15_15min ,_1_min ,countDownTimersleep);
            }
        });
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++60 min++++++++
        _60min_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int __15_15min =  Integer.valueOf(60 * 60*1000);
                _1_min = 60;
                sleep_timer_btn.setVisibility(View.GONE);
                timer_counttxt.setVisibility(View.VISIBLE);
//                try{
//                    countDownTimersleep.cancel();
//                    timer_counttxt.setText("");
//                    Toast.makeText(PlayJavaVideoActivity.this, "timer is not caceled", Toast.LENGTH_SHORT).show();
////                    countDownTimersleep=null;
//                }catch (Exception e)
//                {
//                    Toast.makeText(PlayJavaVideoActivity.this, "This is first", Toast.LENGTH_SHORT).show();
//                    timer_counttxt.setText("");
//                    countDownTimersleep=null;
////                    countDownTimersleep.notify();
//                }
//                countDownTimersleep =  new CountDownTimer(__15_15min, 1000) {
//
//                    public void onTick(long millisUntilFinished) {
//                        if(_1_min ==0)
//                        {
//                            _1_min = 60;
//                        }
//                        timer_counttxt.setText(String.valueOf(String.valueOf((millisUntilFinished  /60 )/1000)+":"+ _1_min--));
//                        timer_counttxt.setTextColor(Color.GREEN);
//                        //here you can have your logic to set text to edittext
//                        dialog.dismiss();
//                    }
//
//                    public void onFinish() {
//                        sleep_timer_btn.setVisibility(View.VISIBLE);
//                        timer_counttxt.setVisibility(View.GONE);
//                        simpleExoplayer.setPlayWhenReady(false);
//                    }
//
//                }.start();
                dialog.dismiss();
                HandlerTimerWithHandler(v ,__15_15min ,_1_min ,countDownTimersleep);
            }
        });

        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        timer_counttxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PlayJavaVideoActivity.this, "timer clicked", Toast.LENGTH_SHORT).show();
                SleepTimerWay(v);
//                Dialog dialog = new Dialog(v.getContext());
//                dialog.setCancelable(true);
//                View view = LayoutInflater.from(v.getContext()).inflate(R.layout.sleep_timer_xml, null);
////                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
////                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                Drawable d = new ColorDrawable(Color.BLACK);
//                d.setAlpha(130);
//                dialog.getWindow().setBackgroundDrawable(d);
//                dialog.setContentView(view);
//                ImageView close_btn_timer = dialog.findViewById(R.id.close_btn_timer);
//                TextView _15min_txt = dialog.findViewById(R.id._15min_txt);
//                dialog.show();
            }
        });

        close_btn_timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void HandlerTimerWithHandler(View v, int __15_15min, int _1_min, CountDownTimer countDownTimersleeps) {
//        sleepcountHandler  = new Handler();
//        sleepcountRunnable = new Runnable() {
//            @Override
//            public void run() {
//                sleepcountHandler.postDelayed(this, 1000);
//            }
//        };
//        sleepcountHandler.postDelayed(r, 1000);

        try{
            countDownTimersleep.cancel();
            countDownTimersleep =null;
//                    countDownTimersleep=null;
            timer_counttxt.setText("");
        }catch (Exception e)
        {
            Toast.makeText(PlayJavaVideoActivity.this, "This is first", Toast.LENGTH_SHORT).show();
            timer_counttxt.setText("");
//            countDownTimersleeps =null;
        }
        countDownTimersleep =  new CountDownTimer(__15_15min, 1000) {

            public void onTick(long millisUntilFinished) {
                if(PlayJavaVideoActivity.this._1_min ==0)
                {
                    PlayJavaVideoActivity.this._1_min = 60;
                }
                timer_counttxt.setText(String.valueOf(String.valueOf((millisUntilFinished  /60 )/1000)+":"+ PlayJavaVideoActivity.this._1_min--));
                timer_counttxt.setTextColor(Color.GREEN);
                //here you can have your logic to set text to edittext

            }

            public void onFinish() {
                sleep_timer_btn.setVisibility(View.VISIBLE);
                timer_counttxt.setVisibility(View.GONE);
                simpleExoplayer.setPlayWhenReady(false);
            }

        };
        countDownTimersleep.start();
    }

    private void takeScreenshot(PlayerView vidview) {
        ScreenshotManager.INSTANCE.requestScreenshotPermission(this, REQUEST_ID);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
//            Toast.makeText(this, "You done it well", Toast.LENGTH_LONG).show();

            // Hide the full-screen UI (controls, etc.) while in picture-in-picture mode.
        } else {
            if (mainli.getVisibility() == View.VISIBLE) {
                imghideshow.setRotation((-90));
                mainli.setVisibility(View.GONE);

            } else {
                imghideshow.setRotation((90));
                mainli.setVisibility(View.VISIBLE);
            }
            if (StoporNot) {
                simpleExoplayer.stop();
//                    finish()
            }
//                simpleExoplayer.stop()
//                finish()
            // Restore the full-screen UI.
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_ID) {
//            ScreenshotManager.INSTANCE.onActivityResult(resultCode, data);
            mainli.setVisibility(View.VISIBLE);
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            ScreenshotManager.INSTANCE.takeScreenshot(this, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitializeEverything() {
        vidview = findViewById(com.ics.likeplayer.R.id.simpleExoPlayerView);
        moreplayoptionsli = findViewById(com.ics.likeplayer.R.id.moreplayoptionsli);
        screenshot = findViewById(com.ics.likeplayer.R.id.screenshot);
        controls = findViewById(com.ics.likeplayer.R.id.controls);
        slevidname = findViewById(com.ics.likeplayer.R.id.slevidname);
        hideli = findViewById(com.ics.likeplayer.R.id.hideli);
        brigtnessview = findViewById(com.ics.likeplayer.R.id.brigtnessview);
        volumeview = findViewById(com.ics.likeplayer.R.id.volumeview);
        pipmode = findViewById(com.ics.likeplayer.R.id.pipmode);
        imghideshow = findViewById(com.ics.likeplayer.R.id.imghideshow);
        mainli = findViewById(com.ics.likeplayer.R.id.mainli);

    }

    private void InitializePlayer() {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(this,
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());
        prepareExoplayer();
    }

    private void prepareExoplayer() {
        vidview.setPlayer(simpleExoplayer);
        try {
            if (!database_players_playArrayList.isEmpty()) {
                myvideo =Playlist_song_url ;
//                Playlist_song_url= myvideo;
            }
        }catch (Exception e)
        {
            myvideo = getIntent().getStringExtra("vidurl");
            e.printStackTrace();
        }
        _videofile = new File(myvideo);
        Uri uri = Uri.parse(myvideo);
//        MediaSource mediaSource = buildMediaSource(uri);
        MediaSource mediaSource = buildMediaSource(uri);
        simpleExoplayer.prepare(mediaSource, true, true);
        simpleExoplayer.setPlayWhenReady(true);
        controls.setPlayer(simpleExoplayer);
        pipmode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enterPIPMode();

            }
        });
        vidview.setControllerShowTimeoutMs(0);

        simpleExoplayer.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//                if (playWhenReady && playbackState == Player.STATE_READY) {
//                    playbackState =1;
//                    // media actually playing
//                } else if (playWhenReady) {
//                    playbackState =2;
//                    // might be idle (plays after prepare()),
//                    // buffering (plays when data available)
//                    // or ended (plays when seek away from end)
//                } else {
//                    playbackState =3;
//                    // player paused in any state
//                }
                if (playbackState == Player.STATE_BUFFERING) {

                } else
                if (playbackState == Player.STATE_READY) {
                    Toast.makeText(context, "Player is ready", Toast.LENGTH_SHORT).show();
                    StartTheTimer();
                } else
                if (playbackState == Player.STATE_ENDED) {

                    Toast.makeText(getApplicationContext(), "Your video has been ended", Toast.LENGTH_SHORT).show();

                    if(!Playlist_song_url.isEmpty())
                    {
                        Playlist_position++;
                        simpleExoplayer.release();
                        simpleExoplayer.stop();
                        try {
                            if(repeat_mode_int ==1)
                            {

                                if(Playlist_position !=-1) {
                                    if(play_fromwhere ==1) {
                                        if (Playlist_position == database_players_playArrayList.size()) {
                                            Playlist_song_url = database_players_playArrayList.get(0).getSong_url();
                                        }else {
                                            Playlist_song_url = database_players_playArrayList.get(Playlist_position).getSong_url();
                                        }

                                    }else {
                                        if (Playlist_position == _players_playArrayList.size()) {
                                            Playlist_song_url = _players_playArrayList.get(0).getPath();
                                        }else {
                                            Playlist_song_url = _players_playArrayList.get(Playlist_position).getPath();
                                        }
                                    }
                                }
                            }else if(repeat_mode_int ==2) {
                                if(Playlist_position != database_players_playArrayList.size()) {
                                    Playlist_song_url = database_players_playArrayList.get(Playlist_position+2).getSong_url();
                                }
                                else if(repeat_mode_int ==3) {
                                    if (Playlist_position != database_players_playArrayList.size()) {
                                        Playlist_song_url = database_players_playArrayList.get(Playlist_position + 2).getSong_url();
                                    }
                                }
                                else
                                {
                                    if(Playlist_position !=-1) {
                                        Playlist_song_url = database_players_playArrayList.get(Playlist_position--).getSong_url();
                                    }
                                }
                            }else {
                                if(Playlist_position == database_players_playArrayList.size())
                                {
                                    Playlist_song_url = database_players_playArrayList.get(0).getSong_url();
                                }
                            }
                            if(play_fromwhere ==1) {
                                Playlist_song_url = database_players_playArrayList.get(Playlist_position).getSong_url();
                            }else {
                                Playlist_song_url = _players_playArrayList.get(Playlist_position).getPath();
                            }
                            InitializePlayer();
                        }catch (Exception e)
                        {
                            Toast.makeText(context, "Playlist ended", Toast.LENGTH_SHORT).show();

                            e.printStackTrace();
                        }
                        InitializePlayer();
                    }
                }
            }
        });

    }

    private void StartTheTimer() {
        Toast.makeText(context, "Timer starting", Toast.LENGTH_SHORT).show();
        seekdownHandler = new Handler();
        seekrunnable = new Runnable() {
            public void run() {
                Log.e("seeking" , "running");
                Currenttimeseek = simpleExoplayer.getCurrentPosition();
                if(positionneedrepeatb != -1) {
                    if (Currenttimeseek > positionneedrepeatb) {
//                        Toast.makeText(context, "Matched", Toast.LENGTH_SHORT).show();
                        simpleExoplayer.seekTo(positionneedrepeata);
                    } else {
//                        Toast.makeText(context, "Not " + Currenttimeseek + " /" + positionneedrepeatb, Toast.LENGTH_SHORT).show();
                    }
                }
                seekdownHandler.postDelayed(this, 1000);
            }
        };
        seekdownHandler.postDelayed(seekrunnable, 1000);

    }

    private MediaSource buildMediaSource(Uri uris) {
        return new ExtractorMediaSource.Factory(
                new DefaultDataSourceFactory(PlayJavaVideoActivity.this, "Exoplayer-local")).
                createMediaSource(uris);
    }

    @Override
    protected void onUserLeaveHint() {
        Toast.makeText(this, "you are in onUserLeaveHint ", Toast.LENGTH_LONG).show();
        super.onUserLeaveHint();
        enterPIPMode();
    }

    private void enterPIPMode() {

        videoPosition = simpleExoplayer.getCurrentPosition();
        vidview.setUseController(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mainli.setVisibility(View.GONE);
            PictureInPictureParams.Builder params = new PictureInPictureParams.Builder();
            this.enterPictureInPictureMode(params.build());
            Toast.makeText(this, "you are if ", Toast.LENGTH_LONG).show();
//                if(mainli.visibility == View.GONE)
//                {
//                    mainli.visibility = View.VISIBLE
//                }else if(mainli.visibility == View.VISIBLE){
//                    mainli.visibility = View.GONE
//                }
        }

    }
    @Override
    protected void onPostResume() {
        Log.e("Post Resume", "called");
        super.onPostResume();
    }

    @Override
    protected void onStop() {
        Log.e("onStop", "called");
        StoporNot = true;

        super.onStop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        simpleExoplayer.stop();

    }

    private class MyVolumeGestureListener implements GestureDetector.OnGestureListener ,GestureDetector.OnDoubleTapListener{
        private static final String DEBUG_TAG = "VolGestures";
        @Override
        public boolean onSingleTapConfirmed(MotionEvent ex) {
//            System.gc();
            try {
                if (simpleExoplayer != null ) {
//                    simpleExoplayer.setPlayWhenReady(false);
//                    Constants.isMediaPlaying = false;
                    long bufferPosition = simpleExoplayer.getBufferedPosition();
                    long currentPosition = simpleExoplayer.getCurrentPosition();
                    if ((currentPosition - 2000) < bufferPosition - 10) {
                        simpleExoplayer.seekTo(simpleExoplayer.getCurrentWindowIndex(), currentPosition - 1000);
                        simpleExoplayer.setPlayWhenReady(true);
//                        simpleExoplayer.s
//                        SyncStateContract.Constants.isMediaPlaying = true;
                    }
                }
            } catch (Exception e) {
                Toast.makeText(PlayJavaVideoActivity.this ,"Error",Toast.LENGTH_LONG).show();
            }

//
            return false;
        }
        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

            if (e1.getY() < e2.getY())
            {
                Log.d(DEBUG_TAG, "Up to Down swipe performed");
                Toast.makeText(PlayJavaVideoActivity.this, "Scrolling up", Toast.LENGTH_SHORT).show();
                verticalSlider.setVisibility(View.VISIBLE);
                volprogressText.setVisibility(View.VISIBLE);
                if (verticalSlider.getProgress() != 0) {
                    simpleExoplayer.setVolume(verticalSlider.getProgress());
                    audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//                    Log.e("volume is",""+ AudioManager.ADJUST_LOWER);
                    audio.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0);
                    verticalSlider.setProgress(verticalSlider.getProgress() + AudioManager.ADJUST_LOWER);
                    volprogressText.setText(String.valueOf(verticalSlider.getProgress()));
//                                verticalSlider.setVisibility(View.GONE);
                } else {
                    verticalSlider.setProgress(verticalSlider.getProgress());
                    volprogressText.setText(String.valueOf(verticalSlider.getProgress()));
                }

            }

            if (e1.getY() > e2.getY()) {
                Log.d(DEBUG_TAG, "Down to Up swipe performed");
                volprogressText.setVisibility(View.VISIBLE);
                verticalSlider.setVisibility(View.VISIBLE);
                if (verticalSlider.getProgress() < verticalSlider.getMax()) {
                    audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                    Log.e("volume is",""+ AudioManager.ADJUST_RAISE);
                    simpleExoplayer.setVolume(verticalSlider.getProgress());
                    audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                            AudioManager.ADJUST_RAISE,0);
                    verticalSlider.setProgress(verticalSlider.getProgress() + AudioManager.ADJUST_RAISE);
                    verticalSlider.setProgress(verticalSlider.getProgress());
                } else if (verticalSlider.getProgress() == 100) {
                    verticalSlider.setProgress(verticalSlider.getProgress());

                    verticalSlider.setProgress(verticalSlider.getProgress());
                }

            }
            return true;
        }
        @Override
        public void onLongPress(MotionEvent e) {
        }
        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
            return true;
        }
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }
    private class MyBrightnessGestureListener implements GestureDetector.OnGestureListener , GestureDetector.OnDoubleTapListener{
        private static final String DEBUG_TAG = "BrightGestures";
        @Override
        public boolean onDown(MotionEvent e) {

            return false;
        }
        @Override
        public void onShowPress(MotionEvent e) {

        }
        @Override
        public boolean onSingleTapUp(MotionEvent e) {

            return false;
        }
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            if (e1.getY() < e2.getY()) {
                Log.d(DEBUG_TAG, "Up to Down swipe performed ,Decreasing");
                brightverticalSlider.setVisibility(View.VISIBLE);
                brightprogressText.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Settings.System.canWrite(getApplicationContext()))
                    {
                        if (myVericalbrigrtness <=0) {
                            brightness = 0;
                            myVericalbrigrtness = 0;
                            brightverticalSlider.setProgress(0);
                            ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
                            Settings.System.putInt(cResolver,Settings.System.SCREEN_BRIGHTNESS, 0);
                            brightprogressText.setText(String.valueOf(brightverticalSlider.getProgress()));
                        }
                        else if (myVericalbrigrtness <= 100) {
                            brightness = (float) (brightness - 2.25);
                            myVericalbrigrtness = myVericalbrigrtness-1;
                            int actual_progress =brightverticalSlider.getProgress();
                            brightverticalSlider.setProgress((int) (actual_progress - 1));
                            if(brightness == 0) {
                                ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
                                Settings.System.putInt(cResolver,Settings.System.SCREEN_BRIGHTNESS, (int) 0);
                                brightprogressText.setText(String.valueOf(brightverticalSlider.getProgress()));
                            }else {
                                ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
                                Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int) brightness);
                                brightprogressText.setText(String.valueOf(brightverticalSlider.getProgress()));
                            }
                        }
                    } else
                    {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + PlayJavaVideoActivity.this.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            }

            if (e1.getY() > e2.getY()) {
                Log.d(DEBUG_TAG, "Down to Up swipe performed Increasing");
                brightprogressText.setVisibility(View.VISIBLE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    brightverticalSlider.setVisibility(View.VISIBLE);
                    if (Settings.System.canWrite(getApplicationContext()))
                    {
//                        brightness
                        if (myVericalbrigrtness >=100) {
                            myVericalbrigrtness = 100;
                            brightness = 225;
                            brightverticalSlider.setProgress(100);
                            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int) 225);
                            brightprogressText.setText(String.valueOf(brightverticalSlider.getProgress()));
                        }
                        else if (myVericalbrigrtness >= 0) {
                            myVericalbrigrtness = myVericalbrigrtness +1;
                            brightness = (float) (brightness + 2.25);
                            brightverticalSlider.setProgress(myVericalbrigrtness);
                            ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
                            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int) brightness);
                            brightprogressText.setText(String.valueOf(brightverticalSlider.getProgress()));

                        }
//                        if(brightness <=225) {
//                            ContentResolver cResolver = PlayJavaVideoActivity.this.getApplicationContext().getContentResolver();
//                            Settings.System.putInt(cResolver, Settings.System.SCREEN_BRIGHTNESS, (int) brightness);
//                        }
                    } else
                    {
                        Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                        intent.setData(Uri.parse("package:" + PlayJavaVideoActivity.this.getPackageName()));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }

            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            try {
                if (simpleExoplayer != null ) {
//                    simpleExoplayer.setPlayWhenReady(false);
//                    Constants.isMediaPlaying = false;
                    long bufferPosition = simpleExoplayer.getBufferedPosition();
                    long currentPosition = simpleExoplayer.getCurrentPosition();
                    if ((currentPosition + 2000) < bufferPosition + 10) {
                        simpleExoplayer.seekTo(simpleExoplayer.getCurrentWindowIndex(), currentPosition + 2000);
                        simpleExoplayer.setPlayWhenReady(true);
//                        simpleExoplayer.s
//                        SyncStateContract.Constants.isMediaPlaying = true;
                    }
                }
            } catch (Exception ep) {
                Toast.makeText(PlayJavaVideoActivity.this ,"Error",Toast.LENGTH_LONG).show();
            }
            return true;
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            return false;
        }
    }

}
