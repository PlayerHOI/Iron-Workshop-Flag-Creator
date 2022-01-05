package com.iw.flagCreator;

import com.twelvemonkeys.image.ResampleOp;
import flag_templates.EU4FlagSpecs;
import flag_templates.GenericFlagTemplate;
import flag_templates.hoi4FlagSpecs;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.net.URL;

public class MainController
{
    @FXML
    protected AnchorPane mainWindowElement;

    @FXML
    protected ImageView hoi4GameIcon;

    @FXML
    protected ImageView hoi3GameIcon;

    @FXML
    protected ImageView eu4GameIcon;

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
    protected HBox flagPreviewElement;

    @FXML
    protected HBox gameSelectionElement;

    @FXML
    protected Button createFlagButton;

    @FXML
    protected Button previewFlagButton;

    File imageToConvert = new File("C:\\Users\\PlayerPC\\Desktop\\IW Flag Creator\\hoi4FlagTemplate.tga");
    File outputImage = new File("");
    GenericFlagTemplate newFlagSpecs = null;

    @FXML
    public void initialize(){
        /// Listener to flag source text field to prevent generating preview when no file is loaded
        flagFilePathTextField.textProperty().addListener((ObservableValue<? extends String> observable, String oldValue, String newValue) -> {
            if (!(newValue.isEmpty())) {
                imageToConvert = new File(flagFilePathTextField.getText());
                previewFlagButton.setDisable(false);
            }
            if (newValue.isEmpty()) {
                imageToConvert = new File("");
                previewFlagButton.setDisable(true);
            }
        });
    }

    @FXML
    public void createFlagHoi4(){
        gameIconClickEffect();
        hoi4GameIcon.setOpacity(100);
        newFlagSpecs = new hoi4FlagSpecs(imageToConvert,flagFilePathTextField.getText(),outputImage,outputFolderTextField.getText(),flagTagTextField.getText(),flagSuffixTextField.getText());
    }

    @FXML
    public void createFlagVic2(){
        gameIconClickEffect();
        hoi3GameIcon.setOpacity(100);
        newFlagSpecs = new GenericFlagTemplate(imageToConvert,flagFilePathTextField.getText(),outputImage,outputFolderTextField.getText(),flagTagTextField.getText(),flagSuffixTextField.getText());
    }

    @FXML
    public void createFlagEU4(){
        gameIconClickEffect();
        eu4GameIcon.setOpacity(100);
        newFlagSpecs = new EU4FlagSpecs(imageToConvert,flagFilePathTextField.getText(),outputImage,outputFolderTextField.getText(),flagTagTextField.getText(),flagSuffixTextField.getText());
    }


    public void gameIconClickEffect(){
        for (Node child : gameSelectionElement.getChildren())
        {
            child.setOpacity(0.25);
        }
    }

    @FXML
    public void createFlagButtonClick(){
        newFlagSpecs.createFlagFolders();
        newFlagSpecs.createFlag();
        gameIconClickEffect();
    }

    public void generateFlagPreview(){
        if(newFlagSpecs.getBaseFlagWidth()==82){
            generateThreeFlagPreview();
        }else {
            generateSingleFlagPreview();
            gameIconClickEffect();
        }
    }

    /// Generates a 3 flag preview for HOI4 flags
    public void generateThreeFlagPreview(){
        /// Removes all flags from Node to prevent buildup of too many flags
        flagPreviewElement.getChildren().removeAll(flagPreviewElement.getChildren());
        /// Resets imageToConvert to prevent conflict and writes flag from URL
        if(flagFilePathTextField.getText().startsWith("https://")){
            imageToConvert = new File("C:\\Users\\PlayerPC\\Desktop\\IW Flag Creator\\hoi4FlagTemplate.tga");
            try {
                URL flagURL = new URL(flagFilePathTextField.getText());
                BufferedImage img = ImageIO.read(flagURL);
                ImageIO.write(img, "png", imageToConvert);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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
        /// Removes all flags from Node to prevent buildup of too many flags
        flagPreviewElement.getChildren().removeAll(flagPreviewElement.getChildren());
        /// Resets imageToConvert to prevent conflict and writes flag from URL
        if(flagFilePathTextField.getText().startsWith("https://")){
            imageToConvert = new File("C:\\Users\\PlayerPC\\Desktop\\IW Flag Creator\\hoi4FlagTemplate.tga");
            try {
                URL flagURL = new URL(flagFilePathTextField.getText());
                BufferedImage img = ImageIO.read(flagURL);
                ImageIO.write(img, "png", imageToConvert);
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
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