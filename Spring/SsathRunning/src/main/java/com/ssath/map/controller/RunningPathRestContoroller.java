package com.ssath.map.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssath.map.model.dto.RunningPath;
import com.ssath.map.model.dto.SearchCondition;
import com.ssath.map.model.service.RunningPathService;

import io.swagger.annotations.Api;

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
	
	// 현재 위치에 기반하여 가까운 러닝 경로들 가져오기
	@PostMapping("/path")
	public ResponseEntity<?> list(@RequestBody RunningPath runningPath) {
		System.out.println(runningPath);
		String location = runningPath.getPath();
		String userId = runningPath.getUserId();
		System.out.println(location+" "+userId);
		List<RunningPath> list = runningPathService.selectPaths(runningPath);
//		try {
//			String deLocation = URLDecoder.decode(location,"UTF-8");
//			System.out.println(deLocation.substring(0, deLocation.length()-2));
//			list = runningPathService.selectPaths(deLocation.substring(0, deLocation.length()-2));
//		} catch (UnsupportedEncodingException e) {
//			e.printStackTrace();
//			System.out.println("오류는 아닌 것으로 판명");
//		}
		System.out.println(list.get(0).getCalDist());
		if(list == null || list.isEmpty()) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<RunningPath>>(list, HttpStatus.OK);
	}
	
	// 한 개의 러닝 경로 가져오기
	@PostMapping("/path/{mapId}")
	public ResponseEntity<RunningPath> detail(@RequestBody RunningPath runningPath) {
		System.out.println("한 개의 러닝 경로 가져오기 "+runningPath);
		RunningPath path = runningPathService.selectOnePath(runningPath);
		
		return new ResponseEntity<RunningPath>(path, HttpStatus.OK);
	}
	
	// 러닝 경로 생성
	@PostMapping("/path/create")
	public ResponseEntity<?> create(@RequestBody RunningPath runningPath) {
		int result = runningPathService.createRunningPath(runningPath);
		
		if(result == 0) 
			return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<RunningPath>(runningPath, HttpStatus.OK);
	}
	
	// 러닝 경로 삭제
	@DeleteMapping("/path/delete/{mapId}")
	public ResponseEntity<String> delete(@PathVariable int mapId) {
		int result = runningPathService.deleteRunningPath(mapId);
		
		if(result == 0) return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 러닝 경로 수정
	@PutMapping("/path/update")
	public ResponseEntity<String> update(@RequestBody RunningPath runningPath) {
		int result = runningPathService.updateRunningPath(runningPath);
	
		if(result == 0) return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
	}
	
	// 검색한 장소에서 가장 근접한 러닝 경로 조회
	@GetMapping("/path/search")
	public ResponseEntity<?> select(@RequestBody SearchCondition location) {
		List<RunningPath> list = runningPathService.searchRunningPath(location);
		if(list == null || list.isEmpty()) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<RunningPath>>(list, HttpStatus.OK);
	}
	
	// 좋아요 누른 러닝 경로 조회
	@PostMapping("/path/bookmark")
	public ResponseEntity<?> bookmakredLists(@RequestBody RunningPath runningPath){
		System.out.println("Book: "+runningPath);
		List<RunningPath> list = runningPathService.selectBookmarkedPaths(runningPath);
		System.out.println(list.toString());
		if(list == null || list.isEmpty()) return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
		
		return new ResponseEntity<List<RunningPath>>(list, HttpStatus.OK);
	}
}
