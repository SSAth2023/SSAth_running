package com.ssath.map.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.dto.Comment;
import com.ssath.map.model.service.CommentService;

import io.swagger.annotations.Api;

@RestController
@Api(tags="댓글 컨트롤러")
@RequestMapping("/api")
public class CommentRestController {
	
	private final String SUCCESS = "SUCCESS";	
	private final String FAIL = "FAIL";	
	private CommentService commentService;
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService=commentService;
	}
	
	//댓글 상세조회
	//필요한가? 필요 없음. 빼도 무방함.
	@GetMapping("/comment/{commentId}")
	public ResponseEntity<Comment> detail(@PathVariable int commentId){
		Comment result = commentService.selectComment(commentId);
		return new ResponseEntity<Comment>(result,HttpStatus.OK);
	}
	
	//해당 경로에 대한 모든 댓글 조회
	@GetMapping("/comment/list/{mapId}")
	public ResponseEntity<?> list(@PathVariable int mapId){
		List<Comment> list = commentService.selectComments(mapId);
		if(list == null || list.size()==0)
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		return new ResponseEntity<List<Comment>>(list,HttpStatus.OK);
	}
	
	//댓글 작성
	@PostMapping("/comment/create")
	public ResponseEntity<String> create(Comment comment){
		int result = commentService.createComment(comment);
		if(result == 0)
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
	}
	
	//댓글 삭제
	@DeleteMapping("/comment/delete/{commentId}")
	public ResponseEntity<String> delete(@PathVariable int commentId){
		int result = commentService.deleteComment(commentId);
		if(result == 0)
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
	}
	
	//댓글 수정
	@PutMapping("/comment/update")
	public ResponseEntity<String> update(Comment comment){
		int result = commentService.updateComment(comment);
		if(result == 0)
			return new ResponseEntity<String>(FAIL,HttpStatus.NO_CONTENT);
		return new ResponseEntity<String>(SUCCESS,HttpStatus.OK);
	}
	
}
