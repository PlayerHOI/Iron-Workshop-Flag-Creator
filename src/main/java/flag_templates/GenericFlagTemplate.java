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
    private static File sourceFlagLocation;
    private static File outputFolderLocation;
    private static String outputFileFolderPath;
    private static String flagName;
    private static String flagNameSuffix;
    private static int baseFlagWidth = 93;
    private static int baseFlagHeight = 64;

    public GenericFlagTemplate(int flagWidth, int flagHeight)
    {
        baseFlagWidth = flagWidth;
        baseFlagHeight = flagHeight;
    }

    public void setSourceFlagLocation(File sourceFlagLocation)
    {
        GenericFlagTemplate.sourceFlagLocation = sourceFlagLocation;
    }

    public void setOutputFolderLocation(File outputFolderLocation)
    {
        GenericFlagTemplate.outputFolderLocation = outputFolderLocation;
    }

    public void setOutputFileFolderPath(String outputFileFolderPath)
    {
        GenericFlagTemplate.outputFileFolderPath = outputFileFolderPath;
    }

    public void setFlagName(String name){
        GenericFlagTemplate.flagName = name;
    }

    public void setFlagNameSuffix(String flagNameSuffix)
    {
        GenericFlagTemplate.flagNameSuffix = flagNameSuffix;
    }

    public File getSourceFlagLocation()
    {
        return sourceFlagLocation;
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
        outputFolderLocation = new File(outputFileFolderPath + "\\" + flagName + flagNameSuffix + ".tga");
        System.out.println(outputFileFolderPath);
    }
    protected void createRequiredFolders(String folderPath, String folderName)
    {
        System.out.println(outputFileFolderPath);
        File newFolderCreator = new File(folderPath + "\\" + folderName);
        System.out.println(folderPath+folderName);
        newFolderCreator.mkdir();
    }

    //////////////////////////////////////////////////////////////////////////////////
    ////////// FLAG CREATING LOGIC | SHOULD GO INTO ITS OWN CLASS LATER //////////////
    //////////////////////////////////////////////////////////////////////////////////

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
            //BufferedImage finalImage = ImageIO.read(saveToFile);
            //finalImage = convertTo32(finalImage);
            //ImageIO.write(finalImage,"TGA",saveToFile);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

//    public static BufferedImage convertTo32(BufferedImage imageToConvert)
//    {
//        BufferedImage copy = new BufferedImage(imageToConvert.getWidth(), imageToConvert.getHeight(), BufferedImage.TYPE_INT_ARGB);
//
//        Graphics2D g = copy.createGraphics();
//        try {
//            g.setComposite(AlphaComposite.Src);
//            g.drawImage(imageToConvert, 0, 0, null);
//        }
//        finally {
//            g.dispose();
//        }
//        return copy;
//    }
//    Unused, may come to use later
//    public void copyFiles() throws IOException
//    {
//        InputStream is = null;
//        OutputStream os = null;
//        try {
//            is = new FileInputStream(this.getSourceFileLocationPath());
//            os = new FileOutputStream(outputFileFolderPath + "\\medium\\" + super.getFlagName() + super.getFlagNameSuffix() + ".tga");
//            byte[] buffer = new byte[1024];
//            int length;
//            while ((length = is.read(buffer)) > 0) {
//                os.write(buffer, 0, length);
//            }
//        } catch (IOException e)
//        {
//            e.printStackTrace();
//        } finally {
//            is.close();
//            os.close();
//        }
//    }
}
