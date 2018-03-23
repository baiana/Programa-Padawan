package padawana.recomendafilmes

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
* Programado com amor por Ana Luísa Dias em 21/03/18.
*/
object API {

    private var instance = Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    var moviesApi = instance.create(FilmeInterface::class.java)

}