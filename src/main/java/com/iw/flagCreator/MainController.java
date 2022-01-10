package com.iw.flagCreator;

import com.twelvemonkeys.image.ResampleOp;
import flag_templates.EU3FlagSpecs;
import flag_templates.EU4FlagSpecs;
import flag_templates.GenericFlagTemplate;
import flag_templates.hoi4FlagSpecs;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.net.URL;

public class MainController
{

    @FXML
    protected ImageView patreonIcon;

    @FXML
    protected AnchorPane mainWindowElement;

    @FXML
    protected Button hoi4GameIcon;

    @FXML
    protected Button hoi3GameIcon;

    @FXML
    protected Button eu4GameIcon;

    @FXML
    protected Button eu3GameIcon;

    @FXML
    protected ImageView flagPathFolderIcon;

    @FXML
    protected ImageView outputFolderPathIcon;

    @FXML
    protected TextField flagFilePathTextField;

    @FXML
    protected TextField outputFolderTextField;

    @FXML
    protected TextField flagTagTextField;

    @FXML
    protected TextField flagSuffixTextField;

    @FXML
    public Label notificationsLabel;

    @FXML
    protected HBox flagPreviewElement;

    @FXML
    protected HBox gameSelectionElement;

    @FXML
    protected Button createFlagButton;

    @FXML
    protected Button previewFlagButton;

    @FXML
    protected Label flagCreatorVersionLabel;

    String dummyFlagFilePath = "flagTemplateFile.png";
    File imageToConvert = new File(dummyFlagFilePath);
    File outputImage = new File("");
    GenericFlagTemplate newFlagSpecs = null;
    String flagCreatorVersion = "1.0";

