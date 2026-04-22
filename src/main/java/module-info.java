module edu.utsa.cs3443.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens edu.utsa.cs3443.demo to javafx.fxml;
    exports edu.utsa.cs3443.demo;
    exports edu.utsa.cs3443.demo.model;
    opens edu.utsa.cs3443.demo.model to javafx.fxml;
}