package no.anderska.wta;

import java.util.Arrays;
import java.util.List;

import no.anderska.wta.engines.AdditionEngine;
import no.anderska.wta.engines.EchoEngine;
import no.anderska.wta.servlet.PlayerHandler;

public class SetupGame {
	public static SetupGame instance = setup();
	
	private static SetupGame setup() {
		SetupGame setup = new SetupGame();
		return setup;
	}
	
	private PlayerHandler playerHandler = new PlayerHandlerMemory();
	private GameEngine gameEngine;

	private SetupGame() {
		List<QuestionCategoryEngine> categories = Arrays.asList(
				new EchoEngine(10),new AdditionEngine(20, 4)
				);
		gameEngine = new GameEngine(categories, playerHandler);
	}
	
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}
	
	public GameEngine getGameEngine() {
		return gameEngine;
	}
	
}
