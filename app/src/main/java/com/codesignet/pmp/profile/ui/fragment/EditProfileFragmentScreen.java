package com.codesignet.pmp.profile.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.codesignet.pmp.R;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileUpdateObject;
import com.codesignet.pmp.profile.data_access_layer.pojos.User;
import com.codesignet.pmp.profile.view.OnUpdateCallBack;
import com.codesignet.pmp.profile.view.ProfileNavigateCallBack;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.echodev.resizer.Resizer;

public class EditProfileFragmentScreen extends Fragment {

    @BindView(R.id.til_username)
    TextInputLayout usernameLayout;
    @BindView(R.id.et_username)
    EditText username;
    @BindView(R.id.iv_profile)
    ImageView iv_profile;
    @BindView(R.id.iv_profile_edit)
    ImageView uploadImage;

    private int GALLERY = 1, CAMERA = 2;
    private OnUpdateCallBack listeners;
    private ProfileNavigateCallBack navigateCallBack;
    private String selected_path = "";
    private User user;
    private Bitmap profileBitmap;
    public static EditProfileFragmentScreen newInstance(OnUpdateCallBack listeners,
                                                        ProfileNavigateCallBack navigateCallBack, User user, Bitmap profileBitmap) {
        EditProfileFragmentScreen fragment = new EditProfileFragmentScreen();
        fragment.listeners = listeners;
        fragment.user = user;
        fragment.navigateCallBack = navigateCallBack;
        fragment.profileBitmap = profileBitmap;
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_profile, container, false);
        ButterKnife.bind(this, view);
        username.setText(user.getName());
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        if (profileBitmap != null) {
            profileBitmap.compress(Bitmap.CompressFormat.PNG, 50, stream);
            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(iv_profile);
            Glide.with(this)
                    .load(stream.toByteArray())
                    .asBitmap()
                    .into(iv_profile);
        }
        return view;
    }

    private boolean isValid() {
        boolean isValid = true;
        if (username.getText() == null || username.getText().toString().length() == 0) {
            usernameLayout.setError(getString(R.string.full_name_error_message));
            if (isValid) {
                requestFocus(usernameLayout);
            }
            isValid = false;
        } else {
            usernameLayout.setErrorEnabled(false);
        }
        return isValid;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @OnClick(R.id.btn_update_user)
    public void onUpdateClicked() {
        if (!isValid())
            return;
        ProfileUpdateObject pojo = new ProfileUpdateObject();
        pojo.setName(username.getText().toString());
        listeners.onUpdateClicked(pojo);
    }

    @OnClick(R.id.ll_change_password)
    public void onchangePassword() {
        listeners.onChangePasswordButtonClicked();
    }

    @OnClick(R.id.iv_profile_edit)
    public void openGallary() {
        showPictureDialog();
    }

    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(getActivity());
        pictureDialog.setTitle("Select Action");
        String[] pictureDialogItems = {
                "Select photo from gallery",
                "Capture photo from camera"};
        pictureDialog.setItems(pictureDialogItems,
                (dialog, which) -> {
                    switch (which) {
                        case 0:
                            choosePhotoFromGallary();
                            break;
                        case 1:
                            takePhotoFromCamera();
                            break;
                    }
                });
        pictureDialog.show();
    }

    public void choosePhotoFromGallary() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(galleryIntent, GALLERY);
    }

    private void takePhotoFromCamera() {
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_CANCELED) {
            return;
        }
        if (requestCode == GALLERY) {
            if (data != null) {
                Uri selectedFile = data.getData();
                selected_path = getPath(selectedFile);
                File selectedAttachment = CompressImage(new File(selected_path));
                try {
                    profileBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedFile);
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    profileBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    Glide.with(this)
                            .load(stream.toByteArray())
                            .asBitmap()
                            .into(iv_profile);
                    saveImage(selectedAttachment);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else if (requestCode == CAMERA) {
            if (data != null) {
                Uri selectedFile = data.getData();
                if (selectedFile == null) {
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    selectedFile = getImageUri(getActivity(), imageBitmap);
                }
                selected_path = getPath(selectedFile);
                File selectedAttachment = CompressImage(new File(selected_path));

                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.PNG, 50, stream);
                Log.i("Hello", thumbnail.toString());
                Glide.with(this)
                        .load(stream.toByteArray())
                        .asBitmap()
                        .into(iv_profile);
                saveImage(selectedAttachment);
            }
        }
    }

    public void saveImage(File uri) {
        listeners.onUploadClicked(uri);
    }

    private String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        int column_index = 0;
        if (cursor != null) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        } else
            return uri.getPath();
    }

    private File CompressImage(File newAttachment) {
        File imagePath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "");
        File resizedImage = null;
        try {
            resizedImage = new Resizer(getActivity())
                    .setTargetLength(480)
                    .setQuality(50)
                    .setOutputFormat("PNG")
                    .setOutputFilename("ProfileImage")
                    .setOutputDirPath(imagePath.getPath())
                    .setSourceImage(newAttachment)
                    .getResizedFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resizedImage;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
