package eu.andreatt.ejercicioh_dein.dao;


import eu.andreatt.ejercicioh_dein.bbdd.ConexionBBDD;
import eu.andreatt.ejercicioh_dein.model.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {


    public List<Persona> obtenerTodas() throws SQLException {
        List<Persona> personas = new ArrayList<>();
        String sql = "SELECT * FROM Persona";

        try (Connection conn = ConexionBBDD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Persona persona = new Persona(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellidos"), rs.getInt("edad"));
                personas.add(persona);
            }
        }

        return personas;
    }

    public void agregar(Persona persona) throws SQLException {
        String sql = "INSERT INTO Persona (nombre, apellidos, edad) VALUES (?, ?, ?)";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getApellidos());
            pstmt.setInt(3, persona.getEdad());
            pstmt.executeUpdate();

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    persona.setId(generatedKeys.getInt(1));
                }
            }
        }
    }

    public void actualizar(Persona persona) throws SQLException {
        String sql = "UPDATE Persona SET nombre = ?, apellidos = ?, edad = ? WHERE id = ?";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, persona.getNombre());
            pstmt.setString(2, persona.getApellidos());
            pstmt.setInt(3, persona.getEdad());
            pstmt.setInt(4, persona.getId());
            pstmt.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Persona WHERE id = ?";

        try (Connection conn = ConexionBBDD.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
    }
}