package com.ssath.map.model.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.ssath.map.model.dao.BookmarkDao;
import com.ssath.map.model.dto.Bookmark;

public class BookmarkServiceImpl implements BookmarkService {

	private BookmarkDao bookmarkDao;
	
	@Autowired
	public void setBookmarkDao(BookmarkDao bookmarkDao) {
		this.bookmarkDao = bookmarkDao; 
	}
	
	@Override
	public boolean selectBookmark(int mapId, String userId) {
		return bookmarkDao.selectBookmark(mapId, userId);
	}
	
	@Override
	public int selectBookmarks(int mapId) {
		return bookmarkDao.selectBookmarks(mapId);
	}
	
	@Override
	public int updateBookmark(Bookmark bookmark) {
		return bookmarkDao.updateBookmark(bookmark);
	}

}
