package com.codesignet.pmp.profile.ui.activity;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.profile.data_access_layer.ProfileInteractor;
import com.codesignet.pmp.profile.data_access_layer.pojos.ProfileUpdateObject;
import com.codesignet.pmp.profile.data_access_layer.pojos.User;
import com.codesignet.pmp.profile.dependencies.DaggerProfileComponent;
import com.codesignet.pmp.profile.dependencies.ProfileModule;
import com.codesignet.pmp.profile.presenter.ProfilePresenter;
import com.codesignet.pmp.profile.ui.fragment.ChangePasswordFragmentScreen;
import com.codesignet.pmp.profile.ui.fragment.EditProfileFragmentScreen;
import com.codesignet.pmp.profile.ui.fragment.ProfileDataScreen;
import com.codesignet.pmp.profile.view.OnUpdateCallBack;
import com.codesignet.pmp.profile.view.ProfileNavigateCallBack;
import com.codesignet.pmp.profile.view.ProfileView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;

public class ProfileActivityScreen extends BaseActivity<ProfileInteractor, ProfileView, ProfilePresenter>
        implements ProfileNavigateCallBack, ProfileView, OnUpdateCallBack {


    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout refresh;

    private static final int MY_CAMERA_REQUEST_CODE = 100;
    @BindView(R.id.bmb)
    BoomMenuButton bmb;
    private User user;
    private Bitmap bitmap;
    SharedPreferences exam;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);
        InitializeMenu(menuItems,menuItemsColors);
        if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    MY_CAMERA_REQUEST_CODE);
        }
        loadImageFromStorage();

        user = new User();
        SharedPreferences prefs = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);
        user.setName(prefs.getString(Constants.USER_NAME, "No name defined"));
        user.setEmail(prefs.getString(Constants.USER_EMAIL, "No email defined"));
        exam = getSharedPreferences(Constants.PREF_NAME, MODE_PRIVATE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ProfileDataScreen.newInstance(user, this
                        , exam.getLong(Constants.Exam_TIME, 0), getPreferredTiem(), bitmap))
                .addToBackStack(null)
                .commit();
    }

    void InitializeDagger() {
        DaggerProfileComponent
                .builder()
                .profileModule(new ProfileModule(this))
                .build()
                .inject(this);
    }

    private void InitializeMenu(String[] menuItems, String[] menuItemsColors) {
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++) {
            HamButton.Builder builder = new HamButton.Builder()
                    .normalText(menuItems[i]).normalText(menuItems[i]).pieceColor(Color.parseColor(menuItemsColors[i])).normalColor(Color.parseColor(menuItemsColors[i]));
            bmb.setOnBoomListener(new OnBoomListener() {
                @Override
                public void onClicked(int index, BoomButton boomButton) {
                    switch (index) {
                        case 0:
                            AppRouter.navigateToHomeScreen(getApplicationContext());
                            finish();
                            break;
                        case 1:
                            AppRouter.navigateToPractices(getApplicationContext());
                            finish();
                            break;
                        case 2:
                            AppRouter.navigateToExamOptionScreen(getApplicationContext());
                            finish();
                            break;
                        case 3:
                            AppRouter.navigateToNoteScreen(getApplicationContext());
                            finish();
                            break;
                        case 4:
                            AppRouter.navigateToAskInstructorScreen(getApplicationContext());
                            finish();
                            break;
                        case 5:
                            this.onBoomDidHide();
                            break;
                    }
                }

                @Override
                public void onBackgroundClick() {

                }

                @Override
                public void onBoomWillHide() {

                }

                @Override
                public void onBoomDidHide() {

                }

                @Override
                public void onBoomWillShow() {

                }

                @Override
                public void onBoomDidShow() {

                }
            });
            bmb.addBuilder(builder);
            bmb.setAutoBoom(false);
            bmb.setNormalColor(getResources().getColor(R.color.off_white));
        }
    }

    @Override
    public void gotoEditProfileScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, EditProfileFragmentScreen.newInstance(this, this,user,bitmap))
                .commit();
    }

    @Override
    public File getExternalFilesDir(String type) {
        return super.getExternalFilesDir(type);
    }

    @Override
    public void gotoProfileData(User user) {
        this.user = user;
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ProfileDataScreen.newInstance(user, this
                        , exam.getLong(Constants.Exam_TIME,0), getPreferredTiem(), bitmap))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoProfileData() {
        gotoProfileData(user);
    }

    @Override
    public void gotoChangePasswordScreen() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, ChangePasswordFragmentScreen.newInstance(this, this))
                .commit();
    }

    @Override
    public void gotoHomeScreen() {
        AppRouter.navigateToHomeScreen(getApplicationContext());
        finish();
    }

    @Override
    public void onBackPressed() {
        gotoHomeScreen();
    }

    @Override
    public void showLoader() {
        refresh.setRefreshing(true);
    }

    @Override
    public void hideLoader() {
        refresh.setRefreshing(false);
    }

    @Override
    public void showUserData(User user) {
        mPresenter.getUserImage(getAccessToken());
        this.user = user;
    }

    @Override
    public void showUserImage(ResponseBody responseBody) {
        if (writeResponseBodyToDisk(responseBody)) {
            gotoProfileData(user);
        }
    }

    @Override
    public void refresh() {
        showLoader();
        mPresenter.getUserData(getAccessToken());
    }

    @Override
    public void onUpdateClicked(ProfileUpdateObject newData) {
        mPresenter.updateProfileData(getAccessToken(), newData);
    }

    @Override
    public void onChangePasswordButtonClicked() {
        gotoChangePasswordScreen();
    }

    @Override
    public void onUploadClicked(File file) {
        mPresenter.updateProfileImage(getAccessToken(), file);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_REQUEST_CODE) {

            for(int i =0 ; i < permissions.length; i++){
//                Toast.makeText(this, permissions[i] + grantResults[i] , Toast.LENGTH_LONG).show();
            }

        }
    }

    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            File futureStudioIconFile = new File(getExternalFilesDir(null) + "ProfileImage.png");

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];

                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(futureStudioIconFile);
                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
//                    Log.d("Hello", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                outputStream.flush();
                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    private void loadImageFromStorage() {
        try {
            File f = new File(getExternalFilesDir(null) + "ProfileImage.png");
            if (f.exists()) {
                InputStream is = new FileInputStream(f);
                Bitmap bitmap = BitmapFactory.decodeStream(is);
                this.bitmap = bitmap;
            } else {
                bitmap = null;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            bitmap = null;
        }
    }


}
