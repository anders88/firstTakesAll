package no.anderska.wta.dto;

public class QuestionCategoryDTO {
    private final int id;
    private final String description;

    private QuestionCategoryDTO(int id, String description) {
        this.id = id;
        this.description = description;
    }
    

    public static QuestionCategoryDTO create(int id,String description) {
        return new QuestionCategoryDTO(id,description);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuestionCategoryDTO)) {
            return false;
        }
        return id == ((QuestionCategoryDTO) obj).id;
    }
    
    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }
    
    public String getDescription() {
		return description;
	}
    
    public int getId() {
        return id;
    }

}
