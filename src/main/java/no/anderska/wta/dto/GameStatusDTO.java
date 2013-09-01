package no.anderska.wta.dto;

import java.util.List;

public class GameStatusDTO {
    private List<PlayerDTO> players;
    private List<CategoryDTO> categories;

    public GameStatusDTO(List<PlayerDTO> players, List<CategoryDTO> categories) {
        this.players = players;
        this.categories = categories;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }
}
