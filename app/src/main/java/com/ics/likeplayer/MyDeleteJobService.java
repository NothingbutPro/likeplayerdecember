package com.ics.likeplayer;
import android.util.Log;
import android.widget.Toast;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

public class MyDeleteJobService  extends JobService {

    private static final String TAG = MyDeleteJobService.class.getSimpleName();

    @Override
    public boolean onStartJob(final JobParameters params) {
        //Offloading work to a new thread.
        Toast.makeText(this, "Job started", Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                codeYouWantToRun(params);
            }
        }).start();

        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public void codeYouWantToRun(final JobParameters parameters) {
        try {
            Toast.makeText(this, "Job running", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "completeJob: " + "jobStarted");
            //This task takes 2 seconds to complete.
            Thread.sleep(2000);

            Log.d(TAG, "completeJob: " + "jobFinished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            Toast.makeText(this, "Job running", Toast.LENGTH_SHORT).show();
            //Tell the framework that the job has completed and doesnot needs to be reschedule
            jobFinished(parameters, true);
        }
    }
}
