package com.ironmind.here

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.ironmind.here.ui.theme.HereTheme
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.ironmind.here.sftpManager.DataUploader
import com.ironmind.here.sftpManager.DataDownloader
import com.ironmind.here.sftpManager.DataDeleter
import com.ironmind.here.sftpManager.ClearCache

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Copier la base depuis les assets si elle n'existe pas encore
        val dbFile = applicationContext.getDatabasePath("appli_presence.db")
        if (!dbFile.exists()) {
            assets.open("appli_presence.db").use { input ->
                dbFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }
        }
        val uploadRequest = OneTimeWorkRequestBuilder<DataUploader>().build()
        val downloadRequest = OneTimeWorkRequestBuilder<DataDownloader>().build()
        val deleteRequest = OneTimeWorkRequestBuilder<DataDeleter>().build()
        val clearCache = OneTimeWorkRequestBuilder<ClearCache>().build()

        //comment utiliser les request : exemples
        //WorkManager.getInstance(this).enqueue(uploadRequest) //pour upload la bdd sur le raspberry
        //WorkManager.getInstance(this).enqueue(deleteRequest) //pour delete
        //WorkManager.getInstance(this).enqueue(downloadRequest) //pour telecharger
        //WorkManager.getInstance(this).enqueue(clearCache) //pour nettoyer la bdd locale

        enableEdgeToEdge()
        setContent {
            HereTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HereTheme {
        Greeting("Android")
    }
}