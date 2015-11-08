package rybin.service;

import java.util.List;

import rybin.entity.GameLog;

public interface GameLogService {
	
	public void save(GameLog gameLog);
	public GameLog findById(int id);
	public List<GameLog> findAllLogs();
}
