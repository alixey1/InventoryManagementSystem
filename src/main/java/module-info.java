module amandalixey.lixeysoftware1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens amandalixey.lixeysoftware1 to javafx.fxml;
    exports amandalixey.lixeysoftware1;
}