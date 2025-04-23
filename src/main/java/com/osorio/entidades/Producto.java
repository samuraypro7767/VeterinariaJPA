package com.osorio.entidades;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "productos")

public class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto")
    private Long idProducto;

    @Column(name = "nombre_producto", nullable = false, length = 45)
    private String nombreProducto;

    @Column(name = "precio_producto")
    private Double precioProducto;

    @ManyToMany(mappedBy = "listaProductos")
    private  List<com.osorio.entidades.Persona> listaPersonas;

    //Se crea el constructor vacío
    public Producto(){

    }

    //Se crea el constructor con parametros
    public Producto(Long idProducto, String nombreProducto, Double precioProducto, List<com.osorio.entidades.Persona> listaPersonas) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.precioProducto = precioProducto;
        this.listaPersonas = listaPersonas;
    }

    //Getters y setters

    public Long getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Long idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Double getPrecioProducto() {
        return precioProducto;
    }

    public void setPrecioProducto(Double precioProducto) {
        this.precioProducto = precioProducto;
    }

    public List<com.osorio.entidades.Persona> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(List<com.osorio.entidades.Persona> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    @Override
    public String toString() {
        return "Producto [idProducto=" + idProducto + ","
                + " nombreProducto=" + nombreProducto + ","
                + " precioProducto="+ precioProducto + "]";
    }
}
