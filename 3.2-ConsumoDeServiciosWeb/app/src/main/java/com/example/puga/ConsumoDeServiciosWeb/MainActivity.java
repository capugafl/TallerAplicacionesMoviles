package com.example.puga.ConsumoDeServiciosWeb;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button click;
    public static TextView data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        click = (Button)findViewById(R.id.btnConsultar);
        data = (TextView)findViewById(R.id.txtSalida);


        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetJson proces=new GetJson();
                proces.execute();
            }
        });

    }
}
