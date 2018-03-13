package padawana.filmesapis.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import padawana.filmesapis.CartoesFilmesAdapter
import padawana.filmesapis.Model.Filme
import padawana.filmesapis.R
import padawana.filmesapis.R.layout.activity_main

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = Recycler1
        recyclerView.adapter = CartoesFilmesAdapter(filmes(), this)
        //TODO chamar a tela FilmeSelecionado
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager



    }

//TODO programa funcionando no tablet chamando a função filmes() pré definida
    //TODO integrar com o RetroFit


    private fun filmes(): List<Filme> {
        return listOf(
                Filme("aaaaa",
                        "Noé"),
                Filme("BBBBB",
                        "Zootopia"),
                Filme("CCCCCC",
                        "Crepúsculo"))
    }
}
