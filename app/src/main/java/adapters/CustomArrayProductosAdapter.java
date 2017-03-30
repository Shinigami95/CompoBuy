package adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import modelo.ListaProductos;

/**
 * Created by Jorge on 30/03/2017.
 */

public class CustomArrayProductosAdapter extends BaseAdapter {

    private ListaProductos lp;

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
        return null;
    }
}
