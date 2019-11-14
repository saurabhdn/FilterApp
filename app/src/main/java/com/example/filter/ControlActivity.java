package com.example.filter;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.squareup.picasso.Picasso;

public class ControlActivity extends AppCompatActivity {
     Toolbar mControlToolbar;

     ImageView mTickImageView;

     ImageView mCenterImageView;

     final static int PICK_IMAGE = 2;
     final static int MY_PERMISSIONS_REQUEST_STARAGE_PERMISSION = 3;

     ImageView mFirstFilterPreviewImageView;
     ImageView mSecondFilterPreviewImageView;
     ImageView mThirdFilterPreviewImageView;
     ImageView mFourthFilterPreviewImageView;

     private static final String TAG = ControlActivity.class.getCanonicalName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);

        mControlToolbar = (Toolbar) findViewById(R.id.toolbar);
        mCenterImageView = (ImageView) findViewById(R.id.centerImageView);

        mControlToolbar.setTitle(getString(R.string.app_name));
        mControlToolbar.setNavigationIcon(R.drawable.icon);
        mControlToolbar.setTitleTextColor(getResources().getColor(R.color.colorWhite));

        mFirstFilterPreviewImageView = (ImageView) findViewById(R.id.imageView5);
        mSecondFilterPreviewImageView = (ImageView) findViewById(R.id.imageView6);
        mThirdFilterPreviewImageView = (ImageView) findViewById(R.id.imageView7);
        mFourthFilterPreviewImageView = (ImageView) findViewById(R.id.imageView8);

        mTickImageView = (ImageView) findViewById(R.id.imageView4);
        mTickImageView.setOnClickListener(new View.OnClickListener() {
           @Override
            public void onClick(View v){
               Intent intent = new Intent(ControlActivity.this,ImagePreviewActivity.class);
               startActivity(intent);
           }

        });

        mCenterImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                if(ContextCompat.checkSelfPermission(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    if(ActivityCompat.shouldShowRequestPermissionRationale(ControlActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                        new MaterialDialog.Builder(ControlActivity.this).title("Permission Required")
                                .content("You need to give the storage access to easily save your filtered image")
                                .negativeText("No")
                                .positiveText("Yes")
                                .canceledOnTouchOutside(true)
                                .show();
                    }
                    else{
                        ActivityCompat.requestPermissions(ControlActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},MY_PERMISSIONS_REQUEST_STARAGE_PERMISSION);

                    }
                        return;
                }

               Intent intent = new Intent();
               intent.setType("image/*");
               intent.setAction(Intent.ACTION_GET_CONTENT);
               startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
            }
        });

    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        switch (requestCode){
            case MY_PERMISSIONS_REQUEST_STARAGE_PERMISSION:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    new MaterialDialog.Builder(ControlActivity.this).title("Permission Required")
                            .content("Thank You!")
                            .positiveText("Ok")
                            .canceledOnTouchOutside(true)

                            .show();

                }
                else {
                    Log.d(TAG,"Permission denied");
                }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK){
            Uri selectedImageUri = data.getData();

            Picasso.with(ControlActivity.this).load(selectedImageUri).fit().centerInside().into(mCenterImageView);

            Picasso.with(ControlActivity.this).load(selectedImageUri).fit().centerInside().into(mFirstFilterPreviewImageView);
            Picasso.with(ControlActivity.this).load(selectedImageUri).fit().centerInside().into(mSecondFilterPreviewImageView);
            Picasso.with(ControlActivity.this).load(selectedImageUri).fit().centerInside().into(mThirdFilterPreviewImageView);
            Picasso.with(ControlActivity.this).load(selectedImageUri).fit().centerInside().into(mFourthFilterPreviewImageView);


        }
    }

}
