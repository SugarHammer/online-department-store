package com.pojo;

import com.pojo.Goods;
import lombok.Data;

@Data
public class GoodsDTO extends Goods {
    Integer byGoods;
    String name;
    String url;
}
