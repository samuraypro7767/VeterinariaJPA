package com.osorio.dao;
import com.osorio.aplicacion.JPAUtil;
import com.osorio.entidades.Mascota;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDao {
    EntityManager entityManager=JPAUtil.getEntityManagerFactory().createEntityManager();

    public String registrarMascota(Mascota miMascota){
        String respuesta="";
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(miMascota);
            entityManager.getTransaction().commit();

            respuesta= "Mascota Registrada;";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puede registrar" + "la mascota verifique que el dueño exista", "ERROR" ,JOptionPane.ERROR_MESSAGE );
        }
        return respuesta;
    }

    public Mascota consultarMascota(Long idMascota) {
        Mascota miMascota=entityManager.find(Mascota.class,idMascota);
        if (miMascota!=null) {
            return miMascota;
        }else {
            return null;
        }
    }

    public List<Mascota> consultarListaMascotas() {
        List<Mascota> listaMascotas=new ArrayList<Mascota>();
        Query query=entityManager.createQuery("SELECT m FROM Mascota m");
        listaMascotas=query.getResultList();
        return listaMascotas;
    }
    public List<Mascota> consultarListaMascotasPorSexo(String sexo) {
        List<Mascota> listaMascotas=new ArrayList<Mascota>();
        String sentencia="SELECT m FROM Mascota m WHERE m.sexo= :sexoMascota";
        Query query=entityManager.createQuery(sentencia);
        query.setParameter("sexoMascota", sexo);
        listaMascotas=query.getResultList();
        return listaMascotas;
    }

    public String actualizarMascota(Mascota miMascota) {
        String respuesta = "";
        try{
            entityManager.getTransaction().begin();
            entityManager.merge(miMascota);
            entityManager.getTransaction().commit();

            respuesta="Mascota Actualizada!";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puede actualizar" + "verifique que el dueño de la mascota exista", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        return respuesta;
    }


    public String eliminarMascota(Mascota miMascota) {
        entityManager.getTransaction().begin();
        entityManager.remove(miMascota);
        entityManager.getTransaction().commit();
        String resp="Mascota Eliminada!";
        return resp;
    }

    public void close() {
        entityManager.close();
        JPAUtil.shutdown();
    }
}

