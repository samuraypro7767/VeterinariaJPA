package com.osorio.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

import jakarta.persistence.EntityManager;
import com.osorio.aplicacion.JPAUtil;
import com.osorio.entidades.Persona;
import com.osorio.entidades.PersonasProductos;
import com.osorio.entidades.Producto;
import jakarta.persistence.Query;

public class ProductoDao {
    EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();

    public String registrarProducto(Producto miProducto) {
        String respuesta = "";
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(miProducto);
            entityManager.getTransaction().commit();
            respuesta = "Producto Registrado";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puede registrar el producto", "ERROR", JOptionPane.ERROR_MESSAGE);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return respuesta;
    }

    public Producto consultarProducto(Long idProducto) {
        Producto miProducto = entityManager.find(Producto.class, idProducto);
        return miProducto;
    }

    public List<Producto> consultarListaProductos() {
        List<Producto> listaProductos = new ArrayList<>();
        Query query = entityManager.createQuery("SELECT p FROM Producto p");
        listaProductos = query.getResultList();
        return listaProductos;
    }

    public String actualizarProducto(Producto miProducto) {
        String respuesta = "";
        try {
            entityManager.getTransaction().begin();
            entityManager.merge(miProducto);
            entityManager.getTransaction().commit();
            respuesta = "Producto Actualizado!";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puede actualizar el producto", "ERROR", JOptionPane.ERROR_MESSAGE);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return respuesta;
    }

    public String eliminarProducto(Producto miProducto) {
        String respuesta = "";
        try {
            entityManager.getTransaction().begin();
            // Make sure the entity is managed before removing
            if (!entityManager.contains(miProducto)) {
                miProducto = entityManager.merge(miProducto);
            }
            entityManager.remove(miProducto);
            entityManager.getTransaction().commit();
            respuesta = "Producto Eliminado!";
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se puede eliminar el producto", "ERROR", JOptionPane.ERROR_MESSAGE);
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
        }
        return respuesta;
    }

    public String registrarCompra(Long personaId, Long productoId, int cantidad, LocalDate fechaCompra) {
        String respuesta = "";
        try {
            entityManager.getTransaction().begin();

            Persona persona = entityManager.find(Persona.class, personaId);
            Producto producto = entityManager.find(Producto.class, productoId);

            if (persona == null || producto == null) {
                throw new Exception("Persona o producto no encontrados.");
            }

            // Crear una nueva instancia de PersonasProductos
            PersonasProductos compra = new PersonasProductos();
            compra.setPersonaId(personaId);
            compra.setProductoId(productoId);
            compra.setCantidad(cantidad);
            compra.setFechaCompra(java.sql.Date.valueOf(fechaCompra));

            // Persistir la compra en la base de datos
            entityManager.persist(compra);

            // Actualizar la relación many-to-many
            persona.getListaProductos().add(producto);

            entityManager.getTransaction().commit();
            respuesta = "Se realizó la compra del producto!";
        } catch (Exception e) {
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            JOptionPane.showMessageDialog(null, "No se puede registrar la compra del producto: " + e.getMessage(),
                    "ERROR", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return respuesta;
    }

    public List<Persona> obtenerPersonasPorProducto(Long productoId) {
        String jpql = "SELECT p FROM Persona p JOIN p.listaProductos prod WHERE prod.id = :productoId";
        List<Persona> listaPersonas = entityManager.createQuery(jpql, Persona.class)
                .setParameter("productoId", productoId)
                .getResultList();
        return listaPersonas;
    }

    public void close() {
        entityManager.close();
        JPAUtil.shutdown();
    }
}