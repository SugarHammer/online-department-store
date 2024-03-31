package com.service.interfaces;
import com.pojo.Comments;

import java.util.List;

public interface ICommentsService {
    //查询所有评论
    List<Comments> selectAllComments();
    //添加货物
    //public void addGoods(Goods goods);
    //按cid查找评论信息
    public Comments selectCommentsBycId(int id);

    //按userId查找评论信息
    public Comments selectCommentsByuserId(int userId);


    //添加评论
    public void addComments(Comments comments);
    //按byGoodsId查找评论信息
    List<Comments>  selectCommentsbyGoodsId(int id);
    //修改评论信息
    public void updateComments(Comments comments);

    //根据cid删除评论信息
    public  void deleteCommentsBycId(int cId);

    //根据userId删除评论信息
    public void deleteCommentsByuserId(int userId);

    //按keyword搜索评论信息
    public List<Comments> searchComments(String keyword);
}
