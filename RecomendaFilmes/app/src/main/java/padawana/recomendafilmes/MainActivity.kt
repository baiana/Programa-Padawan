package padawana.recomendafilmes

import android.os.Bundle
import android.provider.Settings.Global.getString
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.toolbar.*
import padawana.recomendafilmes.R.id.*
import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        val call: Call<FilmResult> = API.moviesApi.listaFilmes("9d61623e84414389bee8063e589ae6f4", "pt-BR")
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
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

    }

    private fun initRecycleView(films: FilmResult?) {
        if  (films?.result != null && films.result.isNotEmpty()) {
            recyclerView?.setHasFixedSize(true)
            recyclerView?.layoutManager = GridLayoutManager(this,2)
            recyclerView?.adapter = SampleRecyclerViewAdapter(this, films)
        } else {
            ////TODO mudar texto do alert par erro

            displayAlert("Erro no initRecycleView")
        }
    }
    fun displayAlert(stringerro:String) {
        val alert = AlertDialog.Builder(this)
        val nullParent: ViewGroup? = null
        val editaAlerta = layoutInflater.inflate(R.layout.alert, nullParent)
        editaAlerta.textComplementoErro.text = stringerro

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
