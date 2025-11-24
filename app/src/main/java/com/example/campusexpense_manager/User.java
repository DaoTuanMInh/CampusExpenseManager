package com.example.campusexpense_manager;

public class User {
    private int id;
    private String username;
    private String password;
    private String email;
    private String phonenumber;

    public User(int id, String username, String password, String email, String phonenumber){
        this.id =id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phonenumber = phonenumber;
    }
    public String getUsername(){
        return this.username;
    }
    public String getPassword(){
        return this.password;
    }
    public String getEmail(){
        return this.email;
    }
    public String getPhonenumber(){
        return this.phonenumber;
    }
    public int getId(){
        return this.id;
    }
}
