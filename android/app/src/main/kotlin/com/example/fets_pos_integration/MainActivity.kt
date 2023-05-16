package com.example.fets_pos_integration

import android.content.ComponentName
import android.content.Intent
import com.example.fets_pos_integration.models.FetsTransactionModel
import com.example.fets_pos_integration.receivers.iCallback
import com.google.gson.JsonObject
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity(), ICallback {
    init {
        iCallback = this
    }

    lateinit var result: MethodChannel.Result

    companion object{
        private const val FETS_CHANNEL = "com.irecharge.fets"
        private const val FETS_INTENT = "fets.pos.checkout"
        private const val IRECHARGE_PACKAGE_NAME = "com.fets.infostrategypayment"
        private const val FETS_PACKAGE_NAME = "com.fets.infostrategypayment.Purchase"
        const val FETS_REQUEST_CODE = 1000
    }

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
        val jsonObject = JsonObject()
        jsonObject.addProperty("amount", paymentModel.amount)
        jsonObject.addProperty("reference", paymentModel.reference)


        val intent = Intent(FETS_INTENT)
            .putExtra("data", jsonObject.toString())
            .setAction(FETS_INTENT)
            .setPackage(IRECHARGE_PACKAGE_NAME)
            .addFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES)
            .setComponent(ComponentName(IRECHARGE_PACKAGE_NAME, FETS_PACKAGE_NAME))

        startActivityForResult(intent, FETS_REQUEST_CODE)
    }

    override fun callBack(data: Any?) {
        result.success(data)
    }
}

interface ICallback {
    fun callBack(data: Any?)
}
