module com.restaurantsim {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.restaurantsim to javafx.fxml;
    exports com.restaurantsim;
}
