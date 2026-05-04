package local.mounir.studentmanager.service;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import local.mounir.studentmanager.classes.Etudiant;
import local.mounir.studentmanager.util.MySQLiteHelper;
import java.util.ArrayList;
import java.util.List;

/**
 * Service gérant les opérations CRUD pour la table Etudiant.
 * Amélioration : Utilisation des constantes du Helper et gestion sécurisée de la DB.
 */
public class EtudiantService {

    private final MySQLiteHelper helper;

    public EtudiantService(Context context) {
        this.helper = new MySQLiteHelper(context);
    }

    // --- CREATE ---
    public void create(Etudiant e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        // On utilise les constantes du Helper pour éviter les fautes de frappe
        values.put(MySQLiteHelper.COL_NOM, e.getNom());
        values.put(MySQLiteHelper.COL_PRENOM, e.getPrenom());

        db.insert(MySQLiteHelper.TABLE_ETUDIANT, null, values);
        db.close(); // Important : toujours fermer la base
    }

    // --- READ (Find by ID) ---
    public Etudiant findById(int id) {
        SQLiteDatabase db = helper.getReadableDatabase();
        Etudiant etudiant = null;

        // Utilisation d'une requête filtrée avec des arguments pour éviter les injections SQL
        Cursor cursor = db.query(
                MySQLiteHelper.TABLE_ETUDIANT,
                new String[]{MySQLiteHelper.COL_ID, MySQLiteHelper.COL_NOM, MySQLiteHelper.COL_PRENOM},
                MySQLiteHelper.COL_ID + " = ?",
                new String[]{String.valueOf(id)},
                null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            etudiant = new Etudiant(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2)
            );
            cursor.close();
        }
        db.close();
        return etudiant;
    }

    // --- READ (Find All) ---
    public List<Etudiant> findAll() {
        List<Etudiant> liste = new ArrayList<>();
        SQLiteDatabase db = helper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + MySQLiteHelper.TABLE_ETUDIANT, null);

        if (cursor.moveToFirst()) {
            do {
                Etudiant e = new Etudiant();
                e.setId(cursor.getInt(0));
                e.setNom(cursor.getString(1));
                e.setPrenom(cursor.getString(2));
                liste.add(e);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return liste;
    }

    // --- DELETE ---
    public void delete(int id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        // Amélioration : On supprime directement par ID (plus rapide)
        db.delete(MySQLiteHelper.TABLE_ETUDIANT,
                MySQLiteHelper.COL_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // --- UPDATE ---
    public void update(Etudiant e) {
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COL_NOM, e.getNom());
        values.put(MySQLiteHelper.COL_PRENOM, e.getPrenom());

        db.update(MySQLiteHelper.TABLE_ETUDIANT, values,
                MySQLiteHelper.COL_ID + " = ?",
                new String[]{String.valueOf(e.getId())});
        db.close();
    }
}