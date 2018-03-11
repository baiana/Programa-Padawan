package padawana.filmesapis

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

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
                Filme(1243,
                        "Noé"),
                Filme( 5675,
                        "Zootopia"),
                Filme(3545,
                        "Crepúsculo"))
    }
}
