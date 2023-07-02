package controlador;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PuntuacionController implements Initializable {

    @FXML
    private TextArea mensajeTextArea;
    @FXML
    private Button intentarBtn;

    private String nombreCompleto;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void jugardenuevo(ActionEvent event) {
        try {
            String rutaFXML = "/vista/categoria.fxml";
            URL location = getClass().getResource(rutaFXML);
            if (location == null) {
                throw new FileNotFoundException("No se pudo encontrar el archivo FXML: " + rutaFXML);
            }

            FXMLLoader loader = new FXMLLoader(location);
            Parent categoriaRoot = loader.load();
            Scene categoriaScene = new Scene(categoriaRoot);

            CategoriaController categoriaController = loader.getController();

            Stage stage = (Stage) intentarBtn.getScene().getWindow();
            stage.setScene(categoriaScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void mostrarPuntaje(int preguntasCorrectas, int preguntasIncorrectas) {
        mensajeTextArea.setText("""
                                MATCQUEST
                                \u00a1Felicitaciones!
                                Tu puntaje es:
                                Preguntas Correctas: """ + preguntasCorrectas + "\nPreguntas Incorrectas: " + preguntasIncorrectas);
    }
}
