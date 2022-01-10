package flag_templates;

import com.iw.flagCreator.MainController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

public class hoi4FlagSpecs extends GenericFlagTemplate
{
    private String outputFileFolderPath = getOutputFileFolderPath();
    private final File outputFolderLocation = getOutputFolderLocation();
    private final int baseFlagWidth = 82;
    private final int baseFlagHeight = 52;
    private final int mediumFlagWidth = 41;
    private final int mediumFlagHeight = 26;
    private final int smallFlagWidth = 10;
    private final int smallFlagHeight = 7;
    private String sourceFileLocationPath;

    public hoi4FlagSpecs(File sourceFlagLocation, String sourceFileLocationPath, File outputFolderLocation,String outputFileFolderPath,String name,String suffix)
    {
        super(sourceFlagLocation,sourceFileLocationPath,outputFolderLocation,outputFileFolderPath,name,suffix);
    }

    @Override
    public void createFlagFolders(){
        super.createFlagFolders();
        /// Medium Flags folder
        outputFileFolderPath = outputFileFolderPath + "\\"+ "flags";
        super.createRequiredFolders(outputFileFolderPath, "medium");
        /// Small Flags folder
        super.createRequiredFolders(outputFileFolderPath, "small");
    }

    @Override
    public void createFlag()
    {
        try {
            /// Creating Large Flag
            BufferedImage largeFlag = ImageIO.read(getSourceFlagLocation());
            largeFlag = resizeImage(largeFlag,baseFlagWidth,baseFlagHeight);
            convertToTGA(createFlipped(largeFlag),new File(outputFileFolderPath + "\\" + super.getFlagName() + super.getFlagNameSuffix() + ".tga"));
            /// Creating Medium Flag
            BufferedImage mediumFlag = ImageIO.read(getSourceFlagLocation());
            mediumFlag = resizeImage(mediumFlag,mediumFlagWidth,mediumFlagHeight);
            convertToTGA(createFlipped(mediumFlag),new File(outputFileFolderPath + "\\medium\\" + super.getFlagName() + super.getFlagNameSuffix() + ".tga"));
            /// Creating Small Flag
            BufferedImage smallFlag = ImageIO.read(getSourceFlagLocation());
            smallFlag = resizeImage(mediumFlag,smallFlagWidth,smallFlagHeight);
            convertToTGA(createFlipped(smallFlag),new File(outputFileFolderPath + "\\small\\" + super.getFlagName() + super.getFlagNameSuffix() + ".tga"));

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static BufferedImage createFlipped(BufferedImage image)
    {
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -image.getHeight()));
        return createTransformed(image, at);
    }

    private static BufferedImage createTransformed( BufferedImage image, AffineTransform at)
    {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(),BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

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

    @Override
    public int getBaseFlagWidth()
    {
        return this.baseFlagWidth;
    }

    @Override
    public int getBaseFlagHeight()
    {
        return this.baseFlagHeight;
    }
}
