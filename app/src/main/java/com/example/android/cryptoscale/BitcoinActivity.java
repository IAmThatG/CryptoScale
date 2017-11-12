package com.example.android.cryptoscale;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.cryptoscale.Utils.ApiUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.os.Build.VERSION_CODES.M;

public class BitcoinActivity extends AppCompatActivity
{
    private String btcSymbol;
    TextView amountTextView;
    private double mResult = 0;
    private CardView mBitcoinCardView;
    private String selectedItemText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bitcoin);

        amountTextView = (TextView) findViewById(R.id.frm_btc_amount_txt_view);
        mBitcoinCardView = (CardView) findViewById(R.id.bitcoin_card_view);

        //Get bitcoin symbol
        btcSymbol = getIntent().getStringExtra("BITCOIN_SYMBOL");

        //Get spinner view in activity
        final Spinner currencySpinner = (Spinner) findViewById(R.id.curency_spinner_one);

        List<String> currencyList = new ArrayList<>();
        currencyList.add("Currency");
        currencyList.add("NGN");
        currencyList.add("USD");
        currencyList.add("AUD");
        currencyList.add("EUR");
        currencyList.add("GBP");
        currencyList.add("JPY");
        currencyList.add("CHF");
        currencyList.add("BHD");
        currencyList.add("KYD");
        currencyList.add("CNY");
        currencyList.add("GIP");
        currencyList.add("JOD");
        currencyList.add("BHD");
        currencyList.add("LVL");
        currencyList.add("KWD");
        currencyList.add("OMR");
        currencyList.add("DKK");
        currencyList.add("HKD");
        currencyList.add("NZD");
        currencyList.add("LYD");


        //Create an array adapter using the string array resource and a default spinner layout
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, currencyList
        ){
            @Override
            public boolean isEnabled ( int position)
            {
                // disable the first item from spinner. This item will be used for hint
                return position != 0;
            }

            @Override
            public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
            {
                View view = super.getDropDownView(position, convertView, parent);
                TextView textView = (TextView) view;
                if (position == 0)
                    //set the hint text color to grey
                    textView.setTextColor(Color.GRAY);
                else
                    textView.setTextColor(Color.BLACK);
                return view;
            }
        };

        //Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //Apply the adapter to the spinner
        currencySpinner.setAdapter(adapter);

        currencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                selectedItemText = (String) adapterView.getItemAtPosition(i);
                if (i > 0)
                    new CurrencyConversionTask().execute(selectedItemText);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
        setBitcoinCardViewOnClickListener();
    }

    private void setBitcoinCardViewOnClickListener()
    {
        mBitcoinCardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), BitcoinConversionActivity.class);
                intent.putExtra("CONVERSION_RATE", mResult);
                intent.putExtra("CURRENCY_NAME", selectedItemText);
                if (selectedItemText.compareToIgnoreCase("Currency") == 0 || mResult == 0)
                    Toast.makeText(BitcoinActivity.this, "Pls Choose Currency", Toast.LENGTH_LONG).show();
                else
                    startActivity(intent);
            }
        });
    }

    private class CurrencyConversionTask extends AsyncTask<String, Void, Double>
    {
        @Override
        protected Double doInBackground(String... strings)
        {
            String tsym = strings[0];
            return ApiUtils.makeCurrencyConversionCall(tsym, btcSymbol);
        }

        @Override
        protected void onPostExecute(Double result)
        {
            super.onPostExecute(result);
            amountTextView.setText(selectedItemText + " " + String.valueOf(result));
            mResult = result;
        }
    }
}
