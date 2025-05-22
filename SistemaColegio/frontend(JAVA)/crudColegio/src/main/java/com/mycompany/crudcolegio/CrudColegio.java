/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.crudcolegio;

import Controller.LoginController;
import View.ViewLogin;
import Service.ServiceLogin;
/**
 *
 * @author osbel
 */
public class CrudColegio {

    public static void main(String[] args) {
        //cargar servicios para el login
        
    ViewLogin loginView = new ViewLogin();
    ServiceLogin serviceLogin = new ServiceLogin();
    LoginController controller = new LoginController(loginView, serviceLogin);
}
}