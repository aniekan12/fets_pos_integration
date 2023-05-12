import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const PaymentPage(),
    );
  }
}

class PaymentPage extends StatelessWidget {
  const PaymentPage({Key? key}) : super(key: key);
  final methodChannel = const MethodChannel('com.irecharge.fets');

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: ElevatedButton(
            onPressed: () async {
              final paymentModel = PaymentModel(amount: "100.00", reference: "");
              FetsTransactionResponse? response = FetsTransactionResponse.fromJson((await methodChannel.invokeMethod('cardPayment', paymentModel.toMap())) as Map<String, dynamic>);
              debugPrint('$response');
            },
            child: const Text("Make Payment")),
      ),
    );
  }
}

class PaymentModel {
  String? amount, reference;
  PaymentModel({this.amount, this.reference});

  Map<String, dynamic> toMap() {
    return {'amount': amount, 'reference': reference};
  }
}

class FetsTransactionResponse {
  String? tid,
      mid,
      issuer,
      cardName,
      pan,
      exDate,
      scheme,
      stan,
      responseCode,
      payDate,
      tellerNo,
      tellerNoName,
      rrn,
      authCode,
      channel,
      type;
  num? amount;

  FetsTransactionResponse(
      {this.tid,
      this.mid,
      this.issuer,
      this.cardName,
      this.pan,
      this.exDate,
      this.scheme,
      this.stan,
      this.responseCode,
      this.payDate,
      this.tellerNo,
      this.tellerNoName,
      this.rrn,
      this.authCode,
      this.channel,
      this.type,
      this.amount});


  @override
  String toString() {
    return 'FetsTransactionResponse{tid: $tid, mid: $mid, issuer: $issuer, cardName: $cardName, pan: $pan, exDate: $exDate, scheme: $scheme, stan: $stan, responseCode: $responseCode, payDate: $payDate, tellerNo: $tellerNo, tellerNoName: $tellerNoName, rrn: $rrn, authCode: $authCode, channel: $channel, type: $type, amount: $amount}';
  }

  factory FetsTransactionResponse.fromJson(Map<String, dynamic> json) {
    return FetsTransactionResponse(
        tid: json['tid'],
        mid: json['mid'],
        amount: json['amount'],
        issuer: json['issuer'],
        cardName: json['cardName'],
        pan: json['pan'],
        exDate: json['exDate'],
        scheme: json['scheme'],
        stan: json['stan'],
        responseCode: json['responseCode'],
        payDate: json['payDate'],
        tellerNo: json['tellerNo'],
        tellerNoName: json['tellerNoName'],
        rrn: json['rrn'],
        authCode: json['authCode'],
        channel: json['channel'],
        type: json['type']);
  }
}
