package mod.dalhome3.spaceexplorer;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(SpaceExplorer.MODID)
public final class SpaceExplorer {
    public static final String MODID="space-explorer";

    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public SpaceExplorer() {
        LOGGER.debug("Welcome to Space Explorer!");
    }
}
