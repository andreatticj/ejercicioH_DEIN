package eu.andreatt.ejercicioh_dein.controller;

import eu.andreatt.ejercicioh_dein.dao.PersonaDAO;
import eu.andreatt.ejercicioh_dein.model.Persona;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class HelloController {

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
            mostrarAlerta("Error al cargar las personas: " + e.getMessage());
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

    @FXML
    public void agregarPersona() {
        mostrarFormularioPersona(null);
    }

    @FXML
    public void modificar() {
        Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            mostrarFormularioPersona(personaSeleccionada);
        } else {
            mostrarAlerta("Seleccione una persona para modificar.");
        }
    }

    private void mostrarFormularioPersona(Persona persona) {
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
            mostrarAlerta("Error: " + e.getMessage());
        }
    }


    @FXML
    public void eliminar() {
        Persona personaSeleccionada = tabla.getSelectionModel().getSelectedItem();
        if (personaSeleccionada != null) {
            try {
                personaDAO.eliminar(personaSeleccionada.getId());
                listaPersonas.remove(personaSeleccionada);
            } catch (SQLException e) {
                mostrarAlerta("Error al eliminar: " + e.getMessage());
            }

        } else {
            mostrarAlerta("Seleccione una persona para eliminar.");
        }
    }

    private void mostrarAlerta(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
