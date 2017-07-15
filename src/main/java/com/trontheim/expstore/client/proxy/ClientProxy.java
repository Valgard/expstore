package com.trontheim.expstore.client.proxy;

import com.trontheim.expstore.common.proxy.CommonProxy;
import com.trontheim.expstore.init.ESBlocks;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
    ESBlocks.registerBlockRenderer();
  }

  @Override
  public void init(FMLInitializationEvent event) {
    super.init(event);
  }

  @Override
  public void postInit(FMLPostInitializationEvent event) {
    super.postInit(event);
  }

}
