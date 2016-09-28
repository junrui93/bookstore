package junrui.model;

public class PublPerson {
    private Integer publId;
    private Integer personId;
    
    private int author;
    
	public int getAuthor() {
		return author;
	}

    public Integer getPublId() {
        return publId;
    }

    public void setPublId(Integer publId) {
        this.publId = publId;
    }

    public Integer getPersonId() {
        return personId;
    }

    public void setPersonId(Integer personId) {
        this.personId = personId;
    }
}