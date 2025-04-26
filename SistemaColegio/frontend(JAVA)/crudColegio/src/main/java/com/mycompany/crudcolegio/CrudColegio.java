/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.crudcolegio;

/**
 *
 * @author osbel
 */
public class CrudColegio {

    public static void main(String[] args) {
       Configuracion.Conexion objetoConexion = new Configuracion.Conexion();
        objetoConexion.estableceConexion();
        
        Formularios.menuPrincipal objetoMenuPrincipal = new Formularios.menuPrincipal();
        objetoMenuPrincipal.setVisible(true);
    }
}
