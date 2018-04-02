package padawana.recomendafilmes

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import padawana.recomendafilmes.Fragmentos.FragmentoAventura
import padawana.recomendafilmes.Fragmentos.FragmentoDrama
import padawana.recomendafilmes.Fragmentos.FragmentoPopular
import padawana.recomendafilmes.Fragmentos.FragmentoScifi

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        //TODO  ViewPager
        val viewPager: ViewPager = view_pager
        viewPager.adapter = Adapter(supportFragmentManager)
        //TODO tabLayout

        val tabLayout = tab_layout
        tabLayout.setupWithViewPager(viewPager)
    }

    private class Adapter(fm: FragmentManager?) : FragmentPagerAdapter(fm) {

        val fragments = arrayListOf<Fragment>()
        val titulos = arrayListOf<String>()

        init {
            fragments.add(FragmentoPopular())
            titulos.add("Popular")
            fragments.add(FragmentoDrama())
            titulos.add("Drama")
            fragments.add(FragmentoAventura())
            titulos.add("Aventura")
            fragments.add(FragmentoScifi())
            titulos.add("Ficção Científica")

        }
        override fun getItem(position: Int): Fragment {
            return fragments[position]

        }

        override fun getCount(): Int {
            return fragments.size

        }

        override fun getPageTitle(position: Int): CharSequence {
            return titulos[position]
        }

    }

   private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.title = getString(R.string.app_name)
    }


}
