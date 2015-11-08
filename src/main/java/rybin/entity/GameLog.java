package rybin.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


	@Entity
	public class GameLog {
		@Id
		@GeneratedValue(strategy=GenerationType.IDENTITY)
	    private int id;
		private String playerHand;
		private String dealerHand;
		private String result;
		@Temporal(TemporalType.TIMESTAMP)
		private Date gameDate;
		
		public GameLog() {
			
		}
		public String getPlayerHand() {
			return playerHand;
		}
		public void setPlayerHand(String playerHand) {
			this.playerHand = playerHand;
		}
		public String getDealerHand() {
			return dealerHand;
		}
		public void setDealerHand(String dealerHand) {
			this.dealerHand = dealerHand;
		}
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public Date getGameDate() {
			return gameDate;
		}
		public void setGameDate(Date gameDate) {
			this.gameDate = gameDate;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}

	}
