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
      body: Center(child: ElevatedButton(onPressed: () {
        final paymentModel = PaymentModel(amount: "100.00", reference: "");

        methodChannel.invokeMethod('cardPayment', paymentModel.toMap());
      }, child: const Text("Make Payment")),),
    );
  }
}

class PaymentModel {
  String? amount, reference;
  PaymentModel({this.amount, this.reference});

  Map<String, dynamic> toMap() {
    return {
      'amount': amount,
      'reference': reference
    };
  }
}

