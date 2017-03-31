package frags;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.api.compobuy.ProductoActivity;
import com.api.compobuy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import BD.DoHTTPRequest;
import adapters.CustomArrayProductosAdapter;
import modelo.Producto;

public class ListaProductosFragment extends Fragment
                                    implements DoHTTPRequest.AsyncResponse{
    private OnListaProductosFragmentInteractionListener mListener;
    private ArrayAdapter<String> aaCategorias;
    private ArrayList<String> listaCat;
    private CustomArrayProductosAdapter aaProductos;
    private ArrayList<Producto> listaProd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_lista_productos, container, false);
        Spinner spin = (Spinner) v.findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cambiarCategoria(parent, view, position, id);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        ListView lvProd = (ListView) v.findViewById(R.id.lv_productos);
        lvProd.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                verProducto(parent, view, position, id);
            }
        });
        return v;
    }

    public void verProducto(AdapterView<?> parent, View view, int position, long id) {
        Producto prod = listaProd.get(position);
        long idC = prod.idComponente;
        mListener.onListaProductosFragmentSeleccionarComponente(this, idC);

    }

    public void cambiarCategoria(AdapterView<?> parent, View view, int position, long id) {
        String cat = listaCat.get(position);
        DoHTTPRequest doHTTP = new DoHTTPRequest(this, getActivity(), -1);
        if(cat.equals(getResources().getString(R.string.all))) {
            doHTTP.prepCgetallproduct();
        } else {
            doHTTP.prepCgetcatproduct(cat);
        }
        doHTTP.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof OnListaProductosFragmentInteractionListener) {
            mListener = (OnListaProductosFragmentInteractionListener) getActivity();
            mListener.onListaProductosFragmentInteractionListener(this);
        } else {
            throw new RuntimeException(getActivity().toString()
                    + " must implement OnInfoCentroFragmentInteractionListener");
        }
    }

    public void actualizarFragment(String usuario){
        TextView tvBienv = (TextView) getView().findViewById(R.id.tv_bienvenido_user);
        String text = tvBienv.getText().toString();
        text = text.replace("$USER_NAME$",usuario);
        tvBienv.setText(text);
        DoHTTPRequest doHTTP = new DoHTTPRequest(this,getActivity(),-1);
        doHTTP.prepCgetallcategoria();
        doHTTP.execute();

        doHTTP = new DoHTTPRequest(this,getActivity(),-1);
        doHTTP.prepCgetallproduct();
        doHTTP.execute();

    }

    @Override
    public void processFinish(String output, int mReqId) {
        try {
            JSONArray jsonArray = new JSONArray(output);
            JSONObject jsonObj;
            if (mReqId == DoHTTPRequest.GET_ALL_CATEGORIA) {
                listaCat = new ArrayList<String>();
                listaCat.add(getResources().getString(R.string.all));
                for(int i=0; i<jsonArray.length(); i++){
                    jsonObj = jsonArray.getJSONObject(i);
                    listaCat.add(jsonObj.getString("nombre"));
                }
                Spinner spin = (Spinner) getView().findViewById(R.id.spinner);
                aaCategorias = new ArrayAdapter<String>(getActivity(),R.layout.spin_categoria,listaCat);
                spin.setAdapter(aaCategorias);

            } else if (mReqId == DoHTTPRequest.GET_ALL_PRODUCT || mReqId == DoHTTPRequest.GET_CAT_PRODUCTO) {
                listaProd=new ArrayList<Producto>();
                for(int i=0; i<jsonArray.length(); i++) {
                    jsonObj = jsonArray.getJSONObject(i);
                    listaProd.add(new Producto(jsonObj.getLong("idcomp"),jsonObj.getString("nombre")));
                }
                aaProductos = new CustomArrayProductosAdapter(getActivity(),listaProd);
                ListView lv = (ListView)getView().findViewById(R.id.lv_productos);
                lv.setAdapter(aaProductos);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface OnListaProductosFragmentInteractionListener {
        void onListaProductosFragmentInteractionListener(ListaProductosFragment fr);
        void onListaProductosFragmentSeleccionarComponente(ListaProductosFragment fr, long idC);
    }

}
