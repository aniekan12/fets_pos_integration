package com.example.fets_pos_integration

import android.app.Activity
import android.content.ComponentName
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import io.flutter.embedding.android.FlutterActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import com.example.fets_pos_integration.models.FetsTransactionModel
import com.example.fets_pos_integration.models.FetsTransactionResponse
import com.google.gson.JsonObject
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    companion object{
        private const val FETS_CHANNEL = "com.irecharge.fets"
        private const val FETS_INTENT = "fets.pos.checkout"
        private const val IRECHARGE_PACKAGE_NAME = "com.fets.infostrategypayment"
        private const val FETS_PACKAGE_NAME = "com.fets.infostrategypayment.Purchase"
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
                        makePayment(FetsTransactionModel(amount = call.argument<String>("amount")!!, reference = call.argument<String?>("reference")!!))
                    }
                }
            }
        }
    }


    private fun makePayment(paymentModel: FetsTransactionModel){
        val startActivityForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if(activityResult.resultCode == RESULT_OK){
                val response: FetsTransactionResponse? = activityResult.data?.extras?.get("data") as FetsTransactionResponse?

                //Send the response back to flutter.
                result.success(response?.toMap())
            }
        }

        val jsonObject = JsonObject()
        jsonObject.addProperty("amount", paymentModel.amount)
        jsonObject.addProperty("reference", paymentModel.reference)


        startActivityForResult.launch(Intent(FETS_INTENT)
            .putExtra("data", jsonObject.toString())
            .setAction(FETS_INTENT)
            .setPackage(IRECHARGE_PACKAGE_NAME)
            .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            .setComponent(ComponentName(IRECHARGE_PACKAGE_NAME, FETS_PACKAGE_NAME)))
    }

    private fun <I, O> Activity.registerForActivityResult(contract: ActivityResultContract<I, O>, callback: ActivityResultCallback<O>) = (this as ComponentActivity).registerForActivityResult(contract, callback)
}
