/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ComboItem;

/**
 *
 * @author osbel
 */
public class ComboItem {
    // combo encargados
    private int id;
    private String descripcion;

    public ComboItem(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return descripcion; // Lo que se muestra en el JComboBox
    }
    
    // combo docente
    
}
