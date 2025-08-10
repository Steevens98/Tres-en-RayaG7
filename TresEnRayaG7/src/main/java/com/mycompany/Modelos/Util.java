/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.Modelos;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.DialogPane;
import javafx.util.Duration;

/**
 *
 * @author gutav
 */
public class Util {

    public static void mostrarMensaje(String mensaje, String title) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();
    }

    public static void mostrarMensaje2(String mensaje, String title) {
        // Crear un nuevo DialogPane
        DialogPane dialogPane = new DialogPane();
        dialogPane.setContentText(mensaje);

        // Configurar el título
        dialogPane.setHeaderText(title);

        // Agregar un botón "OK" al diálogo
        ButtonType buttonTypeOK = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialogPane.getButtonTypes().add(buttonTypeOK);

        // Crear el diálogo con el contenido personalizado
        Dialog<Void> dialog = new Dialog<>();
        dialog.setDialogPane(dialogPane);

        // Manejar la acción del botón "OK"
        Button okButton = (Button) dialog.getDialogPane().lookupButton(buttonTypeOK);
        okButton.addEventFilter(ActionEvent.ACTION, event -> {
            dialog.close();
            System.out.println("Se hizo clic en OK");
            // Aquí puedes realizar acciones adicionales para el botón OK si es necesario
        });

        // Mostrar el diálogo sin bloquear la aplicación
        dialog.show();
    }

    public static void mostrarMensajeCPU(String mensaje, String title) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(title);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.show();

        // Configurar un retraso antes de cerrar la alerta
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            alerta.close();
        }));
        timeline.play();
    }

    public static void mostrarMensajeCPU2(String mensaje, String title) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alerta");
        alert.setHeaderText("Este es el encabezado");
        alert.setContentText("Este es el contenido de la alerta.");

        // Agregar un escuchador para el evento de ocultar
        alert.setOnHidden(event -> {
            // Esto se ejecutará después de que el usuario haya cerrado la alerta
            System.out.println("Después de cerrar la alerta.");
            // Aquí puedes agregar el código que desees ejecutar después de cerrar la alerta.
        });

        // Mostrar la alerta
        alert.show();
    }

    public static void mostrarMensaje(String mensaje, String title, String header) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(title);
        alerta.setHeaderText(header);
        alerta.setContentText(mensaje);
        alerta.show();
    }

}
