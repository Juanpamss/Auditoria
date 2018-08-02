/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auditoria;

import coneccionBDD.baseDatos;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Pa
 */
public class Auditoria {

    /**
     * @param args the command line arguments
     */
    
    
    public static void main(String[] args) {
        
        main main = new main();
        
        main.setVisible(true);  
        
        ingresarInfoAnalizar();
        
        
    }
    
    public static void ingresarInfoAnalizar(){
    
        baseDatos base = new baseDatos();
        
        base.setDbName(JOptionPane.showInputDialog("Ingrese el nombre de la base: "));
        base.setUser(JOptionPane.showInputDialog("Ingrese el nombre del usuario: "));
        base.setPass(JOptionPane.showInputDialog("Ingrese el password: "));
        
    }
    
}
