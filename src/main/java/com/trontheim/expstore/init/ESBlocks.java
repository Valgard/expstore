package com.trontheim.expstore.init;

import com.trontheim.expstore.block.BlockExpChanger;
import com.trontheim.expstore.block.BlockExpStore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;

public class ESBlocks {

  public static final Block expStore = GameRegistry.registerBlock(new BlockExpStore(), "expStoreBlock");
  public static final Block expChanger = GameRegistry.registerBlock(new BlockExpChanger(), "expChangerBlock");

}
