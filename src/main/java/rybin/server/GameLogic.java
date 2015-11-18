package rybin.server;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/game")
@Singleton
public class GameLogic {

	public String[] deck = {"2 of hearts",
			"3 of hearts",
			"4 of hearts",
			"5 of hearts",
			"6 of hearts",
			"7 of hearts",
			"8 of hearts",
			"9 of hearts",
			"10 of hearts",
			"Jack of hearts",
			"Queen of hearts",
			"King of hearts",
			"Ace of hearts",
			"2 of diamonds",
			"3 of diamonds",
			"4 of diamonds",
			"5 of diamonds",
			"6 of diamonds",
			"7 of diamonds",
			"8 of diamonds",
			"9 of diamonds",
			"10 of diamonds",
			"Jack of diamonds",
			"Queen of diamonds",
			"King of diamonds",
			"Ace of diamonds",
			"2 of spades",
			"3 of spades",
			"4 of spades",
			"5 of spades",
			"6 of spades",
			"7 of spades",
			"8 of spades",
			"9 of spades",
			"10 of spades",
			"Jack of spades",
			"Queen of spades",
			"King of spades",
			"Ace of spades",
			"2 of clubs",
			"3 of clubs",
			"4 of clubs",
			"5 of clubs",
			"6 of clubs",
			"7 of clubs",
			"8 of clubs",
			"9 of clubs",
			"10 of clubs",
			"Jack of clubs",
			"Queen of clubs",
			"King of clubs",
	"Ace of clubs"};

	public Integer getCardValues(String card) {
		card = card.substring(0, card.indexOf(" "));
		
		switch (card) {
		case "Jack":
			return 10;
		case "Queen":
			return 10;
		case "King":
			return 10;
		case "Ace":
			return 11;
		default:
		return Integer.valueOf(card);
		}
	}

	private ArrayList<String> playerHand = new ArrayList<>();
	private ArrayList<String> dealerHand = new ArrayList<>();
	private ArrayList<String> dealtCards = new ArrayList<>();
	private Random rand = new Random();
	private String result;
	private Boolean firstTurn = true;

	@GET
	@Path("/deal")
	@Produces(MediaType.TEXT_PLAIN)
	public String deal() {
		playerHand.clear();
		dealerHand.clear();
		dealtCards.clear();
		do {
			String card = deck[rand.nextInt(52)];	//TODO: 52 error
			boolean sameCard = false;
			if (dealtCards.size() == 0) {
				dealtCards.add(card);
				continue;
			}
			else
				for (int i = 0; i < dealtCards.size(); i++) {
					if (card.equals(dealtCards.get(i)))
						sameCard = true;
				}
			if (sameCard) {
				sameCard = false;
				continue;
			}				
			else
				dealtCards.add(card);
		} while (dealtCards.size() != 4); 

		for (int i = 0; i <= 2; i = i + 2) {
			playerHand.add(dealtCards.get(i));
			dealerHand.add(dealtCards.get(i + 1));	
		}
		firstTurn = true;
		resultAfterTurn();
		return "Deal done";
	}

	@GET
	@Path("/hit")
	@Produces(MediaType.TEXT_PLAIN)
	public String hit() {
		boolean sameCard = false;
		boolean flag = false;
		while(flag == false) {
			String card = deck[rand.nextInt(52)];
			for (int i = 0; i < dealtCards.size(); i++) 	
				if (card.equals(dealtCards.get(i))) 
					sameCard = true;
			if (sameCard) {
				sameCard = false;
				continue;
			}				
			else {
				dealtCards.add(card);
				playerHand.add(card);
				break;
			}
		}
		resultAfterTurn();
		return "hit";
	}
	
	@GET
	@Path("/stand")
	@Produces(MediaType.TEXT_PLAIN)
	public String stand() {
		dealerTurn();
		resultFinal();
		return "stand";
	}

	@GET
	@Path("/dealer")
	@Produces(MediaType.TEXT_PLAIN)
	public String dealerTurn() {
		boolean sameCard = false;
		boolean flag = false;

		while (dealerHandSum() < 17) {
			while(flag == false) {
				String card = deck[rand.nextInt(52)];
				for (int i = 0; i < dealtCards.size(); i++) 	
					if (card.equals(dealtCards.get(i))) 
						sameCard = true;
				if (sameCard) {
					sameCard = false;
					continue;
				}				
				else {
					dealtCards.add(card);
					dealerHand.add(card);
					break;
				}
			}
		}
		return "dealer turn";
	}

	@GET
	@Path("/playersum")
	@Produces(MediaType.TEXT_PLAIN)
	public Integer playerHandSum() {
		Integer sum = 0;
		Integer aceCount = 0;
		for (int i = 0; i < playerHand.size(); i++) {
			String card = playerHand.get(i);
			String face = card.substring(0, card.indexOf(" "));
			//	face = "Ace";
			if (!face.equals("Ace"))
				sum += getCardValues(card);
			else if (face.equals("Ace") & (sum <= 10)) {
				sum += getCardValues(card);
				aceCount++;
			}
			else if (face.equals("Ace") & (sum > 10))
				sum += 1;

			if (sum > 21 && aceCount != 0) {
				sum = sum - 10;		////////making Ace value 1 instead of 11
				aceCount--;
			}			
		}
		System.out.println("Player hand: " + sum);
		return sum;
	}

