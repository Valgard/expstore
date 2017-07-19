package com.trontheim.expstore.server.gui;

import com.trontheim.expstore.block.tileentity.TileEntityExpStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class GuiServerExpStore extends Container {

  private EntityPlayer player;
  private World world;
  private TileEntityExpStore tileEntity;

  public GuiServerExpStore(EntityPlayer player, World world, int x, int y, int z) {
    this.player = player;
    this.world = world;
    this.tileEntity = getTileEntity(world, x, y, z);
  }

  private static TileEntityExpStore getTileEntity(World world, int x, int y, int z) {
    // gets tile entity from the location
    TileEntity tileEntity = world.getTileEntity(x, y, z);

    // check for the right TileEntity class
    if(!(tileEntity instanceof TileEntityExpStore)) {
      return null;
    }

    return (TileEntityExpStore) tileEntity;
  }

  @Override
  public void detectAndSendChanges() {
  }

  @Override
  public Slot getSlotFromInventory(IInventory p_75147_1_, int p_75147_2_) {
    return new Slot(p_75147_1_, p_75147_2_, 0, 0);
  }

  @Override
  public boolean canInteractWith(EntityPlayer p_75145_1_) {
    return true;
  }

}
