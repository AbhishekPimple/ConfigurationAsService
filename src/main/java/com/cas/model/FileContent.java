package com.cas.model;

import java.io.Serializable;

public class FileContent implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    String name;
    String content;
    String[] serverIds;
    String isRestart;

    public FileContent(){
        //Auto-generated
    }

    public FileContent(String name, String content){
        this.name = name;
        this.content = content;
    }

    public String getIsRestart() {
        return isRestart;
    }

    public void setIsRestart(String isRestart) {
        this.isRestart = isRestart;
    }

    public String[] getServerIds() {
        return serverIds;
    }

    public void setServerIds(String[] serverIds) {
        this.serverIds = serverIds;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
}
