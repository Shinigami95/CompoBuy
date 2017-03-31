package frags;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.api.compobuy.ProductoActivity;
import com.api.compobuy.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import BD.DoHTTPRequest;
import BD.ImageGetter;
import modelo.Producto;


public class ProductoFragment extends Fragment
        implements DoHTTPRequest.AsyncResponse, ImageGetter.AsyncImage{

    private OnProductoFragmentInteractionListener mListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //TODO muchoo
        View v =  inflater.inflate(R.layout.fragment_producto, container, false);
        Button butAddCarro = (Button) v.findViewById(R.id.bt_add_carro);
        butAddCarro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCarro(v);
            }
        });
        return v;
    }

    private void addCarro(View v){
        DoHTTPRequest doHTTP = new DoHTTPRequest(this,getActivity(),-1);
        doHTTP.prepCaddcarrito(username,idComp);
        doHTTP.execute();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof OnProductoFragmentInteractionListener) {
            mListener = (OnProductoFragmentInteractionListener) getActivity();
            mListener.onProductoFragmentInteractionListener(this);
        } else {
            throw new RuntimeException(getActivity().toString()
                    + " must implement OnInfoCentroFragmentInteractionListener");
        }
    }

    private String username;
    private long idComp;

    public void actualizarFragment(long idC, String user){
        username = user;
        idComp = idC;
        DoHTTPRequest doHTTP = new DoHTTPRequest(this,getActivity(),-1);
        doHTTP.prepCgetproducto(idC);
        doHTTP.execute();
    }

    @Override
    public void processFinish(String output, int mReqId) {
        try {
            if (mReqId == DoHTTPRequest.GET_PRODUCTO) {
                JSONObject jsonObj = new JSONObject(output);
                String nombre = jsonObj.getString("nombre");
                String descr = jsonObj.getString("descr");
                String spec = jsonObj.getString("spec");
                String pathImg = jsonObj.getString("imagepath");
                double precio = jsonObj.getDouble("precio");

                TextView tvNombre = (TextView) getView().findViewById(R.id.tv_nombre);
                tvNombre.setText(nombre);
                TextView tvDesc = (TextView) getView().findViewById(R.id.tv_descripcion_text);
                tvDesc.setText(descr);
                TextView tvSpec = (TextView) getView().findViewById(R.id.tv_especificaciones_text);
                tvSpec.setText(spec);
                TextView tvPrecio = (TextView) getView().findViewById(R.id.tv_precio_text);
                tvPrecio.setText(precio+"");

                ImageGetter imgGet = new ImageGetter(this,pathImg,0);
                imgGet.execute();
            } else if(mReqId == DoHTTPRequest.ADD_CARRITO){
                JSONObject jsonObj = new JSONObject(output);
                String status = jsonObj.getString("status");
                if(status.equals("ok")){
                    String msg = getResources().getString(R.string.add_carro_ok);
                    Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                } else {
                    String msg = getResources().getString(R.string.add_carro_error);;
                    Toast.makeText(getActivity().getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void imageLoadead(Bitmap bitmap, int mReqId) {
        ImageView i = (ImageView) getView().findViewById(R.id.iv_producto);
        i.setImageBitmap(bitmap);
        i.setClickable(true);
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog settingsDialog = new Dialog(getActivity());
                settingsDialog.setContentView(getActivity().getLayoutInflater().inflate(R.layout.dialog_image, null));
                ImageView imv = (ImageView) settingsDialog.findViewById(R.id.imview);
                ImageView compoImg = (ImageView) v;
                Bitmap bit = ((BitmapDrawable)compoImg.getDrawable()).getBitmap();
                imv.setImageBitmap(bit);
                imv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        settingsDialog.cancel();
                    }
                });
                settingsDialog.show();
            }
        });
    }

    public interface OnProductoFragmentInteractionListener {
        void onProductoFragmentInteractionListener(ProductoFragment fr);
    }

}
