package clases;

import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import com.osorio.dao.ProductoDao;
import com.osorio.entidades.Persona;
import com.osorio.entidades.Producto;

public class GestionProductos {
    private ProductoDao productoDao = new ProductoDao();

    public GestionProductos() {
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        String menu = "MENÚ DE OPCIONES - GESTIÓN PRODUCTOS\n\n";
        menu += "1. Registrar Producto\n";
        menu += "2. Consultar Producto\n";
        menu += "3. Consultar Lista de Productos\n";
        menu += "4. Consultar Personas por Producto\n";
        menu += "5. Comprar Producto\n";
        menu += "6. Actualizar Producto\n";
        menu += "7. Eliminar Producto\n";
        menu += "8. Salir\n";

        int opcion = 0;
        while (opcion != 8) {
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcion) {
                    case 1: registrarProducto(); break;
                    case 2: consultarProducto(); break;
                    case 3: consultarListaProductos(); break;
                    case 4: consultarPersonasPorProducto(); break;
                    case 5: comprarProducto(); break;
                    case 6: actualizarProducto(); break;
                    case 7: eliminarProducto(); break;
                    case 8:
                        productoDao.close();
                        JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void registrarProducto() {
        try {
            Producto producto = new Producto();
            producto.setNombreProducto(JOptionPane.showInputDialog("Ingresa el nombre del producto"));
            producto.setPrecioProducto(Double.parseDouble(
                    JOptionPane.showInputDialog("Ingresa el precio del producto")));

            String resultado = productoDao.registrarProducto(producto);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El precio debe ser un número válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar producto: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarProducto() {
        try {
            Long idProducto = Long.parseLong(JOptionPane.showInputDialog("Ingresa el ID del producto"));
            Producto producto = productoDao.consultarProducto(idProducto);

            if (producto != null) {
                JOptionPane.showMessageDialog(null, producto.toString(), "Información del Producto",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el producto con ID: " + idProducto,
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarListaProductos() {
        List<Producto> productos = productoDao.consultarListaProductos();
        StringBuilder lista = new StringBuilder("Lista de Productos:\n\n");

        for (Producto producto : productos) {
            lista.append(producto.toString()).append("\n\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString(), "Lista de Productos", JOptionPane.INFORMATION_MESSAGE);
    }

    private void actualizarProducto() {
        try {
            Long idProducto = Long.parseLong(JOptionPane.showInputDialog("Ingresa el ID del producto a actualizar"));
            Producto producto = productoDao.consultarProducto(idProducto);

            if (producto != null) {
                producto.setNombreProducto(JOptionPane.showInputDialog("Nuevo nombre:", producto.getNombreProducto()));
                producto.setPrecioProducto(Double.parseDouble(
                        JOptionPane.showInputDialog("Nuevo precio:", producto.getPrecioProducto())));

                String resultado = productoDao.actualizarProducto(producto);
                JOptionPane.showMessageDialog(null, resultado);
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        try {
            Long idProducto = Long.parseLong(JOptionPane.showInputDialog("Ingresa el ID del producto a eliminar"));
            Producto producto = productoDao.consultarProducto(idProducto);

            if (producto != null) {
                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de eliminar el producto " + producto.getNombreProducto() + "?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    String resultado = productoDao.eliminarProducto(producto);
                    JOptionPane.showMessageDialog(null, resultado);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void comprarProducto() {
        try {
            Long personaId = Long.parseLong(JOptionPane.showInputDialog("Ingrese el documento de la persona"));
            Long productoId = Long.parseLong(JOptionPane.showInputDialog("Ingrese el código del producto"));
            int cantidad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese la cantidad"));
            LocalDate fechaCompra = LocalDate.now();

            String resultado = productoDao.registrarCompra(personaId, productoId, cantidad, fechaCompra);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar valores numéricos válidos", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar compra: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarPersonasPorProducto() {
        try {
            Long productoId = Long.parseLong(JOptionPane.showInputDialog("Ingresa el ID del producto"));
            List<Persona> personas = productoDao.obtenerPersonasPorProducto(productoId);

            if (personas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron personas con este producto");
            } else {
                StringBuilder info = new StringBuilder("Personas con este producto:\n\n");
                for (Persona persona : personas) {
                    info.append(persona.toString()).append("\n\n");
                }
                JOptionPane.showMessageDialog(null, info.toString());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}