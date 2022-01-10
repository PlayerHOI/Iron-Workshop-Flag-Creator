package flag_templates;

import com.twelvemonkeys.image.ResampleOp;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

public class GenericFlagTemplate
{
    private final String sourceFileLocationPath;
    private final File sourceFlagLocation;
    private File outputFolderLocation;
    private String outputFileFolderPath;
    private String flagName;
    private final String flagNameSuffix;
    private final int baseFlagWidth = 93;
    private final int baseFlagHeight = 64;

    public GenericFlagTemplate(File sourceFlagLocation, String sourceFileLocationPath, File outputFile,String outputFileFolderPath,String name,String suffix)
    {
        this.sourceFileLocationPath = sourceFileLocationPath;
        this.sourceFlagLocation = sourceFlagLocation;
        this.outputFolderLocation = outputFile;
        this.outputFileFolderPath = outputFileFolderPath;
        this.flagName = name;
        this.flagNameSuffix = suffix;
    }

    public File getSourceFlagLocation()
    {
        return sourceFlagLocation;
    }

    public String getSourceFileLocationPath()
    {
        return sourceFileLocationPath;
    }

    public File getOutputFolderLocation()
    {
        return outputFolderLocation;
    }

    public String getOutputFileFolderPath()
    {
        return outputFileFolderPath;
    }

    public String getFlagName()
    {
        return flagName;
    }

    public String getFlagNameSuffix()
    {
        return flagNameSuffix;
    }

    public int getBaseFlagWidth()
    {
        return baseFlagWidth;
    }

    public int getBaseFlagHeight()
    {
        return baseFlagHeight;
    }

    public void createFlagFolders(){
        createRequiredFolders(outputFileFolderPath, "flags");
        outputFileFolderPath = outputFileFolderPath + "//" + "flags";
        outputFolderLocation = new File(outputFileFolderPath + "\\" + this.flagName + this.flagNameSuffix + ".tga");
        System.out.println(outputFileFolderPath);
    }
    protected void createRequiredFolders(String folderPath, String folderName)
    {
        System.out.println(outputFileFolderPath);
        File newFolderCreator = new File(folderPath + "\\" + folderName);
        System.out.println(folderPath+folderName);
        newFolderCreator.mkdir();
    }

    public void setFlagName(String name){
        flagName = name;
    }

    /////////////////////////////////////////
    ////////// FLAG CREATING LOGIC //////////
    /////////////////////////////////////////

    public void createFlag() throws IOException
    {
        BufferedImage image = ImageIO.read(sourceFlagLocation);
        image = resizeImage(image,this.getBaseFlagWidth(),this.getBaseFlagHeight());
        convertToTGA(image,outputFolderLocation);
    }
    public BufferedImage resizeImage(BufferedImage imageToResize, int width, int height)
    {
        BufferedImageOp resampledImage = new ResampleOp(width, height, ResampleOp.FILTER_LANCZOS); // A good default filter, see class documentation for more info
        return resampledImage.filter(imageToResize, null);
    }

    public void convertToTGA (BufferedImage imageToConvert, File saveToFile){
        try {
            // Converting given image to TGA format
            if (!ImageIO.write(imageToConvert, "TGA", saveToFile)) {
                System.out.println("Failed");
            }
            // Converting TGA file to 32bpp
            BufferedImage finalImage = ImageIO.read(saveToFile);
            finalImage = convertTo32(finalImage);
            ImageIO.write(finalImage,"TGA",saveToFile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static BufferedImage convertTo32(BufferedImage imageToConvert)
    {
        BufferedImage copy = new BufferedImage(imageToConvert.getWidth(), imageToConvert.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = copy.createGraphics();
        try {
            g.setComposite(AlphaComposite.Src);
            g.drawImage(imageToConvert, 0, 0, null);
        }
        finally {
            g.dispose();
        }
        return copy;
    }
}
