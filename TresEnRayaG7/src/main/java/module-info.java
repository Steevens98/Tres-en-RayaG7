module com.mycompany.tresenrayag7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens com.mycompany.tresenrayag7 to javafx.fxml;
    exports com.mycompany.tresenrayag7;
}
