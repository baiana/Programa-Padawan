package padawana.recomendafilmes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import padawana.recomendafilmes.R.id.add
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
        initRecycleView()
        initToolbar()
        /* RetrofitIncializador()
        val interface1:FilmeInterface = RetrofitIncializador().FilmeInterface()*/

        val retrofit = Retrofit.Builder().baseUrl("https://api.themoviedb.org/").addConverterFactory(GsonConverterFactory.create()).build()

        val interface1: FilmeInterface = retrofit.create(FilmeInterface::class.java)


        val call: Call<List<FilmResult>> = interface1.ListaFilmes("9d61623e84414389bee8063e589ae6f4", "pt-BR")

        call.enqueue(object : Callback<List<FilmResult>?> {
            override fun onFailure(call: Call<List<FilmResult>?>?, t: Throwable?) {

                Log.e("ERRO no ON FAILURE\n", t?.message)

            }

            override fun onResponse(call: Call<List<FilmResult>?>?, response: Response<List<FilmResult>?>?) {

                response?.body()?.let {
                   createData()
                }

            }
        })
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)

    }

    private fun initRecycleView() {
        val data = createData()
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = GridLayoutManager(this,2)
        recyclerView?.adapter = SampleRecyclerViewAdapter(this, data)


    }

    private fun createData(): ArrayList<FilmResult.Filme> = ArrayList<FilmResult.Filme>().apply {
        //TODO chamar resposta do retrofit
        add(FilmResult.Filme(2345, "FIlme 1", "sinopse generica 1", "http://cinedestak.com/wp-content/uploads/2014/02/No%C3%A9-filme-poster-nacional-1.jpg", false, "teste", 3467530.895))
        add(FilmResult.Filme(2346, "FIlme 2", "sinopse generica 2", "https://vignette.wikia.nocookie.net/marvelcinematicuniverse/images/a/a5/Civil_War_Alternate_poster.jpg/revision/latest?cb=20160330133735", false, "teste", 3467530.895))
        add(FilmResult.Filme(2347, "FIlme 3", "sinopse generica 3", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQztffXJHWS8PBNhDf3fxSYGxoUQW01uyKTa9qQ35RCLcRBpJbvPw", false, "teste", 3467530.895))
        add(FilmResult.Filme(2348, "FIlme 4", "sinopse generica 4", "http://i.imgur.com/DvpvklR.png", false, "teste", 3467530.895))
        add(FilmResult.Filme(2349, "FIlme 5", "sinopse generica 5", "http://i.imgur.com/DvpvklR.png", false, "teste", 3467530.895))
        add(FilmResult.Filme(2345, "FIlme 1", "sinopse generica 1", "http://cinedestak.com/wp-content/uploads/2014/02/No%C3%A9-filme-poster-nacional-1.jpg", false, "teste", 3467530.895))
        add(FilmResult.Filme(2345, "FIlme 2", "sinopse generica 2", "https://vignette.wikia.nocookie.net/marvelcinematicuniverse/images/a/a5/Civil_War_Alternate_poster.jpg/revision/latest?cb=20160330133735", false, "teste", 3467530.895))
        add(FilmResult.Filme(2345, "FIlme 3", "sinopse generica 3", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQztffXJHWS8PBNhDf3fxSYGxoUQW01uyKTa9qQ35RCLcRBpJbvPw", false, "teste", 3467530.895))
        add(FilmResult.Filme(2345, "FIlme 4", "sinopse generica 4", "http://i.imgur.com/DvpvklR.png", false, "teste", 3467530.895))
        add(FilmResult.Filme(2345, "FIlme 5", "sinopse generica 5", "http://i.imgur.com/DvpvklR.png", false, "teste", 3467530.895))

    }
}
