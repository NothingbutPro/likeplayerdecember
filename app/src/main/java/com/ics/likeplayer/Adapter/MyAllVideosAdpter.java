package com.ics.likeplayer.Adapter;

import android.app.Activity;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.archit.calendardaterangepicker.customviews.DateRangeCalendarView;
import com.ics.likeplayer.Basic.SplashAct;
import com.ics.likeplayer.Database.DatabaseHelper;
import com.ics.likeplayer.Database.Model.Database_players_play;
import com.ics.likeplayer.FurtherActivity.PlayJavaVideoActivity;
import com.ics.likeplayer.FurtherActivity.PlayVideoActivity;
import com.ics.likeplayer.Model.AllVideos;
import com.ics.likeplayer.Model.PojoClass;
import com.ics.likeplayer.MyDeleteJobService;
import com.ics.likeplayer.MyDeleteService;
import com.ics.likeplayer.R;
import com.ics.likeplayer.SessionManage.SessionManager;
import com.ics.likeplayer.SessionManage.Shared_Preference;
import com.marcoscg.dialogsheet.DialogSheet;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * Created by kuldeep on 13/02/18.
 */

public class MyAllVideosAdpter extends RecyclerView.Adapter<MyAllVideosAdpter.ViewHolder> {
    File file;
    private Context context;
    View view;
    long id;

    ArrayList<Database_players_play> database_players_playsdbList = new ArrayList<>();
    public static String _Song_name, _Song_url, _Song_time;
    private ArrayList<AllVideos> pojoClassArrayList;
    DialogPlus dialog;

    public MyAllVideosAdpter(Context context, ArrayList<AllVideos> pojoClassArrayList) {
        this.context = context;
        this.pojoClassArrayList = pojoClassArrayList;

    }

    @Override
    public MyAllVideosAdpter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allvideos, parent, false);
//        file = new File("xyz");
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyAllVideosAdpter.ViewHolder holder, int position) {
        file = new File(pojoClassArrayList.get(position).getPath());
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
//use one of overloaded setDataSource() functions to set your data source
        retriever.setDataSource(context, Uri.fromFile(file));
        String time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
        long timeInMillisec = Long.parseLong(time);
        retriever.release();
        Bitmap bMap = ThumbnailUtils.createVideoThumbnail(file.getAbsolutePath(), MediaStore.Video.Thumbnails.MICRO_KIND);
        holder.song_name.setText(pojoClassArrayList.get(position).getName());
        holder.vidthm.setImageBitmap(bMap);
        holder.time.setText(String.format("%d:%d",
                TimeUnit.MILLISECONDS.toMinutes(timeInMillisec),
                TimeUnit.MILLISECONDS.toSeconds(timeInMillisec) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillisec))
        ));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(v.getContext() , PlayVideoActivity.class);
                Intent intent = new Intent(v.getContext(), PlayJavaVideoActivity.class);
//                intent.putExtra("vidurl", "" + pojoClassArrayList.get(position).getPath());
//                intent.putExtra("dirpath" , ""+intent.getStringExtra("dirpath"));
//                intent.putExtra("dirpath", "" + pojoClassArrayList.get(position).getRooDirName());
                intent.putExtra("slevidname", "" + pojoClassArrayList.get(position).getName());
                intent.putExtra("playplaylist" ,pojoClassArrayList );
                v.getContext().startActivity(intent);
//                ((Activity) v.getContext()).finish();
            }
        });
        holder.videooptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showsitdialog(v, position, holder);
            }
        });
