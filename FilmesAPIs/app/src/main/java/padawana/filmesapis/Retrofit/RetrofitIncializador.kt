package padawana.filmesapis.Retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Programado com amor por Ana Luísa Dias em 11/03/2018.
 */
class RetrofitIncializador{
    val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
//TODO verifcar URl base e chamar chave da API dos values
    fun Filmeservice(): FilmeService = retrofit.create(FilmeService::class.java)
}
