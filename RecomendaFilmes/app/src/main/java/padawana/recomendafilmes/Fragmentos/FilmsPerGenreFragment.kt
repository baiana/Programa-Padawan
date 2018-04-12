package padawana.recomendafilmes.Fragmentos

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.*
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.fragment_filmes.*
import padawana.recomendafilmes.Retrofit.API
import padawana.recomendafilmes.R
import padawana.recomendafilmes.R.id.search
import padawana.recomendafilmes.SampleRecyclerViewAdapter
import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FilmsPerGenreFragment: Fragment() {

    enum class Genre {
        POPULAR,
        DRAMA,
        ADVENTURE,
        SCIFI,
        STARWARS
    }

    companion object {
        val genreKey = "KEY_GENRE"
        fun newInstance(genre: Genre): FilmsPerGenreFragment {
            val bundle = Bundle()
            bundle.putSerializable(genreKey, genre)
            val fragmentoFilme = FilmsPerGenreFragment()
            fragmentoFilme.arguments = bundle
            return fragmentoFilme
        }
    }


        override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        val genre: Genre = arguments.getSerializable(genreKey) as Genre
        val call: Call<FilmResult> = when (genre) {
            Genre.POPULAR -> API.moviesApi.listaFilmes("9d61623e84414389bee8063e589ae6f4", "pt-BR")
            Genre.DRAMA -> API.moviesApi.listaDrama("9d61623e84414389bee8063e589ae6f4", "pt-BR", "created_at.asc")
            Genre.ADVENTURE -> API.moviesApi.listaAventura("9d61623e84414389bee8063e589ae6f4", "pt-BR")
            Genre.SCIFI -> API.moviesApi.listaSciFi("9d61623e84414389bee8063e589ae6f4", "pt-BR")
            Genre.STARWARS ->API.moviesApi.pesquisaFilme("9d61623e84414389bee8063e589ae6f4", "pt-BR","Star Wars")
          }
        call.enqueue(object: Callback<FilmResult?> {
            override fun onFailure(call: Call<FilmResult?>?, t: Throwable?) {
                Log.e("ERRO no ON FAILURE\n", t?.message)
                displayAlert("Erro no CallBack")
            }

            override fun onResponse(call: Call<FilmResult?>?, response: Response<FilmResult?>?) {
                if (response != null && response.isSuccessful) {
                    initRecycleView(response.body())
                } else {
                    displayAlert("Erro no OnResponse")
                }
            }
        })

        return inflater!!.inflate(R.layout.fragment_filmes, container, false)
    }

    private fun initRecycleView(films: FilmResult?) {
        if  (films?.results != null && films.results.isNotEmpty()) {
            recyclerViewFilme?.setHasFixedSize(true)
            recyclerViewFilme?.layoutManager = GridLayoutManager(context,2)
            recyclerViewFilme?.adapter = SampleRecyclerViewAdapter(context, films)
        } else {
            displayAlert("Erro no initRecycleView")
        }
    }

    fun displayAlert(stringErro: String) {
        val alert = AlertDialog.Builder(context)
        val nullParent: ViewGroup? = null
        val editaAlerta = layoutInflater.inflate(R.layout.alert, nullParent)
        editaAlerta.textComplementoErro.text = stringErro

        with(alert) {
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }

        val alertDialog: AlertDialog = alert.create()
        alertDialog.run {
            setView(editaAlerta)
            show()
        }
    }


}