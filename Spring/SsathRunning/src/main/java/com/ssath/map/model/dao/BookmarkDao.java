package com.ssath.map.model.dao;

import com.ssath.map.model.dto.Bookmark;

public interface BookmarkDao {

	//코스 별, 유저 별 좋아요 생성
	public boolean selectBookmark(String mapId, String userId);
	
	//코스 별 좋아요 출력
	public int selectBookmarks(int mapId);
	
	//좋아요 갱신
	public int updateBookmark(Bookmark bookmark);
	
}
