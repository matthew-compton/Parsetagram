package com.codepath.parsetagram.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;

public class PhotoUtils {

    private static final String TAG = PhotoUtils.class.toString();
    private static final String AUTHORITY = "com.codepath.parsetagram";

    public static String dispatchTakePictureIntent(Fragment fragment, int requestCode) {
        if (fragment == null) return null;
        Activity activity = fragment.getActivity();
        if (activity == null) return null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(activity.getPackageManager()) == null) {
            return null;
        }

        File photo = null;
        try {
            photo = getTempImageFile(activity);
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }

        if (photo == null) {
            return null;
        }
        String path = photo.getAbsolutePath();
        Uri uri = FileProvider.getUriForFile(activity, AUTHORITY, photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        fragment.startActivityForResult(intent, requestCode);
        return path;
    }

    private static File getTempImageFile(Context context) throws IOException {
        String fileNamePrefix = "JPEG_" + DateUtils.getCurrentTimestamp() + "_";
        String fileNameSuffix = ".jpg";
        File directory = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return File.createTempFile(fileNamePrefix, fileNameSuffix, directory);
    }

    public static void setImageBitmap(ImageView imageView, String path) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        int photoW = options.outWidth;
        int photoH = options.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;
        options.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(path, options);
        imageView.setImageBitmap(bitmap);
    }

}
