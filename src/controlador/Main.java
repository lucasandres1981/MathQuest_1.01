package controlador;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Cargar la vista de inicio
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/inicio.fxml"));
            Parent root = loader.load();
            
            // Obtener el controlador de la vista de inicio
            InicioController inicioController = loader.getController();

            // Obtener los elementos de la vista de inicio
            Button btnInicio = (Button) root.lookup("#btninicio");
            TextField txtNomJugador = (TextField) root.lookup("#txtNomJugador");
            
            // Definir el evento para el botón de inicio
            btnInicio.setOnAction((event) -> {
                String nombreJugador = txtNomJugador.getText();
                guardarNombreJugador(nombreJugador);
                System.out.println("¡Inicio exitoso! Nombre del jugador: " + nombreJugador);
                
                // Cargar la vista de categorías
                try {
                    FXMLLoader categoriaLoader = new FXMLLoader(getClass().getResource("/vista/categoria.fxml"));
                    Parent categoriaRoot = categoriaLoader.load();
                    Scene categoriaScene = new Scene(categoriaRoot);
                    
                    // Obtener el controlador de la vista de categorías
                    CategoriaController categoriaController = categoriaLoader.getController();
                    categoriaController.setNombreJugador(nombreJugador); // Pasa el nombre del jugador al controlador
                    
                    // Cambiar a la vista de categorías
                    primaryStage.setScene(categoriaScene);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            
            // Configurar la escena y mostrar la ventana
            primaryStage.setTitle("MATCHQUEST");
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
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

}
