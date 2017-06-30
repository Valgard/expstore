package com.trontheim.expstore;

import com.trontheim.expstore.block.BlockExpStore;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;

@Mod(
        modid = ExperienceStore.MODID,
        name = ExperienceStore.NAME,
        version = ExperienceStore.VERSION,
        acceptedMinecraftVersions = ExperienceStore.MCVERSION
        // updateJSON = "http://minecraft.valgard-lp.de"
)
public class ExperienceStore
{
  public static final String MODID = "expstore";
  public static final String NAME = "Experience Store";
  public static final String VERSION = "0.0.1-alpha";
  public static final String MCVERSION = "[1.7.10]";

  private Logger logger;

  public static BlockExpStore expStoreBlock;

  @EventHandler
  public void preinit(FMLPreInitializationEvent event)
  {
    logger = event.getModLog();

    expStoreBlock = new BlockExpStore();
    expStoreBlock.setLogger(logger);
    GameRegistry.registerBlock(expStoreBlock, "expStoreBlock");

    GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt, 2), new ItemStack(Blocks.dirt));
    GameRegistry.addShapelessRecipe(new ItemStack(expStoreBlock), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt));
  }

  @EventHandler
  public void init(FMLInitializationEvent event)
  {
  }

  @EventHandler
  public void postinit(FMLPostInitializationEvent event)
  {
  }
}
