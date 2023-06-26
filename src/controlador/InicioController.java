package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InicioController implements Initializable {

    @FXML
    private Button btninicio;
    @FXML
    private TextField txtNomJugador;
    private String nombreJugador;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void inicio(ActionEvent event) {
        String nombreJugador = txtNomJugador.getText();
        guardarNombreJugador(nombreJugador);
        System.out.println("¡Inicio exitoso! Nombre del jugador: " + nombreJugador);

        // Obtener la escena actual y su stage
        Stage currentStage = (Stage) btninicio.getScene().getWindow();

        // Cargar la vista de categorías
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/categoria.fxml"));
            Parent categoriaRoot = loader.load();
            Scene categoriaScene = new Scene(categoriaRoot);

            // Obtener el controlador de la vista de categorías
            CategoriaController categoriaController = loader.getController();
            categoriaController.setNombreJugador(nombreJugador); // Pasa el nombre del jugador al controlador

            // Cambiar a la vista de categorías
            currentStage.setScene(categoriaScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void guardarNombreJugador(String nombreJugador) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("datos.txt"))) {
            writer.write(nombreJugador);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
    }

    @FXML
    private void NomJugador(ActionEvent event) {
    }

}
