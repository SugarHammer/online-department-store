package com.mapper;


import com.pojo.Comments;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface CommentsMapper {
    //查找所有货物信息
    List<Comments> selectAllComments();
    //按cid查找评论信息
    Comments selectCommentsBycId(@Param("cId") int cId);
    //按userId查找评论信息
    Comments selectCommentsByuserId(@Param("userId") int userId);
    //按byGoodsId查找评论信息
    List<Comments> selectCommentsbyGoodsId(@Param("byGoodsId") int byGoodsId);
    //添加货物信息
    //void addGoods(Goods goods);
    void addComments(Comments comments);
    //修改评论信息
    void updateComments(Comments comments);
    //根据cid删除评论数据
    void deleteCommentsBycId(int cId);
    //根据userId删除评论数据
    void deleteCommentsByuserId(int userId);
    //按keyword搜索评论信息
    List<Comments> searchComments(@Param("keyword") String keyword);

}
