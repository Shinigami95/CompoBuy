package dialogs;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TimePicker;

/**
 * Created by Jorge on 04/03/2017.
 */

public class DateDialog extends DialogFragment implements DatePickerDialog.OnDateSetListener{

    //interfaz que debera implementar la actividad/fragmento que utilice el dialogo
    public interface GestorFecha{
        public void administrarFecha(int year, int month, int dayOfMonth);
    }

    GestorFecha gf;

    //indicar la interfaz del dialogo
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        super.onCreateDialog(savedInstanceState);
        gf = (GestorFecha) getActivity();
        //fecha por defecto
        int year = 1990;
        int month = 1;
        int day = 1;
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        gf.administrarFecha(year, month, dayOfMonth);
    }
}
