package no.anderska.wta;

import java.util.HashMap;
import java.util.Map;

import no.anderska.wta.servlet.PlayerHandler;

public class  PlayerHandlerMemory implements PlayerHandler {
	private static class Player {
		@SuppressWarnings("unused")
		private long id;
		@SuppressWarnings("unused")
		private String name;
		@SuppressWarnings("unused")
		private int points = 0;

		public Player(long id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public void addPoints(int givenPoints) {
			points+=givenPoints;
		}
	}
	
	private Map<Long,Player> players = new HashMap<>();
	
	private long playerid = 0;

	@Override
	public synchronized long createPlayer(String name) {
		long givenid = ++playerid;
		players.put(givenid, new Player(givenid, name));
		return givenid;
	}

	@Override
	public void addPoints(long id, int points) {
		players.get(id).addPoints(points);
	}

	@Override
	public boolean playerPlaying(long id) {
		return players.containsKey(id);
	}

}
