package flag_templates;

import java.io.File;
public class EU3FlagSpecs extends GenericFlagTemplate
{

    private final int baseFlagWidth = 64;
    private final int baseFlagHeight = 64;

    public EU3FlagSpecs(File sourceFlagLocation, String sourceFileLocationPath, File outputFolderLocation, String outputFileFolderPath, String name, String suffix)
    {
        super(sourceFlagLocation,sourceFileLocationPath,outputFolderLocation,outputFileFolderPath,name,suffix);
    }

    @Override
    public int getBaseFlagWidth()
    {
        return baseFlagWidth;
    }

    @Override
    public int getBaseFlagHeight()
    {
        return baseFlagHeight;
    }
}
