package com.example.fets_pos_integration

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import io.flutter.embedding.android.FlutterActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.example.fets_pos_integration.models.FetsTransactionModel
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    companion object{
        private const val FETS_CHANNEL = "com.irecharge.fets";
        private const val FETS_INTENT = "fets.pos.checkout";
    }

    lateinit var result: MethodChannel.Result

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, FETS_CHANNEL).setMethodCallHandler{
                call, result ->
            this.result = result;

            when(call.method) {
                "cardPayment" -> {
                    if(call.argument<String>("amount") != null){
                        makePayment(FetsTransactionModel(amount = call.argument<String>("amount")!!, reference = call.argument<String?>("reference")))
                    }
                }
            }
        }
    }


    private fun makePayment(paymentModel: FetsTransactionModel){
        val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val response = activityResult?.data

            //TODO parse result as payment response
            result.success(response?.data)
        }

//        val paymentExtra = FetsTransactionModel(amount = "100.00")
//
//
//        val intent = Intent("fets.pos.checkout")
//            .putExtra("amount", "100.00")


        //TODO: parse json and add extra to the intent
        startActivityForResult.launch(Intent(FETS_INTENT)
            //TODO add extra key
            .putExtra("", bundleOf(Pair("amount", paymentModel.amount), Pair("reference", paymentModel.reference))))
    }

    private fun <I, O> Activity.registerForActivityResult(contract: ActivityResultContract<I, O>, callback: ActivityResultCallback<O>) = (this as ComponentActivity).registerForActivityResult(contract, callback)
}
