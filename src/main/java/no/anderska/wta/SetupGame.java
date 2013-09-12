package no.anderska.wta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import no.anderska.wta.engines.*;
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
	
	private PlayerHandlerMemory playerHandler = new PlayerHandlerMemory();
    private GameHandler gameHandler = new GameHandler();
    private Map<String,Class<? extends Engine>> allEngines = new HashMap<>();

	private SetupGame() {
        allEngines.put("Echo",EchoEngine.class);
        allEngines.put("Addition",AdditionEngine.class);
        allEngines.put("Minesweeper",MinesweeperEngine.class);
        allEngines.put("PrimeFactor",PrimeFactorEngine.class);
        allEngines.put("ToRoman",RomanNumberEngine.class);
        allEngines.put("FromRoman",ToRomanNumberEngine.class);
        allEngines.put("Computation",ComputationEngine.class);
        allEngines.put("Equation",EquationEngine.class);
        allEngines.put("TimeCalc",TimeCalculationEngine.class);

        gameHandler.setPlayerHandler(playerHandler);

        Map<String, Engine> engines = createEngines(allEngines.keySet());
        gameHandler.setEngines(engines);
	}

    public Map<String, Engine> createEngines(Set<String> categoryNames) {
        Map<String,Engine> engines = new HashMap<>();

        for (String category : categoryNames) {
            Class<? extends Engine> engineClass = allEngines.get(category);
            if (engineClass == null) {
                throw new IllegalArgumentException("Unknown category " + category);
            }
            try {
                Engine engine = engineClass.newInstance();
                engines.put(category,engine);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return engines;
    }


    public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