//        holder.singer_name.setText(pojoClassArrayList.get(position).getSinger_name());
//        holder.time.setText(pojoClassArrayList.get(position).getTime());

    }

    private void directlysendtofav(View v, ViewHolder holder, int position) {
//        File play_file = new File(pojoClassArrayList.get(position).getPath());
        id = holder.db.insertToFovorite(context, holder.song_name.getText().toString(), pojoClassArrayList.get(position).getPath(), holder.time.getText().toString(), "Favorites");
        if(id !=0)
        {
            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Failed ", Toast.LENGTH_SHORT).show();
        }
    }



    private void showsitdialog(View v, int position, ViewHolder holder) {
        DialogSheet dialogSheet = new DialogSheet(v.getContext());
        dialogSheet.setView(R.layout.filterdeialog);
        View filterview = dialogSheet.getInflatedView();
//        Log.e("Playlist are" ,""+holder.db.getPlaylistCountnSongs());
        Log.e("Song are" ,""+holder.db.getAllSOngsbyPlaylist().size());
        TextView songfialogname = filterview.findViewById(R.id.songfialogname);
        LinearLayout addplaylist = filterview.findViewById(R.id.addplaylist);
        LinearLayout deletethesongli = filterview.findViewById(R.id.deletethesongli);
        LinearLayout addtofavli1 = filterview.findViewById(R.id.addtofavli1);
        dialogSheet.setColoredNavigationBar(true).setCancelable(true).setBackgroundColor(Color.WHITE) // Your custom background color
                .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                .show();
        //+++++++++++++++++++++++++++++++++++++++++Delete dialog++++++++++++++++++++++++++++++++++++++++
        deletethesongli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Show_dialog_for_delete(v ,position);
                dialogSheet.dismiss();
            }
        });
        //+++++++++++++++++++++++++++++++++++++
        addtofavli1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSheet.dismiss();
                directlysendtofav(v,holder,position );
            }
        });
        addplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSheet.dismiss();
                _Song_name= pojoClassArrayList.get(position).getName();
                _Song_time = String.valueOf(pojoClassArrayList.get(position).getLength());
                _Song_url = pojoClassArrayList.get(position).getPath();
                checkforPlaylist(holder ,v,position);

            }
        });
        songfialogname.setText(pojoClassArrayList.get(position).getName());

    }

    private void Show_dialog_for_delete(View v, int position) {
        DialogSheet dialogSheet = new DialogSheet(v.getContext());
        dialogSheet.setView(R.layout.deletedialogfor);
        com.archit.calendardaterangepicker.customviews.DateRangeCalendarView del_calendar;
        LinearLayout calenderli;
        TextView sceduledelsbtn;
        TextView del_now_txt;
        View filterview = dialogSheet.getInflatedView();
        del_calendar = filterview.findViewById(R.id.del_calendar);
        del_now_txt = filterview.findViewById(R.id.del_now_txt);
        calenderli = filterview.findViewById(R.id.calenderli);
        sceduledelsbtn = filterview.findViewById(R.id.sceduledelsbtn);
        sceduledelsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(calenderli.getVisibility() == View.VISIBLE) {
                    calenderli.setVisibility(View.GONE);
                }else {
                    calenderli.setVisibility(View.VISIBLE);
                }
            }
        });
        del_now_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File photoLcl = new File(new  SessionManager(v.getContext()).getGetDeleteURl());
                Uri imageUriLcl = FileProvider.getUriForFile(v.getContext(),
                        v.getContext().getApplicationContext().getPackageName() +
                                ".provider", photoLcl);
                ContentResolver contentResolver =v.getContext().getContentResolver();
                contentResolver.delete(imageUriLcl, null, null);
                Toast.makeText(v.getContext(), "deletion success", Toast.LENGTH_SHORT).show();
                dialogSheet.dismiss();
            }
        });
        del_calendar.setCalendarListener(new DateRangeCalendarView.CalendarListener() {
            @Override
            public void onFirstDateSelected(Calendar startDate) {
//                Toast.makeText(v.getContext(), "Start Date: " + startDate.getTime().toString(), Toast.LENGTH_SHORT).show();
               Log.e("start" , "Date is"+startDate.getTime().getDate());

//               Log.e("start" , "TIme is"+startDate.getTime().getHours() +":"+""+startDate.getTime().getMinutes());
            }

            @Override
            public void onDateRangeSelected(Calendar startDate, Calendar endDate) {
//                Toast.makeText(v.getContext(), "Start Date: " + startDate.getTime().toString() + " End date: " + endDate.getTime().toString(), Toast.LENGTH_SHORT).show();
                Log.e("start" , "Date is"+endDate.getTime().getDate());
//                Toast.makeText(context, "we are starting the deleting service", Toast.LENGTH_SHORT).show();
                // Create a new dispatcher using the Google Play driver.
                //creating new firebase job dispatcher
                SessionManager sessionManager = new SessionManager(v.getContext());
                sessionManager.setDelete(pojoClassArrayList.get(position).getPath() , pojoClassArrayList.get(position).getName() , String.valueOf(endDate.getTime().getDate()));
                dialogSheet.dismiss();
//                scheduleJob(v ,position ,endDate.getTime().getDate());
                //FOr OReo++++++++++++++++++++++++++++++++

                //++++++++++++++++
                // Create a new dispatcher using the Google Play driver.

            }
        });

        dialogSheet.setColoredNavigationBar(true).setCancelable(true).setBackgroundColor(Color.WHITE) // Your custom background color
                .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                .show();
        dialogSheet.show();

    }

    private void scheduleJob(View v, int position, int date) {
        ComponentName componentName = new ComponentName(v.getContext(), MyDeleteService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) v.getContext().getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            SessionManager sessionManager = new SessionManager(v.getContext());
            sessionManager.setDelete(pojoClassArrayList.get(position).getPath() , pojoClassArrayList.get(position).getName() , String.valueOf(date));
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }
    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) v.getContext().getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }
