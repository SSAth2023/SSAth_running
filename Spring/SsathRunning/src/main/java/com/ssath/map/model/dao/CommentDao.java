package com.ssath.map.model.dao;

import java.util.List;

import com.ssath.map.model.dto.Comment;

public interface CommentDao {
	
	//댓글 상세조회
	public Comment selectComment(int commentId);
	
	//모든 댓글 조회
	public List<Comment> selectComments(int mapId);
	
	//댓글 작성
	public int createComment(Comment comment);
	
	//댓글 삭제
	public int deleteComment(int commentId);
	
	//댓글 수정
	public int updateComment(Comment comment);
	
}
