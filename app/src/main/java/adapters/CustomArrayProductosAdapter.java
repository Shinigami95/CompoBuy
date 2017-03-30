package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.api.compobuy.R;

import modelo.ListaProductos;

/**
 * Created by Jorge on 30/03/2017.
 */

public class CustomArrayProductosAdapter extends BaseAdapter {

    private ListaProductos lp;
    private Context context;
    private LayoutInflater inflater;

    public CustomArrayProductosAdapter(Activity activity, ListaProductos lista) {
        lp = lista;
        context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lp.lista.size();
    }

    @Override
    public Object getItem(int position) {
        return lp.lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lp.lista.get(position).idComponente;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(context.getResources().getLayout(R.layout.lv_productos),null);

        TextView tvNombre = (TextView) rowView.findViewById(R.id.tv_nombre_producto);
        String nombreProducto = lp.lista.get(position).nombre;
        tvNombre.setText(nombreProducto);
        return rowView;
    }
}
