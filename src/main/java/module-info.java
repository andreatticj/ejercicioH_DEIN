module eu.andreatt.ejerciciof_dein {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.slf4j;
    requires java.sql;


//    opens eu.andreatt.ejercicioe_dein to javafx.fxml;
//    exports eu.andreatt.ejercicioe_dein;
    exports eu.andreatt.ejercicioh_dein.controller;
    opens eu.andreatt.ejercicioh_dein.controller to javafx.fxml;
    exports eu.andreatt.ejercicioh_dein.application;
    opens eu.andreatt.ejercicioh_dein.application to javafx.fxml;
}