package com.wangjin.entity;

/**
 * Created by WJ on 2019/4/16.
 */
public class PidaoXinXianDu {
    private Long pingdaoid;//频道id;
    private Long timestamp;//消息的时间
    private Long newUserCount;

    public Long getTimestring() {
        return Timestring;
    }

    public void setTimestring(Long timestring) {
        Timestring = timestring;
    }

    public Long getGroupByFields() {
        return groupByFields;
    }

    public void setGroupByFields(Long groupByFields) {
        this.groupByFields = groupByFields;
    }

    private Long Timestring;
    private Long groupByFields;

    public Long getOldUserCount() {
        return oldUserCount;
    }

    public void setOldUserCount(Long oldUserCount) {
        this.oldUserCount = oldUserCount;
    }

    private Long oldUserCount;
    public Long getNewUserCount() {
        return newUserCount;
    }

    public void setNewUserCount(Long newUserCount) {
        this.newUserCount = newUserCount;
    }

    public Long getPingdaoid() {
        return pingdaoid;
    }

    public void setPingdaoid(Long pingdaoid) {
        this.pingdaoid = pingdaoid;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }



}
