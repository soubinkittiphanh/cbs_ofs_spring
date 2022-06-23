package com.ldb.core.model;

public class T24Response {
    private String mti;
    private String desc;
    
    public T24Response(String mti, String desc) {
        this.mti = mti;
        this.desc = desc;
    }
    public String getMti() {
        return mti;
    }
    public void setMti(String mti) {
        this.mti = mti;
    }
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    
}
