package com.ssath.map.model.dto;

public class SearchCondition {

	private String location;
	
	public SearchCondition() {
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	@Override
	public String toString() {
		return "SearchCondition [location=" + location + "]";
	}
	
}
