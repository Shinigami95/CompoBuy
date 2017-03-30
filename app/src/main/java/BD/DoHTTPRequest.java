package BD;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.ProgressBar;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Locale;

public class DoHTTPRequest extends AsyncTask<String, Void, String> {

    public interface AsyncResponse {
        void processFinish(String output, int mReqId);
    }
    public AsyncResponse delegate=null;

    private Context mContext;
    private int mReqId;
    private String param = "";
    private ProgressBar mProgressBar = null;
    private int mProgressBarId;
    private HttpURLConnection urlConnection = null;
    private String errorMessage = "";

    public static final int LOG_IN = 100;
    public static final int REGISTER = 101;
    public static final int GET_PRODUCTO = 102;
    public static final int GET_CAT_PRODUCTO = 103;
    public static final int GET_ALL_PRODUCT = 104;
    public static final int GET_ALL_CATEGORIA = 105;
    public static final int GET_CARRITO_USU = 106;
    public static final int DELETE_CARRITO = 107;
    public static final int ADD_CARRITO = 108;



    public DoHTTPRequest(AsyncResponse deleg, Context context, final int reqId, int progressBarId, String [] datos) {

        delegate = deleg;
        mContext = context;
        mReqId = reqId;
        mProgressBarId = progressBarId;
        errorMessage = "";

    }

    public void prepClogin(String nombre, String pass){
        try {
            mReqId = LOG_IN;
            param = "func=login";
            param += "&nombre=" + URLEncoder.encode(nombre, "UTF-8");
            param += "&pass=" + URLEncoder.encode(pass, "UTF-8");
        } catch(UnsupportedEncodingException e){

        }
    }

    public void prepCregister(String nombre, String pass, String fechanacimiento, String ciudad){
        try {
            mReqId = REGISTER;
            param = "func=register";
            param += "&nombre=" + URLEncoder.encode(nombre, "UTF-8");
            param += "&pass=" + URLEncoder.encode(pass, "UTF-8");
            param += "&fechanacimiento=" + URLEncoder.encode(fechanacimiento, "UTF-8");
            param += "&ciudad=" + URLEncoder.encode(ciudad, "UTF-8");
        } catch(UnsupportedEncodingException e){

        }
    }

    public void prepCgetproducto(String idcomp){
        try {
            mReqId = GET_PRODUCTO;
            param = "func=getproducto";
            param += "&idcomp=" + URLEncoder.encode(idcomp, "UTF-8");
        } catch(UnsupportedEncodingException e){

        }
    }

    public void prepCgetcatproduct(String nombreCategoria){
        try {
            mReqId = GET_CAT_PRODUCTO;
            param = "func=getcatproduct";
            param += "&nombre=" + URLEncoder.encode(nombreCategoria, "UTF-8");
        } catch(UnsupportedEncodingException e){

        }
    }

    public void prepCgetallproduct(){
        mReqId = GET_ALL_PRODUCT;
        param = "func=getallproduct";
    }

    public void prepCgetallcategoria(){
        mReqId = GET_ALL_CATEGORIA;
        param = "func=getallcategoria";
    }

    public void prepCgetcarritousu(String username){
        try {
            mReqId = GET_CARRITO_USU;
            param = "func=login";
            param += "&nombre=" + URLEncoder.encode(username, "UTF-8");
        } catch(UnsupportedEncodingException e){

        }
    }

    public void prepCdeletecarrito(String username, String idCompra){
        try {
            mReqId = DELETE_CARRITO;
            param = "func=deletecarrito";
            param += "&nombre=" + URLEncoder.encode(username, "UTF-8");
            param += "&id=" + URLEncoder.encode(idCompra, "UTF-8");
        } catch(UnsupportedEncodingException e){

        }
    }

    public void prepCaddcarrito(String username, String idComponente){
        try {
            mReqId = ADD_CARRITO;
            param = "func=addcarrito";
            param += "&nombre=" + URLEncoder.encode(username, "UTF-8");
            param += "&idcomp=" + URLEncoder.encode(idComponente, "UTF-8");
        } catch(UnsupportedEncodingException e){

        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (mProgressBarId != -1) {
            mProgressBar = (ProgressBar) ((Activity) mContext).findViewById(mProgressBarId);
            mProgressBar.setVisibility(ProgressBar.VISIBLE);
        }
    }

    @Override
    protected String doInBackground(String... params) {

        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (!(netInfo != null && netInfo.isConnected())) {
            errorMessage = "No Internet Connection";
            return errorMessage;
        }

        String targetURLstr = "http://galan.ehu.eus/malboniga002/WEB/indexcompobuy.php";
        InputStream inputStream;
        try {
            URL targetURL = new URL(targetURLstr);
            urlConnection = (HttpURLConnection) targetURL.openConnection();
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            urlConnection.setRequestProperty("Accept", "application/json");
            urlConnection.setRequestProperty("Accept-Language", Locale.getDefault().getLanguage() + "-" + Locale.getDefault().getCountry());
            urlConnection.setConnectTimeout(5000);
            urlConnection.setReadTimeout(5000);
            urlConnection.setDoOutput(true);

            PrintWriter out = new PrintWriter(urlConnection.getOutputStream());
            out.print(param);
            out.close();

            int statusCode = urlConnection.getResponseCode();

            if (statusCode == 200) {
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line;
                String result = "";
                while((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                inputStream.close();
                String response = result;
                return response;
            }
            else{
                errorMessage = "Error al conectar con el servidor";
                urlConnection.disconnect();
                return errorMessage;
            }
        } catch (Exception e) {
            errorMessage = "Error al conectar a Internet";
            return errorMessage;
        } finally {
            if(urlConnection != null)
                urlConnection.disconnect();
        }
    }

    @Override
    protected void onPostExecute(final String result) {
        delegate.processFinish(result, mReqId);
        if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
    }

    @Override
    protected void onCancelled() {
        if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
    }

}