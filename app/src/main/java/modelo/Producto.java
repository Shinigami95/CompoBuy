package modelo;

/**
 * Created by Jorge on 30/03/2017.
 */

public class Producto {

    public long idComponente;
    public String nombre;
    public String descripcion;
    public float precio;
    public String especificaciones;
    public String imagen;
    public long idCategoria;

    public Producto(long id,String nomb){
        idComponente = id;
        nombre = nomb;
    }

    public Producto(long id,String nomb,float prec){
        idComponente = id;
        nombre = nomb;
        precio = prec;
    }

    public Producto(long id,String nomb,String desc,float prec,String espec,String img,long idCat){
        idComponente = id;
        nombre = nomb;
        descripcion = desc;
        precio = prec;
        especificaciones = espec;
        imagen = img;
        idCategoria = idCat;
    }
}
