package com.rockbass.contactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rockbass.contactos.db.BaseDeDatosHelper;
import com.rockbass.contactos.db.UsuarioContract;

import java.util.ArrayList;

public class DetalleContactos extends AppCompatActivity {
    private Spinner contConfianza;
    private  TextView nombreA,apePat,apeMat, edadA, telefonoA, emailA;
    ArrayList<String> listaSpinner;
    ArrayList<Persona> listaPersona;

    private BaseDeDatosHelper baseDeDatosHelper;
    int idContacto, position, idModificar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_contactos);
        baseDeDatosHelper = new BaseDeDatosHelper(this);

        nombreA = findViewById(R.id.textViewNombre);
        apePat=findViewById(R.id.textViewApePat);
        apeMat = findViewById(R.id.textViewApeMat);
        edadA = findViewById(R.id.textViewEdad);
        telefonoA = findViewById(R.id.textViewTel);
        emailA = findViewById(R.id.textViewEmail2);
        contConfianza =  findViewById(R.id.spinnerContactos);
        consultaContactos();

        contConfianza.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idContacto = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fabActualizar = findViewById(R.id.fab_actualizar);
        fabActualizar.setOnClickListener(
                view->{
                    SQLiteDatabase db = baseDeDatosHelper.getWritableDatabase();

                    String nombre = nombreA.getText().toString();
                    String apellidoPaterno = apePat.getText().toString();
                    String apellidoMaterno = apeMat.getText().toString();
                    String telefono = telefonoA.getText().toString();
                    int edad = Integer.parseInt(edadA.getText().toString());
                    String email = emailA.getText().toString();
                    String contactoConfianza = contConfianza.getSelectedItem().toString();

                    ContentValues values = new ContentValues();
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_NOMBRE, nombre);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO, apellidoPaterno);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO, apellidoMaterno);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_TELEFONO, telefono);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_EDAD, edad);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_EMAIL, email);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_CONTACTO_CONFIANZA, contactoConfianza);


                    db.update(UsuarioContract.UsuarioEntry.TABLA_CONTACTO,values,"_id="+idModificar,null);
                    Toast.makeText(getApplicationContext(), "Contacto modificado ", Toast.LENGTH_SHORT).show();

                    db.close();


                    finish();

                }
        );

    }

    private void consultaContactos() {
        SQLiteDatabase db = baseDeDatosHelper.getReadableDatabase();
        Persona persona = null;
        String[] columnas = {
                UsuarioContract.UsuarioEntry._ID,
                UsuarioContract.UsuarioEntry.CAMPO_NOMBRE,
                UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO,
                UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO,
                UsuarioContract.UsuarioEntry.CAMPO_TELEFONO,
                UsuarioContract.UsuarioEntry.CAMPO_EDAD,
                UsuarioContract.UsuarioEntry.CAMPO_EMAIL,
        };
        Cursor cursor = db.query(UsuarioContract.UsuarioEntry.TABLA_CONTACTO,
                columnas,
                null,
                null,
                null,
                null,
                null);

        if (!cursor.moveToFirst()) {
            cursor.close();
            db.close();
            return;
        }
        listaPersona = new ArrayList<Persona>();
        do {
            persona = new Persona();

            persona.id = cursor.getInt(cursor.getColumnIndex(UsuarioContract.UsuarioEntry._ID));
            persona.nombre = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_NOMBRE));
            persona.apellidoPaterno = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO));
            persona.apellidoMaterno = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO));
            persona.telefono = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_TELEFONO));
            persona.edad = cursor.getInt(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_EDAD));
            persona.email = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_EMAIL));

            listaPersona.add(persona);
        } while (cursor.moveToNext());

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            position = bundle.getInt("Posicion");
            Persona p = listaPersona.get(position);
            idModificar= p.id;
            nombreA.setText(p.nombre);
            apePat.setText(p.apellidoPaterno);
            apeMat.setText(p.apellidoMaterno);
            edadA.setText(Integer.toString(p.edad));
            telefonoA.setText(p.telefono);
            emailA.setText(p.email);

            cargarSpinner(position);

            cursor.close();
        }
    }
    private void cargarSpinner(int pos){
        listaSpinner = new ArrayList<String>();
        listaSpinner.add("Seleccione");
        for (int i = 0; i < listaPersona.size(); i++) {
            listaSpinner.add(listaPersona.get(i).getNombre()+" "+listaPersona.get(i).getApellidoPaterno());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaSpinner);
        contConfianza.setAdapter(adapter);
    }


}
