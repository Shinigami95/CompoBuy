package com.api.compobuy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import frags.ListaProductosFragment;
import frags.ProductoFragment;

public class MenuPrincipalActivity extends AppCompatActivity
        implements ProductoFragment.OnProductoFragmentInteractionListener,
        ListaProductosFragment.OnListaProductosFragmentInteractionListener{

    String user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_principal);
        Bundle ext = getIntent().getExtras();
        if(ext!=null){
            user = ext.getString("username");
            SharedPreferences prefs = getSharedPreferences("gcu_preferences", Context.MODE_APPEND);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("user_actual",user);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.it_carro:
                irAlCarro();
                return true;
            case R.id.it_log_out:
                cerrarSesion();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void irAlCarro(){
        Intent i = new Intent(this,CarroActivity.class);
        i.putExtra("username",user);
        startActivity(i);
    }

    private void cerrarSesion(){
        SharedPreferences prefs = getSharedPreferences("gcu_preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.remove("user_actual");
        editor.apply();
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onProductoFragmentInteractionListener(ProductoFragment fr) {
    }

    @Override
    public void onListaProductosFragmentInteractionListener(ListaProductosFragment fr) {
        fr.actualizarFragment(user);
    }

    public void onListaProductosFragmentSeleccionarComponente(ListaProductosFragment fr, long idC) {
        ProductoFragment pf = (ProductoFragment) getFragmentManager().findFragmentById(R.id.frag_prod_land);
        //Si existe el fragment que muestra la informacion del centro, actualizara su informacion
        if(pf!=null&&pf.getView()!=null) {
            pf.actualizarFragment(idC,user);
        }
        //Si no existe el fragment llamara la actividad que muestre la info del centro indicado
        else {
            Intent i = new Intent(this, ProductoActivity.class);
            i.putExtra("idcomp", idC);
            i.putExtra("username", user);
            startActivity(i);
        }
    }
}