	@GET
	@Path("/dealersum")
	@Produces(MediaType.TEXT_PLAIN)
	public Integer dealerHandSum() {
		Integer sum = 0;
		Integer aceCount = 0;
		for (int i = 0; i < dealerHand.size(); i++) {
			String card = dealerHand.get(i);
			String face = card.substring(0, card.indexOf(" "));
			//	face = "Ace";
			if (!face.equals("Ace"))
				sum += getCardValues(card);
			else if (face.equals("Ace") & (sum <= 10)) {
				sum += getCardValues(card);
				aceCount++;
			}
			else if (face.equals("Ace") & (sum > 10))
				sum += 1;

			if (sum > 21 && aceCount != 0) {
				sum = sum - 10;		////////making Ace value 1 instead of 11
				aceCount--;
			}			
		}
		System.out.println("Dealer hand: " + sum);
		return sum;
	}
	
	@GET
	@Path("/result")
	@Produces(MediaType.TEXT_PLAIN)
	public String resultAfterTurn() {
		
		if (playerHandSum() == 21 && firstTurn == true) {
			result = "Blackjack!";
			gameResult();
			return "Blackjack!";
		}

//		else if (dealerHandSum() == 21 && firstTurn == true) 
//			return result = "Dealer Blackjack!";
		else if (playerHandSum() > 21) {
			result = "Player bust";
			gameResult();
			return "Player bust";	
		}
		else if (dealerHandSum() > 21) {
			result = "Dealer bust";
			gameResult();
			return "Dealer bust";
		}	
		else if (playerHandSum() == 21 && dealerHandSum() == 21) {
			result = "Player wins";
			gameResult();
			return "Draw";
		}
		else if (playerHandSum() == 21) {
			result = "Player wins";
			gameResult();
			return "Player wins";	
		}	
		else if (dealerHandSum() == 21) {
			result = "Dealer wins";
			gameResult();
			return "Dealer wins";
		}
		
		
		firstTurn = false;
		return result = "_";
	}

	@GET
	@Path("/finalresult")
	@Produces(MediaType.TEXT_PLAIN)
	public String resultFinal() {
		
		if (playerHandSum() > dealerHandSum() & playerHandSum() <= 21 | dealerHandSum() > 21) {
			System.out.println("(server side) final result - player wins");
			return result = "Player wins";
		}
		else if (playerHandSum() < dealerHandSum() & dealerHandSum() <= 21 | playerHandSum() > 21) {
			System.out.println("(server side) final result - dealer wins");
			return result = "Dealer wins";
		}
		else if (playerHandSum() == dealerHandSum())  {
			System.out.println("(server side) final result - draw");
			return result = "Draw";
		}
		
		return result = ".";
	}
	
	@GET
	@Path("/gameresult")
	@Produces(MediaType.TEXT_PLAIN)
	public String gameResult() {	
		System.out.println("(server side) result before method :gameResult:" + result);
		if (result.equals("Player bust") || 
				result.equals("Dealer wins") ||
				result.equals("Player wins") || 
				result.equals("Dealer bust") ||
				result.equals("Blackjack!") ||
				result.equals("Draw")) {
			
			if (result.equals("Blackjack!")) 	
				return "Blackjack!";		
			if (result.equals("Player wins") || result.equals("Dealer bust")) 
				return "Player wins";			
			else if (result.equals("Player bust") || result.equals("Dealer wins")) 
				return "Dealer wins";
			else if (result.equals("Draw"))
				return "Draw";		
		}
		return "Not finished"; 
	}

	@GET
	@Path("/playerhand")
	@Produces(MediaType.APPLICATION_XML)
	public ResponseList ResponsePlayerHand() {
		ResponseList responsePlayerHand = new ResponseList();
		responsePlayerHand.setList(playerHand);
		return responsePlayerHand;
	}
	
	@GET
	@Path("/dealerhand")
	@Produces(MediaType.APPLICATION_XML)
	public ResponseList ResponseDealerHand() {
		ResponseList responseDealerHand = new ResponseList();
		responseDealerHand.setList(dealerHand);
		return responseDealerHand;
	}
	
	@GET
	@Path("/clearhand")
	@Produces(MediaType.TEXT_PLAIN)
	public String clearHand() {
		dealerHand.clear();
		playerHand.clear();
		return "clear";
	}

	public ArrayList<String> getDealerHand() {
		return dealerHand;
	}

	public void setDealerHand(ArrayList<String> dealerHand) {
		this.dealerHand = dealerHand;
	}

	public ArrayList<String> getPlayerHand() {
		return playerHand;
	}

	public void setPlayerHand(ArrayList<String> playerHand) {
		this.playerHand = playerHand;
	}

	
	
//	@POST
//	@Consumes(MediaType.APPLICATION_XML)
//	@Produces(MediaType.APPLICATION_XML)
//	public GameLog addLog(log) {
//		return 
//	}


}

