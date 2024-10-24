package eu.andreatt.ejercicioh_dein.controller;

import eu.andreatt.ejercicioh_dein.dao.PersonaDAO;
import eu.andreatt.ejercicioh_dein.model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Window;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {

    @FXML
    private Button btnAgregarPersona;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnModificar;

    @FXML
    private TableView<Persona> tabla;

    @FXML
    private TableColumn<Persona, String> colNombre;

    @FXML
    private TableColumn<Persona, String> colApellido;

    @FXML
    private TableColumn<Persona, Integer> colEdad;

    @FXML
    private TextField txtFiltro;

    private ObservableList<Persona> listaPersonas = FXCollections.observableArrayList();
    private PersonaDAO personaDAO = new PersonaDAO();

    @FXML
    public void initialize() {
        colNombre.setCellValueFactory(cellData -> cellData.getValue().nombreProperty());
        colApellido.setCellValueFactory(cellData -> cellData.getValue().apellidosProperty());
        colEdad.setCellValueFactory(cellData -> cellData.getValue().edadProperty().asObject());

        try {
            cargarPersonas();
        } catch (SQLException e) {
            mostrarAlert(null, Alert.AlertType.ERROR,"ERROR","Error al cargar las personas: " + e.getMessage());
        }
        // Filtrar personas
        txtFiltro.textProperty().addListener((observable, oldValue, newValue) -> filtrarPersonas(newValue));
    }

    /**
     * Filtra las personas de la lista en función del texto ingresado en el campo de búsqueda.
     * Si no se proporciona ningún filtro, se muestran todas las personas.
     *
     * @param filtro El texto utilizado para filtrar las personas.
     */
    private void filtrarPersonas(String filtro) {
        if (filtro == null || filtro.isEmpty()) {
            tabla.setItems(listaPersonas);
        } else {
            List<Persona> filtrada = listaPersonas.stream()
                    .filter(p -> p.getNombre().toLowerCase().contains(filtro.toLowerCase()))
                    .collect(Collectors.toList());
            tabla.setItems(FXCollections.observableArrayList(filtrada));
        }
    }


    private void cargarPersonas() throws SQLException {
        List<Persona> personas = personaDAO.obtenerTodas();
        listaPersonas.setAll(personas);
        tabla.setItems(listaPersonas);
    }

    /**
     * Evento que se dispara al hacer clic en el botón para agregar una nueva persona.
     * Abre una ventana modal para ingresar los datos de la nueva persona y agregarlo a la bd.
     *
     * @param event Evento de acción que ocurre al hacer clic en el botón
     */
    @FXML
    void agregarPersona(ActionEvent event)  {
        mostrarFormularioPersona("Agregar Persona",null);
    }

    /**
     * Acción que se ejecuta al hacer clic en el botón "Modificar". Abre una ventana modal
     * para permitir la modificación de los datos de la persona seleccionada.
     *
     * @param event Evento que se dispara al hacer clic en el botón.
     */
    @FXML
    void modificar(ActionEvent event) {
        Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            mostrarFormularioPersona("Modificar persona",personaSeleccionada);
        } else {
            mostrarAlert(null, Alert.AlertType.WARNING,"","Seleccione una persona para modificar.");
        }
    }

    /**
     * Método que abre una ventana modal para agregar o modificar una persona.
     * La ventana se carga desde un archivo FXML, y se pasa la lista de personas
     * al controlador del modal para que pueda gestionar los datos.
     *
     * @param titulo El título de la ventana modal.
     * @param persona La persona a modificar, o null si se está agregando una nueva.
     */
    private void mostrarFormularioPersona(String titulo, Persona persona) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/eu/andreatt/ejercicioh_dein/fxml/modalH.fxml"));
            Parent root = loader.load();

            ModalHController controller = loader.getController();
            controller.setPersona(persona);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);  // No permite redimensionar la ventana
            stage.setWidth(300);  // Establece el ancho de la ventana
            stage.setHeight(200);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            Persona personaEditada = controller.getPersona();
            if (personaEditada != null) {
                if (persona == null) {
                    personaDAO.agregar(personaEditada);
                    listaPersonas.add(personaEditada);  // Agregar a la lista de la tabla
                } else {
                    personaDAO.actualizar(personaEditada);
                    tabla.refresh();  // Asegúrate de refrescar la tabla después de actualizar
                }
            }
        } catch (Exception e) {
            mostrarAlert(null, Alert.AlertType.ERROR,"ERROR","Error: " + e.getMessage());
        }
    }

    /**
     * Acción que se ejecuta al hacer clic en el botón "Eliminar". Elimina la persona seleccionada
     * de la base de datos después de confirmar la acción a través de una ventana emergente y lo refleja en la tabla.
     *
     * @param event Evento que se dispara al hacer clic en el botón.
     */
    @FXML
    void eliminar(ActionEvent event) {
        Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {

            try {
                // Verifica si el usuario acepta o cancela la eliminación
                boolean confirmacion = confirmarEliminacion(event, personaSeleccionada);
                if (confirmacion) {
                    personaDAO.eliminar(personaSeleccionada.getId());
                    listaPersonas.remove(personaSeleccionada);
                }

            } catch (SQLException e) {
                mostrarAlert(null, Alert.AlertType.ERROR, "ERROR", "Error al eliminar: " + e.getMessage());
            }
        } else {
            mostrarAlert(null, Alert.AlertType.ERROR, "ERROR", "Seleccione una persona para eliminar.");
        }
    }

    /**
     * Confirma la eliminación de la persona seleccionada.
     *
     * @param event             El evento que desencadena la acción.
     * @param personaSeleccionada La persona que se va a eliminar.
     * @return true si se confirma la eliminación, false si se cancela.
     */
    private boolean confirmarEliminacion(ActionEvent event, Persona personaSeleccionada) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION); // Crea una alerta de confirmación
        alert.setTitle("Confirmar eliminación"); // Título de la alerta
        alert.setHeaderText(null); // Sin encabezado
        alert.setContentText("¿Estás seguro de que deseas eliminar a " + personaSeleccionada.getNombre() + "?"); // Contenido de la alerta

        // Muestra la alerta y espera la respuesta
        ButtonType resultado = alert.showAndWait().orElse(ButtonType.CANCEL); // Si no selecciona nada, es CANCEL

        // Devuelve true si el usuario selecciona OK, de lo contrario false
        return resultado == ButtonType.OK;
    }



    /**
     * Muestra una alerta de acuerdo al tipo, título y contenido especificados.
     *
     * @param win       La ventana sobre la que se mostrará la alerta.
     * @param alertType El tipo de alerta a mostrar.
     * @param title     El título de la alerta.
     * @param content   El contenido de la alerta.
     */
    private void mostrarAlert(Window win, Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType); // Crea una nueva alerta
        alert.initOwner(win); // Establece la ventana principal
        alert.setHeaderText(null); // Sin encabezado
        alert.setTitle(title); // Establece el título
        alert.setContentText(content); // Establece el contenido
        alert.showAndWait(); // Muestra la alerta y espera a que se cierre
    }
}
