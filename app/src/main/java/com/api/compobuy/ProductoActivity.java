package com.api.compobuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import BD.DoHTTPRequest;
import frags.ProductoFragment;

public class ProductoActivity extends AppCompatActivity implements ProductoFragment.OnProductoFragmentInteractionListener{

    private long idC;
    private String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto);
        Bundle ext = getIntent().getExtras();
        if(ext != null){
            idC = ext.getLong("idcomp");
            username = ext.getString("username");
        }
    }


    @Override
    public void onProductoFragmentInteractionListener(ProductoFragment fr) {
        fr.actualizarFragment(idC,username);
    }
}
