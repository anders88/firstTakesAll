package no.anderska.wta.servlet;

import java.util.List;

import no.anderska.wta.dto.PlayerDTO;

public interface PlayerHandler {

    String createPlayer(String name);
    void addPoints(String id, int points);
    boolean playerPlaying(String id);
    List<PlayerDTO> playerList();
    public String playerName(String playerid);

    void clear();
    int getPoints(String playerid);
}
