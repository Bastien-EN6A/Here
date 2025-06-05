package com.ironmind.here.utils

import android.content.Context
import android.util.Log
import com.ironmind.here.data.db.AppDatabase
import com.ironmind.here.data.models.Prof
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream

// Fonction pour copier la base de données depuis les assets
fun copyDatabaseFromAssets(context: Context) {
    val dbFile = context.getDatabasePath("appli_presence_1.db")

    // Vérifie si le fichier de base de données existe déjà
    if (!dbFile.exists()) {
        try {
            // Ouvre l'InputStream du fichier de base de données depuis assets
            val inputStream: InputStream = context.assets.open("appli_presence_1.db")
            // Crée un OutputStream pour écrire dans le fichier de la base de données
            val outputStream: OutputStream = FileOutputStream(dbFile)

            // Copie les données
            val buffer = ByteArray(1024)
            var length: Int
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }

            // Ferme les flux
            outputStream.flush()
            outputStream.close()
            inputStream.close()

            println("Base de données copiée avec succès !")
        } catch (e: IOException) {
            e.printStackTrace()
            println("Erreur lors de la copie de la base de données : ${e.message}")
        }
    } else {
        println("La base de données existe déjà.")
    }
}

// Fonction suspendue pour récupérer les professeurs de manière asynchrone
suspend fun getProfList(db: AppDatabase): List<Prof> {
    // Log pour voir si la récupération des données fonctionne
    Log.d("MainActivity", "Fetching profs from DB...")
    return withContext(Dispatchers.IO) {
        val profs = db.profDao().getAllProf()
        Log.d("MainActivity", "Fetched ${profs.size} profs.")
        profs
    }
}


