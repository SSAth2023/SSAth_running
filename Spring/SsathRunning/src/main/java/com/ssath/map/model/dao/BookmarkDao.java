package com.ssath.map.model.dao;

import com.ssath.map.model.dto.Bookmark;

public interface BookmarkDao {

	//코스 별, 유저 별 좋아요 생성
	public int createBookmark(String mapId, String userId);
	
	//코스 별 좋아요 출력
	public int selectBookmarks(int mapId);
	
	//좋아요 갱신
	public int updateBookmark(String mapId, String userId);
	
	//코스 별, 유저 별 좋아요 생성 여부 확인
	public int existsBookmark(String mapId, String userId);
	
	//좋아요 여부 확인
	public int selectBookmark(String mapId, String userId);
	
}