//+++++++++++++++++++++++++++++++++Dwelete JObs++++++++++++++++++++++++++

//    public static Job createJob(FirebaseJobDispatcher dispatcher){
//
//        Job job = dispatcher.newJobBuilder()
//                //persist the task across boots
//                .setLifetime(Lifetime.FOREVER)
//                //.setLifetime(Lifetime.UNTIL_NEXT_BOOT)
//                //call this service when the criteria are met.
//                .setService(MyDeleteJobService.class)
//                //unique id of the task
//                .setTag("UniqueTagForYourJob")
//                //don't overwrite an existing job with the same tag
//                .setReplaceCurrent(false)
//                // We are mentioning that the job is periodic.
//                .setRecurring(true)
//                // Run between 30 - 60 seconds from now.
//                .setTrigger(Trigger.executionWindow(30, 60))
//                // retry with exponential backoff
//                .setRetryStrategy(RetryStrategy.DEFAULT_LINEAR)
//                //.setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
//                //Run this job only when the network is available.
//                .setConstraints(Constraint.ON_ANY_NETWORK, Constraint.DEVICE_IDLE,Constraint.DEVICE_CHARGING)
//                .build();
//        return job;
//    }
//    public static Job updateJob(FirebaseJobDispatcher dispatcher) {
//        Job newJob = dispatcher.newJobBuilder()
//                //update if any task with the given tag exists.
//                .setReplaceCurrent(true)
//                //Integrate the job you want to start.
//                .setService(MyDeleteJobService.class)
//                .setTag("UniqueTagForYourJob")
//                // Run between 30 - 60 seconds from now.
//                .setTrigger(Trigger.executionWindow(30, 60))
//                .build();
//        return newJob;
//    }
//
//    public void cancelJob(Context context){
//
//        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
//        //Cancel all the jobs for this package
//        dispatcher.cancelAll();
//        // Cancel the job for this tag
//        dispatcher.cancel("UniqueTagForYourJob");
//
//    }
    //+++++++++++++++++++++++++++++++++++++++All Delitions+++++++++++++++++++++++++++++++++complete++++++++++++++
    private void checkforPlaylist(ViewHolder holder, View v, int position) {
        RecyclerView playlistidsrec;
        LinearLayout addplaylist,add_to_fav;
        Allplaylistadapter allplaylistadapter;
        DialogSheet dialogSheet = new DialogSheet(v.getContext());
         dialogSheet.setTitle("Add to playlist")
                .setMessage("")
                .setColoredNavigationBar(true)
                .setCancelable(true)
                .setBackgroundColor(Color.WHITE) // Your custom background color
                .setButtonsColorRes(R.color.colorAccent) // You can use dialogSheetAccent style attribute instead
                .show();
        dialogSheet.setView(R.layout.playlistcreationdialog);
//         Access dialog custom inflated view
        View inflatedView = dialogSheet.getInflatedView();
        playlistidsrec =inflatedView.findViewById(R.id.playlistids);

        addplaylist =inflatedView.findViewById(R.id.addplaylist);
        add_to_fav =inflatedView.findViewById(R.id.add_to_fav);
        add_to_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSheet.dismiss();
                directlysendtofav(v,holder,position );
            }
        });
        addplaylist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogSheet.dismiss();
