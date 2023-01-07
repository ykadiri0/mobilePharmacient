package ma.ensaj.gestionzoneville.beans;


import android.widget.EditText;

public class Pharmacie {

    private Integer id;
    private String name;
    private Double lat;
    private Double lon;
    private String photo;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private Zone zone;
    private int etat;

    public int getEtat() {
        return etat;
    }

    public void setEtat(int etat) {
        this.etat = etat;
    }

    public Pharmacie(int idph) {
        this.id=idph;
    }

    @Override
    public String toString() {
        return "Pharmacie{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", zone=" + zone +
                '}';
    }

    public Pharmacie(String name, Double lat, Double lon, Zone zone) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.zone = zone;
    }
    public Pharmacie(String name, Double lat, Double lon, Zone zone,String photo) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.zone = zone;
        this.photo=photo;
    }

    public Zone getZone() {
        return zone;
    }

    public void setZone(Zone zone) {
        this.zone = zone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
