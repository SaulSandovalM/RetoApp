package com.saul.develop.retoapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by BlisS on 30/11/16.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    String elToken;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("TAG", "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        elToken = refreshedToken;
        subeToken();
    } //onToken

    public void subeToken(){
        SubeleTask task = new SubeleTask();
        task.execute();
    }

    public class SubeleTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {

            //Aqui estamos haciendo el consumo de la API

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String forecastJsonStr = null;

            //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            //StrictMode.setThreadPolicy(policy);

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are avaiable at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
                URL url = new URL("http://54.191.89.231:8000/api/users/updatetoken/");

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");

                //String credentials = "bliss" + ":" + "Poweroso77";
                //String encoded =  "Basic " + new String(Base64.encode(credentials.getBytes(), Base64.DEFAULT));

                // intentando el authenticator
                Authenticator.setDefault (new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication ("bliss", "Poweroso77".toCharArray());
                    }
                });

//                urlConnection.setRequestProperty("Authorization", "basic " +
//                        Base64.encode("bliss:Poweroso77".getBytes(), Base64.NO_WRAP));

                //seteamos el body y datos
                //lo agregamos al conection
                String urlParameters =
                        "token=" + URLEncoder.encode(elToken.toString(), "UTF-8");
                               // "&titulo=" + URLEncoder.encode(elToken.toString(), "UTF-8");

                urlConnection.setRequestMethod("POST");// type of request
                urlConnection.setRequestProperty("Content-Type",
                        "application/x-www-form-urlencoded");

                urlConnection.setRequestProperty("Content-Length", "" +
                        Integer.toString(urlParameters.getBytes().length));
                urlConnection.setRequestProperty("Content-Language", "en-US");

                urlConnection.setUseCaches (false);
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        urlConnection.getOutputStream ());
                wr.writeBytes (urlParameters);
                wr.flush ();
                wr.close ();

                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.v("respuesta",forecastJsonStr);

            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Pinche Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally{
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e("PlaceholderFragment", "Error closing stream", e);
                    }
                }

            }

            return null;
        }//doIN




//        @Override
//        protected void onPostExecute(String[] result) {
//            if(result!=null){
//                adapter.clear();
//                adapter.addAll(result);
//                //for (String resultStrs : result){
//                //  adapter.add(resultStrs);
//                //}
//            }
//        }
    } //task
}
