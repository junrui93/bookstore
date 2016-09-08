package junrui.model;

public class Person {
    private Integer id;

    private Integer type;

    private String name;

    private String aux;

    private String bibtex;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAux() {
        return aux;
    }

    public void setAux(String aux) {
        this.aux = aux == null ? null : aux.trim();
    }

    public String getBibtex() {
        return bibtex;
    }

    public void setBibtex(String bibtex) {
        this.bibtex = bibtex == null ? null : bibtex.trim();
    }
}