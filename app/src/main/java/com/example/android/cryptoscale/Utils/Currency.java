package com.example.android.cryptoscale.Utils;

/**
 * Created by GABRIEL on 11/12/2017.
 */

public class Currency
{
    private String mCurrencyName;
    private String mCurrencySymbol;

    public Currency(String currencyName, String currencySymbol)
    {
        mCurrencyName = currencyName;
        mCurrencySymbol = currencySymbol;
    }

    public String getCurrencyName()
    {
        return mCurrencyName;
    }

    public String getCurrencySymbol()
    {
        return mCurrencySymbol;
    }
}
