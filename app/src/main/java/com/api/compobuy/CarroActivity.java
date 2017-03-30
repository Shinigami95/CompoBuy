package com.api.compobuy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class CarroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        Bundle ext = getIntent().getExtras();
        if(ext != null){
            String user = ext.getString("username");
            TextView tvNombre = (TextView) findViewById(R.id.tv_carro_user);
            String texto = tvNombre.getText().toString();
            texto = texto.replace("$USER$",user);
            tvNombre.setText(texto);
        }
    }
}
