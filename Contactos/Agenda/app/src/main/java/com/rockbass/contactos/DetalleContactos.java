package com.rockbass.contactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DetalleContactos extends AppCompatActivity {
    Spinner contConfianza;
    private RecyclerView cont;
    private  TextView nombre,apePat,apeMat, edad, telefono, email;
    Spinner comboContactos;
    String[] strContactos;
    List<String> listaNom;
    ArrayAdapter<String> comboAdapter;
    String nombreContacto;
    Spinner contactos;
    private View.OnClickListener listener;
    private int indice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contactos);
        contactos = findViewById(R.id.spinnerContactos);

        comboContactos = (Spinner) findViewById(R.id.spinnerContactos);
        listaNom = new ArrayList<>();
        ArrayList<String> nombres = new ArrayList<>();
        for(Persona persona : Memory.PERSONAS){
            nombres.add(persona.nombre);


        }
        comboAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, nombres);
        comboContactos.setAdapter(comboAdapter);

        comboContactos.setSelection(0);

        if (getIntent().hasExtra("position")){
            indice=getIntent().getExtras().getInt("position");
        }else{
            indice=-1;
        }
        Persona persona = Memory.PERSONAS.get(indice);

        nombre = findViewById(R.id.textViewNombre);
        apePat=findViewById(R.id.textViewApePat);
        apeMat = findViewById(R.id.textViewApeMat);

        edad = findViewById(R.id.textViewEdad);

        telefono = findViewById(R.id.textViewTel);

        email = findViewById(R.id.textViewEmail2);

        nombre.setText(persona.nombre);
        apePat.setText(persona.apellidoPaterno);
        apeMat.setText(persona.apellidoMaterno);
        edad.setText(Integer.toString(persona.edad));
        telefono.setText(persona.telefono);
        email.setText(persona.email);

        FloatingActionButton fabActualizar = findViewById(R.id.fab_actualizar);
        fabActualizar.setOnClickListener(
                view->{
                    persona.nombre = nombre.getText().toString();
                    persona.apellidoPaterno = apePat.getText().toString();
                    persona.apellidoMaterno = apeMat.getText().toString();
                    persona.edad = Integer.parseInt(edad.getText().toString());
                    persona.telefono = telefono.getText().toString();
                    persona.email = email.getText().toString();
                    /*if (comboAdapter.isEmpty())
                        persona.contactoConfianza = "";
                    else
                        persona.contactoConfianza = contactos.getSelectedItem().toString();*/



                    finish();

                }

        );







    }


}
