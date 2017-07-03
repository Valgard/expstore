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
    setColor(world, x, y, z, block);

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


    double d0 = 0.625D;
    renderer.setRenderBounds(0.0D, d0, 0.0D, 1.0D, 1.0D, 1.0D);

    int i1 = 0;

    if (isItem)
    {
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, -1.0F, 0.0F);
      renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, -1.0F);
      renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, 1.0F);
      renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(-1.0F, 0.0F, 0.0F);
      renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(1.0F, 0.0F, 0.0F);
      renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
      tessellator.draw();
    }
    else
    {
      renderer.renderStandardBlock(block, x, y, z);
    }

    if (!isItem) {
      setColor(renderer.blockAccess, x, y, z, block);
    }

    IIcon iicon = block.getIcon("north");
    IIcon iicon1 = block.getIcon("top");

    float f1 = 0.125F;

    if (isItem)
    {
      tessellator.startDrawingQuads();
      tessellator.setNormal(1.0F, 0.0F, 0.0F);
      renderer.renderFaceXPos(block, (double)(-1.0F + f1), 0.0D, 0.0D, iicon);
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(-1.0F, 0.0F, 0.0F);
      renderer.renderFaceXNeg(block, (double)(1.0F - f1), 0.0D, 0.0D, iicon);
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, 1.0F);
      renderer.renderFaceZPos(block, 0.0D, 0.0D, (double)(-1.0F + f1), iicon);
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, -1.0F);
      renderer.renderFaceZNeg(block, 0.0D, 0.0D, (double)(1.0F - f1), iicon);
      tessellator.draw();

      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      renderer.renderFaceYPos(block, 0.0D, -1.0D + d0, 0.0D, iicon1);
      tessellator.draw();
    }
    else
    {
      renderer.renderFaceXPos(block, (double)((float) x - 1.0F + f1), (double) y, (double) z, iicon);
      renderer.renderFaceXNeg(block, (double)((float) x + 1.0F - f1), (double) y, (double) z, iicon);
      renderer.renderFaceZPos(block, (double) x, (double) y, (double)((float) z - 1.0F + f1), iicon);
      renderer.renderFaceZNeg(block, (double) x, (double) y, (double)((float) z + 1.0F - f1), iicon);
      renderer.renderFaceYPos(block, (double) x, (double)((float) y - 1.0F) + d0, (double) z, iicon1);
    }

    renderer.setOverrideBlockTexture(iicon);
    double d3 = 0.25D;
    double d4 = 0.25D;
    renderer.setRenderBounds(d3, d4, d3, 1.0D - d3, d0 - 0.002D, 1.0D - d3);

    if (isItem)
    {
      tessellator.startDrawingQuads();
      tessellator.setNormal(1.0F, 0.0F, 0.0F);
      renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, iicon);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(-1.0F, 0.0F, 0.0F);
      renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, iicon);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, 1.0F);
      renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, iicon);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 0.0F, -1.0F);
      renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, iicon);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, 1.0F, 0.0F);
      renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, iicon);
      tessellator.draw();
      tessellator.startDrawingQuads();
      tessellator.setNormal(0.0F, -1.0F, 0.0F);
      renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, iicon);
      tessellator.draw();
    }
    else
    {
      renderer.renderStandardBlock(block, x, y, z);
    }

    if (!isItem)
    {
      double d1 = 0.375D;
      double d2 = 0.25D;
      renderer.setOverrideBlockTexture(iicon);

      if (i1 == 0)
      {
        renderer.setRenderBounds(d1, 0.0D, d1, 1.0D - d1, 0.25D, 1.0D - d1);
        renderer.renderStandardBlock(block, x, y, z);
      }

      if (i1 == 2)
      {
        renderer.setRenderBounds(d1, d4, 0.0D, 1.0D - d1, d4 + d2, d3);
        renderer.renderStandardBlock(block, x, y, z);
      }

      if (i1 == 3)
      {
        renderer.setRenderBounds(d1, d4, 1.0D - d3, 1.0D - d1, d4 + d2, 1.0D);
        renderer.renderStandardBlock(block, x, y, z);
      }

      if (i1 == 4)
      {
        renderer.setRenderBounds(0.0D, d4, d1, d3, d4 + d2, 1.0D - d1);
        renderer.renderStandardBlock(block, x, y, z);
      }

      if (i1 == 5)
      {
        renderer.setRenderBounds(1.0D - d3, d4, d1, 1.0D, d4 + d2, 1.0D - d1);
        renderer.renderStandardBlock(block, x, y, z);
      }
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

}
