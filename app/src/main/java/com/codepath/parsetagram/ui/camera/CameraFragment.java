package com.codepath.parsetagram.ui.camera;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.codepath.parsetagram.R;
import com.codepath.parsetagram.model.Post;
import com.codepath.parsetagram.utils.PhotoUtils;
import com.parse.ParseFile;
import com.parse.ParseUser;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;

public class CameraFragment extends Fragment {

    private static final int REQUEST_CODE_PICTURE = 0;

    public static CameraFragment newInstance() {
        return new CameraFragment();
    }

    public CameraFragment() {
        // Required empty public constructor
    }

    @BindView(R.id.viewCreatePost) protected View mCreatePostView;
    @BindView(R.id.ivPicture) protected ImageView mPictureImageView;
    @BindView(R.id.etCaption) protected EditText mCaptionEditText;
    @BindView(R.id.btnSubmit) protected Button mSubmitButton;
    @BindView(R.id.viewProgress) protected View mProgressView;

    private CameraCallbacks mListener;
    private CameraState mState;
    private Post mPost;
    private String mCurrentPath;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        ButterKnife.bind(this, view);
        updateUI(CameraState.INITIAL);
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK) {
            updateUI(CameraState.INITIAL);
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE_PICTURE:
                updateUI(CameraState.CAPTIONING);
                break;
            default:
                updateUI(CameraState.INITIAL);
                break;
        }
    }

    /*
     * UI Updates
     */

    private void updateUI(CameraState state) {
        mState = state;
        switch (mState) {
            default:
            case INITIAL:
                mProgressView.setVisibility(View.GONE);
                mPictureImageView.setImageBitmap(null);
                mCaptionEditText.setText("");
                mCaptionEditText.setEnabled(false);
                mSubmitButton.setEnabled(false);
                mCreatePostView.setVisibility(View.VISIBLE);
                break;
            case PROGRESS:
                mCreatePostView.setVisibility(View.GONE);
                mProgressView.setVisibility(View.VISIBLE);
                break;
            case CAPTIONING:
                mProgressView.setVisibility(View.GONE);
                PhotoUtils.setImageBitmap(mPictureImageView, mCurrentPath);
                mCaptionEditText.setEnabled(true);
                mSubmitButton.setEnabled(true);
                mCreatePostView.setVisibility(View.VISIBLE);
                break;
        }
    }

    /*
     * UI Listeners
     */

    @OnClick(R.id.fabPhoto)
    protected void onClickTakePhoto() {
        mCurrentPath = PhotoUtils.dispatchTakePictureIntent(this, REQUEST_CODE_PICTURE);
        updateUI(CameraState.PROGRESS);
    }

    @OnClick(R.id.btnSubmit)
    protected void onClickSubmit() {
        ParseUser author = ParseUser.getCurrentUser();
        ParseFile media = new ParseFile(new File(mCurrentPath));
        String caption = mCaptionEditText.getText().toString();
        mPost = Post.newInstance(author, media, caption);
        updateUI(CameraState.INITIAL);
    }

}
