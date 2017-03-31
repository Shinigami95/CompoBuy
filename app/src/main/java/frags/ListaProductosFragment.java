package frags;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO muchoo
        return inflater.inflate(R.layout.fragment_lista_productos, container, false);
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

    ArrayAdapter<String> aaCategorias;
    ArrayList<String> listaCat;
    CustomArrayProductosAdapter aaProductos;
    ArrayList<Producto> listaProd;

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

            } else if (mReqId == DoHTTPRequest.GET_ALL_PRODUCT) {
                listaProd=new ArrayList<Producto>();
                for(int i=0; i<jsonArray.length(); i++) {
                    jsonObj = jsonArray.getJSONObject(i);
                    listaProd.add(new Producto(jsonObj.getInt("idcomp"),jsonObj.getString("nombre")));
                }
                aaProductos = new CustomArrayProductosAdapter(getActivity(),listaProd);
                ListView lv = (ListView)getView().findViewById(R.id.lv_productos);
                lv.setAdapter(aaProductos);
            } else if (mReqId == DoHTTPRequest.GET_CAT_PRODUCTO) {

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public interface OnListaProductosFragmentInteractionListener {
        void onListaProductosFragmentInteractionListener(ListaProductosFragment fr);
    }

}
