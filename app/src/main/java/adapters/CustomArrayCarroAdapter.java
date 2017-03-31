package adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.api.compobuy.R;

import java.util.ArrayList;

import modelo.Producto;

/**
 * Created by Jorge on 30/03/2017.
 */

public class CustomArrayCarroAdapter extends BaseAdapter {

    private ArrayList<Producto> lp;
    private Context context;
    private LayoutInflater inflater;

    public CustomArrayCarroAdapter(Activity activity, ArrayList<Producto> lista) {
        lp = lista;
        context = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lp.size();
    }

    @Override
    public Object getItem(int position) {
        return lp.get(position);
    }

    @Override
    public long getItemId(int position) {
        return lp.get(position).idComponente;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView;
        rowView = inflater.inflate(context.getResources().getLayout(R.layout.lv_carro),null);

        TextView tvNombre = (TextView) rowView.findViewById(R.id.tv_nombre_producto);
        String nombreProducto = lp.get(position).nombre;
        tvNombre.setText(nombreProducto);

        TextView tvProducto = (TextView) rowView.findViewById(R.id.tv_precio_producto);
        String precio = lp.get(position).precio+"";
        tvNombre.setText(precio);
        return rowView;
    }
}
