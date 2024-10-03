module com.example.granny_gains_new {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.prefs;

    requires javafx.web;

    requires com.opencsv;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;


    opens com.example.granny_gains_new to javafx.fxml;
    exports com.example.granny_gains_new;
    exports com.example.granny_gains_new.controller;
    opens com.example.granny_gains_new.controller to javafx.fxml;
    exports com.example.granny_gains_new.database;
    opens com.example.granny_gains_new.database to javafx.fxml;
}