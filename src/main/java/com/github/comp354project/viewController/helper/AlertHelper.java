package com.github.comp354project.viewController.helper;

import com.github.comp354project.service.exceptions.ValidationException;
import javafx.scene.control.Alert;

import java.util.List;
import java.util.stream.Collectors;

public class AlertHelper {

    /**
     * Generates a general alert
     * @param alertType
     * @param title
     * @param header
     * @param content
     * @return
     */
    public static Alert generateAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        return alert;
    }

    public static Alert generateErrorAlert(String title, String header, String content) {
        return generateAlert(Alert.AlertType.ERROR, title, header, content);
    }

    public static Alert generateErrorAlert(String title, String header, ValidationException exception) {
        final String errorsStr = exception.getErrors()
                .stream()
                .map(e -> e.getMessage())
                .collect(Collectors.joining("\n"));
        return generateAlert(Alert.AlertType.ERROR, title, header, errorsStr);
    }
}
