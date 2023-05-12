package com.example.fets_pos_integration.models

import android.os.Parcelable
import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Keep
@Parcelize
data class FetsTransactionResponse(
  @SerializedName("T/ID")
  val tid: String?,

  @SerializedName("M/ID")
  val mid: String?,

  @SerializedName("amount")
  val amount: Float?,

  @SerializedName("issuer")
  val issuer: String?,

  @SerializedName("cardName")
  val cardName: String?,

  @SerializedName("pan")
  val pan: String?,

  @SerializedName("exDate")
  val exDate: String?,

  @SerializedName("scheme")
  val scheme: String?,

  @SerializedName("stan")
  val stan: String?,

  @SerializedName("responseCode")
  val responseCode: String?,

  @SerializedName("payDate")
  val payDate: String?,

  @SerializedName("tellerNo")
  val tellerNo: String?,

  @SerializedName("tellerNoName")
  val tellerNoName: String?,

  @SerializedName("rrn")
  val rrn: String?,

  @SerializedName("authCode")
  val authCode: String?,

  @SerializedName("channel")
  val channel: String?,

  @SerializedName("type")
  val type: String?,

) : Parcelable {
  fun toMap(): HashMap<String, Any?> {
    return hashMapOf(
      Pair("tid", tid),
      Pair("mid", mid),
      Pair("amount", amount),
      Pair("issuer", issuer),
      Pair("cardName", cardName),
      Pair("pan", pan),
      Pair("exDate", exDate),
      Pair("scheme", scheme),
      Pair("stan", stan),
      Pair("responseCode", responseCode),
      Pair("payDate", payDate),
      Pair("tellerNo", tellerNo),
      Pair("tellerNoName", tellerNoName),
      Pair("rrn", rrn),
      Pair("authCode", authCode),
      Pair("channel", channel),
      Pair("type", type),
      )
  }
}
