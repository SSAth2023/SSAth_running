package com.ssath.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	
	private final String SUCCESS = "SUCCESS";	
	private final String FAIL = "FAIL";	
	private BookmarkService bookmarkService;
	
	@Autowired
	public void setBookmarkService(BookmarkService bookmarkService) {
		this.bookmarkService=bookmarkService;
	}
	
	@GetMapping({"/bookmark/{mapId}/{userId}"})
	public ResponseEntity<Boolean> detail(@PathVariable("mapId") String mapId, @PathVariable("userId") String userId){
		boolean result = bookmarkService.selectBookmark(mapId, userId);
		if(result)
			return new ResponseEntity<Boolean>(result,HttpStatus.OK);
		return new ResponseEntity<Boolean>(result,HttpStatus.NO_CONTENT);
	}
	
	@GetMapping("/bookmark/list/{mapId}")
	public ResponseEntity<Integer> list(@PathVariable int mapId){
		int cnt = bookmarkService.selectBookmarks(mapId);
		
		return new ResponseEntity<Integer>(cnt,HttpStatus.OK);
	}
	
	@PostMapping("/bookmark/update")
	public ResponseEntity<String> update(@RequestBody Bookmark bookmark){
		int result = bookmarkService.updateBookmark(bookmark);
		if(result == 0)
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
}
