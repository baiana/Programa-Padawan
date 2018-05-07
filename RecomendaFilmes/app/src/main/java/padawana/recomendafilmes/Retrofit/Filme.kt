package padawana.recomendafilmes.retrofit

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity(tableName = "filme")
data class Filme(
        @PrimaryKey
        @SerializedName("id") var idFilme: Int? = null,
        @SerializedName("title") var titulo: String? = "Desconhecido",
        @SerializedName("overview") var sinopse: String? = null,
        @SerializedName("poster_path") var posterPath: String? = null,
        @Ignore
        @SerializedName("adult") var isAdult: Boolean = false,
        @Ignore
        @SerializedName("original_title") var tituloOriginal: String? = "Desconhecido",
        @ColumnInfo(name = "votos")
        @SerializedName("vote_average") var voteAverage: Double? = 0.00,
        @Expose(serialize = false)
        var favorito: Boolean = false
)