package com.example.uca.acercade;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void share(View v){
        ImageView foto = findViewById(R.id.foto);
        foto.setDrawingCacheEnabled(true);
        Bitmap b = foto.getDrawingCache();
        File file;
        try {
            verifyStoragePermissions(this);
            File sdCard = Environment.getExternalStorageDirectory();
            file = new File(sdCard, "image.jpg");
            FileOutputStream fos = new FileOutputStream(file);
            b.compress(Bitmap.CompressFormat.JPEG, 95, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        ArrayList<Uri> imageUris = new ArrayList<Uri>();
        imageUris.add(Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "image.jpg")));
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Sigueme en mis redes: \n"
                + "Github: " + getString(R.string.txt1) + "\n"
                + "Twitter: " + getString(R.string.txt2) + "\n"
                + "Instagram: " + getString(R.string.txt3) + "\n"
                + "Gmail: " + getString(R.string.txt4) + "\n"
                + getString(R.string.txt5) + ".");
        shareIntent.setType("*/*");
        startActivity(Intent.createChooser(shareIntent, "Share images to.."));
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }
    }
}
