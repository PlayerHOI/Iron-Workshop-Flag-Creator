package flag_templates;

import java.io.File;
public class EU3FlagSpecs extends GenericFlagTemplate
{

    final int baseFlagWidth = 64;
    final int baseFlagHeight = 64;

    public EU3FlagSpecs(File sourceFlagFile, File outputFile, String outputFilePathString, String name, String suffix)
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
