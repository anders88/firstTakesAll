package no.anderska.wta.servlet;

import no.anderska.wta.dto.PlayerDTO;

import java.util.List;

public interface PlayerHandler {

    long createPlayer(String name);
    void addPoints(long id, int points);
    boolean playerPlaying(long id);
    List<PlayerDTO> playerList();

}
