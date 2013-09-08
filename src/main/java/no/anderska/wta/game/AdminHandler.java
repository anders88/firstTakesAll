package no.anderska.wta.game;

public interface AdminHandler {
    String restartGame(String password);

    String resetCategories(String password);

    String editCategories(String password, String[] engineNames);
}
