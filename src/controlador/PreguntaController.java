package controlador;

import java.io.FileNotFoundException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.ToggleGroup;

public class PreguntaController implements Initializable {

    @FXML
    private TextArea preguntaTextArea;
    @FXML
    private TextArea opcionesTextArea;
    @FXML
    private Button enviarRespuestaButton;

    private List<Pregunta> preguntas;
    private int preguntaActualIndex;

    @FXML
    private ImageView opcionAImageView;
    @FXML
    private Button btnSiguienteRespuesta;
    @FXML
    private Button btnElejirCategoria;
    @FXML
    private RadioButton btnroundA;
    @FXML
    private RadioButton btnroundB;
    @FXML
    private RadioButton btnroundC;
    @FXML
    private RadioButton btnroundD;
    @FXML
    private TextField txtMostrarPuntaje;
    private ToggleGroup toggleGroupRespuestas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        toggleGroupRespuestas = new ToggleGroup();

        btnroundA.setToggleGroup(toggleGroupRespuestas);
        btnroundB.setToggleGroup(toggleGroupRespuestas);
        btnroundC.setToggleGroup(toggleGroupRespuestas);
        btnroundD.setToggleGroup(toggleGroupRespuestas);

        btnroundA.setUserData("a");
        btnroundB.setUserData("b");
        btnroundC.setUserData("c");
        btnroundD.setUserData("d");

        toggleGroupRespuestas.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                String respuestaSeleccionada = newValue.getUserData().toString();
                System.out.println("Respuesta seleccionada: " + respuestaSeleccionada);
            }
        });
    }

    public void setPreguntas(List<Pregunta> preguntas) {
        this.preguntas = preguntas;
        preguntaActualIndex = 0;
        mostrarPregunta(preguntas.get(preguntaActualIndex));
    }

    public void mostrarPregunta(Pregunta pregunta) {
        preguntaTextArea.setText(pregunta.getPregunta());

        String opciones = "a) " + pregunta.getOpcionA() + "\n"
                + "b) " + pregunta.getOpcionB() + "\n"
                + "c) " + pregunta.getOpcionC() + "\n"
                + "d) " + pregunta.getOpcionD();
        opcionesTextArea.setText(opciones);
    }

    @FXML
    private void enviarRespuesta(ActionEvent event) {
        RadioButton selectedRadioButton = (RadioButton) toggleGroupRespuestas.getSelectedToggle();
        if (selectedRadioButton != null) {
            String respuestaSeleccionada = selectedRadioButton.getUserData().toString();
            System.out.println("Respuesta seleccionada: " + respuestaSeleccionada);

            if (respuestaSeleccionada.equalsIgnoreCase(preguntas.get(preguntaActualIndex).getRespuestaCorrecta())) {
                System.out.println("Respuesta correcta");
                preguntaTextArea.setText("¡Respuesta correcta!");
            } else {
                System.out.println("Respuesta incorrecta");
                preguntaTextArea.setText("¡Respuesta incorrecta!");
            }
        }
    }

    @FXML
    private void SiguienteRespuesta(ActionEvent event) {
        preguntaActualIndex++;
        if (preguntaActualIndex < preguntas.size()) {
            mostrarPregunta(preguntas.get(preguntaActualIndex));
        } else {
            // Aquí se puede porner un mensaje para cuando se hayan mostrado todas las preguntas
        }
    }

    @FXML
    private void ElejirCategoria(ActionEvent event) {
        try {
            String rutaFXML = "/vista/categoria.fxml";
            String fxmlFile = rutaFXML;

            InputStream inputStream = getClass().getResourceAsStream(fxmlFile);
            if (inputStream == null) {
                throw new FileNotFoundException("No se pudo encontrar el archivo FXML: " + fxmlFile);
            }

            FXMLLoader loader = new FXMLLoader();
            Parent categoriaRoot = loader.load(inputStream);
            Scene categoriaScene = new Scene(categoriaRoot);

            CategoriaController categoriaController = loader.getController();

            Stage stage = (Stage) btnElejirCategoria.getScene().getWindow();
            stage.setScene(categoriaScene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void calcularPuntaje() {
        int puntaje = 0;

        for (Pregunta pregunta : preguntas) {
            RadioButton selectedRadioButton = (RadioButton) toggleGroupRespuestas.getSelectedToggle();
            if (selectedRadioButton != null) {
                String respuestaSeleccionada = selectedRadioButton.getUserData().toString();
                if (respuestaSeleccionada.equalsIgnoreCase(pregunta.getRespuestaCorrecta())) {
                    puntaje++;
                }
            }
        }

        mostrarPuntaje(puntaje);
    }

    private void mostrarPuntaje(int puntaje) {
        txtMostrarPuntaje.setText("Puntaje: " + puntaje);
    }

    @FXML
    private void opcionA(ActionEvent event) {
        String respuestaSeleccionada = toggleGroupRespuestas.getSelectedToggle().getUserData().toString();
    }

    @FXML
    private void opcionB(ActionEvent event) {
        String respuestaSeleccionada = toggleGroupRespuestas.getSelectedToggle().getUserData().toString();
    }

    @FXML
    private void opcionC(ActionEvent event) {
        String respuestaSeleccionada = toggleGroupRespuestas.getSelectedToggle().getUserData().toString();
    }

    @FXML
    private void opcionD(ActionEvent event) {
        String respuestaSeleccionada = toggleGroupRespuestas.getSelectedToggle().getUserData().toString();
    }
}
