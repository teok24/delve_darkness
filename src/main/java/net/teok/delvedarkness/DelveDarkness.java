package net.teok.delvedarkness;

import net.fabricmc.api.ModInitializer;
import net.teok.delvedarkness.config.DelveDarknessConfig;
import net.teok.delvedarkness.networking.ModMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DelveDarkness implements ModInitializer {

	//Test Commit
	public static final String MOD_ID = "delvedarkness";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);



	public static final DelveDarknessConfig config = DelveDarknessConfig.createAndLoad();

	@Override
	public void onInitialize() {
		ModMessages.registerC2SPackets();

		
	}
}
