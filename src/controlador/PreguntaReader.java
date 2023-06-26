package controlador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PreguntaReader {

    public List<Pregunta> leerPreguntas(String archivo) {
        List<Pregunta> preguntas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            Pregunta pregunta = null;
            StringBuilder enunciado = new StringBuilder();

            while ((linea = br.readLine()) != null) {
                if (linea.matches("\\d+\\. .*")) {
                    // Nueva pregunta encontrada
                    if (pregunta != null) {
                        // Guardar la pregunta anterior
                        pregunta.setEnunciado(enunciado.toString().trim());
                        preguntas.add(pregunta);
                    }

                    pregunta = new Pregunta();
                    enunciado = new StringBuilder();
                    enunciado.append(linea.substring(linea.indexOf('.') + 2));
                } else if (pregunta != null && linea.startsWith("Respuesta:")) {
                    // Encontrada la respuesta
                    String respuesta = linea.substring(linea.indexOf(':') + 1).trim();
                    pregunta.setRespuestaCorrecta(respuesta);
                } else {
                    enunciado.append("\n").append(linea);
                }
            }

            // Agregar la última pregunta
            if (pregunta != null) {
                pregunta.setEnunciado(enunciado.toString().trim());
                preguntas.add(pregunta);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return preguntas;
    }

}