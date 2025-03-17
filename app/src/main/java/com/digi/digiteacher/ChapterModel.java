package com.digi.digiteacher;

public class ChapterModel {
    private String fk_subject_id,ch_name,ch_id,su_name;

    public ChapterModel() {
    }

    public ChapterModel(String fk_subject_id, String ch_name, String ch_id, String su_name) {
        this.fk_subject_id = fk_subject_id;
        this.ch_name = ch_name;
        this.ch_id = ch_id;
        this.su_name = su_name;
    }

    public String getFk_subject_id() {
        return fk_subject_id;
    }

    public void setFk_subject_id(String fk_subject_id) {
        this.fk_subject_id = fk_subject_id;
    }

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public String getCh_id() {
        return ch_id;
    }

    public void setCh_id(String ch_id) {
        this.ch_id = ch_id;
    }

    public String getSu_name() {
        return su_name;
    }

    public void setSu_name(String su_name) {
        this.su_name = su_name;
    }
}
