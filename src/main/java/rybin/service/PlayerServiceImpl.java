package rybin.service;

import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.transaction.annotation.Transactional;

import rybin.dao.PlayerDao;
import rybin.entity.Player;

@Named
@Transactional
public class PlayerServiceImpl implements PlayerService{
	@Inject
	private PlayerDao playerDao;

	
	public void save(Player player){
		playerDao.save(player);
	}	
	
	public Player findByName(String name) {
		return playerDao.findByName(name);
	}
	public Player findById(int id) {
		return playerDao.findById(id);
	}
	
	public Player findPlayer(int id) {
		System.out.println(playerDao.findPlayer(id).getWallet());
		return playerDao.findPlayer(id);
	}

}
