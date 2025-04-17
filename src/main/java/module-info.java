module fr.bts.sio {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires jbcrypt;

    opens fr.bts.sio to javafx.fxml, javafx.graphics;
    opens fr.bts.sio.Config to com.fasterxml.jackson.databind;
    opens fr.bts.sio.Controllers to javafx.fxml;
    exports fr.bts.sio;
}