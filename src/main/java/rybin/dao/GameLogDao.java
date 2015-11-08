package rybin.dao;

import java.util.List;

import rybin.entity.GameLog;

public interface GameLogDao {
	public void save(GameLog gameLog);
	public GameLog findById(int id);
	public List<GameLog> findAllLogs();

}
