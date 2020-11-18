module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens tropico.Object to com.google.gson;
    opens tropico.Controller to javafx.fxml;
    exports tropico.view;
}