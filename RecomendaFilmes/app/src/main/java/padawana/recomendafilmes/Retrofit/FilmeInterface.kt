package padawana.recomendafilmes

import android.support.v4.content.res.TypedArrayUtils.getString
import padawana.recomendafilmes.R.string.APIChave
import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Programado com amor por Ana Luísa Dias em 15/03/2018.
 */

interface FilmeInterface {
    @GET("/3/discover/movie?")
    fun LoadFilmes(
            @Query("api_key") apikey: String = "9d61623e84414389bee8063e589ae6f4",
            @Query("language") idioma: String = "pt-BR",
            @Query("include_adult") adulto: String = "false",
            @Query("include_video") video: String = "false",
            @Query("page") pagina: String,
            @Query("with_genres") genero: String
    ): Call<FilmResult>

    @GET("3/movie/popular?")
    fun listaFilmes(
            @Query("api_key") apikey: String = "9d61623e84414389bee8063e589ae6f4",
            @Query("language") idioma: String = "pt-BR"
    ): Call<FilmResult>

    @GET("3/genre/18/movies?")
    fun listaDrama(
            @Query("api_key") apikey: String = "9d61623e84414389bee8063e589ae6f4",
            @Query("language") idioma: String = "pt-BR",
            @Query("sort_by") ordenar: String = "created_at.asc"
    ): Call<FilmResult>

    @GET("3/genre/12/movies?")
    fun listaAventura(
            @Query("api_key") apikey: String = "9d61623e84414389bee8063e589ae6f4",
            @Query("language") idioma: String = "pt-BR"
    ): Call<FilmResult>


    @GET("3/genre/878/movies?")
    fun listaSciFi(
            @Query("api_key") apikey: String = "9d61623e84414389bee8063e589ae6f4",
            @Query("language") idioma: String = "pt-BR"
    ): Call<FilmResult>

    @GET("/3/search/movie?")
    fun pesquisaFilme(
            @Query("api_key") apikey: String = "9d61623e84414389bee8063e589ae6f4",
            @Query("language") idioma: String = "pt-BR",
            @Query("query") filmeNome: String?,
            @Query("include_adult") adulto: String = "false"
    ): Call<FilmResult>
}