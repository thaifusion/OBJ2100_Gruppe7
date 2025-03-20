module com.restaurantsim {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.restaurantsim to javafx.fxml;
    exports com.restaurantsim;
}
