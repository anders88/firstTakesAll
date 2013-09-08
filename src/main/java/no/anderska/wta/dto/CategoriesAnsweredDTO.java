package no.anderska.wta.dto;

import java.util.List;

public class CategoriesAnsweredDTO {
    private String playerName;
    private List<String> categories;

    public CategoriesAnsweredDTO(String playerName, List<String> categories) {
        this.playerName = playerName;
        this.categories = categories;
    }

    public String getPlayerName() {
        return playerName;
    }

    public List<String> getCategories() {
        return categories;
    }
}
