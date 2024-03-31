package com.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("goods")
public class Goods {

    private Integer gid;
    private String gname;
    private double gprice;
    private String gdesc;
    private String pthumbnail;


    public int getGid() {
        return gid;
    }

    public void setGid(int gid) {
        this.gid = gid;
    }

    public void setGname(String gname) {
        this.gname = gname;
    }

    public String getGname() {
        return gname;
    }

    public double getGprice() {
        return gprice;
    }

    public void setGprice(double gprice) {
        this.gprice = gprice;
    }

    public String getGdesc() {
        return gdesc;
    }

    public void setGdesc(String gdesc) {
        this.gdesc = gdesc;
    }

    public String getPthumbnail() {
        return pthumbnail;
    }

    public void setPthumbnail(String pthumbnail) {
        this.pthumbnail = pthumbnail;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "gid='" + gid + '\'' +
                ", gname='" + gname + '\'' +
                ", gprice='" + gprice + '\'' +
                ", pthumbnail='" + pthumbnail + '\'' +
                ", gdesc='" + gdesc + '\'' +
                '}';
    }

    public Goods() {   }
    public Goods(int gid, String gname, double gprice, String gdesc,String pthumbnail) {
        this.gid = gid;
        this.gname = gname;
        this.gprice = gprice;
        this.gdesc = gdesc;
        this.pthumbnail = pthumbnail;
    }
}



