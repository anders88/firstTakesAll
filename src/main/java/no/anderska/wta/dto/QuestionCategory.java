package no.anderska.wta.dto;

public class QuestionCategory {
    private final int id;
    private final String description;

    private QuestionCategory(int id, String description) {
        this.id = id;
        this.description = description;
    }
    

    public static QuestionCategory create(int id,String description) {
        return new QuestionCategory(id,description);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuestionCategory)) {
            return false;
        }
        return id == ((QuestionCategory) obj).id;
    }
    
    @Override
    public int hashCode() {
        return new Integer(id).hashCode();
    }
    
    public String getDescription() {
		return description;
	}

}
