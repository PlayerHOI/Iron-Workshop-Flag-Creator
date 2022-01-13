module com.iw.iw_flag_creator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires com.twelvemonkeys.imageio.tga;
    //requires com.twelvemonkeys.common.image;
    requires org.apache.commons.io;
    requires batik.transcoder;
    //requires com.twelvemonkeys.imageio.batik;
    requires common.image;


    opens com.iw.flagCreator to javafx.fxml;
    exports com.iw.flagCreator;
}