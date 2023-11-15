package com.ssath.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.service.CommentService;

import io.swagger.annotations.Api;

@RestController
@Api(tags="댓글 컨트롤러")
@RequestMapping("/api")
public class CommentRestController {
	
	private CommentService commentService;
	
	@Autowired
	public void setCommentService(CommentService commentService) {
		this.commentService=commentService;
	}
	
}
