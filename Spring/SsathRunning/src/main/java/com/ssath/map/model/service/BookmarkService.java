package com.ssath.map.model.service;

import com.ssath.map.model.dto.Bookmark;

public interface BookmarkService {
	
	public int createBookmark(String mapId, String userId);
	
	public int selectBookmarks(int mapId);
	
	public int updateBookmark(String mapId, String userId);
	
	public int existsBookmark(String mapId, String userId);
	
	public int selectBookmark(String mapId, String userId);
	
}