    @FXML
    public void initialize(){
        flagCreatorVersionLabel.setText("Version: " + flagCreatorVersion);
        openAboutPopup();
        /// Listener to flag source text field to prevent generating preview when no file is loaded
        flagFilePathTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            // Disable Preview Button if no flag is loaded
            if (!(newValue.isEmpty()) && flagPathIsProperFile(flagFilePathTextField.getText())) {
                imageToConvert = new File(flagFilePathTextField.getText());
                previewFlagButton.setDisable(false);
            }
            if (newValue.isEmpty() || (!flagPathIsProperFile(flagFilePathTextField.getText()))) {
                imageToConvert = new File("");
                previewFlagButton.setDisable(true);
            }
            // Disable Create Button if no flag is loaded
            if (!(newValue.isEmpty()) && flagPathIsProperFile(flagFilePathTextField.getText())) {
                createFlagButton.setDisable(false);
            }
            if (newValue.isEmpty() || (!flagPathIsProperFile(flagFilePathTextField.getText()))) {
                createFlagButton.setDisable(true);
            }
        });
    }

    @FXML
    public void openPatreonPage(){
        try {
            Desktop.getDesktop().browse(new URL("https://www.patreon.com/playerhoi").toURI());
        } catch (Exception e) {}
    }

    @FXML
    public void openGitHubPage(){
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/PlayerHOI/IWFC").toURI());
        } catch (Exception e) {}
    }

    @FXML
    public void openDiscordPage(){
        try {
            Desktop.getDesktop().browse(new URL("https://discord.gg/sSCU3WS").toURI());
        } catch (Exception e) {}
    }

    public void openKnownIssuesPage(){
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/PlayerHOI/IWFC/issues/1").toURI());
        } catch (Exception e) {}
    }

    public void openFlagCreatorReleasesPage(){
        try {
            Desktop.getDesktop().browse(new URL("https://github.com/PlayerHOI/IWFC/releases").toURI());
        } catch (Exception e) {}
    }

    @FXML
    public void openAboutPopup(){
        Alert aboutPopup = new Alert(Alert.AlertType.INFORMATION);
        aboutPopup.setTitle("Iron Workshop Flag Creator " + flagCreatorVersion);
        aboutPopup.setHeaderText("Created by PlayerHOI\nA product of the Iron Workshop\nironworkshopbiz@gmail.com\n");
        aboutPopup.setContentText("Use this software at your own discretion, any consequence of using this software" +
                " is the sole responsibility of the user.\n\nClick on 'Help' -> 'How to use' section for instructions on how " +
                "to use the flag creator.\n\nSupported file formats: JPEG, JPG, TGA, PNG and BMP.");
        aboutPopup.setWidth(200);
        aboutPopup.setHeight(500);
        aboutPopup.showAndWait();
    }

    /// Methods to create instances of flags
    @FXML
    public void createFlagHoi4(){
        gameIconClickEffect();
        hoi4GameIcon.getStyleClass().add("gameIconClickedClass");
        newFlagSpecs = new hoi4FlagSpecs(imageToConvert,flagFilePathTextField.getText(),
                outputImage,outputFolderTextField.getText(),flagTagTextField.getText(),flagSuffixTextField.getText());
    }

    @FXML
    public void createFlagVic2(){
        gameIconClickEffect();
        hoi3GameIcon.getStyleClass().add("gameIconClickedClass");
        newFlagSpecs = new GenericFlagTemplate(imageToConvert,flagFilePathTextField.getText()
                ,outputImage,outputFolderTextField.getText(),flagTagTextField.getText(),flagSuffixTextField.getText());
    }

    @FXML
    public void createFlagEU4(){
        gameIconClickEffect();
        eu4GameIcon.getStyleClass().add("gameIconClickedClass");
        newFlagSpecs = new EU4FlagSpecs(imageToConvert,flagFilePathTextField.getText()
                ,outputImage,outputFolderTextField.getText(),flagTagTextField.getText(),flagSuffixTextField.getText());
    }

    @FXML
    public void createFlagEU3(){
        gameIconClickEffect();
        eu3GameIcon.getStyleClass().add("gameIconClickedClass");
        newFlagSpecs = new EU3FlagSpecs(imageToConvert,flagFilePathTextField.getText()
                ,outputImage,outputFolderTextField.getText(),flagTagTextField.getText(),flagSuffixTextField.getText());
    }

    @FXML
    public void gameIconHoverEffectHOI4(){
        hoi4GameIcon.getStyleClass().add("gameIconHoverClass");
    }

    @FXML
    public void gameIconHoverEffectHOI3(){
        hoi3GameIcon.getStyleClass().add("gameIconHoverClass");
    }

    @FXML
    public void gameIconHoverEffectEU3(){
        eu3GameIcon.getStyleClass().add("gameIconHoverClass");
    }

    @FXML
    public void gameIconHoverEffectEU4(){
        eu4GameIcon.getStyleClass().add("gameIconHoverClass");
    }

    @FXML
    public void gameIconRemoveHoverEffectHOI4(){
        hoi4GameIcon.getStyleClass().remove("gameIconHoverClass");
    }

    @FXML
    public void gameIconRemoveHoverEffectHOI3(){
        hoi3GameIcon.getStyleClass().remove("gameIconHoverClass");
    }

    @FXML
    public void gameIconRemoveHoverEffectEU3(){
        eu3GameIcon.getStyleClass().remove("gameIconHoverClass");
    }

    @FXML
    public void gameIconRemoveHoverEffectEU4(){
        eu4GameIcon.getStyleClass().remove("gameIconHoverClass");
    }

    public void gameIconClickEffect(){
        for (Node child : gameSelectionElement.getChildren())
        {
            child.getStyleClass().remove("gameIconClickedClass");
        }
    }

    public void checkIfFlagObjectExists(){
        if(flagFilePathTextField.getText().startsWith("https://")){
            imageToConvert = new File(dummyFlagFilePath);
            try {
                URL flagURL = new URL(flagFilePathTextField.getText());
                BufferedImage img = ImageIO.read(flagURL);
                ImageIO.write(img, "png", imageToConvert);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void createFlagButtonClick(){
        checkIfNotificationNeeded();
        gameIconClickEffect();
        newFlagSpecs.createFlagFolders();
        checkIfFlagObjectExists();
        try
        {
            newFlagSpecs.setFlagName(flagTagTextField.getText());
            newFlagSpecs.createFlag();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void checkIfNotificationNeeded(){
        if(outputFolderTextField.getText().isEmpty()){
            createWarningPopup("No output folder set", "Make sure an output folder has been selected");
        }
        if(newFlagSpecs==null){
            createWarningPopup("No Game Selected", "Click on a game icon before creating the flag");
        }
        if(newFlagSpecs instanceof hoi4FlagSpecs && !(outputFolderTextField.getText().isEmpty())){
            createWarningPopup("Important", "Hearts of Iron 4 flags files " +
                    "are created upside down due " +
                    "to an ongoing issue, the flags will appear properly in-game.");
        }
    }

    public void createWarningPopup(String alertTitle ,String alertText){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(alertTitle);
        alert.setHeaderText(null);
        alert.setContentText(alertText);
        alert.showAndWait();
    }

    public void generateFlagPreview(){
        if(newFlagSpecs==null){
            createWarningPopup("No Game Selected", "No game selected, click on a game icon before " +
                    "generating a preview.");
        }
        if(newFlagSpecs instanceof hoi4FlagSpecs){
            generateThreeFlagPreview();
        }else {
            generateSingleFlagPreview();
            gameIconClickEffect();
        }
    }

    public boolean flagPathIsProperFile(String flagPath){
        if(flagPath.endsWith("jpg") || flagPath.endsWith("jpeg")
                || flagPath.endsWith("png") || flagPath.endsWith("bmp")
                || flagPath.endsWith("tga")) {
            return true;
        }
        if(flagPath.endsWith("svg") && flagPath.contains("wikipedia.org")){
                Alert fileNotSupportedPopup = new Alert(Alert.AlertType.ERROR);
            fileNotSupportedPopup.setTitle("File type not supported");
            fileNotSupportedPopup.setHeaderText("SVG files are not supported and cannot be used");
            fileNotSupportedPopup.setContentText("It looks like you are trying to use a flag file from Wikipedia.\n" +
                    "see 'Help' -> 'How to use' " +
                    "on how to retrieve flags from Wikipedia.");
            fileNotSupportedPopup.showAndWait();
            flagFilePathTextField.clear();
        }
        return false;
    }

    /// Generates a 3 flag preview for HOI4 flags
    public void generateThreeFlagPreview(){
        /// Removes all flags from Node to prevent buildup of too many flags
        flagPreviewElement.getChildren().removeAll(flagPreviewElement.getChildren());
        /// Resets imageToConvert to prevent conflict and writes flag from URL
        checkIfFlagObjectExists();
        try {
            flagPreviewElement.setSpacing(20);
            // First Flag
            BufferedImage flagPreviewLarge = ImageIO.read(imageToConvert);
            flagPreviewLarge = resizeImage(flagPreviewLarge,82,52);
            flagPreviewElement.getChildren().add(convertImageToNode(flagPreviewLarge));
            // Second Flag
            BufferedImage flagPreviewMedium = ImageIO.read(imageToConvert);
            flagPreviewMedium = resizeImage(flagPreviewMedium,41,26);
            flagPreviewElement.getChildren().add(convertImageToNode(flagPreviewMedium));
            // Third Flag
            BufferedImage flagPreviewSmall = ImageIO.read(imageToConvert);
            flagPreviewSmall = resizeImage(flagPreviewSmall,10,7);
            flagPreviewElement.getChildren().add(convertImageToNode(flagPreviewSmall));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /// Generates a single flag preview
    public void generateSingleFlagPreview(){
        /// Removes all flags from Node to prevent buildup of too many flags
        flagPreviewElement.getChildren().removeAll(flagPreviewElement.getChildren());
        /// Resets imageToConvert to prevent conflict and writes flag from URL
        checkIfFlagObjectExists();
        try {
            BufferedImage flagPreviewLarge = ImageIO.read(imageToConvert);
            flagPreviewLarge = resizeImage(flagPreviewLarge,newFlagSpecs.getBaseFlagWidth(),newFlagSpecs.getBaseFlagHeight());
            flagPreviewElement.getChildren().add(convertImageToNode(flagPreviewLarge));
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /// Converts flag image to Node to allow showing in preview Node
    public ImageView convertImageToNode(BufferedImage imageToConvert){
        BufferedImage bf = imageToConvert;
        WritableImage wr = null;
        if (bf != null) {
            wr = new WritableImage(bf.getWidth(), bf.getHeight());
            PixelWriter pw = wr.getPixelWriter();
            for (int x = 0; x < bf.getWidth(); x++) {
                for (int y = 0; y < bf.getHeight(); y++) {
                    pw.setArgb(x, y, bf.getRGB(x, y));
                }
            }
        }

        return new ImageView(wr);
    }

    /// Opens source flag locator window
    @FXML
    public void openFlagFolderLocator(){
        FileChooser flagFileChooser = new FileChooser();
        File selectedDirectory = flagFileChooser.showOpenDialog(null);
        if(selectedDirectory != null){
            flagFilePathTextField.setText(String.valueOf(selectedDirectory));
        }
    }

    /// Opens output flag folder locator
    public void openOutputFolderLocator(){
        DirectoryChooser outputFolderChooser = new DirectoryChooser();
        File selectedDirectory = outputFolderChooser.showDialog(null);
        if(selectedDirectory != null){
            outputFolderTextField.setText(String.valueOf(selectedDirectory));
        }
    }

    //// Resize method is used to generate previews, should stay here
    public static BufferedImage resizeImage(BufferedImage imageToResize, int width, int height)
    {
        BufferedImageOp resampledImage = new ResampleOp(width, height, ResampleOp.FILTER_LANCZOS); // A good default filter, see class documentation for more info
        return resampledImage.filter(imageToResize, null);
    }
}