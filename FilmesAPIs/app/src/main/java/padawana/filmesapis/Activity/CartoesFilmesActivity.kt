package padawana.filmesapis.Activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import padawana.filmesapis.CartoesFilmesAdapter
import padawana.filmesapis.Model.Filme
import padawana.filmesapis.R
import padawana.filmesapis.Retrofit.FilmeResposta
import padawana.filmesapis.Retrofit.FilmesWebClient

/**
 * Programado com amor por Ana Luísa Dias em 10/03/2018.
 */

class CartoesFilmesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_cartoes)

        FilmesWebClient().lista(object : FilmeResposta {
            override fun sucesso(filmes: List<Filme>) {
                configuraLista(filmes)
            }
        })
    }
//TODO tornar activity principal (?)
    private fun configuraLista(filmes:List<Filme>) {
        val recyclerView = Recycler1
        recyclerView.adapter = CartoesFilmesAdapter(filmes, this)
    //TODO mudar para gridView
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