package com.trontheim.expstore.block;

import com.trontheim.expstore.ExperienceStore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BlockExpChanger extends Block {

  private static final Logger logger = LogManager.getLogger(ExperienceStore.MODID);

  public static final String NAME = "expchanger";

  public BlockExpChanger() {
    super(Material.rock);
    setBlockName(ExperienceStore.MODID + "." + NAME);
    setBlockTextureName(ExperienceStore.MODID + ":" + NAME);
    setCreativeTab(CreativeTabs.tabBlock);
    setHardness(3F);
    setResistance(8F);
    setLightLevel(0.5F);
    setStepSound(soundTypeStone);
    setHarvestLevel("pickaxe", 2);
  }

  @SideOnly(Side.CLIENT)
  public void registerBlockIcons(IIconRegister iconRegister) {
    blockIcon = iconRegister.registerIcon(getTextureName());
  }

  public boolean isOpaqueCube() {
    return false;
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    if(world.isRemote) {
      return false;
    }

    if(player.isSneaking()) {
      player.experienceLevel = 0;
      player.experience = 0.0F;
      player.experienceTotal = 0;
      player.setScore(0);

      player.addChatComponentMessage(new ChatComponentText("experience points reseted"));
    }

    return true;
  }


  @Override
  public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    if(world.isRemote) {
      return;
    }

    if(player.isSneaking()) {
      player.addExperience(10);
      player.addChatComponentMessage(new ChatComponentText("10 experience points added"));
    } else {
      player.addChatComponentMessage(new ChatComponentText(player.experienceTotal + " experience points"));
    }
  }

}
