package com.om.taskmanager.model;

public class ImageResponse {

    private String filename;
    private String destintion;

    public ImageResponse(String filename, String destintion) {
        this.filename = filename;
        this.destintion = destintion;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getDestintion() {
        return destintion;
    }

    public void setDestintion(String destintion) {
        this.destintion = destintion;
    }



    public String getFilename() {
        return filename;
    }


}
