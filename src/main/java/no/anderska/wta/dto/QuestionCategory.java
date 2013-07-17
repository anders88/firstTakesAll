package no.anderska.wta.dto;

public class QuestionCategory {
    private final Long id;
    private final String description;

    private QuestionCategory(Long id, String description) {
        this.id = id;
        this.description = description;
    }
    

    public static QuestionCategory create(Long id,String description) {
        return new QuestionCategory(id,description);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof QuestionCategory)) {
            return false;
        }
        return nullSafeEquals(id,((QuestionCategory) obj).id);
    }
    
    private static <T> boolean nullSafeEquals(T a, T b) {
        return (a != null) ? a.equals(b) : b != null;
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : -1;
    }
    
    public String getDescription() {
		return description;
	}

}
