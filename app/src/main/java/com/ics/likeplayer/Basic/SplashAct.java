package com.ics.likeplayer.Basic;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.ics.likeplayer.MainActivity;
import com.ics.likeplayer.R;

import java.util.ArrayList;
import java.util.List;

public class SplashAct extends AppCompatActivity {
    private static final int REQUEST_ID_MULTIPLE_PERMISSIONS =1 ;
    private Handler mHandler;
    Runnable r;
    //Window object, that will store a reference to the current window
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkAndRequestPermissions();
        ///+++++++++++++++++++++++++++++++++++++++++++++Handler check+++++++++++++++++++++++++
        //+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++FOR Volume hide+++++++++++++++++++++++++++++++++++++++++++++++++++++
        mHandler = new Handler();
        r = new Runnable() {
            public void run() {
                Log.e("i volume ma", "in");
                Toast.makeText(SplashAct.this, "Failing permissions", Toast.LENGTH_SHORT).show();

                if( checkAndRequestPermissions()) {
                    mHandler.removeCallbacksAndMessages(null);

                    Toast.makeText(SplashAct.this, "Thanks", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SplashAct.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else {
//                        sleep(5000 +1000);
                    Toast.makeText(SplashAct.this, "Please allow us all permission", Toast.LENGTH_SHORT).show();
                }
                mHandler.postDelayed(this, 4000);
            }
        };
        mHandler.postDelayed(r, 3000);
        //++++++++++++++++++++++++++++++++++++++++++++++++++++++++For Brightness+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++


        //+++++++++++++++++++++++++++++++++++++++++++++++++++
//            Thread background = new Thread() {
//            public void run() {
//
//                try {
//                    // Thread will sleep for 5 seconds\
//
//                    Toast.makeText(SplashAct.this, "Failing permissions", Toast.LENGTH_SHORT).show();
//
//                    if(  checkAndRequestPermissions()) {
//
//
//                        Intent intent = new Intent(SplashAct.this, MainActivity.class);
//                        startActivity(intent);
//                    }else {
////                        sleep(5000 +1000);
//                        Toast.makeText(SplashAct.this, "Please allow us all permission", Toast.LENGTH_SHORT).show();
//                    }
//
//                    // After 5 seconds redirect to another intent
//                    sleep(1000);
//
//                    //Remove activity
//                    finish();
//
//                } catch (Exception e) {
//
//                }
//            }
//
//        };
//        background.start();
        // start thread


    }


    //*******************************************************************
    private  boolean checkAndRequestPermissions() {
//        int permissionCamara = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS);
        int permissionStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int permissionStorage1 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
//        int permissionStorage2 = ContextCompat.checkSelfPermission(this, Manifest.permission.WAKE_LOCK);
//        int permissionStorage3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_SETTINGS);

        List<String> listPermissionsNeeded = new ArrayList<>();
//        if (permissionCamara != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Failed permissionCamara", Toast.LENGTH_SHORT).show();
//            listPermissionsNeeded.add(Manifest.permission.WRITE_SETTINGS);
//        }
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Failed permissionCamara", Toast.LENGTH_SHORT).show();
            listPermissionsNeeded.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (permissionStorage1 != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Failed READ_EXTERNAL_STORAGE", Toast.LENGTH_SHORT).show();
            listPermissionsNeeded.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
//        if (permissionStorage2 != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(this, "Failed WAKE_LOCK", Toast.LENGTH_SHORT).show();
//            listPermissionsNeeded.add(Manifest.permission.WAKE_LOCK);
//        }
//        if (permissionStorage3 != PackageManager.PERMISSION_GRANTED) {
//            if(checkSystemWritePermissionforsetting()){
//                Toast.makeText(this, "Thanks for allowing", Toast.LENGTH_SHORT).show();
//            }else {
//                Toast.makeText(this, "Not for allowing", Toast.LENGTH_SHORT).show();
//            }
//
//            Toast.makeText(this, "Failed WRITE_SETTINGS", Toast.LENGTH_SHORT).show();
//            listPermissionsNeeded.add(Manifest.permission.WRITE_SETTINGS);
//        }

        if (!listPermissionsNeeded.isEmpty()) {

            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]),REQUEST_ID_MULTIPLE_PERMISSIONS);
            return false;
        }

        return true;
    }

    private Boolean checkSystemWritePermissionforsetting() {
        Boolean retVal = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            retVal = Settings.System.canWrite(this);
            Log.d("TAG", "Can Write Settings: $retVal");
            if (retVal) {
                Toast.makeText(this, "Write allowed :-)", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Write not allowed :-(", Toast.LENGTH_LONG).show();
                openAndroidPermissionsMenu();
            }
        }
        return retVal;

    }

    //++++++++++++++++++++++++++++++++++++++++++++++OPen Setting Permission ++++++++++++++++++++
    private void openAndroidPermissionsMenu() {
        Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
        intent.setData( Uri.parse("package:" + SplashAct.this.getPackageName())) ;
        startActivity(intent);
    }

    //**********************************************************************
    @Override
    protected void onDestroy() {
        mHandler.removeCallbacksAndMessages(null);
//        mHandler.set(r);
        super.onDestroy();
    }
}
