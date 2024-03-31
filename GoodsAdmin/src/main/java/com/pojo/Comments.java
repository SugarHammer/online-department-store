package com.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Alias("comments")
public class Comments {
    private Integer cId;
    private Integer userId;
    private Integer byGoodsId;
    private String nickName;
    private String picUrl;
    private String content;
    private String time;

    public Integer getcId() {
        return cId;
    }

    public void setcId(Integer cId) {
        this.cId = cId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getByGoodsId() {
        return byGoodsId;
    }

    public void setByGoodsId(Integer byGoodsId) {
        this.byGoodsId = byGoodsId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setContent(String content) {
        this.content = content;
    }
    @Override
    public String toString() {
        return "Comments{" +
                "cId='" + cId + '\'' +
                ", useId='" + userId + '\'' +
                ", byGoodsId='" + byGoodsId + '\'' +
                ", nickName='" + nickName + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ",content'" + content +'\''+
                ",time'" + time +'\''+
                '}';
    }

    ;

}
