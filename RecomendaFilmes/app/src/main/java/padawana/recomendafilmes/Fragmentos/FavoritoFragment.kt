package padawana.recomendafilmes.Fragmentos


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_favorito.*
import kotlinx.android.synthetic.main.fragment_resultados.*
import padawana.recomendafilmes.Database.AppDatabase
import padawana.recomendafilmes.R
import padawana.recomendafilmes.SampleRecyclerViewAdapter
import padawana.recomendafilmes.retrofit.Filme

class FavoritoFragment : Fragment() {
    var vazio: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val favoritos = AppDatabase.getInstance(context!!)?.filmesDao()!!.loadFavoritos()
        initRecycleView(films = favoritos)
        return inflater.inflate(R.layout.fragment_favorito, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (view.visibility == View.VISIBLE && vazio) {
            val toast = Toast.makeText(context, "Ei! Você não selecionou nenhum faovrito!", Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
    }


    private fun initRecycleView(films: List<Filme>) {
        progressBar?.visibility = View.VISIBLE
        if (!films.isEmpty()) {
//            carinhaTriste.visibility = View.GONE
            recyclerViewFavorito?.setHasFixedSize(true)
            recyclerViewFavorito?.layoutManager = GridLayoutManager(context, 2)
            recyclerViewFavorito?.adapter = SampleRecyclerViewAdapter(context!!, films as ArrayList<Filme>)
            vazio = false
        } else {
            vazio = true

        }

    }

}
