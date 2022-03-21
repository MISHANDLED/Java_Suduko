module com.example.suduko {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.suduko to javafx.fxml;
    exports com.example.suduko;
}