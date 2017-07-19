package com.trontheim.expstore.block;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.client.renderer.block.RenderBlockExpStore;
import com.trontheim.expstore.block.tileentity.TileEntityExpStore;
import com.trontheim.expstore.init.ESBlocks;
import com.trontheim.expstore.network.packet.OpenGuiMessage;
import com.trontheim.expstore.network.PacketHandler;
import com.trontheim.expstore.network.packet.ResetExperienceStoreMessage;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.trontheim.expstore.common.gui.GuiHandlerExpStore.Gui.STORE;

public class BlockExpStore extends BlockContainer {

  public static final String NAME = "expstore";

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

  public BlockExpStore() {
    super(Material.iron);
    setCreativeTab(CreativeTabs.tabMisc);
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
  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    if(world.isRemote) { // CLIENT: open gui from server, server open client gui
      if(!player.isSneaking()) {
        PacketHandler.sendToServer(new OpenGuiMessage(STORE.ordinal(), x, y, z));
      } else {
        if(ESBlocks.isDevelopmentEnvironment()) {
          PacketHandler.sendToServer(new ResetExperienceStoreMessage(x, y, z));
        }
      }
    }

    return false;
  }

  @Override
  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister iconRegister) {
    blockIcon = iconRegister.registerIcon(getTextureName() + "_north");
    blockIconSouth = iconRegister.registerIcon(getTextureName() + "_south");
    blockIconEast = iconRegister.registerIcon(getTextureName() + "_east");
    blockIconWest = iconRegister.registerIcon(getTextureName() + "_west");
    blockIconTop = iconRegister.registerIcon(getTextureName() + "_top");
    blockIconBottom = iconRegister.registerIcon(getTextureName() + "_bottom");
  }

  @Override
  @SideOnly(Side.CLIENT)
  public String getItemIconName() {
    return getTextureName();
  }

  @Override
  public TileEntity createNewTileEntity(World world, int metadata) {
    return new TileEntityExpStore();
  }

}
