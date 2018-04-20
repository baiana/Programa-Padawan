package padawana.recomendafilmes

import android.annotation.SuppressLint
import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.Menu
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_resultados.*
import kotlinx.android.synthetic.main.toolbar.*
import padawana.recomendafilmes.Fragmentos.FilmsPerGenreFragment
import padawana.recomendafilmes.Fragmentos.FilmsPerGenreFragment.Genre.*
import padawana.recomendafilmes.Fragmentos.ResultadosFragment
import padawana.recomendafilmes.R.id.carinhaTriste
import padawana.recomendafilmes.Retrofit.API
import padawana.recomendafilmespackage.FilmResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var resultados: ResultadosFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()

        val viewPager: ViewPager = viewPager
        viewPager.adapter = Adapter(supportFragmentManager)

        val tabLayout = tabLayout
        tabLayout.setupWithViewPager(viewPager)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView
        searchView.queryHint = "Nome do filme"
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(filmePesquisado: String?): Boolean {
                if (!filmePesquisado.isNullOrBlank() && filmePesquisado!!.length >= 4) {
                    if (viewPager.visibility == View.VISIBLE) {
                        viewPager.visibility = View.GONE
                        tabLayout.visibility = View.GONE
                    }
                    if (resultados == null) {
                        resultados = ResultadosFragment()
                        val backStateName = resultados!!.javaClass.name
                        val manager = supportFragmentManager
                        manager.popBackStackImmediate(backStateName, FragmentManager.POP_BACK_STACK_INCLUSIVE)
                        val transaction = manager.beginTransaction()
                        transaction.replace(R.id.containerFrame, resultados)
                        transaction.addToBackStack(backStateName)
                        transaction.commit()
                    }

                    resultados?.onSearchStart()
                    val call: Call<FilmResult> = API.moviesApi.pesquisaFilme("9d61623e84414389bee8063e589ae6f4", "pt-BR", filmePesquisado)
                    call.enqueue(object : Callback<FilmResult?> {
                        override fun onFailure(call: Call<FilmResult?>?, t: Throwable?) {
                            resultados?.onSearchError(t.toString())
                        }

                        @SuppressLint("NewApi")
                        override fun onResponse(call: Call<FilmResult?>?, response: Response<FilmResult?>?) {
                            progressBar.visibility = View.GONE
                            if (response != null && response.isSuccessful) {
                               resultados?.onSearchResult(response.body())
                            } else {
                                resultados?.displayAlert(getString(R.string.ErroOnResponse))
                            }
                        }
                    })

                    containerFrame.visibility = View.VISIBLE


                } else {
                    containerFrame.visibility = View.GONE
                    viewPager.visibility = View.VISIBLE
                    tabLayout.visibility = View.VISIBLE
                }
                return true
            }
        })
        return true
    }

    private class Adapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {
        val fragments = arrayListOf<Fragment>()
        val titulos = arrayListOf<String>()

        init {
            fragments.add(FilmsPerGenreFragment.newInstance(POPULAR))
            titulos.add("Popular")
            fragments.add(FilmsPerGenreFragment.newInstance(DRAMA))
            titulos.add("Drama")
            fragments.add(FilmsPerGenreFragment.newInstance(STARWARS))
            titulos.add("Star Wars")
            fragments.add(FilmsPerGenreFragment.newInstance(ADVENTURE))
            titulos.add("Aventura")
            fragments.add(FilmsPerGenreFragment.newInstance(SCIFI))
            titulos.add("Ficção Científica")
        }

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence = titulos[position]

    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }

}
