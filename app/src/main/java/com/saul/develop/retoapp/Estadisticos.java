package com.saul.develop.retoapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Estadisticos extends Fragment {

    public Estadisticos() {
        // Required empty public constructor
    }



/*
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //declaro mis datos
        String[] data = {
                "Bimbo",
                "Pemex",
                "Coca Cola",
                "Pepsi",
                "Wonder",
                "Sony",
                "Fanta",
                "Sabritas"
        };

        List<String> fakaData = new ArrayList<String>(Arrays.asList(data));

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.item,R.id.elTexto,fakaData);

        View rootView = inflater.inflate(R.layout.fragment_estadisticos, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.laLista);
        listView.setAdapter(adapter);
        return rootView;
    }*/
}
