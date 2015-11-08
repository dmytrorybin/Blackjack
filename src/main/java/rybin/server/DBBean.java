package rybin.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import rybin.entity.GameLog;
import rybin.entity.Player;
import rybin.service.GameLogService;
import rybin.service.PlayerService;

@Path("/db")
@Singleton
public class DBBean {
	ApplicationContext context = new ClassPathXmlApplicationContext("/spring/application-config.xml");
	PlayerService playerService = context.getBean(PlayerService.class);
	GameLogService gameLogService = context.getBean(GameLogService.class);
	
//	@Inject
//	private GameLogService gameLogService;

	public DBBean() {

	}
	
	@GET
	@Path("/showmoney")
	@Produces(MediaType.TEXT_PLAIN)
	public String showMoney() {
		Player p = new Player();
		p = playerService.findPlayer(1);
		System.out.println(p.getWallet());
		Integer money = p.getWallet();
		
		return money.toString();
	}
	
	@GET
	@Path("/updatemoney/{amount}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateMoney(@PathParam("amount") int money) {
		Player p = new Player();
		p = playerService.findPlayer(1);
		p.setWallet(money);
		playerService.save(p);
		return "money saved";
	}
	
//	@GET
//	@Path("/showlog")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String showMoney() {
//		Player p = new Player();
//		p = playerService.findPlayer(1);
//		System.out.println(p.getWallet());
//		Integer money = p.getWallet();
//		
//		return money.toString();
//	}
	
	@GET
	@Path("/updatelog/{phand}/{dhand}/{result}")
	@Produces(MediaType.TEXT_PLAIN)
	public String updateLog(@PathParam("phand") String playerHand, @PathParam("dhand") String dealerHand,
			@PathParam("result") String res) {
		GameLog gl = new GameLog();
		playerHand = playerHand.replaceAll("-", " ");
		dealerHand = dealerHand.replaceAll("-", " ");
		res = res.replaceAll("-", " ");
		gl.setPlayerHand(playerHand);
		gl.setDealerHand(dealerHand);
		gl.setGameDate(Calendar.getInstance().getTime());
		gl.setResult(res);
		gameLogService.save(gl);
		return "log saved";
	}
}

//-Dcatalina.base="C:\Users\ִלטענטי\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1" 
//-Dcatalina.home="C:\eclipse\apache-tomcat-8.0.28" 
//-Dwtp.deploy="C:\Users\ִלטענטי\workspace\.metadata\.plugins\org.eclipse.wst.server.core\tmp1\wtpwebapps" 
//-Djava.endorsed.dirs="C:\eclipse\apache-tomcat-8.0.28\endorsed"
