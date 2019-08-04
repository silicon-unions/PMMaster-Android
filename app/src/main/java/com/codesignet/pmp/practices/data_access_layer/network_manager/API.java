package com.codesignet.pmp.practices.data_access_layer.network_manager;


import com.codesignet.pmp.basics.BaseResponse;
import com.codesignet.pmp.exam.data_access_layer.pojos.ReportObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesListResponse;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.PracticesUpdateObject;
import com.codesignet.pmp.practices.data_access_layer.pojos.UserLevels;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface API {

    @POST("api/questions")
    Observable<PracticesListResponse> getQuestionsByLevel(@Body PracticesObject practicesObject);

    @POST("api/updateUserLevel")
    Observable<BaseResponse> updateUserLevel(@Body PracticesUpdateObject updateObject);


    @POST("api/reportQuestion")
    Observable<BaseResponse> reportQuestion(@Body ReportObject obj);

    @GET("api/userLevels")
    Observable<UserLevels> getLevels();
}
