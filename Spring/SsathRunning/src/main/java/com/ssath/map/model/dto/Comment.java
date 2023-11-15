package com.ssath.map.model.dto;

public class Comment {

	private int commentId;
	private int mapId;
	private String userId;
	private String content;
	private String comRegDate;
	
	public Comment() {
	}

	public Comment(int commentId, int mapId, String userId, String content, String comRegDate) {
		super();
		this.commentId = commentId;
		this.mapId = mapId;
		this.userId = userId;
		this.content = content;
		this.comRegDate = comRegDate;
	}

	public int getCommentId() {
		return commentId;
	}

	public void setCommentId(int commentId) {
		this.commentId = commentId;
	}

	public int getMapId() {
		return mapId;
	}

	public void setMapId(int mapId) {
		this.mapId = mapId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getComRegDate() {
		return comRegDate;
	}

	public void setComRegDate(String comRegDate) {
		this.comRegDate = comRegDate;
	}

	@Override
	public String toString() {
		return "Comment [commentId=" + commentId + ", mapId=" + mapId + ", userId=" + userId + ", content=" + content
				+ ", comRegDate=" + comRegDate + "]";
	}
	
}
