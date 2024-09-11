package com.tinder.model;

public class User {
    private int id;
    private String email;
    private String password;
    private String nick;
    private String imgLink;

    // Constructor
    public User(int id, String email, String password, String nick, String imgLink) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nick = nick;
        this.imgLink = imgLink;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}