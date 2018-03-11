package padawana.filmesapis

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by aninh_000 on 10/03/2018.
 */
class CartoesFilmesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_cartoes)

        val recyclerView = Recycler1
        recyclerView.adapter = CartoesFilmesAdapter(filmes(), this)
        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        recyclerView.layoutManager = layoutManager
    }

    private fun filmes(): List<Filme> {
        return listOf(
                Filme(1243,
                        "Noé"),
                Filme( 5675,
                        "Zootopia"),
                Filme(3545,
                        "Crepúsculo"))
    }

}