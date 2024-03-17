package survivalblock.tameable_axolotls;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TameableAxolotls implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("tameable_axolotls");

	@Override
	public void onInitialize() {
		if(FabricLoader.getInstance().isDevelopmentEnvironment()){
			LOGGER.info(" \"Tell you someday baby, you and I should be one\" - Bee Gees ");
		}
	}
}