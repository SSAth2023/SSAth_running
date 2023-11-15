package com.ssath.map.model.dao;

import com.ssath.map.model.dto.Bookmark;

public interface BookmarkDao {

	//코스 별, 유저 별 좋아요 여부
	public boolean selectBookmark(int mapId, String userId);
	
	//코스 별 좋아요 출력
	public int selectBookmarks(int mapId);
	
	//
	public int updateBookmark(Bookmark bookmark);
	
}
