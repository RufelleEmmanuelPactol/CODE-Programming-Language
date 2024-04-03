package com.code.virtualmachine;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class CodeWeb extends Application {
    private static WebView webView;
    private static Stage primaryStage;
    private static volatile boolean javafxLaunched = false;

    @Override
    public void start(Stage primaryStage) {
        CodeWeb.primaryStage = primaryStage;
        CodeWeb.javafxLaunched = true;

        // Initialize the WebView and Scene here but do not show the stage yet
        webView = new WebView();
        primaryStage.setScene(new Scene(webView, 800, 600));
        primaryStage.setTitle("CODE Standard HTML Viewer");

        // Hide the primary stage if you don't want to show a blank WebView initially
        // primaryStage.show();
    }

    // Synchronized method to view HTML content
    public static synchronized void view(String html) {
        // Ensure that the JavaFX thread is ready
        if (!javafxLaunched) {
            // Launch the JavaFX application on a new thread
            new Thread(() -> Application.launch(CodeWeb.class)).start();

            // Wait until JavaFX application has started
            while (!javafxLaunched) {
                try {
                    Thread.sleep(10); // Use minimal sleep time to wait
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return;
                }
            }
        }

        // Now that JavaFX is ready, update the content in the JavaFX thread
        Platform.runLater(() -> {
            webView.getEngine().loadContent(html);
            // Show the stage if it was not shown initially or if it needs to be shown again
            if (!primaryStage.isShowing()) {
                primaryStage.show();
            }
        });
    }
}