package com.example.minachoi.findjuyou.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OIL {
    //    SerializedName 어노테이션은 JSON결과값에서 field이름을 나타냄.
    @SerializedName("UNI_ID")
    @Expose
    private String uNIID;

    @SerializedName("POLL_DIV_CD")
    @Expose
    private String pOLLDIVCD;

    @SerializedName("OS_NM")
    @Expose
    private String oSNM;

    @SerializedName("PRICE")
    @Expose
    private Integer pRICE;

    @SerializedName("DISTANCE")
    @Expose
    private Double dISTANCE;

    @SerializedName("GIS_X_COOR")
    @Expose
    private Double gISXCOOR;

    @SerializedName("GIS_Y_COOR")
    @Expose
    private Double gISYCOOR;

    public String getUNIID() {
        return uNIID;
    }

    public void setUNIID(String uNIID) {
        this.uNIID = uNIID;
    }

    public String getPOLLDIVCD() {
        return pOLLDIVCD;
    }

    public void setPOLLDIVCD(String pOLLDIVCD) {
        this.pOLLDIVCD = pOLLDIVCD;
    }

    public String getOSNM() {
        return oSNM;
    }

    public void setOSNM(String oSNM) {
        this.oSNM = oSNM;
    }

    public Integer getPRICE() {
        return pRICE;
    }

    public void setPRICE(Integer pRICE) {
        this.pRICE = pRICE;
    }

    public Double getDISTANCE() {
        return dISTANCE;
    }

    public void setDISTANCE(Double dISTANCE) {
        this.dISTANCE = dISTANCE;
    }

    public Double getGISXCOOR() {
        return gISXCOOR;
    }

    public void setGISXCOOR(Double gISXCOOR) {
        this.gISXCOOR = gISXCOOR;
    }

    public Double getGISYCOOR() {
        return gISYCOOR;
    }

    public void setGISYCOOR(Double gISYCOOR) {
        this.gISYCOOR = gISYCOOR;
    }

}