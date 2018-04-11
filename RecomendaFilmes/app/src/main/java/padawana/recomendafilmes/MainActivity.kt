package padawana.recomendafilmes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import padawana.recomendafilmes.Fragmentos.*
import android.app.SearchManager
import android.content.Context
import android.support.v7.widget.SearchView
import android.view.View
import android.webkit.RenderProcessGoneDetail


class MainActivity : AppCompatActivity() {

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
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false

            override fun onQueryTextChange(newText: String?): Boolean {
                if (!newText.isNullOrBlank() && newText!!.length >= 3) {
                    if (viewPager.visibility == View.VISIBLE) {

                    } else {

                    }
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
            fragments.add(FilmsPerGenreFragment.newInstance(FilmsPerGenreFragment.Genre.POPULAR))
            titulos.add("Popular")
            fragments.add(FilmsPerGenreFragment.newInstance(FilmsPerGenreFragment.Genre.DRAMA))
            titulos.add("Drama")
            fragments.add(FilmsPerGenreFragment.newInstance(FilmsPerGenreFragment.Genre.ADVENTURE))
            titulos.add("Aventura")
            fragments.add(FilmsPerGenreFragment.newInstance(FilmsPerGenreFragment.Genre.SCIFI))
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
