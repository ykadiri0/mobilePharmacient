package ma.ensaj.gestionzoneville.beans;



public class Gard {
    public Gard(Integer id) {
        this.id = id;
    }

    private Integer id;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
