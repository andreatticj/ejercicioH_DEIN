package eu.andreatt.ejercicioh_dein.model;

import javafx.beans.property.*;

public class Persona {
    private IntegerProperty id;
    private StringProperty nombre;
    private StringProperty apellidos;
    private IntegerProperty edad;

    public Persona(int id, String nombre, String apellidos, int edad) {
        this.id = new SimpleIntegerProperty(id);
        this.nombre = new SimpleStringProperty(nombre);
        this.apellidos = new SimpleStringProperty(apellidos);
        this.edad = new SimpleIntegerProperty(edad);
    }

    public Persona(String nombre, String apellidos, int edad) {
        this(0, nombre, apellidos, edad);
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public String getNombre() {
        return nombre.get();
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public String getApellidos() {
        return apellidos.get();
    }

    public void setApellidos(String apellidos) {
        this.apellidos.set(apellidos);
    }

    public int getEdad() {
        return edad.get();
    }

    public void setEdad(int edad) {
        this.edad.set(edad);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty nombreProperty() {
        return nombre;
    }

    public StringProperty apellidosProperty() {
        return apellidos;
    }

    public IntegerProperty edadProperty() {
        return edad;
    }
}
