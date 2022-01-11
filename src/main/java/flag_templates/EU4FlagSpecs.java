package flag_templates;

import java.io.File;

public class EU4FlagSpecs extends GenericFlagTemplate
{
    final int baseFlagWidth = 128;
    final int baseFlagHeight = 128;

    public EU4FlagSpecs(File sourceFlagFile, File outputFile, String outputFilePathString, String name, String suffix)
    {
        super(sourceFlagFile, outputFile, outputFilePathString, name, suffix);
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
