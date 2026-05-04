package local.mounir.studentmanager.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Classe utilitaire pour la gestion de la structure de la base de données.
 * Amélioration : Centralisation des noms de colonnes pour une maintenance facile.
 */
public class MySQLiteHelper extends SQLiteOpenHelper {

    // Informations sur la base de données
    private static final String DATABASE_NAME = "ecole.db";
    private static final int DATABASE_VERSION = 1;

    // Constantes pour la table et les colonnes (évite les erreurs de syntaxe SQL)
    public static final String TABLE_ETUDIANT = "etudiant";
    public static final String COL_ID = "id";
    public static final String COL_NOM = "nom";
    public static final String COL_PRENOM = "prenom";

    // Requête SQL de création de la table
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE " + TABLE_ETUDIANT + " (" +
                    COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COL_NOM + " TEXT NOT NULL, " +
                    COL_PRENOM + " TEXT NOT NULL);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Exécution de la création de la table
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // En TP, on supprime et on recrée.
        // En production, on ferait des "ALTER TABLE".
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ETUDIANT);
        onCreate(db);
    }
}