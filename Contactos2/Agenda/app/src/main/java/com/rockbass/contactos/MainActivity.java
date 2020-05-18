package com.rockbass.contactos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.rockbass.contactos.db.BaseDeDatosHelper;
import com.rockbass.contactos.db.UsuarioContract;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {


    private BaseDeDatosHelper baseDeDatosHelper;
    private ArrayList<String> listaNom;
    ArrayList <Persona> listaContactos;
    private UsuariosAdapter usuariosAdapter;
    AdapterPersona adapterPersona;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton buttonAgregar = findViewById(R.id.fab_agregar);
        buttonAgregar.setOnClickListener(
                (view)->{
                    Intent intent = new Intent(MainActivity.this, RegistrarActivity.class);
                    startActivity(intent);
                }
        );
        baseDeDatosHelper = new BaseDeDatosHelper(MainActivity.this);
        usuariosAdapter = new UsuariosAdapter();
        consultaContactos();

        RecyclerView recyclerView = findViewById(R.id.recyclerview_contactos);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(usuariosAdapter);



    }

    @Override
    protected void onResume() {
        super.onResume();

        consultaContactos();
    }

    class AdapterPersona extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.persona_card, parent, false);

            return new RecyclerView.ViewHolder(view) {};
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            View view = holder.itemView;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intentD = new Intent(MainActivity.this, DetalleContactos.class);
                    intentD.putExtra("position",position);
                    startActivity(intentD);

                }
            });

        }




        @Override
        public int getItemCount() {
            return Memory.PERSONAS.size();
        }
    }
    class UsuariosAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        private final List<Persona> usuarios;

        public UsuariosAdapter(){
            usuarios = new ArrayList<>();
        }

        public void setData(final List<Persona> usuarios){
            this.usuarios.clear();
            this.usuarios.addAll(usuarios);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.persona_card, parent, false);

            RecyclerView.ViewHolder holder = new RecyclerView.ViewHolder(view) {};

            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            View view = holder.itemView;

            Persona usuario = usuarios.get(position);

            TextView nombreCompleto, edad, telefono, email,contConfianza;

            nombreCompleto = view.findViewById(R.id.textViewNombreCompleto);

            edad = view.findViewById(R.id.textViewEdad);

            telefono = view.findViewById(R.id.textViewTelefono);

            email = view.findViewById(R.id.textViewEmail);

            contConfianza = view.findViewById(R.id.textViewContConfianza);



            nombreCompleto.setText(
                    String.format("%s %s %s",
                            usuario.nombre,
                            usuario.apellidoPaterno,
                            usuario.apellidoMaterno)
            );


            edad.setText(Integer.toString(usuario.edad));

            telefono.setText(usuario.telefono);
            email.setText(usuario.email);
            contConfianza.setText("contacto de confianza:"+usuario.contactoConfianza);
            view.setOnClickListener((v)->{
                Intent intent = new Intent(MainActivity.this, DetalleContactos.class);
                Bundle bundle = new Bundle();
                bundle.putInt("Posicion",position);
                intent.putExtras(bundle);
                startActivity(intent);
            });

        }

        @Override
        public int getItemCount() {
            return usuarios.size();
        }
    }
    private void consultaContactos() {

        SQLiteDatabase db = baseDeDatosHelper.getReadableDatabase();
        String[] columnas = {
                UsuarioContract.UsuarioEntry._ID,
                UsuarioContract.UsuarioEntry.CAMPO_NOMBRE,
                UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO,
                UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO,
                UsuarioContract.UsuarioEntry.CAMPO_TELEFONO,
                UsuarioContract.UsuarioEntry.CAMPO_EDAD,
                UsuarioContract.UsuarioEntry.CAMPO_EMAIL,
                UsuarioContract.UsuarioEntry.CAMPO_CONTACTO_CONFIANZA,
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
        List<Persona> personas = new ArrayList<>();
        do{
            Persona persona = new Persona();

            persona.id = cursor.getInt(cursor.getColumnIndex(UsuarioContract.UsuarioEntry._ID));
            persona.nombre = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_NOMBRE));
            persona.apellidoPaterno = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_APEPATERNO));
            persona.apellidoMaterno = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_APEMATERNO));
            persona.telefono = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_TELEFONO));
            persona.edad = cursor.getInt(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_EDAD));
            persona.email = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_EMAIL));
            persona.contactoConfianza = cursor.getString(cursor.getColumnIndex(UsuarioContract.UsuarioEntry.CAMPO_CONTACTO_CONFIANZA));

            personas.add(persona);
        }while(cursor.moveToNext());
        cursor.close();

        usuariosAdapter.setData(personas);
        usuariosAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onStop() {
        super.onStop();

        baseDeDatosHelper.close();
    }



}
