package rybin.service;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import rybin.dao.GameLogDao;
import rybin.entity.GameLog;

@Named
public class GameLogServiceImpl implements GameLogService {
	@Inject
	private GameLogDao gameLogDao;
	
	@Transactional
	public void save(GameLog gameLog){
		gameLogDao.save(gameLog);
	}	
	
	public GameLog findById(int id) {
		return gameLogDao.findById(id);
	}
	
	public List<GameLog> findAllLogs() {
		return gameLogDao.findAllLogs();
	}
}
