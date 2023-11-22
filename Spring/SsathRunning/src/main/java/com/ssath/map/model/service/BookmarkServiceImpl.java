package com.ssath.map.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssath.map.model.dao.BookmarkDao;
import com.ssath.map.model.dto.Bookmark;

@Service
public class BookmarkServiceImpl implements BookmarkService {

	private BookmarkDao bookmarkDao;
	
	@Autowired
	public void setBookmarkDao(BookmarkDao bookmarkDao) {
		this.bookmarkDao = bookmarkDao; 
	}
	
	@Override
	public int createBookmark(String mapId, String userId) {
		return bookmarkDao.createBookmark(mapId, userId);
	}
	
	@Override
	public int selectBookmarks(int mapId) {
		return bookmarkDao.selectBookmarks(mapId);
	}
	
	@Override
	public int updateBookmark(String mapId, String userId) {
		return bookmarkDao.updateBookmark(mapId, userId);
	}

	@Override
	public int existsBookmark(String mapId, String userId) {
		return bookmarkDao.existsBookmark(mapId, userId);
	}

	@Override
	public int selectBookmark(String mapId, String userId) {
		return bookmarkDao.selectBookmark(mapId, userId);
	}

}
