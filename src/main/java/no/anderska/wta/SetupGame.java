package no.anderska.wta;

import java.util.Arrays;
import java.util.List;

import no.anderska.wta.engines.AdditionEngine;
import no.anderska.wta.engines.EchoEngine;
import no.anderska.wta.servlet.PlayerHandler;

public class SetupGame {
	public static final SetupGame instance = setup();
	
	private static SetupGame setup() {
		SetupGame setup = new SetupGame();
		return setup;
	}
	
	private PlayerHandler playerHandler = new PlayerHandlerMemory();

	private SetupGame() {
	}
	
	public PlayerHandler getPlayerHandler() {
		return playerHandler;
	}
	

}
