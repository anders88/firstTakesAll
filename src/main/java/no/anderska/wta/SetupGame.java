package no.anderska.wta;

import no.anderska.wta.game.GameHandler;
import no.anderska.wta.game.GameLogger;
import no.anderska.wta.logging.LogReader;
import no.anderska.wta.logging.MemoryGameLogger;
import no.anderska.wta.questions.QuestionGeneratorFactory;
import no.anderska.wta.servlet.PlayerHandler;

public class SetupGame {

    private static final SetupGame inst = new SetupGame();

	public static SetupGame instance() {
        return inst;
    }

    private final GameHandler gameHandler;
    private final MemoryGameLogger memoryGameLogger;

	private SetupGame() {
        try {
            gameHandler = new GameHandler();
            memoryGameLogger = new MemoryGameLogger(gameHandler.getPlayerHandler());
            gameHandler.setGameLogger(memoryGameLogger);
            memoryGameLogger.setPlayerHandler(gameHandler.getPlayerHandler());
            gameHandler.setQuestionGeneratorFactory(QuestionGeneratorFactory.withAllQuestions());
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            throw e;
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
