package no.anderska.wta.servlet;

public interface PlayerHandler {

    long createPlayer(String name);
    void addPoints(long id, int points);

}
