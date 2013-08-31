package no.anderska.wta;

import java.util.*;

import no.anderska.wta.dto.PlayerDTO;
import no.anderska.wta.servlet.PlayerHandler;

public class  PlayerHandlerMemory implements PlayerHandler {
	private static class Player {
		@SuppressWarnings("unused")
		private String id;
		@SuppressWarnings("unused")
		private String name;
		@SuppressWarnings("unused")
		private int points = 0;

		public Player(String id, String name) {
			this.id = id;
			this.name = name;
		}
		
		public void addPoints(int givenPoints) {
			points+=givenPoints;
		}
	}
	
	private Map<String,Player> players = new HashMap<>();
	
	private long playerid = 0;

	@Override
	public synchronized String createPlayer(String name) {
		String givenid = new Long(++playerid).toString();
		players.put(givenid, new Player(givenid, name));
		return givenid;
	}

	@Override
	public void addPoints(String id, int points) {
		players.get(id).addPoints(points);
	}

	@Override
	public boolean playerPlaying(String id) {
		return players.containsKey(id);
	}

    @Override
    public List<PlayerDTO> playerList() {
        List<PlayerDTO> result = new ArrayList<>();
        for (Player player : players.values()) {
            result.add(new PlayerDTO(player.name,player.points));
        }
        return result;
    }

}
