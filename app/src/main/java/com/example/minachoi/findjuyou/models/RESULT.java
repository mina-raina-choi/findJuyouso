package com.example.minachoi.findjuyou.models;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RESULT {

    @SerializedName("OIL")
    @Expose
    private List<OIL> oIL;

    public List<OIL> getOIL() {
        return oIL;
    }

    public void setOIL(List<OIL> oIL) {
        this.oIL = oIL;
    }

}