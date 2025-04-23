package com.osorio.aplicacion;
import clases.GestionMascotas;
import clases.GestionPersonas;
import clases.GestionProductos;

import javax.swing.*;

public class Principal {
    public static void main(String[] args) {

        String menu="MENU DE OPCIONES\n\n";
        menu+="1. Gestionar Personas\n";
        menu+="2. Gestionar Mascotas\n";
        menu+="3. Gestionar Productos\n";
        menu+="4. Salir\n\n";

        int opcion=0;

        while (opcion !=4){
            opcion =Integer.parseInt(JOptionPane.showInputDialog(menu));
            switch (opcion){
                case 1: new GestionPersonas(); break;
                case 2: new GestionMascotas(); break;
                case 3: new GestionProductos(); break;
            }
        }
    }
}

