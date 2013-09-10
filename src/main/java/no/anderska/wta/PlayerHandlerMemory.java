package no.anderska.wta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import no.anderska.wta.dto.PlayerDTO;
import no.anderska.wta.servlet.PlayerHandler;

public class  PlayerHandlerMemory implements PlayerHandler {
	private static class Player {
		@SuppressWarnings("unused")
		private final String id;
		private final String name;
		private int points = 0;

		public Player(String id, String name) {
			this.id = id;
			this.name = name;
		}

		public void addPoints(int givenPoints) {
			points+=givenPoints;
		}
	}

	private final Map<String,Player> players = new HashMap<>();

	private long playerid = 0;

    private synchronized long nextId() {
        playerid++;
        return playerid;
    }

    private String genNewId() {
        StringBuilder id = new StringBuilder();
        long givenid = nextId();
        if (givenid < 10L) {
            id.append("0");
        }
        id.append(givenid);
        int randomSeed = new Random().nextInt(90000) + 10000;
        id.append(randomSeed);
        return id.toString();
    }

	@Override
	public String createPlayer(String name) {
		String givenid = genNewId();
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

    @Override
    public String playerName(String playerid) {
        return players.get(playerid).name;
    }

    @Override
    public void clear() {
        players.clear();
    }

    @Override
    public int getPoints(String playerid) {
        return players.get(playerid).points;
    }

}
