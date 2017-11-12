package com.example.android.cryptoscale;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import static android.os.Build.VERSION_CODES.M;

public class MainActivity extends AppCompatActivity
{
    ImageView imgBtc;
    ImageView imgEth;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgBtc = (ImageView) findViewById(R.id.img_bitcoin);
        imgEth = (ImageView) findViewById(R.id.img_ethereum);

        setImgBtcOnClickListener();
        setImgEthOnClickListener();
    }

    private void setImgBtcOnClickListener()
    {
        imgBtc.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), BitcoinActivity.class);
                intent.putExtra("BITCOIN_SYMBOL", "BTC");
                startActivity(intent);
            }
        });
    }

    private void setImgEthOnClickListener()
    {
        imgEth.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getBaseContext(), EthereumActivity.class);
                intent.putExtra("ETHEREUM_SYMBOL", "ETH");
                startActivity(intent);
            }
        });
    }
}
