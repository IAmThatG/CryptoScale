package com.example.android.cryptoscale;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EthereumConversionActivity extends AppCompatActivity
{
    private double conversionRate = 0;
    private String currencyName;
    private Button btnETHConversion;
    private TextView resultTxtView;
    private EditText amountEditTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ethereum_conversion);

        conversionRate = getIntent().getDoubleExtra("CONVERSION_RATE", 0);
        currencyName = getIntent().getStringExtra("CURRENCY_NAME");

        btnETHConversion = (Button) findViewById(R.id.btn_ethereum_convert);
        resultTxtView = (TextView) findViewById(R.id.eth_conversion_result_txt_view);

        amountEditTxt = (EditText) findViewById(R.id.eth_amount_edit_text);

        setConvertBtnClickListener();
    }

    private void setConvertBtnClickListener()
    {
        btnETHConversion.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                double amount = 0;
                try {
                    amount = Double.parseDouble(amountEditTxt.getText().toString());
                } catch (NumberFormatException e) {
                    // p did not contain a valid double
                }
                double result = conversionRate * amount;
                String resultString = String.valueOf(result);
                resultTxtView.setText(currencyName + " " + resultString);
            }
        });
    }
}
