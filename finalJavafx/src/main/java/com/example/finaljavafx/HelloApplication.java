package com.example.finaljavafx;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.util.Duration;

public class HelloApplication extends Application {

    private int minutes = 0;
    private int seconds = 0;
    private int microseconds = 0;
    private Label timerLabel;
    private Timeline timeline;
    private boolean edited = false;

    @Override
    public void start(Stage primaryStage) {
        timerLabel = new Label("00:00:000");
        timerLabel.setStyle("-fx-font-size: 48px;");

        TextField minutesField = new TextField("0");
        minutesField.setPrefColumnCount(3);
        TextField secondsField = new TextField("0");
        secondsField.setPrefColumnCount(3);

        Button startButton = new Button("Start");
        startButton.setOnAction(event -> startTimer());
        Button stopButton = new Button("Stop");
        stopButton.setOnAction(event -> stopTimer());
        Button editButton = new Button("Edit");
        editButton.setOnAction(event -> editTimer(minutesField, secondsField));
        Button reset = new Button("Reset");
        reset.setOnAction(event -> reset(minutesField, secondsField));

        HBox buttons = new HBox(startButton, stopButton, editButton,reset);
        buttons.setAlignment(Pos.CENTER);
        buttons.setSpacing(20);

        HBox inputTimer = new HBox(new Label("Minutes:"), minutesField, new Label("Seconds:"), secondsField);
        inputTimer.setAlignment(Pos.CENTER);
        inputTimer.setSpacing(10);
        VBox root = new VBox(timerLabel, buttons , inputTimer);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        root.setSpacing(20);

        //here we need to add the width and height
        Scene scene = new Scene(root);
        primaryStage.setTitle("Stop watch");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void alert(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Error in Timer");
        alert.setHeaderText("Timer Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void startTimer(){
        if(minutes == 0 && seconds == 0 && microseconds ==  0 || edited) {
            minutes = Integer.parseInt(((TextField) ((HBox) timerLabel.getParent().getChildrenUnmodifiable().get(2)).getChildren().get(1)).getText());
            seconds = Integer.parseInt(((TextField) ((HBox) timerLabel.getParent().getChildrenUnmodifiable().get(2)).getChildren().get(3)).getText());

            microseconds = 0;
            updateLabel();
        }
            timeline = new Timeline(new KeyFrame(Duration.millis(10), start -> {
                updateTime();
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
    }
    private void stopTimer(){
        timeline.pause();
        edited = false;
    }
    private void editTimer(TextField minutesField,TextField secondsField){
        stopTimer();
        minutesField.setText(String.valueOf(minutes));
        secondsField.setText(String.valueOf(seconds));
        microseconds = 0;
        edited = true;

    }

    private void  updateTime(){
        microseconds -= 10;
        if (microseconds < 0) {
            microseconds = 990;
            seconds -= 1;
        }
        if (seconds < 0) {
            seconds = 59;
            minutes -= 1;
        }
        if (minutes < 0) {
            stopTimer();
            minutes = 0;
            seconds = 0;
            microseconds = 0;
        }
        updateLabel();
    }

    private void reset(TextField minutesField,TextField secondsField){
        Alert resetAlert = new Alert(Alert.AlertType.CONFIRMATION);
        resetAlert.setTitle("Confirmation Reset");
        resetAlert.setHeaderText("Are you sure you want to Reset the Timer");

        ButtonType okButton = new ButtonType("OK");
        ButtonType cancelBtn = new ButtonType("Cancel");

        resetAlert.getButtonTypes().setAll(okButton,cancelBtn);

        resetAlert.showAndWait().ifPresent(response ->{
            if(response == okButton){
                minutes = 0;
                seconds = 0;
                microseconds = 0;
                updateLabel();
                minutesField.setText(String.valueOf(minutes));
                secondsField.setText(String.valueOf(seconds));
                edited = false;
                resetAlert.close();
            }
            if(response == cancelBtn){
                resetAlert.close();
            }
        });
    }

    private void updateLabel() {
        String label = String.format("%02d:%02d:%03d", minutes, seconds, microseconds);
        timerLabel.setText(label);
    }

    public static void main(String[] args) {
        launch(args);
    }
}