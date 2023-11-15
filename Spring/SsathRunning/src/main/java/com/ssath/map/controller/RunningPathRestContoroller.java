package com.ssath.map.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.service.RunningPathService;

import io.swagger.annotations.Api;

@RestController
@Api(tags="러닝 경로 컨트롤러")
@RequestMapping("/api")
public class RunningPathRestContoroller {
	
	private RunningPathService runningPathService;
	
	@Autowired
	public void setRunningPathService(RunningPathService runningPathService) {
		this.runningPathService=runningPathService;
	}
	
}
