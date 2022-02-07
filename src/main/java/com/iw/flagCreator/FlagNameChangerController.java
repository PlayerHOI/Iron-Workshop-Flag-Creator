package com.iw.flagCreator;

import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;

public class FlagNameChangerController
{

    @FXML
    protected Text topDescText;

    @FXML
    protected TextField filelocationTextField;

    @FXML
    protected TextField oldtextTextField;

    @FXML
    protected TextField newTextTextField;

    @FXML
    protected Button commitChangesButton;

    public void initialize(){
        topDescText.setText("This tool allows batch renaming of file names.");

        filelocationTextField.textProperty().addListener(
            (ObservableValue<? extends String> observable,String oldValue, String newValue) -> {
            if (newValue.isEmpty()) {
                commitChangesButton.setDisable(true);
            }
        });

        oldtextTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable,String oldValue, String newValue) -> {
                if (newValue.isEmpty()) {
                    commitChangesButton.setDisable(true);
                }
        });

        newTextTextField.textProperty().addListener(
                (ObservableValue<? extends String> observable,String oldValue, String newValue) -> {
                if (newValue.isEmpty()) {
                    commitChangesButton.setDisable(true);
                }else {
                    commitChangesButton.setDisable(false);
                }
        });
    }

    public void openFilesLocationFolderLocator(){
        DirectoryChooser outputFolderChooser = new DirectoryChooser();
        File selectedDirectory = outputFolderChooser.showDialog(null);
        if(selectedDirectory != null){
            filelocationTextField.setText(String.valueOf(selectedDirectory));
        }
    }
    public void commitChangesClick()
    {
        File dir = new File(filelocationTextField.getText());
        if (dir.isDirectory())
        { // make sure it's a directory
            for (final File f : dir.listFiles())
            {
                try
                {
                    String oldFileName = f.getName();
                    String newFileName = "";
                    newFileName = oldFileName.replaceAll(oldtextTextField.getText(),newTextTextField.getText());
                    File newfile = new File(filelocationTextField.getText() + "\\" + newFileName);
                    if (f.renameTo(newfile))
                    {
                        System.out.println("Rename succesful");
                    } else
                    {
                        System.out.println("Rename failed");
                    }
                } catch (Exception e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
            }
        }
    }
}
