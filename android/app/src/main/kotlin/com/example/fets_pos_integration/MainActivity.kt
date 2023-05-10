package com.example.fets_pos_integration

import android.app.Activity
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultCallback
import io.flutter.embedding.android.FlutterActivity
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class MainActivity: FlutterActivity() {
    private val FETS_CHANNEL = "com.irecharge.fets";

    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, FETS_CHANNEL).setMethodCallHandler{
            call, result ->

        }
    }


    fun makePayment(){
        val startActivityForResult =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
                val result = activityResult?.data


            }

        val intent = Intent("fets.pos.checkout")

        //TODO: make a model to accept payments and pass it as an extra to the intent
        startActivityForResult.launch(intent)
    }

        private fun <I, O> Activity.registerForActivityResult(
        contract: ActivityResultContract<I, O>,
        callback: ActivityResultCallback<O>
    ) = (this as ComponentActivity).registerForActivityResult(contract, callback)

}
