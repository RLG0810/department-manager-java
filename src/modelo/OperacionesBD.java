/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author Rafaela
 */
public class OperacionesBD {
    private Connection conexion;

    public OperacionesBD() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            System.out.println("Error en OperacionesBD Class.forName" + ex.getMessage());
        }
        
        try {
            this.conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/y9027250v", "root","");
        } catch (SQLException ex) {
            System.out.println("Error en OperacionesBD Conexion" + ex.getMessage());
        }
    }

    public OperacionesBD(Connection conexion) {
        this.conexion = conexion;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
     public ArrayList<Departamento> listadoDepartamentos() {
        ArrayList<Departamento> lista = new ArrayList<>();
        String sql = "SELECT * FROM Departamentos";
        try {
            Statement myStatement = this.conexion.createStatement();
            ResultSet rs = myStatement.executeQuery(sql);
            while (rs.next()== true) {
                Departamento dep = new Departamento(
                    rs.getInt("codigo"),
                    rs.getString("nombre"),
                    rs.getInt("id_localizacion"),
                    rs.getInt("id_manager")
                );
                lista.add(dep);
            }
            myStatement.close();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Error en listadoDepartamentos: " + e.getMessage());
        }
        return lista;
    }
     public boolean insertarDepartamento(Departamento dep) {
        try {
            String sql = "INSERT INTO Departamentos VALUES (?, ?, ?, ?)";
            PreparedStatement preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, dep.getCodigo());
            preparada.setString(2, dep.getNombre());
            preparada.setInt(3, dep.getIdLocalizacion());
            preparada.setInt(4, dep.getIdManager());
            preparada.executeUpdate(); 
            preparada.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en insertarDepartamento: " + e.getMessage());
        }
        return false;
    }
     public boolean bajaDepartamento(int codigo) {
        String sql = "DELETE FROM Departamentos WHERE codigo = ?";
        try {
            PreparedStatement preparada = conexion.prepareStatement(sql);
            preparada.setInt(1, codigo);
            preparada.executeUpdate();
            preparada.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en bajaDepartamento: " + e.getMessage());
        }
        return false;
}
      public boolean modificarDepartamento(int codigo, String nombre, int idLocalizacion, int idManager) {
        String sql = "UPDATE Departamentos SET nombre = ?, id_localizacion = ?, id_manager = ? WHERE codigo = ?";
        try {
            PreparedStatement preparada = conexion.prepareStatement(sql);
            preparada.setString(1, nombre);
            preparada.setInt(2, idLocalizacion);
            preparada.setInt(3, idManager);
            preparada.setInt(4, codigo);
            preparada.executeUpdate();
            preparada.close();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en modificarDepartamento: " + e.getMessage());
        }
        return false;}
}
