package com.muhamadarief.operatorpraktek.Model;

/**
 * Created by Muhamad Arief on 29/04/2017.
 */

public class BaseResponse {
    private boolean error;
    private String message;

    public BaseResponse() {
    }

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
