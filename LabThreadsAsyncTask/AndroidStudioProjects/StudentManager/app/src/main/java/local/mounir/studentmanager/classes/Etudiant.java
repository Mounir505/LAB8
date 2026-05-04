package local.mounir.studentmanager.classes;

/**
 * Modèle métier représentant un Étudiant.
 * Amélioration : Ajout d'une méthode toString pour faciliter le debug et l'affichage.
 */
public class Etudiant {
    // Attributs privés pour respecter l'encapsulation
    private int id;
    private String nom;
    private String prenom;

    // Constructeur complet (utile lors de la récupération depuis la base de données)
    public Etudiant(int id, String nom, String prenom) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
    }

    // Constructeur sans ID (utile pour l'insertion, car l'ID est auto-incrémenté)
    public Etudiant(String nom, String prenom) {
        this.nom = nom;
        this.prenom = prenom;
    }

    // Constructeur vide (requis par certains frameworks et pour l'instanciation manuelle)
    public Etudiant() {
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    /**
     * Amélioration Premium : Redéfinition de toString()
     * Cela permet d'afficher directement l'objet dans les logs ou un adaptateur
     * sans avoir à concaténer manuellement nom et prénom à chaque fois.
     */
    @Override
    public String toString() {
        return nom.toUpperCase() + " " + prenom;
    }
}