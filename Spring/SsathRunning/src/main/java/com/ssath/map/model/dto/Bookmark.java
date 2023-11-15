package com.ssath.map.model.dto;

public class Bookmark {
	
	private int mapId;
	private String userId;
	private boolean marked;
	
	public Bookmark() {
	}

	public Bookmark(int mapId, String userId, boolean marked) {
		super();
		this.mapId = mapId;
		this.userId = userId;
		this.marked = marked;
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

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	@Override
	public String toString() {
		return "Bookmark [mapId=" + mapId + ", userId=" + userId + ", marked=" + marked + "]";
	}
	
}
