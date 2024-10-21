package eu.andreatt.ejercicioh_dein.controller;

import eu.andreatt.ejercicioh_dein.model.Persona;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * Controlador de la ventana modal para agregar o modificar una persona.
 * Permite gestionar la información ingresada y agregarla a una lista de personas,
 * o cancelar la operación y cerrar la ventana modal.
 */
public class ModalHController {
    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtApellido;

    @FXML
    private TextField txtEdad;

    private Persona persona;
    private ObservableList<Persona> personas;  // Lista de personas para verificar duplicados

    /**
     * Establece la lista de personas donde se añadirá o editará la persona.
     *
     * @param personas Lista observable de personas.
     */
    public void setPersonas(ObservableList<Persona> personas) {
        this.personas = personas;  // Establece la lista de personas
    }

    /**
     * Carga los datos de la persona en los campos de texto de la ventana modal para su modificación.
     *
     * @param persona La persona cuyos datos serán cargados en los campos.
     */
    public void setPersona(Persona persona) {
        this.persona = persona;
        if (persona != null) {
            txtNombre.setText(persona.getNombre());
            txtApellido.setText(persona.getApellidos());
            txtEdad.setText(String.valueOf(persona.getEdad()));
        }
    }

    /**
     * Verifica la validez de la información ingresada en los campos de texto.
     * Verifica que los campos no estén vacíos y que la edad sea un número válido.
     *
     * @return Una cadena con los errores encontrados, o una cadena vacía si no se encuentran errores.
     */
    private String verificarInfo() {
        StringBuilder errores = new StringBuilder();

        // Verifica si el campo Nombre está vacío
        if (txtNombre.getText().isEmpty()) {
            errores.append("El campo Nombre es obligatorio.\n");
        }

        // Verifica si el campo Apellido está vacío
        if (txtApellido.getText().isEmpty()) {
            errores.append("El campo Apellido es obligatorio.\n");
        }

        // Verifica si el campo Edad está vacío
        if (txtEdad.getText().isEmpty()) {
            errores.append("El campo Edad es obligatorio.\n");
        } else {
            // Verifica que la edad sea un número válido
            try {
                Integer.parseInt(txtEdad.getText());  // Intenta convertir el campo edad a un número
            } catch (NumberFormatException e) {
                errores.append("El campo Edad debe ser un número válido.\n");  // Agrega un error si no es un número válido
            }
        }

        return errores.toString();  // Retorna la cadena de errores
    }


    /**
     * Muestra una alerta de error con un mensaje específico.
     *
     * @param error El mensaje de error a mostrar.
     */
    private void mostrarAlertError(String error) {
        mostrarAlert(Alert.AlertType.ERROR, "ERROR", error);
    }

    /**
     * Muestra una alerta de acuerdo al tipo, título y contenido especificados.
     *
     * @param alertType El tipo de alerta a mostrar.
     * @param title     El título de la alerta.
     * @param content   El contenido de la alerta.
     */
    private void mostrarAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);  // Crea una nueva alerta
        alert.setHeaderText(null);  // Sin encabezado
        alert.setTitle(title);  // Establece el título de la alerta
        alert.setContentText(content);  // Establece el contenido de la alerta
        alert.showAndWait();  // Muestra la alerta y espera a que el usuario la cierre
    }

    /**
     * Maneja el evento de guardar una persona. Si es una nueva persona, la agrega a la lista.
     * Si es una modificación, actualiza los datos de la persona seleccionada.
     * Verifica que los datos ingresados sean válidos antes de realizar cambios.
     */
    @FXML
    public void guardar() {
        String errores = verificarInfo();
        if (errores.isEmpty()){
            String nombre = txtNombre.getText();
            String apellidos = txtApellido.getText();
            int edad = Integer.parseInt(txtEdad.getText());

            if (persona == null) {
                persona = new Persona(nombre, apellidos, edad);
            } else {
                persona.setNombre(nombre);
                persona.setApellidos(apellidos);
                persona.setEdad(edad);
            }
            cerrarVentana();
        }else{
           mostrarAlertError(errores);
        }
    }

    @FXML
    public void cancelar() {
        persona = null;  // Si cancelamos, no se guarda la persona
        cerrarVentana();
    }

    private void cerrarVentana() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    public Persona getPersona() {
        return persona;  // Retorna la persona creada o editada
    }
}
