package com.example.puga.ProyectoDeProgramacion;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class Pacientes extends AppCompatActivity {

    DBAdapter db;
    AdaptadorLista al;

    ListView lista;

    int ids[];
    //String nombres[];
    //String fechas[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacientes);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lista=(ListView) findViewById(R.id.ListaPacientes);

        db=new DBAdapter(getApplicationContext());
        db.open();

        //db.insertPaciente("Raul Rafael Carvajal Gutiérrez","Canada #95","raracarvajalgu@ittepic.edu.mx");
        //db.insertMedicamento(1,"Clonazepam","Ansiedad","Tres gotas al dia",Calendar.getInstance().getTime().toGMTString(),
        //Calendar.getInstance().getTime().toGMTString(),Calendar.getInstance().getTime().toGMTString(),0);

        String nombres[]=new String[db.lengthPacientes()];
        String fechas[]=new String[db.lengthPacientes()];
        ids=new int[db.lengthPacientes()];
        int i=0;
        Cursor result=db.getAllPacientesAZ();
        result.moveToFirst();
        while (!result.isAfterLast()) {
            int id = result.getInt(0);
            String name=result.getString(1);
            String fecha=result.getString(4);
            ids[i]=id;
            nombres[i]=name;
            fechas[i]=fecha;
            i++;
            result.moveToNext();
        }
        result.close();
        db.close();

        al=new AdaptadorLista(getApplicationContext(),nombres,fechas);
        lista.setAdapter(al);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Pacientes.this,addupPaciente.class);
                i.putExtra("op",0);
                startActivity(i);
            }
        });

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent inte=new Intent(Pacientes.this,Medicamento.class);
                inte.putExtra("idp",ids[i]);
                inte.putExtra("op",0);
                startActivity(inte);
            }
        });

        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent in=new Intent(Pacientes.this,addupPaciente.class);
                in.putExtra("idp",ids[i]);
                in.putExtra("op",1);//0 insert 1 update
                startActivity(in);
                Toast.makeText(getApplication(),""+i,Toast.LENGTH_SHORT).show();
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pacientes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Toast.makeText(this,"Setting",Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
