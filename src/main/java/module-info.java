module com.example.roguelike {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.roguelike to javafx.fxml;
    exports com.example.roguelike;
}