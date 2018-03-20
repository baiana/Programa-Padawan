package padawana.recomendafilmes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Programado com amor por Ana Luísa Dias em 15/03/2018.
 */
class RetrofitIncializador{
    val retrofit = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun FilmeInterface(): FilmeInterface = retrofit.create(FilmeInterface::class.java)
}