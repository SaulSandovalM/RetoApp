package com.saul.develop.retoapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

public class Registro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_meat:
                if (checked) {
                    // Put some meat on the sandwich
                }else
                // Remove the meat
                break;
            case R.id.checkbox_cheese:
                if (checked) {
                    // Cheese me
                }else
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }
    }
}
