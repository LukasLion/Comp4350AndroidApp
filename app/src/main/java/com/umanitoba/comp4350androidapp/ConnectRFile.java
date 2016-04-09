package com.umanitoba.comp4350androidapp;

/**
 * Created by PERSENTECH on 2016-04-08.
 */
public class ConnectRFile {
    private int id;
    private int profileID;
    private String fileName;
    private String contentType;
    private byte[] content;
    private int fileType;

    public ConnectRFile(int id, int profileID, String fileName, String contentType, byte[] content, int fileType) {
        this.id = id;
        this.profileID = profileID;
        this.fileName = fileName;
        this.contentType = contentType;
        this.content = content;
        this.fileType = fileType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProfileID() {
        return profileID;
    }

    public void setProfileID(int profileID) {
        this.profileID = profileID;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public int getFileType() {
        return fileType;
    }

    public void setFileType(int fileType) {
        this.fileType = fileType;
    }
}
