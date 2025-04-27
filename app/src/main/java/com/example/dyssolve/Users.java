package com.example.dyssolve;

public class Users {

    String user_name, dob, drop_items, school, phone, password, email;

    public Users() {
    }

    public Users(String user_name, String dob, String drop_items, String school, String phone, String password, String email) {
        this.user_name = user_name;
        this.dob = dob;
        this.drop_items = drop_items;
        this.school = school;
        this.phone = phone;
        this.password = password;
        this.email = email;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getDrop_items() {
        return drop_items;
    }

    public void setDrop_items(String drop_items) {
        this.drop_items = drop_items;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
