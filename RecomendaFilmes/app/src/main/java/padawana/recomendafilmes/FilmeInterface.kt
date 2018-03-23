package padawana.recomendafilmes

import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Programado com amor por Ana Luísa Dias em 15/03/2018.
 */

interface FilmeInterface {
    @GET("3/movie/popular?")
    fun listaFilmes(
            @Query("api_key") apikey: String,
            @Query("language") idioma: String
    ): Call<FilmResult>

}