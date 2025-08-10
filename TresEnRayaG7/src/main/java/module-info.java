
module com.mycompany.tresenrayag7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    exports com.mycompany.Controllers;
    exports com.mycompany.tresenrayag7;
    opens com.mycompany.tresenrayag7 to javafx.fxml;
    opens com.mycompany.Controllers to javafx.fxml;
}
