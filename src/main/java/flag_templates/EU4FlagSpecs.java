package flag_templates;

import java.io.File;

public class EU4FlagSpecs extends GenericFlagTemplate
{

    private final int baseFlagWidth = 128;
    private final int baseFlagHeight = 128;

    public EU4FlagSpecs(File sourceFlagLocation, String sourceFileLocationPath, File outputFolderLocation, String outputFileFolderPath, String name, String suffix)
    {
        super(sourceFlagLocation,sourceFileLocationPath,outputFolderLocation,outputFileFolderPath,name,suffix);
    }

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
