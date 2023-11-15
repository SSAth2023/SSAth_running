package com.ssath.map.model.service;

import java.util.List;

import com.ssath.map.model.dto.Comment;

public interface CommentService {
	
	public Comment selectComment(int commentId);
	
	public List<Comment> selectComment();
	
	public int createComment(Comment comment);
	
	public int deleteComment(int commentId);
	
	public int updateComment(Comment comment);
	
}
