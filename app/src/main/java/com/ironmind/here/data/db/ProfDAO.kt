package com.ironmind.here.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.ironmind.here.data.models.Prof

@Dao
interface ProfDAO {
    @Query("SELECT * FROM profs")
    fun getAllProf(): List<Prof>


    @Query("SELECT * FROM profs WHERE id = :id")
    fun getProfById(id: Int): Prof

    @Insert
    fun insertProf(profs: Prof)

    @Delete
    fun deleteProf(profs: Prof)

    @Query("DELETE FROM profs")
    fun clearAll() // Méthode pour supprimer toutes les entrées

    // Exemple de méthode pour mettre à jour un professeur
    @Query("UPDATE profs SET nom = :name, prenom = :prenom WHERE id = :id")
    fun updateProf(id: Int, name: String, prenom: String)
}