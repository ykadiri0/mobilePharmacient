package ma.ensaj.gestionzoneville.beans;

import java.util.ArrayList;
import java.util.List;

public class PharmacieGard {

    private Integer id;
    private String date_debut;
    private String date_fin;
    private List<Pharmacie> pharmacie;
    List<Gard> list ;

    public PharmacieGard() {
    }

    List<Pharmacie> list2 ;


    public PharmacieGard( int idph,String date_debut, String date_fin, int idp) {
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        list2=new ArrayList<>();
        list2.add(new Pharmacie(idph));
        this.pharmacie = list2;
        list=new ArrayList<>();
        list.add(new Gard(idp));
        this.gard = list;
    }

    private List<Gard> gard;

    public List<Pharmacie> getPharmacie() {
        return pharmacie;
    }

    public void setPharmacie(List<Pharmacie> pharmacie) {
        this.pharmacie = pharmacie;
    }

    public List<Gard> getGard() {
        return gard;
    }

    public void setGard(List<Gard> gard) {
        this.gard = gard;
    }





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    @Override
    public String toString() {
        return "PharmacieGard{" +
                "id=" + id +
                ", date_debut='" + date_debut + '\'' +
                ", date_fin='" + date_fin + '\'' +
                ", pharmacie=" + pharmacie +
                ", list=" + list +
                ", list2=" + list2 +
                ", gard=" + gard +
                '}';
    }
}

