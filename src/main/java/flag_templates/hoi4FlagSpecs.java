package flag_templates;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class hoi4FlagSpecs extends GenericFlagTemplate
{
    final int mediumFlagWidth = 41;
    final int mediumFlagHeight = 26;
    final int smallFlagWidth = 10;
    final int smallFlagHeight = 7;

    public hoi4FlagSpecs(int flagWidth, int flagHeight)
    {
        super(flagWidth, flagHeight);
    }
    @Override
    public void createFlagFolders(){
        super.createFlagFolders();
        /// Medium Flags folder
        super.createRequiredFolders(super.getOutputFileFolderPath(), "medium");
        /// Small Flags folder
        super.createRequiredFolders(super.getOutputFileFolderPath(), "small");
    }

    @Override
    public void createFlag()
    {
        try {
            /// Creating Large Flag
            BufferedImage largeFlag = ImageIO.read(getSourceFlagLocation());
            largeFlag = resizeImage(largeFlag,super.getBaseFlagWidth(),super.getBaseFlagHeight());
            convertToTGA(createFlipped(largeFlag),new File(super.getOutputFileFolderPath() + "\\" + super.getFlagName() + super.getFlagNameSuffix() + ".tga"));
            /// Creating Medium Flag
            BufferedImage mediumFlag = ImageIO.read(getSourceFlagLocation());
            mediumFlag = resizeImage(mediumFlag,mediumFlagWidth,mediumFlagHeight);
            convertToTGA(createFlipped(mediumFlag),new File(super.getOutputFileFolderPath() + "\\medium\\" + super.getFlagName() + super.getFlagNameSuffix() + ".tga"));
            /// Creating Small Flag
            BufferedImage smallFlag = ImageIO.read(getSourceFlagLocation());
            smallFlag = resizeImage(mediumFlag,smallFlagWidth,smallFlagHeight);
            convertToTGA(createFlipped(smallFlag),new File(super.getOutputFileFolderPath() + "\\small\\" + super.getFlagName() + super.getFlagNameSuffix() + ".tga"));

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
}
