/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coneccionBDD;

import java.sql.SQLException;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Juan Pa
 */
public class baseDatos {

    static String dbName = "";
    static String user = "";
    static String pass = "";
    
    public static void setDbName(String dbName) {
        baseDatos.dbName = dbName;
    }

    public static void setUser(String user) {
        baseDatos.user = user;
    }

    public static void setPass(String pass) {
        baseDatos.pass = pass;
    }
    
    
    public static Connection getConnection () {

        Connection connection = null;
        
        
        try {
            
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String connectionDB = "jdbc:sqlserver://localhost;databaseName="
                    + dbName + ";user=" + user + ";password=" + pass + ";";
            
            connection = DriverManager.getConnection(connectionDB);
            
            
        } catch (ClassNotFoundException e) {
            
            System.out.println("Error: " + e.getMessage());
                        
        }catch (SQLException e){
        
            System.out.println("Error: " + e.getMessage());
        }catch (Exception e){
        
            System.out.println("Error: " + e.getMessage());
        }
    
        
        return connection;

    }
    
    public static ResultSet consulta(String query){
    
        Connection con = getConnection();
        Statement st;
        
        try {
            
            st = con.createStatement();
            
            ResultSet respuesta = st.executeQuery(query);
            
            return respuesta;
            
        } catch (SQLException e) {
            
            System.out.println("Error: " + e.getMessage());
        }
        
        return null;
    }
    
}
