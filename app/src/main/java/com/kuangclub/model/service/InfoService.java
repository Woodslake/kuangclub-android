package com.kuangclub.model.service;

import com.kuangclub.http.ResponseBody;
import com.kuangclub.model.bean.Info;
import com.kuangclub.model.bean.InfoType;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Woodslake on 2018/8/9.
 */
public interface InfoService {

    @GET(Api.getInfoList)
    Call<ResponseBody<List<Info>>> getInfoList();

    @GET(Api.getInfoTypeList)
    Call<ResponseBody<List<InfoType>>> getInfoTypeList(@Query("type")String type, @Query("page")int page);

}
