package com.saul.develop.retoapp;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Empresarios extends Fragment {

    private ArrayAdapter<String> adapter;

    public Empresarios() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        new consumoTask().execute();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main3, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_refresh){
            Empresarios.consumoTask task = new Empresarios.consumoTask();
            task.execute();
            return true;
        }
        return  super.onOptionsItemSelected(item);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //declaro mis datos
        String[] data = {
                /*"Juan",
                "Pedro",
                "Felipe",
                "Diego",
                "Omar",
                "Fernando",
                "Luis"*/
        };

        List<String> fakaData = new ArrayList<String>(Arrays.asList(data));

       adapter = new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.elTexto,fakaData);

        View rootView = inflater.inflate(R.layout.fragment_empresarios, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.laLista);
        listView.setAdapter(adapter);
        return rootView;

    }

    public class consumoTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String forecastJsonStr = null;

            try {

                URL url = new URL("https://agile-thicket-30819.herokuapp.com/api/empresas/");

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");

                Authenticator.setDefault (new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication ("bliss", "Poweroso77".toCharArray());
                    }
                });

                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                forecastJsonStr = buffer.toString();
                Log.v("Funciona t(*-*t)", forecastJsonStr);
            } catch (IOException e) {
                Log.e("PlaceholderFragment", "Error ", e);
                return null;
            } finally {
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
            try {
                return getDataFormJson(forecastJsonStr);
            } catch(JSONException e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return null;
        }
        private String[] getDataFormJson(String forecastJsonStr) throws JSONException{
            final String FIELDS = "fields";
            final String EMPRESAS = "name";
            JSONArray forecastJson = new JSONArray(forecastJsonStr);
            String[] resultStrs = new String[forecastJson.length()];
            for(int i=0;i < forecastJson.length(); i++){

                JSONObject elemento_cero = forecastJson.getJSONObject(i);
                JSONObject fields = elemento_cero.getJSONObject(FIELDS);
                String empresas = fields.getString(EMPRESAS);

                Log.v("el array", forecastJson.toString());
                Log.v("el field", forecastJsonStr);
                Log.v("la empresas", forecastJsonStr);

                resultStrs[i] = empresas;

                Log.v("la respuesta", resultStrs.toString());
            }
            return resultStrs;
        }

        @Override
        protected void onPostExecute(String[] result) {
            if (result != null){
                adapter.clear();
                adapter.addAll(result);
            }
        }
    }
}
