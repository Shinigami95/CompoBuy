package com.api.compobuy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import BD.MyBDManager;
import dialogs.ErrorLoginDialog;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button btRegistro=(Button)findViewById(R.id.bt_signup);
        Button btAcceder=(Button)findViewById(R.id.bt_sign_in);
        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrar(v);
            }
        });
        btAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(v);
            }
        });
    }

    private void registrar(View v){
        Intent registro=new Intent(this,RegisterActivity.class);
        startActivity(registro);
    }

    private void login(View v){
        EditText etUser=(EditText)findViewById(R.id.et_login_user);
        EditText etPass=(EditText)findViewById(R.id.et_login_pass);
        MyBDManager mDB= new MyBDManager();
        String user=etUser.getText().toString();
        String pass=etPass.getText().toString();
        user=mDB.login(user,pass);
        if (user.equals(null)){
            ErrorLoginDialog error=new ErrorLoginDialog();
            error.show(getFragmentManager(),"Error_Login");
        }
        else {
            Intent menuPrincipal=new Intent(this,MenuPrincipalActivity.class);
            menuPrincipal.putExtra("usuario",user);
            startActivity(menuPrincipal);
            finish();
        }
    }
}
