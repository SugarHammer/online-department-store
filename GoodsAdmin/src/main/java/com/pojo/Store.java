package com.pojo;

import lombok.Data;
import org.apache.ibatis.type.Alias;

@Data
@Alias("store")
public class Store {

    private Integer id;
    private String name;
    private String descp;
    private String url;
    private Integer byGoods;

    public Store(Integer id, String name, String descp, String url, Integer byGoods) {
        this.id = id;
        this.name = name;
        this.descp = descp;
        this.url = url;
        this.byGoods = byGoods;
    }

    public String getDescp() {
        return descp;
    }
    public void setDescp(String descp) {
        this.descp = descp;
    }

    public Integer getByGoods() {
        return byGoods;
    }
    public void setByGoods(Integer byGoods) {
        this.byGoods = byGoods;
    }

    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Store{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", descp='" + descp + '\'' +
                ", url='" + url + '\'' +
                ", byGoods=" + byGoods +
                '}';
    }
}



