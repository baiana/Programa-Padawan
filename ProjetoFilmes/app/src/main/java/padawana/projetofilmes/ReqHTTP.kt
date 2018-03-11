package padawana.projetofilmes

import android.os.AsyncTask

/**
 * Created by aninh_000 on 10/03/2018.
 */

 class  ReqHTTP : AsyncTask<Void, Void, Filmes>() {
    private var Id_filme: String = ""

    fun HttpService(Filmes: String) :Void{ Id_filme = Filmes}




    override fun doInBackground(vararg p0: Void?): Filmes {
        TODO("not implemented")
        var resposta:StringBuilder = StringBuilder()



    }

}