package com.cas.service;

import java.sql.SQLException;

import org.json.JSONObject;

public interface TreeViewService {

    public JSONObject populate(String emailId) throws SQLException;
}
