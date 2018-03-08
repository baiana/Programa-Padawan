package padawana.calculadoraana

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AlertDialog.Builder
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.ativity_main.*
import padawana.calculadoraana.R.layout.ativity_main


//TODO é assim que se faz um todo :)
//TODO FAZER JANELA MODAL DE ERRO
//TODO FAZER VERIFICAÇÃO NUM - OP - NUM
// TODO FAZER OPERAÇÕES [FEITO]
//TODO EXIBIR RESULTADOS [FEITO]
//TODO MUDAR ÍCONE[FEITO]
class MainActivity :AppCompatActivity() {
    private var entrada1: Float? = null
    private var entrada2: Float? = null
    private var op: String? = null
    var cliques:Int = 0
    var pontos:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ativity_main)

        btnSub.setOnClickListener {
            handleClick(it as Button)
        }

        btnSoma.setOnClickListener {
            handleClick(it as Button)
        }
        btnDiv.setOnClickListener {
            handleClick(it as Button)
        }
        btnMulti.setOnClickListener {
            handleClick(it as Button)
        }

        btn1.setOnClickListener { digitanum(1) }
        btn2.setOnClickListener { digitanum(2) }
        btn3.setOnClickListener { digitanum(3) }
        btn4.setOnClickListener { digitanum(4) }
        btn5.setOnClickListener { digitanum(5) }
        btn6.setOnClickListener { digitanum(6) }
        btn7.setOnClickListener { digitanum(7) }
        btn8.setOnClickListener { digitanum(8) }
        btn9.setOnClickListener { digitanum(9) }
        btn0.setOnClickListener { digitanum(0) }

        btnponto.setOnClickListener ({

            if(resultadoTxt.text.isNotBlank() && cliques < 2 && pontos < 2){
                resultadoTxt.text = resultadoTxt.text.toString() + btnponto.text.toString()
                pontos += 1
            }
        })

        btnClear.setOnClickListener({
            entrada1 = null
            entrada2 = null
            op = null
            cliques = 0
            pontos = 0
            historicoTxt.text = null
            resultadoTxt.text = null
        })

        btnIgl.setOnClickListener({
            if (!op.isNullOrEmpty()) {
                entrada2 = if( resultadoTxt.text.toString().isBlank())
                    0f
                else
                    resultadoTxt.text.toString().toFloat()

                val num1: Float = entrada1!!
                val num2: Float = entrada2!!
                val result: Float?
                result = when (op) {
                    "+" -> num1.plus(num2)
                    "-" -> num1.minus(num2)
                    "X" -> num1 * num2
                    "÷" -> num1.div(num2)
                    else -> 0f
                }
                resultadoTxt.text = result.toString()
            }else{
                if(resultadoTxt.text.isBlank()){
                    resultadoTxt.text = "0"
                    historicoTxt.text = "nenhuma operação selecionada"}
                else
                    if(op.isNullOrEmpty())
                        historicoTxt.text = resultadoTxt.text.toString()
                historicoTxt.text = historicoTxt.text.toString() + entrada2.toString()
            }
            historicoTxt.text = historicoTxt.text.toString() + entrada2.toString()
            op = null
            pontos = 0
            cliques = 0
            entrada1 = entrada2


        })

    }


    fun digitanum(value:Int) {
        resultadoTxt.text = resultadoTxt.text.toString() + value.toString()
    }

    private fun handleClick(btn: Button) {

        if (!op.isNullOrEmpty()) {
            if (resultadoTxt.text.isNullOrEmpty() && cliques == 1){
                displayAlertDiferente(btn)
            }else{
                if (op != btn.text) {
                    displayAlertDiferente(btn)
                } else {
                    displayAlertIgual(btn)
                }}
        } else op = btn.text.toString()
        if(resultadoTxt.text.isNotBlank())
            entrada1 = resultadoTxt.text.toString().toFloat()
        else
            entrada1 = 0f
        historicoTxt.text = entrada1.toString() + op
        resultadoTxt.text = null
        cliques +=1


    }

    fun displayAlertDiferente(view: View) {
        //TODO colocar ações do substituir e invocar mpetodo[DONE]
        val alert = Builder(this)
        val btn = view as Button
        val nullParent: ViewGroup? = null
        val editaAlerta = layoutInflater.inflate(R.layout.activity_alert1,nullParent)

        with(alert) {
            setPositiveButton(getString(R.string.alertbtnpositive)) { dialog, whichButton ->
                op = btn.text.toString()
                println(op)
                historicoTxt.text = entrada1.toString() + op
                dialog.dismiss()
            }

            setNegativeButton(getString(R.string.negativebtnalert)) { dialog, _ ->
                dialog.dismiss()
            }
        }
        val alertDialog: AlertDialog = alert.create()
        alertDialog.run {
            setView(editaAlerta)
            show()
        }
    }

    fun displayAlertIgual(view: View) {
        val alert = Builder(this)
        val nullParent: ViewGroup? = null
        val editaAlerta = layoutInflater.inflate(R.layout.activity_alert2, nullParent)

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





