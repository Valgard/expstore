package com.trontheim.expstore.block;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.client.renderer.block.RenderBlockExpStore;
import com.trontheim.expstore.tileentity.TileEntityExpStore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class BlockExpStore extends BlockContainer {

  private static final Logger logger = LogManager.getLogger(ExperienceStore.MODID);

  @SideOnly(Side.CLIENT)
  private IIcon blockIconSouth;
  @SideOnly(Side.CLIENT)
  private IIcon blockIconEast;
  @SideOnly(Side.CLIENT)
  private IIcon blockIconWest;
  @SideOnly(Side.CLIENT)
  private IIcon blockIconTop;
  @SideOnly(Side.CLIENT)
  private IIcon blockIconBottom;

  public static final String NAME = "expstore";

  public BlockExpStore() {
    super(Material.iron);
    setCreativeTab(CreativeTabs.tabBlock);
    setBlockName(ExperienceStore.MODID + "." + NAME);
    setBlockTextureName(ExperienceStore.MODID + ":" + NAME);
    setHardness(3F);
    setResistance(8F);
    setLightLevel(0.5F);
    setStepSound(soundTypeMetal);
    setHarvestLevel("pickaxe", 2);
    setBlockBounds(0.125F, 0, 0.125F, 0.875F, 1F, 0.875F);
  }

  @Override
  public boolean renderAsNormalBlock() {
    return false;
  }

  @Override
  public int getRenderType() {
    return RenderBlockExpStore.instance().getRenderId();
  }

  @SideOnly(Side.CLIENT)
  public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side) {
    return true;
  }

  @SideOnly(Side.CLIENT)
  public IIcon getIcon(int side, int meta) {
    String result = "";

    switch(side) {
      case 0:
        result = "bottom";
        break;

      case 1:
        result = "top";
        break;

      case 2:
        result = "north";
        break;

      case 3:
        result = "south";
        break;

      case 4:
        result = "west";
        break;

      case 5:
        result = "east";
        break;
    }

    return getIcon(result);
  }

  public IIcon getIcon(String icon) {
    IIcon result;

    result = blockIcon;

    if(icon.equals("south")) {
      result = blockIconSouth;
    } else if(icon.equals("east")) {
      result = blockIconEast;
    } else if(icon.equals("west")) {
      result = blockIconWest;
    } else if(icon.equals("top")) {
      result = blockIconTop;
    } else if(icon.equals("bottom")) {
      result = blockIconBottom;
    }

    return result;
  }

  @Override
  public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB mask, List list, Entity entity) {
    setBlockBounds(0.125F, 0, 0.125F, 0.875F, 1F, 0.875F);
    super.addCollisionBoxesToList(world, x, y, z, mask, list, entity);
  }

  @Override
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    if(world.isRemote) {
      return false;
    }

    TileEntityExpStore tileEntityExpStore = getTileEntity(world, x, y, z);

    if(tileEntityExpStore == null) {
      return false;
    }

    // change something
    if(player.isSneaking()) {
      tileEntityExpStore.restoreExperiencePoints(player);
    }

    // sets the tile entity at the location
    setTileEntity(world, x, y, z, tileEntityExpStore);

    return false;
  }

  @Override
  public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    if(world.isRemote) {
      return;
    }

    TileEntityExpStore tileEntityExpStore = getTileEntity(world, x, y, z);

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

  @Override
  public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
    setBlockBounds(0.125F, 0, 0.125F, 0.875F, 1F, 0.875F);
  }

  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister iconRegister) {
    blockIcon = iconRegister.registerIcon(getTextureName() + "_north");
    blockIconSouth = iconRegister.registerIcon(getTextureName() + "_south");
    blockIconEast = iconRegister.registerIcon(getTextureName() + "_east");
    blockIconWest = iconRegister.registerIcon(getTextureName() + "_west");
    blockIconTop = iconRegister.registerIcon(getTextureName() + "_top");
    blockIconBottom = iconRegister.registerIcon(getTextureName() + "_bottom");
  }

  @SideOnly(Side.CLIENT)
  public String getItemIconName() {
    return getTextureName();
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
  public TileEntity createNewTileEntity(World world, int metadata) {
    return new TileEntityExpStore();
  }

  public TileEntityExpStore getTileEntity(IBlockAccess world, int x, int y, int z) {
    return getTileEntity((World) world, x, y, z);
  }

}
