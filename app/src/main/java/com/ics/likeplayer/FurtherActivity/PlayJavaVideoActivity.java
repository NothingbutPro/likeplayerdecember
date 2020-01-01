package com.ics.likeplayer.FurtherActivity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RemoteViews;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.view.GestureDetectorCompat;

import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.ShakeDetector;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SeekParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.PlaybackControlView;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.hmomeni.verticalslider.VerticalSlider;
import com.ics.likeplayer.R;
import com.ics.likeplayer.ScreenshotManager;
import java.io.File;
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
    public TextView slevidname;
    public TextView exo_position;
    public TextView exo_duration;
    public LinearLayout backplayimg;
    public  File _videofile;
    int currentwindows;
    long currentwindowspositions;
    //    private View progressBar;
//+++++++++++++++++++++++++++++++++For Variables++++++++++++++++++++++++
    public int REQUEST_ID = 1;
    public long videoPosition = 0;
    public int myVericalbrigrtness = 100;
    public Boolean StoporNot = false;
    public Boolean LockORNot = false;
    public Boolean ScreenLockORNot = false;
    public Context context;
    public ImageView fullthedamn;
    //+++++++++++++++++++++++++++++++++++++++++++++++
//+++++++++++++++++++++++++++++++++++++++All Controls+++++++++++++++++++++++++++++++++++++++++++++++++
    ImageView ReverseBtn;
    ImageView NextBtn;
    ImageView FastForwardBtn;
    ImageView BackFastForwardBtn;
    ImageView RepeatBtn;
    ImageView Img_lockscreen;
    ImageView Img_lockscreen_hide;
    int clicks = 1;
    Boolean greysacle = false;
    boolean ppplaybackState;
    //+++++++++++++++++++++++++++++++++++++++FOR GESTURES+++++++++++++++++
    Runnable r,r2;
    //    private DiscreteSlider mSlider;
    Handler mHandler;
    Handler mHandler2;
    private float brightness = (float) 225;
    //Content resolver used as a handle to the system's settings
    private ContentResolver cResolver;
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
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++Audio to video change+++++++++++++++++++++++
    private MediaPlayer mp;
    //++++++++++++++++++++++++++++++++++++++++++++++++++++++
    //++++++++++++++++++++++++++++++++
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected void onDestroy() {
        mHandler.removeCallbacks(r);
        mHandler.removeCallbacks(r2);
        super.onDestroy();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_video);
        super.onCreate(savedInstanceState);
        context = PlayJavaVideoActivity.this;
        //Get the content resolver
        cResolver = getContentResolver();
        window = getWindow();

        mvolumeDetector = new GestureDetectorCompat(this, new MyVolumeGestureListener());
        mvbrigtnessDetector = new GestureDetectorCompat(this, new MyBrightnessGestureListener());
        InitializeEverything();
        InitializePlayer();
        InitializePlayerControls();

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

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }
            @Override
            public void onSeekProcessed() {
                Toast.makeText(context, "my currentwindows is"+currentwindows, Toast.LENGTH_SHORT).show();
                Toast.makeText(context, "my currentwindowspositions is"+currentwindowspositions, Toast.LENGTH_SHORT).show();
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
                finish();

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
        NextBtn = findViewById(R.id.exo_next);
        FastForwardBtn = findViewById(R.id.exo_ffwd);
        fullthedamn = findViewById(R.id.fullthedamn);
        brightverticalSlider = findViewById(R.id.brightverticalSlider);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
        RepeatBtn = findViewById(R.id.exo_repeat_toggle);
        verticalSlider = findViewById(R.id.verticalSlider);
//        VolumeBtn = findViewById(R.id.exo_);
        BackFastForwardBtn = findViewById(R.id.exo_rew);
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
        // get the gesture detector
        //++++++++++++++++++++++++++++++++++++++++++++++++MAin Functions+++++++++++++++++++++++++++++++++++++++++++++++++++++
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

    private void takeScreenshot(PlayerView vidview) {
        ScreenshotManager.INSTANCE.requestScreenshotPermission(this, REQUEST_ID);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (isInPictureInPictureMode) {
            Toast.makeText(this, "You done it well", Toast.LENGTH_LONG).show();

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
            Toast.makeText(this, "Done", Toast.LENGTH_LONG).show();
            ScreenshotManager.INSTANCE.takeScreenshot(this, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void InitializeEverything() {
        vidview = findViewById(com.ics.likeplayer.R.id.simpleExoPlayerView);
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
        myvideo = getIntent().getStringExtra("vidurl");
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
                } else
                if (playbackState == Player.STATE_ENDED) {

                    Toast.makeText(getApplicationContext(), "Your video has been ended", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
