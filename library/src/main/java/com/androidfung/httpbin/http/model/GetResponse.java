package com.androidfung.httpbin.http.model;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

/**
 * Created by funglam on 6/13/17.
 */

public class GetResponse {

    @SerializedName("args")
    private Map<String, Object> args;

    @SerializedName("headers")
    private Map<String, Object> headers;

    @SerializedName("origin")
    private String origin;

    @SerializedName("url")
    private String url;

    public Map<String, Object> getArgs() {
        return args;
    }

    public void setArgs(Map<String, Object> args) {
        this.args = args;
    }

    public Map<String, Object> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, Object> headers) {
        this.headers = headers;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "GetResponse{" +
                "args=" + args +
                ", headers=" + headers +
                ", origin='" + origin + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}
