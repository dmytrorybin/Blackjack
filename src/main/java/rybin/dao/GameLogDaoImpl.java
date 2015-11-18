package rybin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import rybin.entity.GameLog;

@Repository
public class GameLogDaoImpl implements GameLogDao {
	
	@PersistenceContext
    private EntityManager em;
    
    public void save(GameLog gameLog) {
    	if (gameLog.getId() == 0) {
			em.persist(gameLog);
		} else {
			em.merge(gameLog);
		}
	}
 
	public GameLog findById(int id) {
		GameLog gl = null;
		gl = em.find(GameLog.class, id);
		return gl;
	}

	public List<GameLog> findAllLogs() {
		TypedQuery<GameLog> query = em.createQuery("SELECT g FROM GameLog g" 
				, GameLog.class);
		return query.getResultList();
	}

}





