package com.osorio.entidades;

import jakarta.persistence.*;
import java.io.Serializable;
@Entity
@Table(name = "persona")
public class Persona implements Serializable {
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
    @OneToOne(cascade = {CascadeType.ALL})
    @JoinColumn(name = "nacimiento_id", referencedColumnName = "id_nacimiento")
    private Nacimiento nacimiento;


    public Persona() {
    }

    public Persona(Long idPersona, String nombre, String telefono,
                   String profesion, int tipo, Nacimiento nacimiento) {
        this.idPersona = idPersona;
        this.nombre = nombre;
        this.telefono = telefono;
        this.profesion = profesion;
        this.tipo = tipo;
        this.nacimiento = nacimiento;
    }

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
    public Nacimiento getNacimiento() {
        return nacimiento;
    }
    public void setNacimiento(Nacimiento nacimiento) {
        this.nacimiento = nacimiento;
    }

    @Override
    public String toString() {
        return "Persona{" +
                "idPersona=" + idPersona +
                ", nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", profesion='" + profesion + '\'' +
                ", tipo=" + tipo +
                ", nacimiento=" + nacimiento +
                '}';
    }

}