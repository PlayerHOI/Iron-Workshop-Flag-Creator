module com.iw.iw_flag_creator {
    requires batik.transcoder;
    requires com.twelvemonkeys.imageio.batik;
    requires com.twelvemonkeys.common.image;
    requires com.twelvemonkeys.imageio.tga;
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.apache.commons.io;
    //requires common.image;


    opens com.iw.flagCreator to javafx.fxml;
    exports com.iw.flagCreator;
}