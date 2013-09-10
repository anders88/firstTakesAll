package no.anderska.wta;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import no.anderska.wta.engines.AdditionQuestionGenerator;
import no.anderska.wta.engines.ComputationQuestionGenerator;
import no.anderska.wta.engines.EchoQuestionGenerator;
import no.anderska.wta.engines.EquationQuestionGenerator;
import no.anderska.wta.engines.MinesweeperQuestionGenerator;
import no.anderska.wta.engines.PrimeFactorQuestionGenerator;
import no.anderska.wta.engines.RomanQuestionGenerator;
import no.anderska.wta.engines.ToRomanNumberQuestionGenerator;
import no.anderska.wta.game.GameHandler;
import no.anderska.wta.game.QuestionGenerator;
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
    private final Map<String,Class<? extends QuestionGenerator>> allGenerators = new HashMap<>();

	private SetupGame() {
        allGenerators.put("Echo",EchoQuestionGenerator.class);
        allGenerators.put("Addition",AdditionQuestionGenerator.class);
        allGenerators.put("Minesweeper",MinesweeperQuestionGenerator.class);
        allGenerators.put("PrimeFactor",PrimeFactorQuestionGenerator.class);
        allGenerators.put("ToRoman",RomanQuestionGenerator.class);
        allGenerators.put("FromRoman",ToRomanNumberQuestionGenerator.class);
        allGenerators.put("Computation",ComputationQuestionGenerator.class);
        allGenerators.put("Equation",EquationQuestionGenerator.class);

        Map<String, QuestionGenerator> engines = createGenerators(allGenerators.keySet());
        gameHandler.setGenerators(engines);
	}

    public Map<String, QuestionGenerator> createGenerators(Set<String> categoryNames) {
        Map<String,QuestionGenerator> generators = new HashMap<>();

        for (String category : categoryNames) {
            Class<? extends QuestionGenerator> generatorClass = allGenerators.get(category);
            if (generatorClass == null) {
                throw new IllegalArgumentException("Unknown category " + category);
            }
            try {
                generators.put(category, generatorClass.newInstance());
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        return generators;
    }


    public PlayerHandler getPlayerHandler() {
		return gameHandler.getPlayerHandler();
	}

    public GameHandler getGameHandler() {
        return gameHandler;
    }
}
