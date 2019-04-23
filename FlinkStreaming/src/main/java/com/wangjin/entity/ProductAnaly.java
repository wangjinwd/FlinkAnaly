package com.wangjin.entity;

/**
 * Created by WJ on 2019/4/16.
 */
public class ProductAnaly {
    private long productid;
    private String dateString;
    private long chengjiaocount;//成交
    private long weichegnjiao;//未成交
    private String groupbyfield;//分组key

    public String getGroupbyfield() {
        return groupbyfield;
    }

    public void setGroupbyfield(String groupbyfield) {
        this.groupbyfield = groupbyfield;
    }

    public long getProductid() {
        return productid;
    }

    public void setProductid(long productid) {
        this.productid = productid;
    }

    public String getDateString() {
        return dateString;
    }

    public void setDateString(String dateString) {
        this.dateString = dateString;
    }

    public long getChengjiaocount() {
        return chengjiaocount;
    }

    public void setChengjiaocount(long chengjiaocount) {
        this.chengjiaocount = chengjiaocount;
    }

    public long getWeichegnjiao() {
        return weichegnjiao;
    }

    public void setWeichegnjiao(long weichegnjiao) {
        this.weichegnjiao = weichegnjiao;
    }
}