//                new com.orhanobut.dialogplus.ViewHolder(R.layout.playlistcreatedialog)

                 dialog =  DialogPlus.newDialog(v.getContext())
                            .setContentHolder(new Holder() {
                                @Override
                                public void addHeader(View view) {

                                }

                                @Override
                                public void addFooter(View view) {

                                }

                                @Override
                                public void setBackgroundResource(int colorResource) {

                                }

                                @Override
                                public View getView(LayoutInflater inflater, ViewGroup parent) {
                                     view = inflater.inflate(R.layout.playlistcreatedialog , parent,false);
                                     EditText playcreateedt = view.findViewById(R.id.playcreateedt);
                                     Button createplay = view.findViewById(R.id.createplay);
                                    createplay.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if(playcreateedt.getText().toString().length() !=0) {
//                                                Toast.makeText(v.getContext(), "ok created", Toast.LENGTH_SHORT).show();
                                                File play_file = new File(pojoClassArrayList.get(position).getPath());
                                             id =  holder.db.insertPlayList(context,playcreateedt.getText().toString() ,play_file.getParentFile().getAbsolutePath(),play_file.getParentFile().list().toString(),"Play_List");
                                            if(id ==0)
                                            {
                                                Toast.makeText(v.getContext(), "Playlist Name already exist choose another", Toast.LENGTH_SHORT).show();
                                            }else {
                                                dialog.dismiss();
                                                holder.db.insertPlaySong(v.getContext(), (int) id,playcreateedt.getText().toString(),pojoClassArrayList.get(position).getName(),pojoClassArrayList.get(position).getPath(),String.valueOf(pojoClassArrayList.get(position).getLength()));
                                                checkforPlaylist(holder,v ,position);
                                                Toast.makeText(v.getContext(), "Playlist Creation Success", Toast.LENGTH_SHORT).show();

                                            }

                                            }else {
                                                dialog.dismiss();
                                                Toast.makeText(v.getContext(), "Playlist name can not be null", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                    return view;
                                }

                                @Override
                                public void setOnKeyListener(View.OnKeyListener keyListener) {

                                }

                                @Override
                                public View getInflatedView() {
                                    return view;
                                }

                                @Override
                                public View getHeader() {
                                    return null;
                                }

                                @Override
                                public View getFooter() {
                                    return null;
                                }
                            })
                            .setGravity(Gravity.CENTER)
                            .setContentWidth(ViewGroup.LayoutParams.MATCH_PARENT)  // or any custom width ie: 300
                            .setContentHeight(ViewGroup.LayoutParams.WRAP_CONTENT)
                            .setExpanded(true)  // This will enable the expand feature, (similar to android L share dialog)

                            .create();
                dialog.show();

            }
        });
        try {
            if(holder.db.getAllNotesPlaylist() !=null) {
//                ArrayList<Database_players_play> database_players_playslist = new ArrayList<>();
                database_players_playsdbList = holder.db.getAllNotesPlaylist();
                playlistidsrec.setLayoutManager(new LinearLayoutManager(v.getContext()));
                PlayListAdapter playListAdapter = new PlayListAdapter(v.getContext(),database_players_playsdbList);
                playlistidsrec.setAdapter(playListAdapter);
                Toast.makeText(v.getContext(), "Playlist Found"+database_players_playsdbList.get(0).getPlay_list_name(), Toast.LENGTH_SHORT).show();

            }else {
                Toast.makeText(v.getContext(), "No Playlist found", Toast.LENGTH_SHORT).show();
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount()   {
        return pojoClassArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView song_name,singer_name,time;
        ImageView vidthm,videooptions;
        public DatabaseHelper db;
        public ViewHolder(View view) {
            super(view);

            song_name =  view.findViewById(R.id.song_name);
            vidthm =  view.findViewById(R.id.vidthm);
            time =  view.findViewById(R.id.time);
            videooptions =  view.findViewById(R.id.videooptions);
            db = new DatabaseHelper(view.getContext());


        }
    }
}
