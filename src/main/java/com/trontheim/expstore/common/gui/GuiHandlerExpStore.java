package com.trontheim.expstore.common.gui;

import com.trontheim.expstore.client.gui.GuiClientExpStore;
import com.trontheim.expstore.server.gui.GuiServerExpStore;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class GuiHandlerExpStore implements IGuiHandler {

  @Override
  public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if(ID == Gui.STORE.ordinal()) {
      return new GuiServerExpStore(player, world, x, y, z);
    }

    return null;
  }

  @Override
  public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
    if(ID == Gui.STORE.ordinal()) {
      return new GuiClientExpStore(player, world, x, y, z);
    }

    return null;
  }

  public enum Gui {
    STORE
  }

}
