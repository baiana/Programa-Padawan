package padawana.filmesapis.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import padawana.filmesapis.CartoesFilmesAdapter
import padawana.filmesapis.Model.Filme
import padawana.filmesapis.R
import padawana.filmesapis.Retrofit.RetrofitIncializador
import retrofit2.Call
import retrofit2.Response

/**
 * Programado com amor por Ana Luísa Dias em 10/03/2018.
 */

class CartoesFilmesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_cartoes)

        val call= RetrofitIncializador().Filmeservice().listarFilmes()
        call.enqueue(object: retrofit2.Callback<List<Filme>?> {
            override fun onResponse(call: Call<List<Filme>?>?, response: Response<List<Filme>?>?) {
                response?.body()?.let {
                    val filmes:List<Filme> = it
                    configuraLista(filmes)
                }}

            override fun onFailure(call: Call<List<Filme>?>?, t: Throwable?) {
                Log.e("ERRO no ONFAILURE\n",t?.message)

            }
        })

    }

    private fun configuraLista(filmes:List<Filme>) {
        val recyclerView = Recycler1
        recyclerView.adapter = CartoesFilmesAdapter(filmes, this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    /*private fun filmes(): List<Filme> {
        return listOf(
                Filme("filme biblico ",
                        "Noé"),
                Filme("xcddsffds",
                        "Zootopia"),
                Filme("dsdfsfsf",
                        "Crepúsculo"))
    }*/

}