package com.codesignet.pmp.notes.view;

import com.codesignet.pmp.notes.data_access_layer.pojos.NotesResponse;

public interface NotesPresenterCallBack {
    void onNotesSynced(NotesResponse response);
    void onFailure(Throwable e);
}
