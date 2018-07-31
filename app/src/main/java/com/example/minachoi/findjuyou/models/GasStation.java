package com.example.minachoi.findjuyou.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GasStation {
    @SerializedName("RESULT")
    @Expose
    private RESULT rESULT;

    public RESULT getRESULT() {
        return rESULT;
    }

    public void setRESULT(RESULT rESULT) {
        this.rESULT = rESULT;
    }
}
