package padawana.filmesapis.Retrofit

import android.util.Log
import padawana.filmesapis.Model.Filme
import retrofit2.Call
import retrofit2.Response

/**
 * Programado com amor por Ana Luísa Dias em 13/03/2018.
 */
class FilmesWebClient {

    fun lista(filmeResposta: FilmeResposta) {
        val call = RetrofitIncializador().Filmeservice().listarFilmes()
        call.enqueue(object : retrofit2.Callback<List<Filme>?> {


            override fun onResponse(call: Call<List<Filme>?>?, response: Response<List<Filme>?>?) {
                response?.body()?.let {
                    val filmes: List<Filme> = it
                    filmeResposta.sucesso(filmes)
                }
            }

            override fun onFailure(call: Call<List<Filme>?>?, t: Throwable?) {
                Log.e("ERRO no ON FAILURE\n", t?.message)

            }
        })

    }
}