package com.trontheim.expstore;

import com.trontheim.expstore.common.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.InstanceFactory;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import net.minecraft.launchwrapper.Launch;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(
  modid = ExperienceStore.MODID,
  name = ExperienceStore.NAME,
  version = ExperienceStore.VERSION,
  acceptedMinecraftVersions = ExperienceStore.MCVERSION
  // updateJSON = "http://minecraft.valgard-lp.de"
)
public class ExperienceStore {

  public static final String MODID = "expstore";
  public static final String NAME = "Experience Store";
  public static final String VERSION = "0.0.3-alpha";
  public static final String MCVERSION = "[1.7.10]";

  private static final Logger logger = LogManager.getLogger(MODID);

  @SidedProxy(
    clientSide = "com.trontheim.expstore.client.proxy.ClientProxy",
    serverSide = "com.trontheim.expstore.server.proxy.ServerProxy"
  )
  public static CommonProxy proxy;

  private static ExperienceStore instance = new ExperienceStore();

  @InstanceFactory
  public static ExperienceStore instance() {
    return instance;
  }

  @EventHandler
  public void preinit(FMLPreInitializationEvent event) {
    proxy.preInit(event);
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postinit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }

}
