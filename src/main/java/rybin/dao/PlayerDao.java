package rybin.dao;

import rybin.entity.Player;

public interface PlayerDao {
	public void save(Player player);
	public Player findByName(String name);
	public Player findById(int id);
	public Player findPlayer(int id);
}



