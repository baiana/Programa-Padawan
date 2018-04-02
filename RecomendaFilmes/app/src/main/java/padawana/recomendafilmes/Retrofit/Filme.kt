package padawana.recomendafilmes

import com.google.gson.annotations.SerializedName


data class Filme(@SerializedName("id") var id: Int?,
                 @SerializedName("title") var titulo: String?,
                 @SerializedName("overview") var sinopse: String?,
                 @SerializedName("poster_path") var posterPath: String?,
                 @SerializedName("adult") var isAdult: Boolean,
                 @SerializedName("original_title") var tituloOriginal: String?,
                 @SerializedName("vote_average") var voteAverage: Double?)