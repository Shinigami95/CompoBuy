package dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.api.compobuy.R;

/**
 * Created by Jorge on 31/03/2017.
 */

public class CreditDialog extends DialogFragment {

    //interfaz que debera implementar la actividad/fragmento que utilice el dialogo
    public interface GestorCredito{
        public void aceptarCredito();
        public void cancelarCredito();
    }
    private View view;
    private GestorCredito gc;

    //indicar la interfaz del dialogo segun el layout dialog_login
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        gc = (GestorCredito) getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        view = inflater.inflate(getResources().getLayout(R.layout.dialog_credit_card),null);
        builder.setView(view);
        //boton positivo
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                EditText etTitular = (EditText) view.findViewById(R.id.et_cred_titular);
                EditText etNumero = (EditText) view.findViewById(R.id.et_cred_numero);
                EditText etFecha = (EditText) view.findViewById(R.id.et_cred_fecha_caduc);
                EditText etCVC2 = (EditText) view.findViewById(R.id.et_cred_cvc2);

                //TODO cobrar

                gc.aceptarCredito();
            }
        });
        //boton negativo
        builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                gc.cancelarCredito();
            }
        });
        return builder.create();
    }
}
