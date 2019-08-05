package com.codesignet.pmp.notes.data_access_layer.network;

import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.notes.data_access_layer.pojos.NotesResponse;
import com.codesignet.pmp.notes.data_access_layer.pojos.SyncNodeRequest;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("api/note/sync")
    Observable<NotesResponse> syncAllNotes(@Body SyncNodeRequest request);

    @POST("api/updateUserLevel")
    Observable<BaseResponse> syncLevels(@Body UserLevels userLevels);
}
