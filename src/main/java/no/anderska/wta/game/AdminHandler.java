package no.anderska.wta.game;

import java.util.List;

import no.anderska.wta.dto.CategoriesAnsweredDTO;

public interface AdminHandler {
    String restartGame();

    String resetCategories();

    String editCategories(List<String> questionGeneratorNames);

    List<CategoriesAnsweredDTO> categoriesAnswered();

    String toggleLoserBonus();
}
