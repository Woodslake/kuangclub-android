package com.kuangclub.model.service;

import com.kuangclub.http.ResponseBody;
import com.kuangclub.model.bean.Info;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Woodslake on 2018/8/9.
 */
public interface InfoService {

    @GET(Api.getInfoList)
    Call<ResponseBody<List<Info>>> getInfoList();
}
