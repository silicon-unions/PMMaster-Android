package com.codesignet.pmp.exam.data_access_layer.network_manager;


import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.ExamObject;
import com.codesignet.pmp.exam.data_access_layer.pojos.QuestionListResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.notes.data_access_layer.pojos.Note;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface API {

    @POST("api/questions/exam")
    Observable<QuestionListResponse> getUserExam(@Body ExamObject examObject);

    @POST("api/note/add")
    Observable<BaseResponse> addNote(@Body Note note);

    @POST("api/reportQuestion")
    Observable<BaseResponse> reportQuestion(@Body ReportObject obj);
}
