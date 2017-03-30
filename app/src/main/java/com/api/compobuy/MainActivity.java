package com.api.compobuy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import BD.DoHTTPRequest;
import dialogs.ErrorLoginDialog;

public class MainActivity extends AppCompatActivity implements DoHTTPRequest.AsyncResponse{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = getSharedPreferences("gcu_preferences", Context.MODE_PRIVATE);
        if(prefs.contains("user_actual")){
            String userName = prefs.getString("user_actual",null);
            if(userName!=null) {
                //si existe el campo user_actual en preferencias y no es null, ir a la actividad del menu principal
                Intent i = new Intent(this, MenuPrincipalActivity.class);
                i.putExtra("username", userName);
                startActivity(i);
                finish();
            }
        }
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
        String user=etUser.getText().toString();
        String pass=etPass.getText().toString();
        DoHTTPRequest dHTTP = new DoHTTPRequest(this,this,-1);
        dHTTP.prepClogin(user,pass);
        dHTTP.execute();
    }

    @Override
    public void processFinish(String output, int mReqId) {
        try {
            JSONObject json = new JSONObject(output);
            try {
                String user = json.getString("nombre");
                Intent menuPrincipal = new Intent(this, MenuPrincipalActivity.class);
                menuPrincipal.putExtra("username", user);
                startActivity(menuPrincipal);
                finish();
            } catch(JSONException e){
                ErrorLoginDialog error = new ErrorLoginDialog();
                error.show(getFragmentManager(), "Error_Login");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
