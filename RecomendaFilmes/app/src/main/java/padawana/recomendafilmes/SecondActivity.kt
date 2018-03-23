package padawana.recomendafilmes

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.toolbar.*


class SecondActivity : AppCompatActivity() {

    object const {

        @JvmStatic
        val MESSAGE = "Titulo"
        val SINOPSE = "Sinopse"
        val FUNDOURL = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val stringSampleItem = intent.getStringExtra(const.MESSAGE)
        val sinopse = intent.getStringExtra(const.SINOPSE)
        val fundo = intent.getStringExtra(const.FUNDOURL)

        setMessage(stringSampleItem,sinopse,fundo)
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if(const.MESSAGE.isNullOrEmpty()|| const.MESSAGE == "message")
        supportActionBar?.title = getString(R.string.app_name)
        else
        supportActionBar?.title = const.MESSAGE
    }

    private fun setMessage(stringSampleItem: String?,sinopse:String?,fundo:String?) {
        labelTXT?.text = stringSampleItem
        sinopseTXT.text = sinopse

        Picasso.get().load(fundo).into(this.fundop)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home)
            onBackPressed()
        return super.onOptionsItemSelected(item)
    }
}
