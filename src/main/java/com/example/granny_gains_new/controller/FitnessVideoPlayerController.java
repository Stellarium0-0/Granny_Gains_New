package com.example.granny_gains_new.controller;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class FitnessVideoPlayerController {
    @FXML
    WebView webView;

    private String videoUrl;

    // Set the video URL
    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
        loadVideo(); // Load the video immediately after the URL is set
    }

    // Load the video
    private void loadVideo() {
        if (videoUrl != null && !videoUrl.isEmpty()) {
            WebEngine webEngine = webView.getEngine();
            webEngine.load(videoUrl);
        }
    }

    @FXML
    public void initialize() {

        if (videoUrl != null && !videoUrl.isEmpty()) {
            loadVideo();
        }
    }


    @FXML
    void handleBack() {
        Stage stage = (Stage) webView.getScene().getWindow();
        stage.close();
    }

    public WebView getWebView() {
        return webView;
    }
}
