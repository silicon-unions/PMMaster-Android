package com.codesignet.pmp.ask_instructor.data_access_layer;

import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.AskInstructorResponse;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObject;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.QuestionObjectID;
import com.codesignet.pmp.ask_instructor.data_access_layer.pojos.UpdateQuestionResponse;
import com.codesignet.pmp.basics.BaseResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @GET("api/instructorQuestions")
    Observable<AskInstructorResponse> getList();

    @POST("api/instructorQuestion/add")
    Observable<BaseResponse> askQuestion(@Body QuestionObject question);

    @POST("api/instructorQuestion/delete")
    Observable<BaseResponse> deleteQuestion(@Body QuestionObjectID question);

    @POST("api/instructorQuestion/seen")
    Observable<UpdateQuestionResponse> updateQuestionStatus(@Body QuestionObjectID question);
}
