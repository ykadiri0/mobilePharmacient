package ma.ensaj.gestionzoneville.beans;



public class Ville {
    private Integer id;
    private String population;
    private double longitude;
    private double latitude;
    private Zone zone;

    @Override
    public String toString() {
        return "Ville{" +
                "id=" + id +
                ", population='" + population + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", zone=" + zone +
                ", name='" + name + '\'' +
                '}';
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Ville(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Ville() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

