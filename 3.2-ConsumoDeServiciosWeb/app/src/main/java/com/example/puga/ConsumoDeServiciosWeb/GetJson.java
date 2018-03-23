package com.example.puga.ConsumoDeServiciosWeb;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by puga on 21/03/18.
 */

public class GetJson extends AsyncTask<Void,Void,Void> {

    String data ="";
    String dataParsed = "";
    String singleParsed ="";

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://api.coinmarketcap.com/v1/ticker/?convert=EUR&limit=10");
            HttpURLConnection httpURLConnection = (HttpURLConnection)
                    url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new
                    InputStreamReader(inputStream));
            String line = "";
            while(line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONArray JA = new JSONArray(data);
            for(int i =0 ;i <JA.length(); i++) {
                JSONObject JO = (JSONObject) JA.get(0);


                singleParsed = "Moneda: " + JO.get("name") + "\n" +
                        "Simbolo: " + JO.get("symbol") + "\n" +
                        "Precio USD: " + JO.get("price_usd") + "\n" +
                        "Tasa de actualizacion: " + JO.get("percent_change_1h") + "\n" ;
                dataParsed = dataParsed + singleParsed + "\n";
            }
        }
        catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        MainActivity.data.setText(this.dataParsed);
    }


}
