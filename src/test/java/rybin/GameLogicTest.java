package rybin;

import java.util.ArrayList;

import javax.inject.Inject;

import org.eclipse.persistence.annotations.Index;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import junit.framework.Assert;
import rybin.server.GameLogic;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"/test-beans.xml"})
public class GameLogicTest {
	
	GameLogic game = new GameLogic();
	
	
	@Test
	public void dealTest() {
		game.deal();
		Assert.assertEquals(2, game.getDealerHand().size());
	}
	
	@Test
	public void dealTest2() {
		game.deal();
		Assert.assertEquals(2, game.getPlayerHand().size());
	}
	
	@Test
	public void sumTest() {
		ArrayList<String> playerHand = new ArrayList<>();
		playerHand.add("2 of spades");
		playerHand.add("Ace of spades");
		playerHand.add("King of spades");
		game.setPlayerHand(playerHand);
		Assert.assertEquals(13, game.playerHandSum().intValue());
	}
	
	@Test
	public void sumAceTest() {
		ArrayList<String> playerHand = new ArrayList<>();
		playerHand.add("Ace of clubs");
		playerHand.add("Ace of spades");
		playerHand.add("Ace of hearts");
		playerHand.add("Ace of diamonds");
		game.setPlayerHand(playerHand);
		Assert.assertEquals(14, game.playerHandSum().intValue());
	}
	
	@Test
	public void hitCardTest() {
		game.deal();
		game.hit();
		Assert.assertEquals(3, game.getPlayerHand().size());
	}
	
	@Test
	public void finalResultTest1() {
		ArrayList<String> playerHand = new ArrayList<>();
		ArrayList<String> dealerHand = new ArrayList<>();
		playerHand.add("Ace of spades");
		playerHand.add("King of spades");
		game.setPlayerHand(playerHand);
		dealerHand.add("2 of spades");
		dealerHand.add("King of spades");
		game.setDealerHand(dealerHand);
		String message = game.resultFinal();
		Assert.assertEquals("Player wins", message);
	}
	
	@Test
	public void finalResultTest2() {
		ArrayList<String> playerHand = new ArrayList<>();
		ArrayList<String> dealerHand = new ArrayList<>();
		playerHand.add("2 of spades");
		playerHand.add("King of spades");
		game.setPlayerHand(dealerHand);
		dealerHand.add("2 of spades");
		dealerHand.add("King of spades");
		game.setDealerHand(dealerHand);
		String message = game.resultFinal();
		Assert.assertEquals("Draw", message);
	}
	
	@Test
	public void finalResultTest3() {
		ArrayList<String> playerHand = new ArrayList<>();
		ArrayList<String> dealerHand = new ArrayList<>();
		playerHand.add("3 of clubs");
		playerHand.add("2 of spades");
		playerHand.add("4 of hearts");
		game.setPlayerHand(playerHand);
		dealerHand.add("2 of spades");
		dealerHand.add("Queen of spades");
		game.setDealerHand(dealerHand);
		String message = game.resultFinal();
		Assert.assertEquals("Dealer wins", message);
	}

}
