package com.sbt.currency.repository.implementations;

import android.os.AsyncTask;

import com.sbt.currency.interactors.Subscriber;
import com.sbt.currency.exceptions.RequestError;
import com.sbt.currency.repository.CurrencyXmlRequest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pasenchuk Victor on 28/04/2017
 */

public class CurrencyXmlRequestImpl extends AsyncTask<Void, Void, String> implements CurrencyXmlRequest {

    private static final String CURRENCY_URL = "http://www.cbr.ru/scripts/XML_daily.asp";
    private Subscriber<String, RequestError> subscriber;

    @Override
    public void fetchXmlData(Subscriber<String, RequestError> subscriber) {

        this.subscriber = subscriber;
        execute();
    }

    @Override
    protected String doInBackground(Void... voids) {
        try {
            URL url = new URL(CURRENCY_URL);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            try {
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                return readStream(in);
            } catch (IOException e) {
                subscriber.onError(new RequestError(e, RequestError.Kind.IO_ERROR));
            } finally {
                urlConnection.disconnect();
            }
        } catch (IOException e) {
            subscriber.onError(new RequestError(e, RequestError.Kind.NETWORK_ERROR));
        }
        return null;
    }

    private String readStream(InputStream inputStream) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream, "windows-1251"));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line).append('\n');
        }
        return total.toString();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null)
            subscriber.onNext(result);
    }


    @Override
    public boolean isSubscribed() {
        switch (getStatus()) {
            case RUNNING:
                return true;
            case FINISHED:
            case PENDING:
            default:
                return false;
        }
    }

    @Override
    public void unsubscribe() {
        cancel(true);
    }

}
