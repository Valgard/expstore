package com.trontheim.expstore.common.proxy;

import com.trontheim.expstore.init.ESBlocks;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

abstract public class CommonProxy {

  public void preInit(FMLPreInitializationEvent event) {
    ESBlocks.registerTileEntities();
    ESBlocks.registerRecipes();
    ESBlocks.registerNetworkPacketHandler();
  }

  public void init(FMLInitializationEvent event) {
    ESBlocks.registerGuiHandler();
  }

  public void postInit(FMLPostInitializationEvent event) {
  }

  public EntityPlayer getPlayerEntity(MessageContext ctx) {
    return ctx.getServerHandler().playerEntity;
  }

}
