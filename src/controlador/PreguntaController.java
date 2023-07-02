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

    private int preguntasCorrectas;
    private int preguntasIncorrectas;

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

        String opciones = "- " + pregunta.getOpcionA() + "\n"
                + "- " + pregunta.getOpcionB() + "\n"
                + "- " + pregunta.getOpcionC() + "\n"
                + "- " + pregunta.getOpcionD();
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
                calcularPuntaje(); // Calcular el puntaje después de responder
            } else {
                System.out.println("Respuesta incorrecta");
                preguntaTextArea.setText("¡Respuesta incorrecta!");
                calcularPuntaje(); // Calcular el puntaje después de responder
            }

            mostrarPuntaje(preguntasCorrectas, preguntasIncorrectas); // Mostrar el puntaje actualizado
        }
    }

    @FXML
    private void SiguienteRespuesta(ActionEvent event) {
        preguntaActualIndex++;
        if (preguntaActualIndex < preguntas.size()) {
            mostrarPregunta(preguntas.get(preguntaActualIndex));
        } else {
            // El jugador ha respondido todas las preguntas
            System.out.println("TRIVIA FINALIZADA");
            preguntaTextArea.setText("TRIVIA FINALIZADA");

            // Redirigir a la vista de puntuación
            try {
                String rutaFXML = "/vista/puntuacion.fxml";
                String fxmlFile = rutaFXML;

                InputStream inputStream = getClass().getResourceAsStream(fxmlFile);
                if (inputStream == null) {
                    throw new FileNotFoundException("No se pudo encontrar el archivo FXML: " + fxmlFile);
                }

                FXMLLoader loader = new FXMLLoader();
                Parent puntuacionRoot = loader.load(inputStream);
                Scene puntuacionScene = new Scene(puntuacionRoot);

                PuntuacionController puntuacionController = loader.getController();
                puntuacionController.mostrarPuntaje(preguntasCorrectas, preguntasIncorrectas);

                Stage stage = (Stage) btnSiguienteRespuesta.getScene().getWindow();
                stage.setScene(puntuacionScene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
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

    private void calcularPuntaje() {
        Pregunta pregunta = preguntas.get(preguntaActualIndex);
        RadioButton selectedRadioButton = (RadioButton) toggleGroupRespuestas.getSelectedToggle();
        if (selectedRadioButton != null) {
            String respuestaSeleccionada = selectedRadioButton.getUserData().toString();
            if (respuestaSeleccionada.equalsIgnoreCase(pregunta.getRespuestaCorrecta())) {
                preguntasCorrectas++;
            } else {
                preguntasIncorrectas++;
            }
        }
    }

    private void mostrarPuntaje(int preguntasCorrectas, int preguntasIncorrectas) {
        txtMostrarPuntaje.setText("Preguntas correctas: " + preguntasCorrectas + " | " +
                "Preguntas incorrectas: " + preguntasIncorrectas);
        System.out.println("Preguntas correctas: " + preguntasCorrectas);
        System.out.println("Preguntas incorrectas: " + preguntasIncorrectas);
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
