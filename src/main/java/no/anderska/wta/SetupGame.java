package no.anderska.wta;

import java.util.HashMap;
import java.util.Map;

import no.anderska.wta.engines.AdditionEngine;
import no.anderska.wta.engines.EchoEngine;
import no.anderska.wta.game.Engine;
import no.anderska.wta.game.GameHandler;
import no.anderska.wta.servlet.PlayerHandler;

public class SetupGame {
	private static final SetupGame inst = setup();

    public static SetupGame instance() {
        return inst;
    }

    private static SetupGame setup() {
		SetupGame setup = new SetupGame();
		return setup;
	}
	
	private PlayerHandler playerHandler = new PlayerHandlerMemory();
    private GameHandler gameHandler = new GameHandler();

	private SetupGame() {
        gameHandler.setPlayerHandler(playerHandler);
        Map<String, Engine> engines = new HashMap<>();
        engines.put("Echo",new EchoEngine(5));
        engines.put("Addition",new AdditionEngine(5,4));
        gameHandler.setEngines(engines);
	}
	
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
