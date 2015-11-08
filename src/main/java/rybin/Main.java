//package rybin;
//
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.support.ClassPathXmlApplicationContext;
//
//import rybin.client.ClientApplication;
//import rybin.entity.Player;
//import rybin.server.GameLogic;
//import rybin.service.PlayerService;
////import rybin.web.GameBean;
//
//@SuppressWarnings("resource")
//public class Main {
//
//	public static void main(String[] args) {
////		ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
//		ApplicationContext context = new ClassPathXmlApplicationContext("/spring/application-config.xml");
//		PlayerService playerService = context.getBean(PlayerService.class);
//		
//		GameLogic game = new GameLogic();
//		game.deal();
//		game.hit();
//		System.out.println(game.resultAfterTurn());
//		game.dealerTurn();
//		for (int i = 0; i < game.getPlayerHand().size(); i++) 
//			System.out.print(game.getPlayerHand().get(i) + "   ");
//		System.out.println("   sum = " + game.playerHandSum());
//		System.out.println();System.out.println();
//		for (int i = 0; i < game.getDealerHand().size(); i++) 
//			System.out.print(game.getDealerHand().get(i) + "   ");
//		System.out.println("   sum = " + game.dealerHandSum());
//		System.out.println(game.resultFinal());
//		
////		Player p = new Player();
////		p = playerService.findWallet(1);
////		GameBean g = new GameBean();
//////		g.showPlayerMoney(1);
////		int money = p.getWallet();
////		System.out.println("money: " + money + "$" );
//		
//	
////		p.setWallet(1234);
////		g.setMoney(p);
////		g.updatePlayerMoney();
////		money = p.getWallet();
////		System.out.println("money: " + money + "$" );
//		
//		Player p = new Player();
//		ClientApplication client = new ClientApplication();
////		client.getPlayerHand();
////		System.out.println(client.getDeal());
//		p = playerService.findPlayer(1);
////		
////		client.showPlayerMoney(1);
//		int money = p.getWallet();
//		System.out.println("money: " + money + "$" );
//		
//		
//	}
//	
//
//
//}
