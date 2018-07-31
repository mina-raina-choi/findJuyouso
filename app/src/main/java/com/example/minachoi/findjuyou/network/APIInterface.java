package com.example.minachoi.findjuyou.network;

import com.example.minachoi.findjuyou.models.GasStation;
import com.example.minachoi.findjuyou.models.RESULT;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("/api/aroundAll.do")
    Call<GasStation> getNearestGasStation(@Query("code") String code, @Query("x") String x, @Query("y") String y,
                                          @Query("radius") String radius, @Query("sort") String sort, @Query("prodcd") String prodcd, @Query("out") String out);
}
