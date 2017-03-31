package com.api.compobuy;

import android.net.Credentials;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BD.DoHTTPRequest;
import adapters.CustomArrayCarroAdapter;
import dialogs.CreditDialog;
import modelo.Producto;

public class CarroActivity extends AppCompatActivity
        implements DoHTTPRequest.AsyncResponse, CreditDialog.GestorCredito {

    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        Bundle ext = getIntent().getExtras();
        if(ext != null){
            user = ext.getString("username");
            TextView tvNombre = (TextView) findViewById(R.id.tv_carro_user);
            String texto = tvNombre.getText().toString();
            texto = texto.replace("$USER$",user);
            tvNombre.setText(texto);
            DoHTTPRequest doHTTP = new DoHTTPRequest(this,this,-1);
            doHTTP.prepCgetcarritousu(user);
            doHTTP.execute();
            registerForContextMenu(findViewById(R.id.lv_carro));
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_eliminar, menu);
    }

    //Detectar si se ha pulsado eliminar en el menu contextual
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.it_eliminar:
                eliminarProductoCarro(info.position);
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    int posEliminar = -1;
    private void eliminarProductoCarro(int pos){
        if(posEliminar == -1) {
            Producto prod = listaProducto.get(pos);
            long idCompra = prod.idCompra;
            posEliminar = pos;
            DoHTTPRequest doHTTP = new DoHTTPRequest(this, this, -1);
            doHTTP.prepCdeleteCompocarrito(user, idCompra);
            doHTTP.execute();
        }
    }

    private CustomArrayCarroAdapter aaCarro;
    private ArrayList<Producto> listaProducto;
    private double precioTotal;
    @Override
    public void processFinish(String output, int mReqId) {
        try {
            Log.d("QWERTY",output);
            JSONObject jsonObj;
            if(mReqId == DoHTTPRequest.GET_CARRITO_USU){
                precioTotal = 0;
                JSONArray jsonArray = new JSONArray(output);
                listaProducto = new ArrayList<Producto>();
                for(int i=0; i<jsonArray.length(); i++){
                    jsonObj = jsonArray.getJSONObject(i);
                    String nombre = jsonObj.getString("nombre");
                    long idCompra = jsonObj.getLong("id");
                    double precio = jsonObj.getDouble("precio");
                    precioTotal += precio;
                    Producto prod = new Producto(nombre,precio,idCompra);
                    listaProducto.add(prod);
                }
                Button btComprar = (Button) findViewById(R.id.bt_carro_comprar);
                btComprar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        comprar(v);
                    }
                });
                TextView tvPrecio = (TextView) findViewById(R.id.tv_precio_total_text);
                tvPrecio.setText(String.format("%.2f", precioTotal));
                aaCarro = new CustomArrayCarroAdapter(this,listaProducto);
                ListView lvCarro = (ListView) findViewById(R.id.lv_carro);
                lvCarro.setAdapter(aaCarro);
            } else if(mReqId == DoHTTPRequest.DELETE_COMPO_CARRITO){
                jsonObj = new JSONObject(output);
                String status = jsonObj.getString("status");
                if(status.equals("ok")){
                    Producto prod = listaProducto.get(posEliminar);
                    double precio = prod.precio;
                    precioTotal -= precio;
                    TextView tvPrecio = (TextView) findViewById(R.id.tv_precio_total_text);
                    tvPrecio.setText(String.format("%.2f", precioTotal));

                    listaProducto.remove(posEliminar);
                    aaCarro.notifyDataSetChanged();
                    String msg = getResources().getString(R.string.eliminar_carro_ok);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    posEliminar = -1;
                } else {
                    String msg = getResources().getString(R.string.eliminar_carro_error);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            } else if(mReqId == DoHTTPRequest.DELETE_ALL_CARRITO){
                jsonObj = new JSONObject(output);
                String status = jsonObj.getString("status");
                if(status.equals("ok")){
                    precioTotal = 0.0;
                    TextView tvPrecio = (TextView) findViewById(R.id.tv_precio_total_text);
                    tvPrecio.setText(String.format("%.2f", precioTotal));

                    listaProducto.clear();
                    aaCarro.notifyDataSetChanged();
                    String msg = getResources().getString(R.string.compra_realizada);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    String msg = getResources().getString(R.string.compra_error);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            String msg = getResources().getString(R.string.error_carrito);
            Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
    }

    private void comprar(View v){
        CreditDialog cd = new CreditDialog();
        cd.show(getFragmentManager(),"credit");
    }

    @Override
    public void aceptarCredito() {
        DoHTTPRequest doHTTP = new DoHTTPRequest(this,this,-1);
        doHTTP.prepCdeleteAllcarrito(user);
        doHTTP.execute();
    }

    @Override
    public void cancelarCredito() {

    }
}
