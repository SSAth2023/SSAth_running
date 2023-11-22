package com.ssath.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.dto.Bookmark;
import com.ssath.map.model.service.BookmarkService;
import io.swagger.annotations.Api;

@RestController
@Api(tags="좋아요 컨트롤러")
@RequestMapping("/api")
public class BookmarkRestController {
	
	private BookmarkService bookmarkService;
	
	@Autowired
	public void setBookmarkService(BookmarkService bookmarkService) {
		this.bookmarkService = bookmarkService;
	}
	
	@GetMapping({"/bookmark/{mapId}/{userId}"})
	public ResponseEntity<Integer> like(@PathVariable("mapId") String mapId, @PathVariable("userId") String userId){
		int result = 0;
		if(bookmarkService.existsBookmark(mapId,userId)>0) {
			bookmarkService.updateBookmark(mapId, userId);
			result = bookmarkService.selectBookmark(mapId, userId);
		}
		else
			result = bookmarkService.createBookmark(mapId, userId);
		if(result>0)
			return new ResponseEntity<Integer>(1,HttpStatus.OK);
		return new ResponseEntity<Integer>(0,HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/bookmark/list/{mapId}")
	public ResponseEntity<Integer> list(@PathVariable int mapId){
		int cnt = bookmarkService.selectBookmarks(mapId);
		
		return new ResponseEntity<Integer>(cnt,HttpStatus.OK);
	}

	
}
