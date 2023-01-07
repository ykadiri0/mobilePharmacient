package ma.ensaj.gestionzoneville.beans;



public class User {

    private Integer id;

    public User(int idu, Pharmacie pharmacie) {
        this.id=idu;
        this.pharmacie=pharmacie;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", psw='" + psw + '\'' +
                ", pharmacie=" + pharmacie +
                ", photo='" + photo + '\'' +
                '}';
    }

    public User(String nom, String prenom, String email, String psw) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.psw = psw;
    }
    public User(String nom, String prenom, String email, String psw,String url) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.psw = psw;
        this.photo=url;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String nom;
    private String prenom;
    private String email;
    private String psw;


    private Pharmacie pharmacie;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPsw() {
        return psw;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    private String photo;

    public Pharmacie getPharmacie() {
        return pharmacie;
    }

    public void setPharmacie(Pharmacie pharmacie) {
        this.pharmacie = pharmacie;
    }
}
