package com.trontheim.expstore.client.renderer.block;


import com.trontheim.expstore.block.BlockExpStore;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import cpw.mods.fml.client.registry.RenderingRegistry;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

public class RenderBlockExpStore implements ISimpleBlockRenderingHandler {

  private int width = 16;

  private float offsetX = 0;
  private float offsetY = 0;
  private float offsetZ = 0;

  private static RenderBlockExpStore instance = new RenderBlockExpStore();

  private int renderId = RenderingRegistry.getNextAvailableRenderId();

  public void renderInventoryBlock(BlockExpStore block, int metadata, int modelId, RenderBlocks renderer) {
    renderBlockMetadata(block, 0, 0, 0, metadata, true, renderer);
  }

  @Override
  public void renderInventoryBlock(Block block, int metadata, int modelId, RenderBlocks renderer) {
    renderInventoryBlock((BlockExpStore) block, metadata, modelId, renderer);
  }

  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, BlockExpStore block, int modelId, RenderBlocks renderer) {
    return renderBlockMetadata(block, x, y, z, world.getBlockMetadata(x, y, z), false, renderer);
  }

  public boolean renderBlockMetadata(BlockExpStore block, int x, int y, int z, int metadata, boolean isItem, RenderBlocks renderer) {
    Tessellator tessellator = Tessellator.instance;


    // ********************************************************************************
    //
    // setRenderBounds:   minX, minY, minZ, maxX, maxY, maxZ
    // setNormal      :   blue, green, red
    // renderFace*    :   block, x, y, z, texture
    //
    //  east   - renderFaceXPos
    //  west   - renderFaceXNeg
    //  top    - renderFaceYPos
    //  bottom - renderFaceYNeg
    //  south  - renderFaceZPos
    //  north  - renderFaceZNeg
    //
    // ********************************************************************************

    if (isItem) {
    }
    else {
      setColor(renderer.blockAccess, x, y, z, block);
      setRenderOrigin(2, 0, 2);
      addRenderBox(renderer, 1, 1, 1, 10, 12, 10);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 11, 1, 0, 1, 12, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 11, 1, 11, 1, 12, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 0, 1, 0, 1, 12, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 0, 1, 11, 1, 12, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 0, 0, 0, 12, 1, 12);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 0, 13, 0, 12, 1, 12);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 2, 14, 2, 8, 1, 8);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 2, 14, 1, 8, 1, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 2, 14, 10, 8, 1, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 10, 14, 2, 1, 1, 8);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 1, 14, 2, 1, 1, 8);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 3, 15, 3, 6, 1, 6);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 3, 15, 2, 6, 1, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 3, 15, 9, 6, 1, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 9, 15, 3, 1, 1, 6);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 2, 15, 3, 1, 1, 6);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 4, 5, 0, 4, 4, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 5, 9, 0, 2, 1, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 5, 4, 0, 2, 1, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 8, 6, 0, 1, 2, 1);
      renderer.renderStandardBlock(block, x, y, z);
      addRenderBox(renderer, 3, 6, 0, 1, 2, 1);
      renderer.renderStandardBlock(block, x, y, z);
    }

    renderer.clearOverrideBlockTexture();

    return true;
  }

  @Override
  public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
    return renderWorldBlock(world, x, y, z, (BlockExpStore) block, modelId, renderer);
  }

  @Override
  public boolean shouldRender3DInInventory(int modelId) {
    return false;
  }

  @Override
  public int getRenderId() {
    return renderId;
  }

  public static RenderBlockExpStore instance() {
    return instance;
  }

  private void setColor(IBlockAccess world, int x, int y, int z, BlockExpStore block) {
    Tessellator tessellator = Tessellator.instance;

    tessellator.setBrightness(block.getMixedBrightnessForBlock(world, x, y, z));
    int color = block.colorMultiplier(world, x, y, z);
    float red = (color >> 16 & 255) / 255.0F;
    float green = (color >> 8 & 255) / 255.0F;
    float blue = (color & 255) / 255.0F;

    if (EntityRenderer.anaglyphEnable)
    {
      float redAnaglyph = (red * 30.0F + green * 59.0F + blue * 11.0F) / 100.0F;
      float greenAnaglyph = (red * 30.0F + green * 70.0F) / 100.0F;
      float blueAnaglyph = (red * 30.0F + green * 70.0F) / 100.0F;
      red = redAnaglyph;
      green = greenAnaglyph;
      blue = blueAnaglyph;
    }

    tessellator.setColorOpaque_F(red, green, blue);
  }

  public void setWidth(int width) {
    this.width = width;
  }

  private void addRenderBox(RenderBlocks renderer, float x, float y, float z, float width, float height, float depth) {
    float pixel = 1F / this.width;

    x = (offsetX + x) * pixel;
    y = (offsetY + y) * pixel;
    z = (offsetZ + z) * pixel;
    width  = x + (width  * pixel);
    height = y + (height * pixel);
    depth  = z + (depth  * pixel);

    renderer.setRenderBounds(x , y, z, width, height, depth);
  }

  private void setRenderOrigin(int x, int y, int z) {
    offsetX = x;
    offsetY = y;
    offsetZ = z;
  }

}
