package com.codepath.parsetagram.ui.camera;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.model.Post;
import com.codepath.parsetagram.utils.PhotoUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    private static final int REQUEST_PICTURE = 0;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    public CameraFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.ivPicture) protected ImageView mPictureImageView;
    @BindView(R.id.etCaption) protected EditText mCaptionEditText;
    @BindView(R.id.btnSubmit) protected Button mSubmitButton;

    private CameraCallbacks mListener;
    private Post mPost;
    private String mPath;
    private Uri mUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CameraCallbacks) {
            mListener = (CameraCallbacks) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*
     * UI Listeners
     */

    @OnClick(R.id.fabPhoto)
    protected void onClickTakePhoto() {
        if (getActivity() == null) return;
        Pair<String, Uri> pair = PhotoUtils.dispatchTakePictureIntent(this, REQUEST_PICTURE);
        if (pair != null) {
            mPath = pair.first;
            mUri = pair.second;
        }
    }

    @OnClick(R.id.btnSubmit)
    protected void onClickSubmit() {
//        File photoFile = getPhotoFileUri(photoFileName);
//        ParseFile parseFile = new ParseFile(photoFile);
//        Post post = new Post();
//        post.setMedia(parseFile);
//        post.saveInBackground();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_PICTURE) {
            PhotoUtils.addImageToGallery(getContext(), mUri);
            PhotoUtils.setImageBitmap(mPictureImageView, mPath);
        }
    }

}
