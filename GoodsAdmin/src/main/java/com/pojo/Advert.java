package com.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("advert")
public class Advert {

    private Integer aId;
    private String adContent;
    private String adPicUrl;

    public String getAdContent() {
        return adContent;
    }
    public String getAdPicUrl() {
        return adPicUrl;
    }
    public void setAdContent(String adContent) {
        this.adContent = adContent;
    }
    public void setAdPicUrl(String adPicUrl) {
        this.adPicUrl = adPicUrl;
    }
    public void setAId(Integer aId) {
        this.aId = aId;
    }
    public Integer getAId() {
        return aId;
    }

    @Override
    public String toString() {
        return "Advert{" +
                "aId='" + aId + '\'' +
                ", adContent='" + adContent + '\'' +
                ", adPicUrl='" + adPicUrl + '\'' +
                '}';
    }

    public Advert() {   }
    public Advert(int aId, String adContent,String adPicUrl) {
        this.aId = aId;
        this.adContent = adContent;
        this.adPicUrl = adPicUrl;
    }

}



