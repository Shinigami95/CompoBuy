package dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import com.api.compobuy.R;

/**
 * Created by shind on 28/03/2017.
 */

public class ErrorLoginDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.error_login);
        builder.setMessage(R.string.error_login_text);
        builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        return super.onCreateDialog(savedInstanceState);
    }
}
