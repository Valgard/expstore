package com.trontheim.expstore.common.proxy;

import com.trontheim.expstore.init.ESBlocks;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

abstract public class CommonProxy {

  public void preInit(FMLPreInitializationEvent event) {
    ESBlocks.registerTileEntities();
    ESBlocks.registerRecipes();
  }

  public void init(FMLInitializationEvent event) {
  }

  public void postInit(FMLPostInitializationEvent event) {
  }

}
