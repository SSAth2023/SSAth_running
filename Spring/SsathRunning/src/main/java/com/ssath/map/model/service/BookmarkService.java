package com.ssath.map.model.service;

import com.ssath.map.model.dto.Bookmark;

public interface BookmarkService {
	
	public boolean selectBookmark(String mapId, String userId);
	
	public int selectBookmarks(int mapId);
	
	public int updateBookmark(Bookmark bookmark);
	
}
