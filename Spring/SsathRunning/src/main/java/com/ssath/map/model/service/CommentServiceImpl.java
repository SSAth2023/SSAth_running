package com.ssath.map.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssath.map.model.dao.CommentDao;
import com.ssath.map.model.dto.Comment;

@Service
public class CommentServiceImpl implements CommentService {
	
	private CommentDao commentDao;
	
	@Autowired
	public void setCommentDao(CommentDao commentDao) {
		this.commentDao = commentDao;
	}
	
	@Override
	public Comment selectComment(int commentId) {
		return commentDao.selectComment(commentId);
	}

	@Override
	public List<Comment> selectComments(int mapId) {
		return commentDao.selectComments(mapId);
	}

	@Override
	public int createComment(Comment comment) {
		return commentDao.createComment(comment);
	}

	@Override
	public int deleteComment(int commentId) {
		return commentDao.deleteComment(commentId);
	}

	@Override
	public int updateComment(Comment comment) {
		return commentDao.updateComment(comment);
	}
	
}
