package com.trontheim.expstore.block;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.tileentity.TileEntityExpStore;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockExpStore extends BlockContainer {

  private static final Logger logger = LogManager.getLogger(ExperienceStore.MODID);

  public BlockExpStore() {
    super(Material.iron);
    setCreativeTab(CreativeTabs.tabBlock);
    setBlockName(ExperienceStore.MODID + "_expStoreBlock");
    setBlockTextureName(ExperienceStore.MODID + ":expStoreBlock");
    setHardness(3F);
    setResistance(8F);
    setStepSound(soundTypeWood);
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public TileEntity createNewTileEntity(World world, int metadata) {
    return new TileEntityExpStore();
  }

  private TileEntityExpStore getTileEntity(World world, int x, int y, int z) {
    // gets tile entity from the location
    TileEntity tileEntity = world.getTileEntity(x, y, z);

    // check for the right TileEntity class
    if(!(tileEntity instanceof TileEntityExpStore)) {
      return null;
    }

    return (TileEntityExpStore) tileEntity;
  }

  private void setTileEntity(World world, int x, int y, int z, TileEntityExpStore tileEntityExpStore) {
    // sets the tile entity at the location
    world.setTileEntity(x, y, z, tileEntityExpStore);
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    if(world.isRemote) {
      return false;
    }

    TileEntityExpStore tileEntityExpStore = getTileEntity(world, x, y ,z);

    if(tileEntityExpStore == null) {
      return false;
    }

    // change something
    if(player.isSneaking()) {
      tileEntityExpStore.restoreExperiencePoints(player);
    }

    // sets the tile entity at the location
    setTileEntity(world, x, y, z, tileEntityExpStore);

    return true;
  }

  @Override
  public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    if(world.isRemote) {
      return;
    }

    TileEntityExpStore tileEntityExpStore = getTileEntity(world, x, y ,z);

    if(tileEntityExpStore == null) {
      return;
    }

    // change something
    if(player.isSneaking()) {
      tileEntityExpStore.storeExperiencePoints(player);
    } else {
      player.addChatComponentMessage(new ChatComponentText("stored experience points: " + tileEntityExpStore.getSoredExperiencePoints(player).toString()));
    }

    // sets the tile entity at the location
    setTileEntity(world, x, y, z, tileEntityExpStore);
  }

}
