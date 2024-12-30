package com.paroryx;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class User {
    private String username,token,presence;
    private long date;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getPresence() {
        return presence;
    }

    public void setPresence(String presence) {
        this.presence = presence;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "%s :- %s".formatted(username, new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(new Date(date)));
    }
}
