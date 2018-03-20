package padawana.recomendafilmes
import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_sample.view.*
import padawana.recomendafilmespackage.FilmResult

/**
 * Created by aislantavares on 28/02/18.
 */

class SampleRecyclerViewAdapter(private val context: Context, private val sampleArrayList: ArrayList<FilmResult.Filme>) : RecyclerView.Adapter<SampleRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sample, parent, false))

    override fun getItemCount(): Int = sampleArrayList.count()

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        val stringSampleItem = sampleArrayList[position]
        holder?.labelTXT?.text = stringSampleItem.titulo
        Picasso.get().load(stringSampleItem.posterPath).resize(200, 240).centerCrop().into(holder?.fundo)

        holder?.itemView?.setOnClickListener {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra(SecondActivity.const.MESSAGE, stringSampleItem.titulo)
            intent.putExtra(SecondActivity.const.SINOPSE, stringSampleItem.sinopse)
            intent.putExtra(SecondActivity.const.FUNDOURL,stringSampleItem.posterPath)
                 context.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val labelTXT = itemView.labelTXT
        val fundo = itemView.fundo


    }
}