package com.example.screenshottaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    Button btnScreenshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moveTaskToBack(true);

        btnScreenshot = findViewById(R.id.btnScreenshot);

        btnScreenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                startService(new Intent(MainActivity.this, screenshotService.class));

                takeScreenshot();

            }
        });


        new Handler().postDelayed(new Runnable() {  @Override
        public void run() {
            // This method will be executed once the timer is over
            // Start your app Next activity
//            Intent i = new Intent(CurrentActivity.this, NextActivity.class);
//            startActivity(i);
            takeScreenshot();
            // close this activity
//            finish();
        }
        }, 1000);



        if(sentAppToBackground){
            Toast.makeText(getApplicationContext(),"background...",Toast.LENGTH_LONG).show();

            Intent i = new Intent();
            i.setAction(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);

            this.startActivity(i);
        }
    }

    Handler handler=new Handler();
    Runnable r = new Runnable(){
        public void run() {
            Toast.makeText(getApplicationContext(),"handler....",Toast.LENGTH_LONG).show();

//            tv.append("Hello World");
            handler.postDelayed(r, 1000);
        handler.post(r);
        }

    };


//    Handler.Callback.(r);


    private void takeScreenshot() {
        Date now = new Date();
        android.text.format.DateFormat.format("yyyy-MM-dd_hh:mm:ss", now);

        try {
            // image naming and path  to include sd card  appending name you choose for file
            String mPath = Environment.getExternalStorageDirectory().toString() + "/"+"screen"+"/qaz" + now + ".jpg";

            // create bitmap screen capture
            View v1 = getWindow().getDecorView().getRootView();
            v1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(v1.getDrawingCache());
            v1.setDrawingCacheEnabled(false);

            File imageFile = new File(mPath);

            FileOutputStream outputStream = new FileOutputStream(imageFile);
            int quality = 100;
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
            outputStream.flush();
            outputStream.close();

            Toast.makeText(getApplicationContext(),"cliked....",Toast.LENGTH_LONG).show();

//            openScreenshot(imageFile);
        } catch (Throwable e) {
            // Several error may come out with file handling or DOM
            e.printStackTrace();
        }
    }

    boolean sentAppToBackground = moveTaskToBack(true);



    private void openScreenshot(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(imageFile);
        intent.setDataAndType(uri, "image/*");
        startActivity(intent);
    }

}