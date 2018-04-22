package padawana.recomendafilmes

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Filmes")
data class Filme(
        @PrimaryKey
        @SerializedName("id") var id: Int?,
        @SerializedName("title") var titulo: String?,
        @SerializedName("overview") var sinopse: String?,
        @SerializedName("poster_path") var posterPath: String?,
        @Ignore
        @SerializedName("adult") var isAdult: Boolean,
        @Ignore
        @SerializedName("original_title") var tituloOriginal: String?,
        @ColumnInfo(name = "votos")
        @SerializedName("vote_average") var voteAverage: Double?)