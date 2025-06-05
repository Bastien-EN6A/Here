import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "etudiants")
data class Etudiant(
    @PrimaryKey val id: String,
    val nom: String?,
    val prenom: String?,
    val filiere: String?
)
