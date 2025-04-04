module com.example.superhexagon {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;

    opens com.example.superhexagon to javafx.fxml;
    exports com.example.superhexagon;
}