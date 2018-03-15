package com.example.puga.guardarensd;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    EditText txtDatos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDatos = findViewById(R.id.txtDatos);
    }

    public void onClickSave(View view){
        File myFile = new File("/sdcard/myfile.txt");
        String datos = txtDatos.getText().toString();

        try{
            myFile.createNewFile();
            FileOutputStream fout = new FileOutputStream(myFile);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fout);
            myOutWriter.append(datos);
            myOutWriter.close();
            fout.close();
            Toast.makeText(view.getContext(),"Todo se ha guardado correctamente", Toast.LENGTH_LONG).show();
        }catch(IOException e){
            Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void onClickLoad(View view){
        File myFile = new File("/sdcard/myfile.txt");
        try{
            FileInputStream fileInputStream = new FileInputStream(myFile);
            BufferedReader myReader = new BufferedReader( new InputStreamReader(fileInputStream));
            String a = "";
            String b="";
            while((a = myReader.readLine())!=null){
                b +=a;
            }

            myReader.close();
            txtDatos.setText(b);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
