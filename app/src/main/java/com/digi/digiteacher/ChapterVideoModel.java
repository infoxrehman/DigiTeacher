package com.digi.digiteacher;

public class ChapterVideoModel {

    private int av_id;
    private String slug;
    private int fk_subject_id;
    private int fk_chapter_id;
    private String av_name;
    private String av_url;
    private String video_type_url_file;
    private String video_type;
    private int av_status;
    private int av_delete;
    private int added_by;
    private String added_on;
    private String ch_name;
    private String su_name;

    public ChapterVideoModel() {
    }

    public ChapterVideoModel(int av_id, String slug, int fk_subject_id, int fk_chapter_id, String av_name, String av_url, String video_type_url_file, String video_type, int av_status, int av_delete, int added_by, String added_on, String ch_name, String su_name) {
        this.av_id = av_id;
        this.slug = slug;
        this.fk_subject_id = fk_subject_id;
        this.fk_chapter_id = fk_chapter_id;
        this.av_name = av_name;
        this.av_url = av_url;
        this.video_type_url_file = video_type_url_file;
        this.video_type = video_type;
        this.av_status = av_status;
        this.av_delete = av_delete;
        this.added_by = added_by;
        this.added_on = added_on;
        this.ch_name = ch_name;
        this.su_name = su_name;
    }

    public int getAv_id() {
        return av_id;
    }

    public void setAv_id(int av_id) {
        this.av_id = av_id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getFk_subject_id() {
        return fk_subject_id;
    }

    public void setFk_subject_id(int fk_subject_id) {
        this.fk_subject_id = fk_subject_id;
    }

    public int getFk_chapter_id() {
        return fk_chapter_id;
    }

    public void setFk_chapter_id(int fk_chapter_id) {
        this.fk_chapter_id = fk_chapter_id;
    }

    public String getAv_name() {
        return av_name;
    }

    public void setAv_name(String av_name) {
        this.av_name = av_name;
    }

    public String getAv_url() {
        return av_url;
    }

    public void setAv_url(String av_url) {
        this.av_url = av_url;
    }

    public String getVideo_type_url_file() {
        return video_type_url_file;
    }

    public void setVideo_type_url_file(String video_type_url_file) {
        this.video_type_url_file = video_type_url_file;
    }

    public String getVideo_type() {
        return video_type;
    }

    public void setVideo_type(String video_type) {
        this.video_type = video_type;
    }

    public int getAv_status() {
        return av_status;
    }

    public void setAv_status(int av_status) {
        this.av_status = av_status;
    }

    public int getAv_delete() {
        return av_delete;
    }

    public void setAv_delete(int av_delete) {
        this.av_delete = av_delete;
    }

    public int getAdded_by() {
        return added_by;
    }

    public void setAdded_by(int added_by) {
        this.added_by = added_by;
    }

    public String getAdded_on() {
        return added_on;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }

    public String getCh_name() {
        return ch_name;
    }

    public void setCh_name(String ch_name) {
        this.ch_name = ch_name;
    }

    public String getSu_name() {
        return su_name;
    }

    public void setSu_name(String su_name) {
        this.su_name = su_name;
    }
}