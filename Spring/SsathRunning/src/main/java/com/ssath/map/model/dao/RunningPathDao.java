package com.ssath.map.model.dao;

import java.util.List;

import com.ssath.map.model.dto.RunningPath;
import com.ssath.map.model.dto.SearchCondition;

public interface RunningPathDao {
	
	//러닝 경로 한 개 추출
	public RunningPath selectOnePath(int mapId);
	
	//모든 러닝 경로 추출
	public List<RunningPath> selectPaths(RunningPath runningPath);
	
	//러닝 경로 추가
	public int createRunningPath(RunningPath runningPath);
	
	//러닝 경로 삭제
	public int deleteRunningPath(int mapId);
	
	//러닝 경로 업데이트
	public int updateRunningPath(RunningPath runningPath);
	
	//러닝 경로 검색, 검색어와 가장 가까운 위치로 위도 경도를 이동 후 경로를 출력
	public List<RunningPath> searchRunningPath(SearchCondition location);
	
	//좋아요 누른 경로 출력
	public List<RunningPath> selectBookmarkedPaths(RunningPath runningPath);

}
