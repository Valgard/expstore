package com.trontheim.expstore.init;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.block.BlockExpChanger;
import com.trontheim.expstore.block.BlockExpStore;
import com.trontheim.expstore.block.itemblock.ItemBlockExpStore;
import com.trontheim.expstore.client.renderer.block.RenderBlockExpStore;
import com.trontheim.expstore.tileentity.TileEntityExpStore;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

public class ESBlocks {

  public static final Block expStore = GameRegistry.registerBlock(new BlockExpStore(), ItemBlockExpStore.class, "expStoreBlock");
  public static final Block expChanger = GameRegistry.registerBlock(new BlockExpChanger(), "expChangerBlock");

  public static void register() {
    TileEntity.addMapping(TileEntityExpStore.class, ExperienceStore.MODID + ":TileEntityExpStore");
    RenderingRegistry.registerBlockHandler(RenderBlockExpStore.instance());
  }

}
