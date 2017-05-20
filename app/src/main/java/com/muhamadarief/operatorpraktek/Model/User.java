package com.muhamadarief.operatorpraktek.Model;

/**
 * Created by Muhamad Arief on 29/04/2017.
 */

public class User extends BaseResponse {

    private UserData data;

    public User() {
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }
}
