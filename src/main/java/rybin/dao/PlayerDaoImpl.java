package rybin.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import rybin.entity.Player;

@Repository
public class PlayerDaoImpl implements PlayerDao {

	@PersistenceContext
	private EntityManager em;

	public void save(Player player) {
    	if (player.getId() == 0) {
			em.persist(player);
		} else {
			em.merge(player);
		}
	}

	public Player findByName(String name) {
		Player p = null;
		p = em.find(Player.class, name);
		return p;
	}

	public Player findById(int id) {
		Player p = null;
		p = em.find(Player.class, id);
		return p;
	}

	public Player findPlayer(int id) {
//		TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p WHERE p.id = " + id 
//				, Player.class);
		TypedQuery<Player> query = em.createQuery("SELECT p FROM Player p", Player.class);
		List<Player> list = query.getResultList();
	
		return list.get(0);
	}
}
