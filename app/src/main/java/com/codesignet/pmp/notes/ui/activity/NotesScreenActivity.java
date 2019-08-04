package com.codesignet.pmp.notes.ui.activity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.codesignet.pmp.R;
import com.codesignet.pmp.app.AppRouter;
import com.codesignet.pmp.app.Constants;
import com.codesignet.pmp.basics.BaseActivity;
import com.codesignet.pmp.notes.data_access_layer.NoteInteractor;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;
import com.codesignet.pmp.notes.dependencies.DaggerNotesComponent;
import com.codesignet.pmp.notes.dependencies.NotesModule;
import com.codesignet.pmp.notes.presenter.NotePresenter;
import com.codesignet.pmp.notes.ui.fragment.MyNotesScreenFragment;
import com.codesignet.pmp.notes.ui.fragment.QuestionNotesScreen;
import com.codesignet.pmp.notes.view.NoteCardUtilities;
import com.codesignet.pmp.notes.view.NoteView;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NotesScreenActivity extends BaseActivity<NoteInteractor, NoteView, NotePresenter>
        implements NoteView, NoteCardUtilities {


    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.bmb)
    BoomMenuButton bmb;
    private ArrayList<Note> myNotes;
    private ArrayList<Note> questionNotes;
    private ArrayList<Note> allNotes;
    Fragment selectedFragment = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        InitializeDagger();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        InitializeMenu();
        myNotes = new ArrayList();
        questionNotes = new ArrayList();
        allNotes = new ArrayList();
        mPresenter.getAllNotesFromAPI(getApplicationContext());
        InitializeNavigation();
    }

    private void InitializeNavigation() {
        navigation.setOnNavigationItemSelectedListener
                (item -> {
                    switch (item.getItemId()) {
                        case R.id.action_item1:
                            selectedFragment = MyNotesScreenFragment.newInstance(myNotes, this);
                            break;
                        case R.id.action_item2:
                            selectedFragment = QuestionNotesScreen.newInstance(questionNotes, this);
                            break;
                    }
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.frame_layout, selectedFragment);
                    transaction.commit();
                    return true;
                });
    }

    private void InitializeDagger() {
        DaggerNotesComponent
                .builder()
                .notesModule(new NotesModule(this))
                .build()
                .inject(this);
    }

    private void InitializeMenu() {
        String[] menuItemsColors = getResources().getStringArray(R.array.menu_items_colors);
        String[] menuItems = getResources().getStringArray(R.array.menu_items);
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
                            this.onBoomDidHide();
                            break;
                        case 4:
                            AppRouter.navigateToAskInstructorScreen(getApplicationContext());
                            finish();
                            break;
                        case 5:
                            AppRouter.navigateToProfileScreen(getApplicationContext());
                            finish();
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
    public void showAllNotes(List<Note> notes) {
        allNotes.clear();
        myNotes.clear();
        questionNotes.clear();
        splitNotes(notes);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, MyNotesScreenFragment.newInstance(myNotes, this));
        transaction.commit();
    }

    @Override
    public void showLoader() {
    }

    @Override
    public void hideLoader() {
    }

    @Override
    public void onDeleteNote(Note note, int position, String type) {
        showDeleteDialog(note, position, getResources().getString(R.string.delete_confirmation_title), "Are you sure want to remove this note ");
    }

    public void showDeleteDialog(Note note, int position, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    note.setIsdeleted(1);
                    mPresenter.deleteNote(note, getApplicationContext());
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(getIntent());
                    overridePendingTransition(0, 0);
//                    ((MyNotesScreenFragment)selectedFragment).
//                    questionsList.remove(questionsList);
//                    askAdapter.notifyDataSetChanged();
//                    askAdapter.notifyItemRemoved(position);
                })
                .setNegativeButton("No", (dialog, id) -> dialog.cancel());
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onNoteClicked(Note note) {
        Note newnote = new Note();

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.view_note_layout, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        TextView question_data = dialogView.findViewById(R.id.question_data);
        EditText noteData = dialogView.findViewById(R.id.et_message);

        if (note.getType().equals(Constants.QUESTION_TYPE)) {
            question_data.setVisibility(View.VISIBLE);
            question_data.setText(note.getQuestion().getQuestionE());
            noteData.setText(note.getNote());
        } else {
            question_data.setVisibility(View.INVISIBLE);
            noteData.setText(note.getNote());
        }
        dialogBuilder.setTitle(getApplicationContext().getResources().getString(R.string.note));
        dialogBuilder.setPositiveButton(getApplicationContext().getResources().getString(R.string.update), (dialog, whichButton) -> {
            newnote.setType(note.getType());
            newnote.setId(note.getId());
            newnote.setUpdatedAt(getCurrentTime());
            newnote.setNote(noteData.getText().toString());
            newnote.setUpdatedAt(getCurrentTime());
            newnote.setDatabaseId(note.getDatabaseId());
            mPresenter.updateNote(newnote, getApplicationContext());
        });
        dialogBuilder.setNegativeButton(getApplicationContext().getResources().getString(R.string.cancel), (dialog, whichButton) -> {
            dialog.dismiss();
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private String getCurrentTime() {
      String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());
    }

    @Override
    public void onNoteAdd(Note note) {
        mPresenter.addNewNote(note, getApplicationContext());
    }

    @Override
    public void refresh() {
        mPresenter.getAllNotesFromAPI(getApplicationContext());
    }

    private void splitNotes(List<Note> notes) {
        allNotes.addAll(notes);
        for (int i = 0; i < allNotes.size(); i++) {
            if (allNotes.get(i).getType().equals(Constants.QUESTION_TYPE))
                questionNotes.add(allNotes.get(i));
            else {
                myNotes.add(allNotes.get(i));
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    AppRouter.navigateToHomeScreen(getApplicationContext());
    }
}
