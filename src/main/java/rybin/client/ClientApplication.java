package rybin.client;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.MediaType;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.GenericType;

import rybin.entity.GameLog;
import rybin.entity.Player;
import rybin.service.GameLogService;
import rybin.service.PlayerService;

@Named
@Scope("session")
public class ClientApplication {

	private List<GameLog> logList = null;
	private int playerMoney;
	private GameLog newGameLog = null;
	private ArrayList<String> playerHand = new ArrayList<>();
	private ArrayList<String> dealerHand = new ArrayList<>();
	private String result = " ";
	private int bet;
	private String message = " ";
	private boolean show = false;

	public ClientApplication() {}


	public Document loadXMLFromString(String xml) throws Exception
	{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		InputSource is = new InputSource(new StringReader(xml));
		return builder.parse(is);
	}

	public String getDeal() {	      
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/deal").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		showMoney();
		getPlayerCards();
		getDealerCards();
		getResult();
		gameResult();

		return string; 
	}

	public String getHit() {	      
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/hit").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		getPlayerCards();
		getDealerCards();
		getResult();
		gameResult();
		return string; 
	}

	public String getDealerTurn() {
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/dealer").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		if (getDealerSum() > 21) {
			getFinalResult();
			gameResult();
		}		
		getPlayerCards();
		getDealerCards();
		return string; 
	}

	public String getStand() {
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/stand").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		getPlayerCards();
		getDealerCards();
		show = true;
		getFinalResult();
		gameResult();
		//		getDealerTurn();
		//		String res = getFinalResult();
		return string; 
	}

	public String getResult() {
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/result").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		getPlayerCards();
		getDealerCards();
		result = string;
		return string; 
	}

	public String getFinalResult() {	      
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/finalresult").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		getPlayerCards();
		getDealerCards();
		result = string;
		return string; 
	}
	
	public int getDealerSum() {
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/dealersum").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		getPlayerCards();
		getDealerCards();
		
		return Integer.valueOf(string); 
	}
	
	public String makeBet() {	 	
		updateMoney(0 - bet);
		showMoney();
		return "game.xhtml?faces-redirect=true";
	}

	public int showMoney() {	      
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/db/showmoney").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		playerMoney = Integer.valueOf(string);
		return playerMoney; 
	}

	public String updateMoney(int amount) {	      
		playerMoney += amount;
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/db/updatemoney/" + playerMoney).accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		return string; 
	}

	public String updateLog(String res) {	
		res = res.replaceAll(" ", "-");
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/db/updatelog/" +
				playerCardList() + "/" + dealerCardList() + "/" + res).accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		return "log done"; 
	}

	public String gameResult() {
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/gameresult").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
//		System.out.println("" + string);
			
		getPlayerCards();
		getDealerCards();
//System.out.println(string);
		if (string.equals("Player bust") || 
				string.equals("Dealer wins") ||
				string.equals("Player wins") || 
				string.equals("Dealer bust") ||
				string.equals("Blackjack!") ||
				string.equals("Draw")) {
			
			if (string.equals("Blackjack!")) {					
				System.out.println("(client side) Blackjack");
				updateMoney(bet * 25 / 10);
				updateLog("Blackjack!");
				return message = "Blackjack!";
			}
			if (string.equals("Player wins") || string.equals("Dealer bust")) {	
				System.out.println("(client side) player wins");
				updateMoney(bet * 2);
				updateLog("Player wins");
				return message = "Player wins";
			}
			else if (string.equals("Player bust") || string.equals("Dealer wins")) {
				System.out.println("(client side) dealer wins");
				updateLog("Dealer wins");
				return message = "Dealer wins";
			}
			else if (string.equals("Draw")) {
				System.out.println("(client side) draw");
				updateMoney(bet);
				updateLog("Draw");
				return message = "Draw";
			}
		}
		System.out.println("(client side) no result");
		return "/ "; 
	}

	//	public String gameResult() {	
	//		if (result.equals("Player bust") || 
	//				result.equals("Dealer wins") ||
	//				result.equals("Player wins") || 
	//				result.equals("Dealer bust") ||
	//				result.equals("Blackjack!") ||
	//				result.equals("Draw")) {
	//			for (int i = 0; i < dealerHand.size(); i++) 
	//				newGameLog.setDealerHand(dealerHand.get(i) + " | ");
	//			for (int i = 0; i < playerHand.size(); i++) 
	//				newGameLog.setDealerHand(playerHand.get(i) + " | ");
	//			newGameLog.setGameDate(Calendar.getInstance().getTime());
	//
	//			if (result.equals("Blackjack")) {	
	//				newGameLog.setResult("Player wins. blackjack");
	////				gameLogService.save(newGameLog);
	//				return "Blackjack!";
	//			}
	//			
	//			if (result.equals("Player wins") || result.equals("Dealer bust")) {	
	//				newGameLog.setResult("Player wins");
	////				gameLogService.save(newGameLog);
	//				return "Player wins";
	//			}
	//			else if (result.equals("Player bust") || result.equals("Dealer wins")) {
	//				newGameLog.setResult("Dealer wins");
	////				gameLogService.save(newGameLog);
	//				return "Dealer wins";
	//			}
	//			else if (result.equals("Draw")) {
	//				newGameLog.setResult("Draw");
	////				gameLogService.save(newGameLog);
	//				return "Draw";
	//			}
	//		}
	//		return "Not finished"; 
	//	}

	public int betResult() {
		if (result.equals("Player wins") || result.equals("Dealer bust"))
			return bet * 2 - bet;
		else if (result.equals("Player bust") || result.equals("Dealer wins")) 
			return -bet;
		else if (result.equals("Draw"))
			return 0;
		else if (result.equals("Blackjack"))
			return bet * 3 - bet;
		else
			return -1;

	}

	public String getPlayerCards() {	     
		String xml = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/playerhand").accept("application/xml;charset=UTF-8")
				.get(new GenericType<String>(){});
		NodeList result;
		playerHand.clear();
		try {
			result = loadXMLFromString(xml).getElementsByTagName("list");
			for (int i = 0; i < result.getLength(); i++) {
				playerHand.add(result.item(i).getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}	
		return xml; 
	}

	public String getDealerCards() {      
		String xml = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/dealerhand").accept("application/xml;charset=UTF-8")
				.get(new GenericType<String>(){});
		NodeList result;
		dealerHand.clear();
		try {
			result = loadXMLFromString(xml).getElementsByTagName("list");
			for (int i = 0; i < result.getLength(); i++) {
				dealerHand.add(result.item(i).getTextContent());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return xml; 
	}
	
	public String getClearHands() {      
		String string = Client.create().resource("http://localhost:8080/Blackjack/webapi/game/clearhand").accept(MediaType.TEXT_PLAIN)
				.get(new GenericType<String>(){});
		playerHand.clear();
		dealerHand.clear();
		return string;
	}

	public String playerCardList() {
		String hand = "";
		for (int i = 0; i < playerHand.size(); i++) 
			hand += playerHand.get(i) + "   ";
		hand = hand.replaceAll(" ", "-");
		return hand;
	}

	public String dealerCardList() {
		String hand = "";
		for (int i = 0; i < dealerHand.size(); i++) 
			hand += dealerHand.get(i) + "   ";
		hand = hand.replaceAll(" ", "-");
		return hand;
	}
	
	public String startGame() {
		showMoney();
		if (playerMoney < 10)
			return "sorry.xhtml?faces-redirect=true";
		else
		return "game.xhtml?faces-redirect=true";
	}

	public String newGame() {	
		getClearHands();
		result = " ";
		message = " ";
		show = false;
//		getDeal();
		return " ";  
	}

	public ArrayList<String> getPlayerHand() {
		return playerHand;
	}

	public void setPlayerHand(ArrayList<String> playerHand) {
		this.playerHand = playerHand;
	}

	public ArrayList<String> getDealerHand() {
		return dealerHand;
	}

	public void setDealerHand(ArrayList<String> dealerHand) {
		this.dealerHand = dealerHand;
	}

	public int getPlayerMoney() {
		return playerMoney;
	}

	public void setPlayerMoney(int playerMoney) {
		this.playerMoney = playerMoney;
	}


	public List<GameLog> getLogList() {
		return logList;
	}


	public boolean isShow() {
		return show;
	}


	public void setShow(boolean show) {
		this.show = show;
	}


	public void setLogList(List<GameLog> logList) {
		this.logList = logList;
	}


	public GameLog getNewGameLog() {
		return newGameLog;
	}


	public void setNewGameLog(GameLog newGameLog) {
		this.newGameLog = newGameLog;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public int getBet() {
		return bet;
	}


	public void setBet(int bet) {
		this.bet = bet;
	}

	public void setResult(String result) {
		this.result = result;
	}
	
	public String getResultM() {
		return result;
	}



}

