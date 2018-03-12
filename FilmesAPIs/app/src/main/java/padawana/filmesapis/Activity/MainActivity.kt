package padawana.filmesapis.Activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import padawana.filmesapis.CartoesFilmesAdapter
import padawana.filmesapis.Model.Filme
import padawana.filmesapis.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = Recycler1
        recyclerView.adapter = CartoesFilmesAdapter(filmes(), this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

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
