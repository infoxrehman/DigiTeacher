package com.digi.digiteacher;

public class CourseModel {
    private String su_id,su_name,auther_name,book_image, description, su_price, su_delete, su_status, added_by, added_on;

    public CourseModel() {
    }

    public CourseModel(String su_id, String su_name, String auther_name, String book_image, String description, String su_price, String su_delete, String su_status, String added_by, String added_on) {
        this.su_id = su_id;
        this.su_name = su_name;
        this.auther_name = auther_name;
        this.book_image = book_image;
        this.description = description;
        this.su_price = su_price;
        this.su_delete = su_delete;
        this.su_status = su_status;
        this.added_by = added_by;
        this.added_on = added_on;
    }

    public String getSu_id() {
        return su_id;
    }

    public void setSu_id(String su_id) {
        this.su_id = su_id;
    }

    public String getSu_name() {
        return su_name;
    }

    public void setSu_name(String su_name) {
        this.su_name = su_name;
    }

    public String getAuther_name() {
        return auther_name;
    }

    public void setAuther_name(String auther_name) {
        this.auther_name = auther_name;
    }

    public String getBook_image() {
        return book_image;
    }

    public void setBook_image(String book_image) {
        this.book_image = book_image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSu_price() {
        return su_price;
    }

    public void setSu_price(String su_price) {
        this.su_price = su_price;
    }

    public String getSu_delete() {
        return su_delete;
    }

    public void setSu_delete(String su_delete) {
        this.su_delete = su_delete;
    }

    public String getSu_status() {
        return su_status;
    }

    public void setSu_status(String su_status) {
        this.su_status = su_status;
    }

    public String getAdded_by() {
        return added_by;
    }

    public void setAdded_by(String added_by) {
        this.added_by = added_by;
    }

    public String getAdded_on() {
        return added_on;
    }

    public void setAdded_on(String added_on) {
        this.added_on = added_on;
    }
}
