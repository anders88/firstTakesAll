package no.anderska.wta.servlet;

import no.anderska.wta.dto.PlayerDTO;

import java.util.List;

public interface PlayerHandler {

    String createPlayer(String name);
    void addPoints(String id, int points);
    boolean playerPlaying(String id);
    List<PlayerDTO> playerList();
    public String playerName(String playerid);

    void clear();
}
