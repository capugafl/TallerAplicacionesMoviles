package com.ittepic.puga.a31_consumodeserviciosweb;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {


    Button verclima;
    TextView datos, datojson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verclima = (Button)findViewById(R.id.ver);
        datos = (TextView)findViewById(R.id.datos_clima);
        datojson = (TextView)findViewById(R.id.info);

        verclima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new OpenWeatherMapTask(
                        "Tepic",
                        datos).execute();
            }
        });
    }

    private class OpenWeatherMapTask extends AsyncTask<Void, Void, String> {

        String ciudad;
        TextView texto;

        String key = "90c3c8b1f4094aaeecbce61290b03476";
        String link = "http://api.openweathermap.org/data/2.5/weather?q=";
        String linkc = "&appid=" + key;

        OpenWeatherMapTask(String cityName, TextView tvResult){
            this.ciudad = cityName;
            this.texto = tvResult;
        }

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            String queryReturn;

            String query = null;
            try {
                query = link + URLEncoder.encode("Tepic", "UTF-8") + linkc;
                queryReturn = sendQuery(query);
                result += ParseJSON(queryReturn);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                queryReturn = e.getMessage();
            }


            final String finalQueryReturn = query + "\n\n" + queryReturn;


            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    datojson.setText(finalQueryReturn);
                }
            });


            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            texto.setText(s);
        }

        private String sendQuery(String query) throws IOException {
            String result = "";

            URL searchURL = new URL(query);

            HttpURLConnection httpURLConnection = (HttpURLConnection)searchURL.openConnection();
            if(httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                InputStreamReader inputStreamReader = new InputStreamReader(httpURLConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(
                        inputStreamReader,
                        8192);

                String line = null;
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }

                bufferedReader.close();
            }

            return result;
        }

        private String ParseJSON(String json){
            String jsonResult = "";

            try {
                JSONObject JsonObject = new JSONObject(json);
                String cod = jsonHelperGetString(JsonObject, "cod");

                if(cod != null){
                    if(cod.equals("200")){

                        jsonResult += jsonHelperGetString(JsonObject, "name")+", ";
                        JSONObject sys = jsonHelperGetJSONObject(JsonObject, "sys");
                        if(sys != null){
                            jsonResult += jsonHelperGetString(sys, "country") + "\n";
                        }
                        jsonResult += "\n";

                        JSONObject coord = jsonHelperGetJSONObject(JsonObject, "coord");
                        if(coord != null){
                            String lon = jsonHelperGetString(coord, "lon");
                            String lat = jsonHelperGetString(coord, "lat");
                            jsonResult += "Longitud: " + lon + "\n";
                            jsonResult += "Latitud: " + lat + "\n";
                        }
                        // jsonResult += "\n";

                        JSONArray weather = jsonHelperGetJSONArray(JsonObject, "weather");
                        if(weather != null){
                            for(int i=0; i<weather.length(); i++){
                                JSONObject thisWeather = weather.getJSONObject(i);
                                //jsonResult += "Estadisticas clima"+ ":\n";
                                //jsonResult += "id: " + jsonHelperGetString(thisWeather, "id") + "\n";
                                //jsonResult += jsonHelperGetString(thisWeather, "main") + "\n";
                                jsonResult += jsonHelperGetString(thisWeather, "description") + "\n";
                                //jsonResult += "\n";
                            }
                        }

                        JSONObject main = jsonHelperGetJSONObject(JsonObject, "main");
                        if(main != null){
                            jsonResult += "Temperatura: " + jsonHelperGetString(main, "temp") + "\n";
                            //jsonResult += "Presion: " + jsonHelperGetString(main, "pressure") + "\n";
                            jsonResult += "Humedad: " + jsonHelperGetString(main, "humidity") + "\n";
                            //jsonResult += "Minima: " + jsonHelperGetString(main, "temp_min") + "\n";
                            //jsonResult += "Maxima: " + jsonHelperGetString(main, "temp_max") + "\n";
                            //jsonResult += "Nivel Mar: " + jsonHelperGetString(main, "sea_level") + "\n";
                            //jsonResult += "Nivel Tierra: " + jsonHelperGetString(main, "grnd_level") + "\n";

                        }

                        //jsonResult += "visibilidad: " + jsonHelperGetString(JsonObject, "visibility") + "\n";
                        //jsonResult += "\n";

                        JSONObject wind = jsonHelperGetJSONObject(JsonObject, "wind");
                        if(wind != null){
                            // jsonResult += "wind:\n";
                            jsonResult += "Velocidad del viento: " + jsonHelperGetString(wind, "speed") + "\n";
                            //jsonResult += "deg: " + jsonHelperGetString(wind, "deg") + "\n";
                            //jsonResult += "\n";
                        }

                        //...incompleted

                    }else if(cod.equals("404")){
                        String message = jsonHelperGetString(JsonObject, "message");
                        jsonResult += "cod 404: " + message;
                    }
                }else{
                    jsonResult += "cod == null\n";
                }

            } catch (JSONException e) {
                e.printStackTrace();
                jsonResult += e.getMessage();
            }

            return jsonResult;
        }

        private String jsonHelperGetString(JSONObject obj, String k){
            String v = null;
            try {
                v = obj.getString(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return v;
        }

        private JSONObject jsonHelperGetJSONObject(JSONObject obj, String k){
            JSONObject o = null;

            try {
                o = obj.getJSONObject(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return o;
        }

        private JSONArray jsonHelperGetJSONArray(JSONObject obj, String k){
            JSONArray a = null;

            try {
                a = obj.getJSONArray(k);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return a;
        }
    }
}
