package com.danielecampogiani.upnpclient;

/**
 * Created by danielecampogiani on 04/06/14.
 */
public class Picture {

    private String url;

    public Picture(String url){
        this.url = url;
    }

    public String getUrl(){
        return this.url;
    }

    public void setUrl(String url){
        this.url = url;
    }
}
