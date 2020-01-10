package com.ics.likeplayer.FurtherActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.ics.likeplayer.Adapter.MyAllVideosAdpter
import com.ics.likeplayer.MainActivity
import com.ics.likeplayer.Model.AllVideos
import com.ics.likeplayer.R
import java.io.File
import java.util.*


class AllVideoActivity : AppCompatActivity() {
    lateinit var  _videofile: File
    lateinit var  cancelback: ImageView
    lateinit var simpleExoplayer: SimpleExoPlayer
    private var myAllVideosAdpter: MyAllVideosAdpter? =null
    lateinit var RootDirname :String
    lateinit var backplaypause :ImageView
     lateinit var allvideorec : RecyclerView
     lateinit var sectionvid : TextView
     lateinit var backbtns : ImageView
     lateinit var backsongname : TextView
     lateinit var song_namewa:TextView
      var whichone : Boolean =false
    var ppplaybackState = false
     lateinit var hidevidcontrols : com.google.android.exoplayer2.ui.PlaybackControlView
//     lateinit var File : File
    var AllVideosList : ArrayList<AllVideos> = ArrayList()

    override fun onDestroy() {
        if(whichone) {
            Toast.makeText(this, "New video", Toast.LENGTH_SHORT).show();
            simpleExoplayer.stop()
            System.gc()
        }else{
            Toast.makeText(this, "New video", Toast.LENGTH_SHORT).show();
//            simpleExoplayer.stop()
        }
        super.onDestroy()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.e("Created" , "AllVideoActivity.")
        setContentView(R.layout.activity_all_video)
        allvideorec = findViewById(R.id.allvideorec);
        backsongname = findViewById(R.id.exo_position);
        sectionvid = findViewById(R.id.sectionvid);
        backbtns = findViewById(R.id.backbtns);
        song_namewa = findViewById(R.id.song_namewa);
        backplaypause = findViewById(R.id.backplaypause);
        cancelback = findViewById(R.id.cancelback);
        hidevidcontrols = findViewById(R.id.hidevidcontrols);
        RootDirname = intent.getStringExtra("dirpath")
        sectionvid.setText(intent.getStringExtra("sectionvid"))
        cancelback.setOnClickListener {
            simpleExoplayer.stop()
            simpleExoplayer.release()
            hidevidcontrols.visibility = View.GONE

        }
        backplaypause.setOnClickListener {
            if (simpleExoplayer != null) {
                if (ppplaybackState) {
                    simpleExoplayer.playWhenReady = false
                    ppplaybackState = false
                } else {
                    simpleExoplayer.playWhenReady = true
                    ppplaybackState = true
                }
            }
        }
        backbtns.setOnClickListener {
            onBackPressed()
        }
        if(intent.getStringExtra("filepath") !=null)
        {
            val filepath = intent.getStringExtra("filepath")
            prepareExoplayermp3(filepath)
        }
        if(!RootDirname.isBlank() || !RootDirname.isEmpty())
        {
//            File = Uri.fromFile(File(RootDirname.toUri())
            val File = File(RootDirname);
            if(File.exists())
            {
                File.listFiles().forEach {
                    val namepath = it.absolutePath
                    Log.e("path is" , "file:"+File.absolutePath)
                    if(namepath !=null && (namepath.endsWith(".mp4") ||namepath.endsWith(".mkv")
                                ||namepath.endsWith(".m4v") ||namepath.endsWith(".avi") ||namepath.endsWith(".mov") ||
                                namepath.endsWith(".3gp") ||namepath.endsWith(".flv") ||namepath.endsWith(".wmv") ||
                                namepath.endsWith(".rmvb") ||namepath.endsWith(".ts")||namepath.endsWith(".webm")))
                    {
                        AllVideosList.add(AllVideos(it.name,0,"" ,it.absolutePath,RootDirname))
                    }
                }
                myAllVideosAdpter = MyAllVideosAdpter(this, AllVideosList)
                //  mLayoutManager = LinearLayoutManager(context)
                allvideorec.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
                //    songrec.setLayoutManager(mLayoutManager)
                allvideorec.setItemAnimator(DefaultItemAnimator())
                allvideorec.setAdapter(myAllVideosAdpter)
            }else{
                Toast.makeText(this , "No it's not", Toast.LENGTH_LONG).show()
            }
        }else{
            Toast.makeText(this , "Invalid Directory",Toast.LENGTH_LONG).show()
        }
    }
//+++++++++++++++++++++++++++++++++++++++++++Mp3 running +++++++++++++++++++++++++++++
    private fun prepareExoplayermp3(filepath: String) {
        simpleExoplayer = ExoPlayerFactory.newSimpleInstance(
            this,
            DefaultRenderersFactory(this),
            DefaultTrackSelector(), DefaultLoadControl()
        )
        prepareExoplayer(filepath)
    }

    private fun prepareExoplayer(filepath: String) {
//        vidview.setPlayer(simpleExoplayer)
        if(  hidevidcontrols.visibility == View.GONE) {
            hidevidcontrols.visibility = View.VISIBLE
        }else{
            hidevidcontrols.visibility = View.GONE
        }
        _videofile = File(filepath)
        val uri = Uri.parse(filepath)
//        MediaSource mediaSource = buildMediaSource(uri);
        //        MediaSource mediaSource = buildMediaSource(uri);
        val mediaSource: MediaSource = buildMediaSource(uri)
        simpleExoplayer.prepare(mediaSource, true, true)
        simpleExoplayer.setPlayWhenReady(true)
        simpleExoplayer.playWhenReady = true
        hidevidcontrols.setPlayer(simpleExoplayer)
        song_namewa.setText(_videofile.name)
        val currentindex = intent.getLongExtra("currentwindow" ,-1);
        simpleExoplayer.seekTo(currentindex)
        backsongname.setText(_videofile.name)

        simpleExoplayer.addListener(object : Player.EventListener {
            override fun onPlayerStateChanged(
                playWhenReady: Boolean,
                playbackState: Int) {
                if (playbackState == Player.STATE_BUFFERING) {

                } else if (playbackState == Player.STATE_READY) {
                    Toast.makeText(this@AllVideoActivity ,"Player is ready with "+currentindex,Toast.LENGTH_LONG).show();
//
//                        simpleExoplayer.seekTo(currentindex)

                } else if (playbackState == Player.STATE_ENDED) {
                    Toast.makeText(
                        applicationContext,
                        "Your video has been ended",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun buildMediaSource(uri: Uri?): MediaSource {
        return ExtractorMediaSource.Factory(
            DefaultDataSourceFactory(this, "Exoplayer-local")
        ).createMediaSource(uri)
    }

    override fun onBackPressed() {
         intent = Intent(this , MainActivity::class.java)
        startActivity(intent)
//        this.finish()
        super.onBackPressed()
    }

    override fun onStart() {
        Log.e("Started" , "AllVideoActivity.")
        super.onStart()
    }
}
