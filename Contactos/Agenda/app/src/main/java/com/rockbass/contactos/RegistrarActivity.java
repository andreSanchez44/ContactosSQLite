package com.rockbass.contactos;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.rockbass.contactos.db.BaseDeDatosHelper;
import com.rockbass.contactos.db.UsuarioContract;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RegistrarActivity extends AppCompatActivity {

    private EditText editTextNombre, editTextApellidoPaterno, editTextApellidoMaterno,
            editTextEdad, editTextTelefono, editTextEmail;
    private Spinner spinnerContacto;
    private BaseDeDatosHelper baseDeDatosHelper;
    ArrayList<String> listaSpinner;
    ArrayList<Persona> listaPersona;
    private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar);

        baseDeDatosHelper = new BaseDeDatosHelper(this);

        editTextNombre = findViewById(R.id.editTextNombre);
        editTextApellidoPaterno = findViewById(R.id.editTextApellidoPaterno);
        editTextApellidoMaterno = findViewById(R.id.editTextApellidoMaterno);
        editTextEdad = findViewById(R.id.editTextEdad);
        editTextTelefono = findViewById(R.id.editTextTelefono);
        editTextEmail = findViewById(R.id.editTextEmail);
        spinnerContacto = findViewById(R.id.spinnerCont);
        consultarContactos();

        spinnerContacto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id = parent.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        FloatingActionButton fabRegistrar = findViewById(R.id.fab_registrar);
        fabRegistrar.setOnClickListener(
                view->{
                    SQLiteDatabase db = baseDeDatosHelper.getWritableDatabase();

                    String nombre = editTextNombre.getText().toString();
                    String apePaterno = editTextApellidoPaterno.getText().toString();
                    String apeMaterno = editTextApellidoMaterno.getText().toString();
                    String telefono = editTextTelefono.getText().toString();
                    int edad = Integer.parseInt(editTextEdad.getText().toString());
                    String email = editTextEmail.getText().toString();

                    ContentValues values = new ContentValues();
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_NOMBRE, nombre);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO, apePaterno);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO, apeMaterno);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_TELEFONO, telefono);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_EDAD, edad);
                    values.put(UsuarioContract.UsuarioEntry.CAMPO_EMAIL, email);

                    if(spinnerContacto.getCount()<0){
                        values.put(UsuarioContract.UsuarioEntry.CAMPO_CONTACTO_CONFIANZA,id);}

                    long id = db.insertOrThrow(UsuarioContract.UsuarioEntry.TABLA_CONTACTO,null, values);
                    Toast.makeText(getApplicationContext(),"Id registro: "+ id,Toast.LENGTH_SHORT).show();
                    db.close();

                    finish();
                }
        );
    }

    private void consultarContactos() {
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
        do{
            persona = new Persona();

            persona.id = cursor.getInt(cursor.getColumnIndex(UsuarioContract.UsuarioEntry._ID));
            persona.nombre = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_NOMBRE));
            persona.apellidoPaterno = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO));
            persona.apellidoMaterno = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO));
            persona.telefono = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_TELEFONO));
            persona.edad = cursor.getInt(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_EDAD));
            persona.email = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_EMAIL));

            listaPersona.add(persona);
        }while(cursor.moveToNext());
        cargarSpinner();
        cursor.close();

    }

    private void cargarSpinner(){
        listaSpinner = new ArrayList<String>();
        listaSpinner.add("Seleccione");
        for (int i = 0; i < listaPersona.size(); i++) {
            listaSpinner.add(listaPersona.get(i).getId()+"-"+listaPersona.get(i).getNombre()+" "+listaPersona.get(i).getApellidoPaterno());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,listaSpinner);
        spinnerContacto.setAdapter(adapter);
    }
    @Override
    protected void onStop() {
        super.onStop();

        baseDeDatosHelper.close();
    }
}
