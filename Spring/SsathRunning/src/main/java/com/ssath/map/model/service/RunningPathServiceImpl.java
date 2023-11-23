package com.ssath.map.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssath.map.model.dao.RunningPathDao;
import com.ssath.map.model.dto.RunningPath;
import com.ssath.map.model.dto.SearchCondition;

@Service
public class RunningPathServiceImpl implements RunningPathService {
	
	private RunningPathDao runningPathDao;
	
	@Autowired
	public void setRunningPath(RunningPathDao runningPathDao) {
		this.runningPathDao = runningPathDao;
	}
	
	@Override
	public RunningPath selectOnePath(RunningPath runningPath) {
		return runningPathDao.selectOnePath(runningPath);
	}
	
	@Override
	public List<RunningPath> selectPaths(RunningPath runningPath){
		return runningPathDao.selectPaths(runningPath);
	}
	
	@Override
	public int createRunningPath(RunningPath runningPath) {
		return runningPathDao.createRunningPath(runningPath);
	}
	
	@Override
	public int deleteRunningPath(int mapId) {
		return runningPathDao.deleteRunningPath(mapId);
	}
	
	@Override
	public int updateRunningPath(RunningPath runningPath) {
		return runningPathDao.updateRunningPath(runningPath);
	}
	
	@Override
	public List<RunningPath> searchRunningPath(SearchCondition location) {
		return runningPathDao.searchRunningPath(location);
	}

	@Override
	public List<RunningPath> selectBookmarkedPaths(RunningPath runningPath) {
		return runningPathDao.selectBookmarkedPaths(runningPath);
	}
	
}
