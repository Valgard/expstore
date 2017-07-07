package com.trontheim.expstore.common;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.block.BlockExpChanger;
import com.trontheim.expstore.block.BlockExpStore;
import com.trontheim.expstore.client.renderer.block.RenderBlockExpStore;
import com.trontheim.expstore.tileentity.TileEntityExpStore;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.tileentity.TileEntity;

abstract public class CommonProxy {

  private static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

  public void preInit(FMLPreInitializationEvent event) {
    BlockExpStore expStoreBlock = new BlockExpStore();
    GameRegistry.registerBlock(expStoreBlock, "expStoreBlock");

    TileEntity.addMapping(TileEntityExpStore.class, ExperienceStore.MODID + ":TileEntityExpStore");
    RenderingRegistry.registerBlockHandler(RenderBlockExpStore.instance());

    GameRegistry.addRecipe(new ItemStack(expStoreBlock), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Items.gold_ingot, 'G', Blocks.glass);

    if(isDevelopmentEnvironment()) {
      BlockExpChanger expChangerBlock = new BlockExpChanger();
      GameRegistry.registerBlock(expChangerBlock, "expChangerBlock");
      GameRegistry.addRecipe(new ItemStack(expChangerBlock), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Blocks.gold_block, 'G', Blocks.glass);

      GameRegistry.addShapelessRecipe(new ItemStack(Blocks.dirt, 2), new ItemStack(Blocks.dirt));
      GameRegistry.addShapelessRecipe(new ItemStack(expStoreBlock), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt));
      GameRegistry.addShapelessRecipe(new ItemStack(expChangerBlock), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt));
      GameRegistry.addShapelessRecipe(new ItemStack(Blocks.hopper), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt), new ItemStack(Blocks.dirt));
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
