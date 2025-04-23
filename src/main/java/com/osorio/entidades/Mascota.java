package com.osorio.entidades;
import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="mascotas") //Se indica ya que el nombre de la clase es diferente al nombre de la tabla
public class Mascota implements Serializable {

    private static final long serialVersionUID =1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //eL Id de la mascota es auto incremetntable
    @Column(name = "id_mascota") //Indicamos el nombre del campo al que la variable hace referencia ya que son diferente
    private Long idMascota;

    @Column(nullable = false, length = 45) //El nombre no puede ser null en BD, así como el tamaño de varchar 45
    private String nombre;

    @Column(length = 45) //Solo indicamos el tamaño ya que corresponde la variable al nombre del campo en la tabla
    private String raza;

    @Column(name = "color", length = 45) //Se indica el nombre de campo al que la variable have referencia ya que son diferentes
    private String colorMascota;

    @Column(length = 45)
    private String sexo;

    //Se crea el constructor vacio
    public Mascota(){

    }

    //Se crea el constructor con parametros sin el id
    public Mascota(String nombre, String raza, String colorMascota, String sexo, Persona dueno) {
        this.nombre = nombre;
        this.raza = raza;
        this.colorMascota = colorMascota;
        this.sexo = sexo;
        this.dueno = dueno;
    }

    //Se crean los getters y setters
    public Long getIdMascota() {
        return idMascota;
    }
    public void setIdMascota(Long idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getColorMascota() {
        return colorMascota;
    }

    public void setColorMascota(String colorMascota) {
        this.colorMascota = colorMascota;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    //PDF4
    public Persona getDueno() {
        return dueno;
    }

    public void setDueno(Persona dueno) {
        this.dueno = dueno;
    }

    //PDF4
    @ManyToOne
    @JoinColumn(name = "persona_id", referencedColumnName = "id_persona")
    private Persona dueno;

    @Override
    public String toString() {
        return "Mascota [idMascota=" + idMascota + ", nombre=" + nombre + ","
                + " raza=" + raza + ", colorMascota="
                + colorMascota + ", sexo=" + sexo + "]"
                + " Dueño: "+dueno;

    }
}