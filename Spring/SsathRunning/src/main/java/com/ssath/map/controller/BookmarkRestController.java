package com.ssath.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.service.BookmarkService;
import io.swagger.annotations.Api;

@RestController
@Api(tags="좋아요 컨트롤러")
@RequestMapping("/api")
public class BookmarkRestController {
	
	private BookmarkService bookmarkService;
	
	@Autowired
	public void setBookmarkService(BookmarkService bookmarkService) {
		this.bookmarkService=bookmarkService;
	}
	
}
