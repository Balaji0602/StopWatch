module com.example.finaljavafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.finaljavafx to javafx.fxml;
    exports com.example.finaljavafx;
}