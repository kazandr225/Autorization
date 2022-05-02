package com.example.autorization;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Button;

import java.util.Arrays;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    Spinner spinner;

    TextView firstNumber;
    TextView sign;
    TextView secondNumber;
    TextView eq;
    TextView result;

    Button zero;
    Button one;
    Button two;
    Button three;
    Button four;
    Button five;
    Button six;
    Button seven;
    Button eight;
    Button nine;
    Button plus;
    Button minus;
    Button multiply;
    Button divide;
    Button clear;
    Button equals;
    Button transition;

    String act, fn, sn, oper, rslt;
    String[] results = new String[0];
    boolean fnum;
    int x;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        act = "";
        fnum = true;

        spinner = findViewById(R.id.spinner);

        firstNumber = findViewById(R.id.firstNumber);
        sign = findViewById(R.id.sign);
        secondNumber = findViewById(R.id.secondNumber);
        eq = findViewById(R.id.eq);
        result = findViewById(R.id.result);

        zero = findViewById(R.id.zero);
        one = findViewById(R.id.one);
        two = findViewById(R.id.two);
        three = findViewById(R.id.three);
        four = findViewById(R.id.four);
        five = findViewById(R.id.five);
        six = findViewById(R.id.six);
        seven = findViewById(R.id.seven);
        eight = findViewById(R.id.eight);
        nine = findViewById(R.id.nine);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        multiply = findViewById(R.id.multiply);
        divide = findViewById(R.id.divide);
        clear = findViewById(R.id.clear);
        equals = findViewById(R.id.equals);

        zero.setOnClickListener(this);
        one.setOnClickListener(this);
        two.setOnClickListener(this);
        three.setOnClickListener(this);
        four.setOnClickListener(this);
        five.setOnClickListener(this);
        six.setOnClickListener(this);
        seven.setOnClickListener(this);
        eight.setOnClickListener(this);
        nine.setOnClickListener(this);
        plus.setOnClickListener(this);
        minus.setOnClickListener(this);
        multiply.setOnClickListener(this);
        divide.setOnClickListener(this);
        clear.setOnClickListener(this);
        equals.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zero:
            case R.id.one:
            case R.id.two:
            case R.id.three:
            case R.id.four:
            case R.id.five:
            case R.id.six:
            case R.id.seven:
            case R.id.eight:
            case R.id.nine:
                if(!fnum && firstNumber.getText() == "")
                {
                    fnum = !fnum;
                }
                Button button = (Button) view;
                String numText;
                if (fnum) {
                    numText = firstNumber.getText().toString();
                    numText += button.getText().toString();
                    firstNumber.setText(numText);
                } else {
                    numText = secondNumber.getText().toString();
                    numText += button.getText().toString();
                    secondNumber.setText(numText);
                }
                break;
            case R.id.plus:
            case R.id.minus:
            case R.id.multiply:
            case R.id.divide:
                Button button1 = (Button) view;
                x = view.getId();
                switch (x) {
                    case R.id.plus:
                        sign.setText("+");
                        oper += "+";
                        break;
                    case R.id.minus:
                        sign.setText("-");
                        oper += "-";
                        break;
                    case R.id.multiply:
                        sign.setText("*");
                        oper += "*";
                        break;
                    case R.id.divide:
                        sign.setText("/");
                        oper += "/";
                        break;
                }
                if (act == button1.getText().toString()) {
                    act = button1.getText().toString();
                    if (!fnum) {
                        fnum = !fnum;
                    }
                } else {
                    fnum = !fnum;
                }
                break;
            case R.id.clear:
                firstNumber.setText("");
                sign.setText("");
                secondNumber.setText("");
                eq.setText("");
                result.setText("");
                break;
            case R.id.equals:
                float num1 = Float.valueOf(firstNumber.getText().toString());
                float num2 = Float.valueOf(secondNumber.getText().toString());
                fn = firstNumber.getText().toString();
                sn = secondNumber.getText().toString();
                float res;
                eq.setText("=");
                switch (x) {
                    case R.id.plus:
                        res = num1 + num2;
                        result.setText(String.valueOf(res));
                        rslt = String.valueOf(res);
                        break;
                    case R.id.minus:
                        res = num1 - num2;
                        result.setText(String.valueOf(res));
                        rslt = String.valueOf(res);
                        break;
                    case R.id.divide:
                        res = num1 / num2;
                        result.setText(String.valueOf(res));
                        rslt = String.valueOf(res);
                        break;
                    case R.id.multiply:
                        res = num1 * num2;
                        result.setText(String.valueOf(res));
                        rslt = String.valueOf(res);
                        break;
                }
                fnum = !fnum;
                results = Arrays.copyOf(results, results.length + 1);
                String temp;
                for(int i = results.length - 1; i >= 1; i--)
                {
                    temp = results[i - 1];
                    results[i - 1] = results[i];
                    results[i] = temp;
                }
                oper = oper.replace("null", "");
                results[0] = fn + " " + oper + " " + sn + " = " + rslt;
                adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,results);
                spinner.setAdapter(adapter);
                fn = "";
                sn = "";
                oper = "";
                rslt = "";
                break;
        }
    }
}
