package clases;

import com.osorio.dao.MascotaDao;
import com.osorio.dao.PersonaDao;
import com.osorio.entidades.*;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class GestionPersonas {
    private PersonaDao miPersonaDao = new PersonaDao();
    private MascotaDao miMascotaDao = new MascotaDao(); // Agregado para manejar mascotas

    public GestionPersonas() {
        mostrarMenuPrincipal();
    }

    private void mostrarMenuPrincipal() {
        String menu = "MENÚ DE OPCIONES - GESTIÓN PERSONAS\n\n";
        menu += "1. Registrar Persona\n";
        menu += "2. Consultar Persona\n";
        menu += "3. Consultar Lista de Personas\n";
        menu += "4. Consultar Productos por Persona\n";
        menu += "5. Actualizar Persona\n";
        menu += "6. Eliminar Persona\n";
        menu += "7. Salir\n";

        int opcion = 0;
        do {
            try {
                opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

                switch (opcion) {
                    case 1:
                        registrarPersona();
                        break;
                    case 2:
                        consultarPersona();
                        break;
                    case 3:
                        consultarListaPersonas();
                        break;
                    case 4:
                        consultarProductosPorPersona();
                        break;
                    case 5:
                        actualizarPersona();
                        break;
                    case 6:
                        eliminarPersona();
                        break;
                    case 7:
                        miPersonaDao.close();
                        miMascotaDao.close(); // Cerrar también DAO de mascotas
                        JOptionPane.showMessageDialog(null, "Saliendo del sistema...");
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Opción no válida", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Debe ingresar un número válido", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } while (opcion != 7);
    }

    private Mascota obtenerDatosMascota(Persona duenio) {
        Mascota mascota = new Mascota();
        mascota.setNombre(JOptionPane.showInputDialog("Ingresa el nombre de la mascota"));
        mascota.setRaza(JOptionPane.showInputDialog("Ingresa la raza de la mascota"));
        mascota.setColorMascota(JOptionPane.showInputDialog("Ingresa el color de la mascota"));
        mascota.setSexo(JOptionPane.showInputDialog("Ingresa el sexo de tu mascota"));
        mascota.setDueno(duenio);
        return mascota;
    }

    private void registrarPersona() {
        Persona persona = new Persona();

        try {
            persona.setIdPersona(Long.parseLong(JOptionPane.showInputDialog("Ingresa el documento de la persona")));
            persona.setNombre(JOptionPane.showInputDialog("Ingresa el nombre de la persona"));
            persona.setTelefono(JOptionPane.showInputDialog("Ingresa el teléfono de la persona"));
            persona.setProfesion(JOptionPane.showInputDialog("Ingresa la profesión de la persona"));
            persona.setTipo(Integer.parseInt(JOptionPane.showInputDialog("Ingresa el tipo (1, 2, 3...)")));
            persona.setNacimiento(obtenerDatosNacimiento());

            // Manejo de mascotas
            int opcionMascota;
            do {
                opcionMascota = Integer.parseInt(JOptionPane.showInputDialog(
                        "¿Deseas registrar una mascota para esta persona?\n1. Sí\n2. No"));

                if (opcionMascota == 1) {
                    Mascota mascota = obtenerDatosMascota(persona);
                    persona.getListaMascotas().add(mascota);
                    miMascotaDao.registrarMascota(mascota); // Persistir la mascota
                }
            } while (opcionMascota != 2);

            String resultado = miPersonaDao.registrarPersona(persona);
            JOptionPane.showMessageDialog(null, resultado);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar persona: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Nacimiento obtenerDatosNacimiento() {
        Nacimiento nacimiento = new Nacimiento();
        try {
            int dia = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el DÍA de nacimiento"));
            int mes = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el MES de nacimiento"));
            int ano = Integer.parseInt(JOptionPane.showInputDialog("Ingresa el AÑO de nacimiento"));

            nacimiento.setFechaNacimiento(LocalDate.of(ano, mes, dia));
            nacimiento.setCiudadNacimiento(JOptionPane.showInputDialog("Ingresa la ciudad de nacimiento"));
            nacimiento.setDepartamentoNacimiento(JOptionPane.showInputDialog("Ingresa el departamento de nacimiento"));
            nacimiento.setPaisNacimiento(JOptionPane.showInputDialog("Ingresa el país de nacimiento"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en datos de nacimiento: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return nacimiento;
    }

    private void consultarPersona() {
        try {
            Long idPersona = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID de la Persona"));
            Persona persona = miPersonaDao.consultarPersona(idPersona);

            if (persona != null) {
                StringBuilder info = new StringBuilder("Información de la persona:\n");
                info.append(persona.toString()).append("\n");

                if (!persona.getListaMascotas().isEmpty()) {
                    info.append("\nMascotas:\n");
                    for (Mascota mascota : persona.getListaMascotas()) {
                        info.append(mascota.toString()).append("\n");
                    }
                }

                JOptionPane.showMessageDialog(null, info.toString());
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la persona con ID: " + idPersona,
                        "Información", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarListaPersonas() {
        List<Persona> personas = miPersonaDao.consultarListaPersonas();
        StringBuilder lista = new StringBuilder("Lista de Personas:\n\n");

        for (Persona persona : personas) {
            lista.append(persona.toString()).append("\n\n");
        }

        JOptionPane.showMessageDialog(null, lista.toString());
    }

    private void actualizarPersona() {
        try {
            Long idPersona = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID de la Persona a actualizar"));
            Persona persona = miPersonaDao.consultarPersona(idPersona);

            if (persona != null) {
                persona.setNombre(JOptionPane.showInputDialog("Nuevo nombre:", persona.getNombre()));
                persona.setTelefono(JOptionPane.showInputDialog("Nuevo teléfono:", persona.getTelefono()));
                persona.setProfesion(JOptionPane.showInputDialog("Nueva profesión:", persona.getProfesion()));

                // Manejo de mascotas
                int opcionMascota;
                do {
                    opcionMascota = Integer.parseInt(JOptionPane.showInputDialog(
                            "¿Deseas agregar una mascota?\n1. Sí\n2. No"));

                    if (opcionMascota == 1) {
                        Mascota mascota = obtenerDatosMascota(persona);
                        persona.getListaMascotas().add(mascota);
                        miMascotaDao.registrarMascota(mascota);
                    }
                } while (opcionMascota != 2);

                String resultado = miPersonaDao.actualizarPersona(persona);
                JOptionPane.showMessageDialog(null, resultado);
            } else {
                JOptionPane.showMessageDialog(null, "Persona no encontrada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al actualizar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPersona() {
        try {
            Long idPersona = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID de la Persona a eliminar"));
            Persona persona = miPersonaDao.consultarPersona(idPersona);

            if (persona != null) {
                int confirmacion = JOptionPane.showConfirmDialog(null,
                        "¿Está seguro de eliminar a " + persona.getNombre() + "?",
                        "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

                if (confirmacion == JOptionPane.YES_OPTION) {
                    // Primero eliminar las mascotas asociadas
                    for (Mascota mascota : persona.getListaMascotas()) {
                        miMascotaDao.eliminarMascota(mascota);
                    }

                    String resultado = miPersonaDao.eliminarPersona(persona);
                    JOptionPane.showMessageDialog(null, resultado);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Persona no encontrada");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void consultarProductosPorPersona() {
        try {
            Long personaId = Long.parseLong(JOptionPane.showInputDialog("Ingrese el ID de la persona"));
            List<Producto> compras = miPersonaDao.obtenerProductosPorPersona(personaId);

            if (compras.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron compras para esta persona");
            } else {
                StringBuilder info = new StringBuilder("Compras de la persona:\n\n");
                for (Producto compra : compras) {
                    info.append(compra.toString()).append("\n\n");
                }
                JOptionPane.showMessageDialog(null, info.toString());
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Debe ingresar un ID válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}