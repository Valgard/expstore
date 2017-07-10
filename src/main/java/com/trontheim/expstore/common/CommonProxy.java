package com.trontheim.expstore.common;

import com.trontheim.expstore.init.ESBlocks;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;

abstract public class CommonProxy {

  private static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

  public void preInit(FMLPreInitializationEvent event) {

    ESBlocks.register();

    GameRegistry.addRecipe(new ItemStack(ESBlocks.expStore), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Items.gold_ingot, 'G', Blocks.glass);

    if(isDevelopmentEnvironment()) {
      GameRegistry.addRecipe(new ItemStack(ESBlocks.expChanger), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Blocks.gold_block, 'G', Blocks.glass);
    }
  }

  public static boolean isDevelopmentEnvironment() {
    return developmentEnvironment;
  }

  public void init(FMLInitializationEvent event) {
  }

  public void postInit(FMLPostInitializationEvent event) {
  }

}
