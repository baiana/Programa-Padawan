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
            @Query("api_key") apikey: String ,
            @Query("language") idioma: String
    ): Call<FilmResult>

    @GET("3/genre/18/movies?")
    fun listaDrama(
            @Query("api_key") apikey: String,
            @Query("language") idioma: String,
            @Query("sort_by") ordenar:String
    ):Call<FilmResult>

    @GET("3/genre/12/movies?")
    fun listaAventura(
            @Query("api_key") apikey: String,
            @Query("language") idioma: String
    ):Call<FilmResult>

    @GET("3/genre/16/movies?")
    fun listaAnima(
            @Query("api_key") apikey: String,
            @Query("language") idioma: String
    ):Call<FilmResult>

    @GET("3/genre/878/movies?")
    fun listaSciFi(
            @Query("api_key") apikey: String,
            @Query("language") idioma: String
    ):Call<FilmResult>

    @GET("/3/search/movie?")
    fun pesquisaFilme(
            @Query("api_key") apikey: String,
            @Query("language") idioma: String,
            @Query("query")filmeNome:String?,
            @Query("include_adult")adulto:String = "false"

    ):Call<FilmResult>

}