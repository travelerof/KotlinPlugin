package com.hyg.log.data.model;

import android.util.Log;

import com.hyg.log.core.LogPriority;
import com.hyg.log.data.utils.LogDataUtils;

/**
 * Package:      com.hyg.log.data.model
 * ClassName:    LogModel
 * Author:       hanyonggang
 * Date:         2022/1/2 17:41
 * Description:
 */
public class LogModel {
    private String key = "";
    private String message = "";
    private String createTime = LogDataUtils.nowTime();
    private int priority = Log.INFO;

    public LogModel(String key) {
        this.key = key;
    }

    public LogModel(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public LogModel(String key, String message, int priority) {
        this.key = key;
        this.message = message;
        this.priority = priority;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
