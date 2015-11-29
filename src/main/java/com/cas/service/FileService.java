package com.cas.service;

import java.util.List;

import com.cas.model.File;

public interface FileService {

    public List<String> getFile(int fileId);

    public File addFile(File file);

    public boolean saveFile(String name, String content, String serverId, String isRestart);
    public boolean checkModified(String name, String content, String serverId);

}
