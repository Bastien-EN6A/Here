package com.ironmind.here

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.ironmind.here.data.db.AppDatabase
import com.ironmind.here.data.models.Prof
import com.ironmind.here.ui.theme.HereTheme
import com.ironmind.here.utils.copyDatabaseFromAssets
import com.ironmind.here.utils.getProfList
import kotlinx.coroutines.launch

import android.util.Log

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

import androidx.compose.material3.Text

import androidx.compose.runtime.LaunchedEffect

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember



class MainActivity : ComponentActivity() {

    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialisation de la base de données Room
        db = AppDatabase.getDatabase(applicationContext)

        // Copie de la base de données depuis les assets (si nécessaire)
        copyDatabaseFromAssets(applicationContext)

        // Utilisation de lifecycleScope pour appeler la fonction suspendue getProfList
        // obligé d'utiliser lifecycleScope
        // fonction doit etre suspendu car ne doit pas tourner sur le thread principal (sinon bloque)
        lifecycleScope.launch {
            // Récupérer la liste des professeurs de manière asynchrone
            val profList: List<Prof> = getProfList(db)

            // Affichage des professeurs dans le log
            profList.forEach { prof ->
                println("Professeur: ${prof.name} ${prof.prenom}")
            }
        }


        /*
        // affiche sur la page de l'application
        setContent {
            HereTheme {
                Scaffold { paddingValues ->
                    // Appeler la fonction pour récupérer et afficher les professeurs
                    DisplayProfList(modifier = Modifier.padding(paddingValues))
                }
            }

        }

         */
    }

    @Composable
    fun DisplayProfList(modifier: Modifier = Modifier) {
        // Utilisation de 'mutableStateOf' pour mémoriser et mettre à jour les professeurs
        val profList = remember { mutableStateOf<List<Prof>>(emptyList()) }

        // Charger les données dans un thread secondaire
        LaunchedEffect(true) {
            val list = getProfList(db)
            Log.d("MainActivity", "Updating profList with ${list.size} items.")
            profList.value = list
        }

        if (profList.value.isEmpty()) {
            Text(text = "Aucun professeur trouvé.") // Message d'erreur si la liste est vide
        } else {
            LazyColumn(modifier = modifier.fillMaxSize()) {
                items(profList.value) { prof ->
                    ProfItem(prof) // Afficher chaque item de la liste
                }
            }
        }
    }


    // Composable pour afficher un professeur individuel
    @Composable
    fun ProfItem(prof: Prof) {
        Text(
            text = "ID: ${prof.id}, Nom: ${prof.name}, Prénom: ${prof.prenom}",
            modifier = Modifier
        )
    }
}


