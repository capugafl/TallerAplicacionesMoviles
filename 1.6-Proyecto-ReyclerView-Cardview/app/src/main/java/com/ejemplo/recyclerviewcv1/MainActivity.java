package com.ejemplo.recyclerviewcv1;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProductAdapter adapter;

    List<DataProvider> productList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        productList = new ArrayList<>();

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        productList.add(
                new DataProvider("Folklorica", "Movimiento Naranja", R.drawable.folklore, Color.rgb(222, 56, 60))
        );
        productList.add(
                new DataProvider("Rock", "Back in Black", R.drawable.rock, Color.rgb(53, 142, 147))
        );
        productList.add(
                new DataProvider("Acústicos", "In Bloom",R.drawable.acustica, Color.rgb(209, 33, 54))
        );
        productList.add(
                new DataProvider("Metal", "Walk",R.drawable.smn, Color.GRAY)
        );


        adapter = new ProductAdapter(this,productList);
        recyclerView.setAdapter(adapter);



    }
}
