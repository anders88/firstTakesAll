package no.anderska.wta.game;

import java.util.List;

import no.anderska.wta.dto.CategoriesAnsweredDTO;

public interface AdminHandler {
    String restartGame(String password);

    String resetCategories(String password);

    String editCategories(String password, List<String> questionGeneratorNames);

    List<CategoriesAnsweredDTO> categoriesAnswered();

    String toggleLoserBonus(String password);
}
