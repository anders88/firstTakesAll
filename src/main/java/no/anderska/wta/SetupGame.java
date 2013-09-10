package no.anderska.wta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import no.anderska.wta.engines.AdditionEngine;
import no.anderska.wta.engines.ComputationEngine;
import no.anderska.wta.engines.EchoEngine;
import no.anderska.wta.engines.EquationEngine;
import no.anderska.wta.engines.MinesweeperEngine;
import no.anderska.wta.engines.PrimeFactorEngine;
import no.anderska.wta.engines.RomanNumberEngine;
import no.anderska.wta.engines.ToRomanNumberEngine;
import no.anderska.wta.game.QuestionGenerator;
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

    private final GameHandler gameHandler = new GameHandler();
    private final Map<String,Class<? extends QuestionGenerator>> allEngines = new HashMap<>();

	private SetupGame() {
        allEngines.put("Echo",EchoEngine.class);
        allEngines.put("Addition",AdditionEngine.class);
        allEngines.put("Minesweeper",MinesweeperEngine.class);
        allEngines.put("PrimeFactor",PrimeFactorEngine.class);
        allEngines.put("ToRoman",RomanNumberEngine.class);
        allEngines.put("FromRoman",ToRomanNumberEngine.class);
        allEngines.put("Computation",ComputationEngine.class);
        allEngines.put("Equation",EquationEngine.class);

        Map<String, QuestionGenerator> engines = createEngines(allEngines.keySet());
        gameHandler.setEngines(engines);
	}

    public Map<String, QuestionGenerator> createEngines(Set<String> categoryNames) {
        Map<String,QuestionGenerator> engines = new HashMap<>();

        for (String category : categoryNames) {
            Class<? extends QuestionGenerator> engineClass = allEngines.get(category);
            if (engineClass == null) {
                throw new IllegalArgumentException("Unknown category " + category);
            }
            try {
                QuestionGenerator engine = engineClass.newInstance();
                engines.put(category,engine);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return engines;
    }


    public PlayerHandler getPlayerHandler() {
		return gameHandler.getPlayerHandler();
	}

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
