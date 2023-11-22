package com.ssath.map.model.dto;

public class RunningPath {
	
	private int mapId;
	private String userId;
	private String title;
	private String path;
	private String description;
	private int distance;
	private String regDate;
	private int calDist;
	
	public RunningPath() {
	}

	public RunningPath(String title, String path, String description, int distance) {
		super();
		this.title = title;
		this.path = path;
		this.description = description;
		this.distance = distance;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public int getCalDist() {
		return calDist;
	}

	public void setCalDist(int calDist) {
		this.calDist = calDist;
	}

	@Override
	public String toString() {
		return "RunningPath [mapId=" + mapId + ", userId=" + userId + ", title=" + title + ", path=" + path
				+ ", description=" + description + ", distance=" + distance + ", regDate=" + regDate + "]";
	}
	
	
}
