package ma.ensaj.gestionzoneville.beans;


public class Zone {
    public Zone(Integer id) {
        this.id = id;
    }

    private Integer id;
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
