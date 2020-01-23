package com.ics.likeplayer;

import android.app.IntentService;
import android.app.Notification;
import android.app.job.JobService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import com.ics.likeplayer.SessionManage.SessionManager;

import java.io.File;
import java.util.Calendar;

public class MyDeleteService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;
    Handler deletehandler = new Handler();
    Runnable deleterunnable;

    private void doBackgroundWork(final android.app.job.JobParameters params) {

//            deletehandler.removeCallbacksAndMessages(null);
        deleterunnable = new Runnable() {
            public void run() {
                Log.e("working 1", ""+Calendar.getInstance().getTime().getDate());
                Log.e("working 2", ""+new SessionManager(MyDeleteService.this).getUS());

                if(String.valueOf(Calendar.getInstance().getTime().getDate()).equals(new SessionManager(MyDeleteService.this).getUS()))
                {
                    File photoLcl = new File(new  SessionManager(MyDeleteService.this).getGetDeleteURl());
                    Uri imageUriLcl = FileProvider.getUriForFile(MyDeleteService.this,
                            MyDeleteService.this.getApplicationContext().getPackageName() +
                                    ".provider", photoLcl);
                    ContentResolver contentResolver =MyDeleteService.this.getContentResolver();
                    contentResolver.delete(imageUriLcl, null, null);
                    deletehandler.removeCallbacksAndMessages(null);
//                   onStopJob(params);
                    Toast.makeText(MyDeleteService.this, "Deleting files now", Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(MyDeleteService.this, "yet to be deleted", Toast.LENGTH_SHORT).show();
                }
                deletehandler.postDelayed(this, 4000);
            }
        };
        deletehandler.postDelayed(deleterunnable, 3000);
};


    @Override
    public boolean onStartJob(android.app.job.JobParameters params) {
        Log.d(TAG, "Job started");
        try {
            Toast.makeText(this, "job started again", Toast.LENGTH_SHORT).show();
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        doBackgroundWork(params);
        return false;
    }

    @Override
    public boolean onStopJob(android.app.job.JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        deletehandler.removeCallbacksAndMessages(null);
        stopSelf();
        onStopJob(params);
//        onDestroy();
        jobCancelled = true;
        return false;
    }


}
