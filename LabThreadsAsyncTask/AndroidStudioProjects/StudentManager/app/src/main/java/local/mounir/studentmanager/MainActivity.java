package local.mounir.studentmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import local.mounir.studentmanager.classes.Etudiant;
import local.mounir.studentmanager.service.EtudiantService;

/**
 * Activité principale gérant les interactions utilisateurs.
 * Amélioration : Gestion des exceptions, validation des champs et UX fluide.
 */
public class MainActivity extends AppCompatActivity {

    private EditText editNom, editPrenom, editId;
    private Button btnAjouter, btnRechercher, btnSupprimer;
    private TextView txtResultat;
    private EtudiantService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialisation du service
        service = new EtudiantService(this);

        // Liaison des composants UI
        initViews();

        // --- Action : Ajouter un étudiant ---
        btnAjouter.setOnClickListener(v -> {
            String nom = editNom.getText().toString().trim();
            String prenom = editPrenom.getText().toString().trim();

            if (nom.isEmpty() || prenom.isEmpty()) {
                showToast("Veuillez remplir tous les champs");
            } else {
                service.create(new Etudiant(nom, prenom));
                showToast("Étudiant enregistré avec succès !");
                clearInputs();
            }
        });

        // --- Action : Rechercher un étudiant ---
        btnRechercher.setOnClickListener(v -> {
            String idString = editId.getText().toString().trim();

            if (idString.isEmpty()) {
                showToast("Saisissez un ID pour chercher");
                return;
            }

            try {
                int id = Integer.parseInt(idString);
                Etudiant e = service.findById(id);

                if (e != null) {
                    txtResultat.setText("Trouvé : " + e.toString());
                    txtResultat.setTextColor(getResources().getColor(android.R.color.black));
                } else {
                    txtResultat.setText("Aucun étudiant avec l'ID " + id);
                    txtResultat.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
                }
            } catch (NumberFormatException e) {
                showToast("ID invalide");
            }
        });

        // --- Action : Supprimer un étudiant ---
        btnSupprimer.setOnClickListener(v -> {
            String idString = editId.getText().toString().trim();

            if (idString.isEmpty()) {
                showToast("Saisissez un ID pour supprimer");
                return;
            }

            try {
                int id = Integer.parseInt(idString);
                Etudiant e = service.findById(id); // On vérifie s'il existe avant

                if (e != null) {
                    service.delete(id);
                    showToast("Étudiant supprimé");
                    txtResultat.setText("Suppression effectuée");
                    editId.setText("");
                } else {
                    showToast("Cet ID n'existe pas");
                }
            } catch (NumberFormatException e) {
                showToast("ID invalide");
            }
        });
    }

    /**
     * Relie les variables Java aux éléments du layout XML.
     */
    private void initViews() {
        editNom = findViewById(R.id.nom);
        editPrenom = findViewById(R.id.prenom);
        editId = findViewById(R.id.id);
        btnAjouter = findViewById(R.id.bn);
        btnRechercher = findViewById(R.id.load);
        btnSupprimer = findViewById(R.id.delete);
        txtResultat = findViewById(R.id.res);
    }

    /**
     * Vide les champs de texte après une insertion réussie.
     */
    private void clearInputs() {
        editNom.setText("");
        editPrenom.setText("");
        editNom.requestFocus(); // Remet le focus sur le nom
    }

    /**
     * Affiche un message rapide à l'utilisateur.
     */
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}