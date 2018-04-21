package padawana.recomendafilmes.Fragmentos

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.fragment_resultados.*
import padawana.recomendafilmes.R
import padawana.recomendafilmes.SampleRecyclerViewAdapter
import padawana.recomendafilmespackage.FilmResult


@SuppressLint("ValidFragment")

class ResultadosFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
       // var loading: ProgressBar? = progressBar
        return inflater!!.inflate(R.layout.fragment_resultados, container, false)
    }


    private fun initRecycleView(films: FilmResult?) {
        //var loading: ProgressBar? = progressBar
        //loading?.visibility = View.VISIBLE

        val toast = Toast.makeText(context, "Ops! Filme não encontrado\nConfira sua pesquisa tente novamente", Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)

        if (films?.results != null && films.results.isNotEmpty()) {
            carinhaTriste.visibility = View.GONE
            recyclerViewPesquisa?.setHasFixedSize(true)
            recyclerViewPesquisa?.layoutManager = GridLayoutManager(context, 2)
            recyclerViewPesquisa?.adapter = SampleRecyclerViewAdapter(context, films)

        } else {
            toast.show().let {
          //      loading?.visibility = View.GONE
                carinhaTriste.visibility = View.VISIBLE
            }

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

    fun onSearchError(erroMensage: String) {
        //progressBar.visibility = View.GONE
        Log.e(getString(R.string.ErroOnFailure), erroMensage)
        displayAlert(getString(R.string.ErroCallback))
    }


    fun onSearchStart() {
        //progressBar.visibility = View.VISIBLE

    }

    fun onSearchResult(films: FilmResult?) {
        //progressBar.visibility = View.GONE
        initRecycleView(films)
    }

}