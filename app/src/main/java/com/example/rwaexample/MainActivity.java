package com.example.rwaexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    public static final int CAMERA_REQUEST_CODE = 1337;
    public static final int DOCUMENT_VIEWER_REQUEST_CODE = 1272;

    /**
     * 1 - open RW camera applet
     * A -> Create an Intent
     * B -> Start a new activity, with the intent from part A
     * <p>
     * 2 - Capture the returning information (Photo)
     * A -> Identifier
     * B -> Save the photo to a bitmap object
     * <p>
     * 3 - Present the photo on the HMT
     * A -> Create an ImageView
     * B -> Set the photo(Bitmap) to the Image View
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonClicked(View view) {
        Log.i("RWA", "Button was clicked!");
        /**
         * Open the RW Camera Applet
         * Define an intent
         * Define a RequestCode
         * Start the camera activity in a way to get a result
         */

        Intent cameraIntent;
        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
    }

    public void onClickDocumentViewer(View documentViewerButton) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        /**
         * Check the resultCode, did the camera camera application finish properly
         * Check the requestCode and make sure its the same as the code we sent
         * Check the returning intent, isn't null then get the data
         */

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case CAMERA_REQUEST_CODE:
                    if (data != null) {
                        Bitmap returnedPhoto;
                        returnedPhoto = data.getExtras().getParcelable("data");
                        ImageView photoView = findViewById(R.id.imageView3);
                        photoView.setImageBitmap(returnedPhoto);
                        Log.d("RWA_Camera", "Photo Returned");

                        Intent documentViewerIntent = new Intent(Intent.ACTION_VIEW);
                        documentViewerIntent.addCategory(Intent.CATEGORY_DEFAULT);
                        /**
                         * Set the data URI
                         */
                        documentViewerIntent.setDataAndType(data.getData(), "image/jpg");
                        documentViewerIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(documentViewerIntent);
                    }
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}