package com.trontheim.expstore.block;

import com.trontheim.expstore.ExperienceStore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.apache.logging.log4j.Logger;

public class BlockExpStore extends Block {

  private Logger logger;

  public BlockExpStore() {
    super(Material.rock);
    setBlockName(ExperienceStore.MODID + "_expStoreBlock");
    setBlockTextureName(ExperienceStore.MODID + ":expStoreBlock");
    setCreativeTab(CreativeTabs.tabBlock);
    blockHardness = 10F;
    blockResistance = 100;
  }

  @SideOnly(Side.CLIENT)
  public void registerIcons(IIconRegister icon) {
    blockIcon = icon.registerIcon(ExperienceStore.MODID + ":BlockExpStore");
  }

  public boolean isOpaqueCube() {
    return false;
  }

  public void setLogger(Logger log) {
    logger = log;
  }

  @Override
  public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int p_149727_6_, float p_149727_7_, float p_149727_8_, float p_149727_9_) {
    if(player.isSneaking()) {
      player.experienceLevel = 0;
      player.experience = 0.0F;
      player.experienceTotal = 0;
      player.setScore(0);
      // player.addExperience(-10);
    }
    logger.info("["+ExperienceStore.MODID+"][1]: " + player.experience + " | " + player.experienceLevel + " | " + player.experienceTotal + " | " + player.getScore());
    return super.onBlockActivated(world, x, y, z, player, p_149727_6_, p_149727_7_, p_149727_8_, p_149727_9_);
  }

  @Override
  public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
    if(player.isSneaking()) {
      player.addExperience(10);
    }
    logger.info("["+ExperienceStore.MODID+"][2]: " + player.experience + " | " + player.experienceLevel + " | " + player.experienceTotal + " | " + player.getScore());
    super.onBlockClicked(world, x, y, z, player);
  }
}
