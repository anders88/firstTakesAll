package no.anderska.wta;

import java.util.HashMap;
import java.util.Map;

import no.anderska.wta.game.GameHandler;
import no.anderska.wta.game.GameLogger;
import no.anderska.wta.game.QuestionGenerator;
import no.anderska.wta.logging.LogReader;
import no.anderska.wta.logging.MemoryGameLogger;
import no.anderska.wta.questions.AdditionQuestionGenerator;
import no.anderska.wta.questions.ComputationQuestionGenerator;
import no.anderska.wta.questions.EchoQuestionGenerator;
import no.anderska.wta.questions.EquationQuestionGenerator;
import no.anderska.wta.questions.FibonacciQuestionGenerator;
import no.anderska.wta.questions.FixedSudokoGenerator;
import no.anderska.wta.questions.MaxLetterOccurenceQuestionGenerator;
import no.anderska.wta.questions.MinesweeperQuestionGenerator;
import no.anderska.wta.questions.PrimeFactorQuestionGenerator;
import no.anderska.wta.questions.ReverseStringQuestionGenerator;
import no.anderska.wta.questions.RomanQuestionGenerator;
import no.anderska.wta.questions.TimeCalculationGenerator;
import no.anderska.wta.questions.ToRomanNumberQuestionGenerator;
import no.anderska.wta.servlet.PlayerHandler;

public class SetupGame {
	private static final SetupGame inst = new SetupGame();

	public static SetupGame instance() {
        return inst;
    }

    private final GameHandler gameHandler;
    private final MemoryGameLogger memoryGameLogger;

    private final Map<String,Class<? extends QuestionGenerator>> allGenerators = new HashMap<>();

	private SetupGame() {
        gameHandler = new GameHandler();
        memoryGameLogger = new MemoryGameLogger(gameHandler.getPlayerHandler());
        gameHandler.setGameLogger(memoryGameLogger);
        memoryGameLogger.setPlayerHandler(gameHandler.getPlayerHandler());

        allGenerators.put("Echo",EchoQuestionGenerator.class);
        allGenerators.put("Addition",AdditionQuestionGenerator.class);
        allGenerators.put("Reverse",ReverseStringQuestionGenerator.class);
        allGenerators.put("MaxOccurence",MaxLetterOccurenceQuestionGenerator.class);
        allGenerators.put("Fibonacci",FibonacciQuestionGenerator.class);
        allGenerators.put("Minesweeper",MinesweeperQuestionGenerator.class);
        allGenerators.put("PrimeFactor",PrimeFactorQuestionGenerator.class);
        allGenerators.put("ToRoman",RomanQuestionGenerator.class);
        allGenerators.put("FromRoman",ToRomanNumberQuestionGenerator.class);
        allGenerators.put("Computation",ComputationQuestionGenerator.class);
        allGenerators.put("Equation",EquationQuestionGenerator.class);
        allGenerators.put("TimeCalc",TimeCalculationGenerator.class);
        allGenerators.put("Sudoku",FixedSudokoGenerator.class);

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
