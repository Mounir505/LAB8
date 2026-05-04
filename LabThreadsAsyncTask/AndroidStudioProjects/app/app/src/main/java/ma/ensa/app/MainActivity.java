package ma.ensa.app;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    // 1. Déclaration des variables
    private EditText nom;
    private Button bn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Gestion des marges système (EdgeToEdge)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Initialisation des composants via leur ID (doit correspondre au XML)
        nom = findViewById(R.id.nom); // Assurez-vous que l'ID est android:id="@+id/nom"
        bn = findViewById(R.id.bn);   // Assurez-vous que l'ID est android:id="@+id/bn"

        // 3. Ajout de l'événement sur le bouton
        bn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Récupération de la saisie
                String texteSaisi = nom.getText().toString();

                if (!texteSaisi.isEmpty()) {
                    // Affichage d'un petit message
                    Toast.makeText(MainActivity.this, "Bonjour " + texteSaisi, Toast.LENGTH_SHORT).show();
                } else {
                    nom.setError("Veuillez saisir un nom");
                }
            }
        });
    }
}