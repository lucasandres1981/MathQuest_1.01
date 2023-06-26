package controlador;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoriaController implements Initializable {

    @FXML
    private Button btnregresar;
    @FXML
    private Button btnAlgebra;
    @FXML
    private Button btnCalculo;
    @FXML
    private Button btnAritmetica;
    @FXML
    private Button btnEstadistica;
    @FXML
    private Button btnGeometria;

    private String nombreJugador;

    @FXML
    private Button btnTrigonometria;
    @FXML
    private ImageView imgAritmetica;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setNombreJugador(String nombreJugador) {
        this.nombreJugador = nombreJugador;
        System.out.println("Nombre del jugador en la vista de categorías: " + nombreJugador);
    }

    @FXML
    private void regresarInicio(ActionEvent event) {
        // Obtener la escena actual y su stage
        Scene currentScene = btnregresar.getScene();
        Stage currentStage = (Stage) currentScene.getWindow();

        // Cargar la vista de inicio
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/inicio.fxml"));
            Parent inicioRoot = loader.load();
            Scene inicioScene = new Scene(inicioRoot);

            // Obtener el controlador de la vista de inicio
            InicioController inicioController = loader.getController();
            inicioController.setNombreJugador(nombreJugador); // Pasa el nombre del jugador al controlador de inicio

            // Cambiar a la vista de inicio
            currentStage.setScene(inicioScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void categoriaAlgebra(ActionEvent event) {
        cargarPreguntas("algebra");
    }

    @FXML
    private void categoriaCalculo(ActionEvent event) {
        cargarPreguntas("calculo");
    }

    @FXML
    private void categoriaAritmetica(ActionEvent event) {
        cargarPreguntas("aritmetica");
    }

    @FXML
    private void categoriaEstadistica(ActionEvent event) {
        cargarPreguntas("estadistica");
    }

    @FXML
    private void categoriaGeometria(ActionEvent event) {
        cargarPreguntas("geometria");
    }

    @FXML
    private void categoriaTrigonometria(ActionEvent event) {
        cargarPreguntas("trigonometria");
    }

    private void cargarPreguntas(String categoria) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/pregunta.fxml"));
            Parent preguntaRoot = loader.load();
            Scene preguntaScene = new Scene(preguntaRoot);

            PreguntaController preguntaController = loader.getController();

            // Leer las preguntas del archivo correspondiente a la categoría
            String archivoPreguntas = "preguntas_" + categoria + ".txt";
            List<Pregunta> preguntas = leerPreguntasDesdeArchivo(archivoPreguntas);

            // Mostrar la primera pregunta en la vista de pregunta
            if (!preguntas.isEmpty()) {
                Pregunta primeraPregunta = preguntas.get(0);
                preguntaController.setPreguntas(preguntas);
                preguntaController.mostrarPregunta(primeraPregunta);
            }

            Stage currentStage = (Stage) btnAlgebra.getScene().getWindow();
            currentStage.setScene(preguntaScene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Pregunta> leerPreguntasDesdeArchivo(String archivoPreguntas) {
        List<Pregunta> preguntas = new ArrayList<>();

        try (Scanner scanner = new Scanner(getClass().getResourceAsStream("/preguntas/" + archivoPreguntas))) {
            while (scanner.hasNextLine()) {
                String pregunta = scanner.nextLine();
                String opcionA = scanner.nextLine();
                String opcionB = scanner.nextLine();
                String opcionC = scanner.nextLine();
                String opcionD = scanner.nextLine();
                String respuestaCorrecta = scanner.nextLine();

                Pregunta nuevaPregunta = new Pregunta(pregunta, opcionA, opcionB, opcionC, opcionD, respuestaCorrecta);
                preguntas.add(nuevaPregunta);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return preguntas;
    }

    @FXML
    private void categ(MouseEvent event) {
    }
}
