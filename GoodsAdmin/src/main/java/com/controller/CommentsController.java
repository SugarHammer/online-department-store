package com.controller;

import com.pojo.Comments;
import com.service.interfaces.ICommentsService;
import com.utils.JsonResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private ICommentsService iCommentsService;

    //查询所有评论列表
    @GetMapping("/selectAllComments")
    public JsonResponse selectAllComments(){
        return new JsonResponse(JsonResponse.OK,iCommentsService.selectAllComments(),null);
    }

    //按cid查找评论信息
    @GetMapping("/selectCommentsBycId")
    public JsonResponse selectCommentsBycId(int id){
        return new JsonResponse(JsonResponse.OK,iCommentsService.selectCommentsBycId(id),null);
    }

    //修改评论信息
    @GetMapping("/updateComments")
    public JsonResponse updateComments(int id, int userid, int goodsid, String username, String img,
                                       String content, String time){
        Comments comments=new Comments(id,userid,goodsid,username,img,content,time);
        iCommentsService.updateComments(comments);
        return new JsonResponse(JsonResponse.OK,iCommentsService.selectCommentsBycId(id),null);
    }

    //根据cid删除评论数据
    @GetMapping("/deleteCommentsBycId")
    public JsonResponse deleteCommentsBycId(int id){
     iCommentsService.deleteCommentsBycId(id);
     return new JsonResponse(JsonResponse.OK,null,null);
    }

    @GetMapping("/addComments")
    public JsonResponse addComments(int id, int userId,String name, String pic, String content, String time ){
        Comments comments= new Comments(0,userId,id,name,pic,content,time);
        iCommentsService.addComments(comments);
        return new JsonResponse(JsonResponse.OK,null,null);
    }

    //按byGoodsId查找评论信息
    @GetMapping("/selectCommentsbyGoodsId")
    public JsonResponse selectCommentsbyGoodsId(int id){
        return new JsonResponse(JsonResponse.OK,iCommentsService.selectCommentsbyGoodsId(id),null);
    }

    //根据userId删除评论数据
    @GetMapping("/deleteCommentsByuserId")
    public JsonResponse deleteCommentsByuserId(int userId){
     iCommentsService.deleteCommentsByuserId(userId);
     return new JsonResponse(JsonResponse.OK,null,null);
    }

    //按keyword查询评论列表
    @GetMapping("/searchComments")
    public JsonResponse searchComments(String keyword){
     return new JsonResponse(JsonResponse.OK,iCommentsService.searchComments(keyword),null);
   }
}
