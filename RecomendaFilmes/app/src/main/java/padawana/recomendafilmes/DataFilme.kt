package padawana.recomendafilmespackage

/**
 * Programado com amor por Ana Luísa Dias em 14/03/2018.
 */
import com.google.gson.annotations.SerializedName

data class  FilmResult(
        val page:Int,
        val total_results:Int,
        val total_pages:Int,
        val result: List<Filme>) {

    data class Filme(@field:SerializedName("id")
                     var id: Int?,
                     @field:SerializedName("title") var titulo: String?,
                     @field:SerializedName("overview") var sinopse: String?,
                     @field:SerializedName("poster_path") var posterPath: String?,
                     @field:SerializedName("adult") var isAdult: Boolean,
                     @field:SerializedName("original_title") var tituloOriginal: String?,
                     @field:SerializedName("vote_average") var voteAverage: Double?) {

        override fun toString(): String {
            return "Filme(id=$id\n" +
                    "titulo=$titulo\n" +
                    " sinopse=$sinopse\n" +
                    " posterPath=$posterPath\n" +
                    " isAdult=$isAdult\n" +
                    " tituloOriginal=$tituloOriginal\n" +
                    " voteAverage=$voteAverage)"
        }
    }

}
