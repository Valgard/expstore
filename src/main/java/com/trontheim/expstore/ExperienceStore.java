package com.trontheim.expstore;

import com.trontheim.expstore.block.BlockExpChanger;
import com.trontheim.expstore.block.BlockExpStore;
import com.trontheim.expstore.client.renderer.block.RenderBlockExpStore;
import com.trontheim.expstore.tileentity.TileEntityExpStore;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.tileentity.TileEntity;
import org.apache.logging.log4j.LogManager;
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

  private static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

  private static final Logger logger = LogManager.getLogger(MODID);

  @EventHandler
  public void preinit(FMLPreInitializationEvent event)
  {
    BlockExpStore expStoreBlock = new BlockExpStore();
    GameRegistry.registerBlock(expStoreBlock, "expStoreBlock");

    BlockExpChanger expChangerBlock = new BlockExpChanger();
    GameRegistry.registerBlock(expChangerBlock, "expChangerBlock");

    TileEntity.addMapping(TileEntityExpStore.class, MODID + ":TileEntityExpStore");
    RenderingRegistry.registerBlockHandler(RenderBlockExpStore.instance());

    GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt, 2), new ItemStack(Blocks.dirt));
    GameRegistry.addShapelessRecipe(new ItemStack(expStoreBlock), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt));
    GameRegistry.addShapelessRecipe(new ItemStack(expChangerBlock), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt));
    GameRegistry.addShapelessRecipe(new ItemStack(Blocks.hopper), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt));

    GameRegistry.addRecipe(new ItemStack(expStoreBlock), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Items.gold_ingot, 'G', Blocks.glass);
    GameRegistry.addRecipe(new ItemStack(expChangerBlock), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Blocks.gold_block, 'G', Blocks.glass);
  }

  @EventHandler
  public void init(FMLInitializationEvent event)
  {
  }

  @EventHandler
  public void postinit(FMLPostInitializationEvent event)
  {
  }

  public boolean isDevelopmentEnvironment() {
    return developmentEnvironment;
  }

}
