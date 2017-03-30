package com.api.compobuy;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import BD.DoHTTPRequest;
import dialogs.DateDialog;

public class RegisterActivity extends AppCompatActivity implements DateDialog.GestorFecha, DoHTTPRequest.AsyncResponse {

    //se creara la actividad con el formulario del registro
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EditText etDate = (EditText) findViewById(R.id.et_date);
        etDate.setEnabled(false);
        Button butDate = (Button) findViewById(R.id.bt_date);
        //listener del boton de seleccion de fecha
        butDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setFecha(v);
            }
        });
        //listener del boton de registro
        Button butReg = (Button) findViewById(R.id.bt_reg);
        butReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(v);
            }
        });
    }

    //al pulsar registrar se registrara el usuario si los datos son correctos
    private void register(View v){
        //se coge la informacion de todos los edit text
        EditText etUser = (EditText) findViewById(R.id.et_user);
        EditText etPass = (EditText) findViewById(R.id.et_pass);
        EditText etPassRep = (EditText) findViewById(R.id.et_rep_pass);
        EditText etDate = (EditText) findViewById(R.id.et_date);
        EditText etCity = (EditText) findViewById(R.id.et_city);
        String user = etUser.getText().toString();
        String password = etPass.getText().toString();
        String repPass = etPassRep.getText().toString();
        String date = etDate.getText().toString();
        String city = etCity.getText().toString();
        //Si las contrasenas no coinciden -> mensaje de error
        if(!password.equals(repPass)){
            String err = getResources().getString(R.string.err_msg_reg_pass_incorrecta);
            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
        }
        //Si el campo nombre del usuario esta vacio -> mensaje de error
        else if (user.length()==0) {
            String err = getResources().getString(R.string.err_msg_reg_user_incorrecto);
            Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
        }
        //Si los datos son correctos
        else {
            DoHTTPRequest dHTTP = new DoHTTPRequest(this, this, -1);
            dHTTP.prepCregister(user,password,date,city);
            dHTTP.execute();
        }
    }

    private void setFecha(View v){
        DateDialog newFragment = new DateDialog();
        newFragment.show(getFragmentManager(), "date");
    }

    @Override
    public void administrarFecha(int year, int month, int dayOfMonth) {
        EditText etDate = (EditText) findViewById(R.id.et_date);
        etDate.setText(dayOfMonth+"-"+month+"-"+year);
    }

    @Override
    public void processFinish(String output, int mReqId) {
        try {
            if (mReqId == DoHTTPRequest.REGISTER) {
                JSONObject json = new JSONObject(output);

                String username = json.getString("nombre");

                //Si se ha introducido un usuario incorrecto (ya existe) -> mensajede error
                if (username == null) {
                    String err = getResources().getString(R.string.err_msg_reg_user);
                    Toast.makeText(getApplicationContext(), err, Toast.LENGTH_LONG).show();
                }
                //Realizar registro
                else {
                    String msg = getResources().getString(R.string.msg_reg_correcto);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    String notTitle = getResources().getString(R.string.not_reg_title);
                    //Mostrar notificacion de registro realizado
                    String notMsg = getResources().getString(R.string.not_reg_msg);
                    notMsg = notMsg.replace("$USER_NAME$", username);
                    NotificationCompat.Builder elconstructor =
                            new NotificationCompat.Builder(this)
                                    .setSmallIcon(android.R.drawable.stat_sys_warning)
                                    .setContentTitle(notTitle)
                                    .setContentText(notMsg)
                                    .setAutoCancel(true)
                                    .setTicker(notTitle);
                    NotificationManager elnotificationmanager = (NotificationManager)
                            getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
                    elnotificationmanager.notify(1, elconstructor.build());
                    //Ir a la activity de menu principal
                    Intent i = new Intent(this, MenuPrincipalActivity.class);
                    i.putExtra("username", username);
                    startActivity(i);
                    finish();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
