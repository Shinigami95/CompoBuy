package frags;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.api.compobuy.R;

public class ListaProductosFragment extends Fragment {
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

    public void actualizarFragment(){

    }

    public interface OnListaProductosFragmentInteractionListener {
        void onListaProductosFragmentInteractionListener(ListaProductosFragment fr);
    }

}
