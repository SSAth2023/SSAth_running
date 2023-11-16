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

import com.ssath.map.model.dto.RunningPath;
import com.ssath.map.model.dto.SearchCondition;
import com.ssath.map.model.service.RunningPathService;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@Api(tags="러닝 경로 컨트롤러")
@RequestMapping("/api")
public class RunningPathRestContoroller {
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	private RunningPathService runningPathService;
	
	@Autowired
	public void setRunningPathService(RunningPathService runningPathService) {
		this.runningPathService=runningPathService;
	}
	
	@GetMapping("/path")
	public ResponseEntity<?> list() {
		List<RunningPath> list = runningPathService.selectPaths();
		
		if(list.isEmpty()) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<RunningPath>>(list, HttpStatus.OK);
	}
	
	@GetMapping("/path/{mapId}")
	public ResponseEntity<RunningPath> detail(@PathVariable int mapId) {
		RunningPath runningPath = runningPathService.selectOnePath(mapId);
		
		return new ResponseEntity<RunningPath>(runningPath, HttpStatus.OK);
	}
	
	@PostMapping("/path/create")
	public ResponseEntity<?> create(@RequestBody RunningPath runningPath) {
		int result = runningPathService.createRunningPath(runningPath);
		
		if(result == 0) 
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<RunningPath>(runningPath, HttpStatus.OK);
	}
	
	@DeleteMapping("/path/delete")
	public ResponseEntity<String> delete(@PathVariable int mapId) {
		int result = runningPathService.deleteRunningPath(mapId);
		
		if(result == 0) return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@PutMapping("/path/update")
	public ResponseEntity<String> update(@RequestBody RunningPath runningPath) {
		int result = runningPathService.updateRunningPath(runningPath);
	
		if(result == 0) return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	@GetMapping("/path/search")
	public ResponseEntity<?> select(@RequestBody SearchCondition location) {
		List<RunningPath> list = runningPathService.searchRunningPath(location);
		if(list.isEmpty()) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<RunningPath>>(list, HttpStatus.OK);
	}
}
