package com.service.impl;

import com.mapper.CommentsMapper;
import com.pojo.Comments;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsServiceImpI implements com.service.interfaces.ICommentsService {
    @Autowired
    private CommentsMapper commentsMapper;

    private static Logger log = Logger.getLogger(CommentsServiceImpI.class);
    @Override
    public List<Comments> selectAllComments() {
        log.info("/selectAllComments start...");
        return commentsMapper.selectAllComments();

    }

    @Override
    public Comments selectCommentsBycId(int id) {
        return commentsMapper.selectCommentsBycId(id);
    }

    @Override
    public Comments selectCommentsByuserId(int userId) {
        return commentsMapper.selectCommentsByuserId(userId);
    }

    @Override
    public void addComments(Comments comments) {
        commentsMapper.addComments(comments);
        return;
    }

    @Override
    public List<Comments> selectCommentsbyGoodsId(int id) {
        return commentsMapper.selectCommentsbyGoodsId(id);
    }
    @Override
    public void updateComments(Comments comments) {
        commentsMapper.updateComments(comments);
        return;

    }

    @Override
    public void deleteCommentsBycId(int cId) {
        commentsMapper.deleteCommentsBycId(cId);
        return;

    }

    @Override
    public void deleteCommentsByuserId(int userId) {
        commentsMapper.deleteCommentsByuserId(userId);
        return;
    }

    @Override
    public List<Comments> searchComments(String keyword) {
        return commentsMapper.searchComments(keyword);
    }
}
