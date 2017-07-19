package com.trontheim.expstore.init;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.block.BlockExpChanger;
import com.trontheim.expstore.block.BlockExpStore;
import com.trontheim.expstore.block.itemblock.ItemBlockExpStore;
import com.trontheim.expstore.block.tileentity.TileEntityExpStore;
import com.trontheim.expstore.client.renderer.block.RenderBlockExpStore;
import com.trontheim.expstore.common.gui.GuiHandlerExpStore;
import com.trontheim.expstore.network.PacketHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.launchwrapper.Launch;
import net.minecraft.tileentity.TileEntity;

public class ESBlocks {

  public static final Block expStore = GameRegistry.registerBlock(new BlockExpStore(), ItemBlockExpStore.class, "expStoreBlock");
  public static final Block expChanger = GameRegistry.registerBlock(new BlockExpChanger(), "expChangerBlock");

  private static final boolean developmentEnvironment = (Boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");

  public static void registerTileEntities() {
    TileEntity.addMapping(TileEntityExpStore.class, ExperienceStore.MODID + ":TileEntityExpStore");
  }

  public static void registerRecipes() {
    GameRegistry.addRecipe(new ItemStack(ESBlocks.expStore), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Items.gold_ingot, 'G', Blocks.glass);

    if(isDevelopmentEnvironment()) {
      GameRegistry.addRecipe(new ItemStack(ESBlocks.expChanger), "ogo", "gGg", "ogo", 'o', Blocks.obsidian, 'g', Blocks.gold_block, 'G', Blocks.glass);
    }
  }

  public static void registerBlockRenderer() {
    RenderingRegistry.registerBlockHandler(RenderBlockExpStore.instance());
  }

  public static void registerGuiHandler() {
    NetworkRegistry.INSTANCE.registerGuiHandler(ExperienceStore.instance(), new GuiHandlerExpStore());
  }

  public static void registerNetworkPacketHandler() {
    PacketHandler.registerPackets();
  }

  public static boolean isDevelopmentEnvironment() {
    return developmentEnvironment;
  }

}
