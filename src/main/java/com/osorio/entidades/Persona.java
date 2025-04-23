package com.osorio.entidades;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "persona")
public class Persona  implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id_persona")
    private Long idPersona;

    @Column(name = "nombre_persona")
    private String nombre;

    @Column(name = "telefono_persona")
    private String telefono;

    @Column(name = "profesion_persona")
    private String profesion;

    @Column(name = "tipo_persona")
    private int tipo;

    //Se crea el constructor vacio
    public Persona(){
        //PDF4
        this.listaMascotas = new ArrayList<com.osorio.entidades.Mascota>();
    }

    //Se crea el constructor con parametros
    public Persona(Long idPersona, String nombre, String telefono, String profesion, int tipo, com.osorio.entidades.Nacimiento nacimiento) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.telefono = telefono;
        this.profesion = profesion;
        this.tipo = tipo;
        this.nacimiento = nacimiento;
        this.listaMascotas= new ArrayList<com.osorio.entidades.Mascota>();//PDF4
    }

    //Se crea los getters y setters

    public Long getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(Long idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getProfesion() {
        return profesion;
    }

    public void setProfesion(String profesion) {
        this.profesion = profesion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public com.osorio.entidades.Nacimiento getNacimiento() {
        return nacimiento;
    }

    public void setNacimiento(com.osorio.entidades.Nacimiento nacimiento) {
        this.nacimiento = nacimiento;
    }

    //PDF4
    public List<com.osorio.entidades.Mascota> getListaMascotas() {
        return listaMascotas;
    }

    public void setListaMascotas(List<com.osorio.entidades.Mascota> listaMascotas) {
        this.listaMascotas = listaMascotas;
    }

    public List<Producto> getListaProductos() {
        return listaProductos;
    }

    public void setListaProductos(List<Producto> listaProductos) {
        this.listaProductos = listaProductos;
    }

    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "nacimiento_id", referencedColumnName = "id_nacimiento")
    private com.osorio.entidades.Nacimiento nacimiento;

    //PDF4
    @OneToMany(mappedBy = "dueno", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<com.osorio.entidades.Mascota> listaMascotas;

    @ManyToMany
    @JoinTable(name = "personas_productos",
            joinColumns = @JoinColumn(name = "persona_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> listaProductos;


    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", profesion='" + profesion + '\'' +
                ", tipo=" + tipo +
                ", nacimiento=" + nacimiento +
                ", listaMascotas=" + listaMascotas +
                ", listaProductos=" + listaProductos +
                '}';
    }
}
