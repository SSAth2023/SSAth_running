package com.ssath.map.model.service;

import java.util.List;

import com.ssath.map.model.dto.RunningPath;
import com.ssath.map.model.dto.SearchCondition;

public interface RunningPathService {
	
	public RunningPath selectOnePath(int mapId);
	
	public List<RunningPath> selectPaths(RunningPath runningPath);
	
	public int createRunningPath(RunningPath runningPath);
	
	public int deleteRunningPath(int mapId);
	
	public int updateRunningPath(RunningPath runningPath);
	
	public List<RunningPath> searchRunningPath(SearchCondition location);
	
}
