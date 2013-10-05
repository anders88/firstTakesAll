package no.anderska.wta;

import java.util.HashMap;
import java.util.Map;

import no.anderska.wta.game.GameHandler;
import no.anderska.wta.game.GameLogger;
import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.logging.LogReader;
import no.anderska.wta.logging.MemoryGameLogger;
import no.anderska.wta.questions.*;
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

    private final GameHandler gameHandler;
    private final MemoryGameLogger memoryGameLogger;

    private final Map<String,Class<? extends QuestionGenerator>> allGenerators = new HashMap<>();

	private SetupGame() {
        gameHandler = new GameHandler();
        memoryGameLogger = new MemoryGameLogger();
        gameHandler.setGameLogger(memoryGameLogger);
        memoryGameLogger.setPlayerHandler(gameHandler.getPlayerHandler());

        allGenerators.put("Echo",EchoQuestionGenerator.class);
        allGenerators.put("Addition",AdditionQuestionGenerator.class);
        allGenerators.put("Minesweeper",MinesweeperQuestionGenerator.class);
        allGenerators.put("PrimeFactor",PrimeFactorQuestionGenerator.class);
        allGenerators.put("ToRoman",RomanQuestionGenerator.class);
        allGenerators.put("FromRoman",ToRomanNumberQuestionGenerator.class);
        allGenerators.put("Computation",ComputationQuestionGenerator.class);
        allGenerators.put("Equation",EquationQuestionGenerator.class);
        allGenerators.put("TimeCalc",TimeCalculationGenerator.class);

        for (String category : allGenerators.keySet()) {
            gameHandler.addQuestionCategory(category, createGenerator(category));
        }



	}

    public QuestionGenerator createGenerator(String category) {
        Class<? extends QuestionGenerator> generatorClass = allGenerators.get(category);
        if (generatorClass == null) {
            throw new IllegalArgumentException("Unknown category " + category);
        }
        try {
            return generatorClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public PlayerHandler getPlayerHandler() {
		return gameHandler.getPlayerHandler();
	}

    public GameHandler getGameHandler() {
        return gameHandler;
    }

    public GameLogger getGameLogger() {
        return memoryGameLogger;
    }

    public LogReader getLogReader() {
        return memoryGameLogger;
    }
}
