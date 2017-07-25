package com.trontheim.expstore.client.gui;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.block.tileentity.TileEntityExpStore;
import com.trontheim.expstore.block.tileentity.TileEntityExpStore.Experience;
import com.trontheim.expstore.network.PacketHandler;
import com.trontheim.expstore.network.packet.ExperienceMessage;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

import static com.trontheim.expstore.network.packet.ExperienceMessage.MODE.RESTORE;
import static com.trontheim.expstore.network.packet.ExperienceMessage.MODE.STORE;

public class GuiClientExpStore extends GuiScreen {

  private static final ResourceLocation backgroundLocation = new ResourceLocation(ExperienceStore.MODID, "textures/gui/background.png");
  private static final ResourceLocation logoLocation = new ResourceLocation(ExperienceStore.MODID, "textures/gui/logo.png");

  private EntityPlayer player;
  private TileEntityExpStore tileEntity;

  /**
   * The X size of the gui window in pixels.
   */
  private int xSize = 248;
  /**
   * The Y size of the gui window in pixels.
   */
  private int ySize = 166;
  /**
   * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
   */
  private int guiLeft;
  /**
   * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
   */
  private int guiTop;

  private GuiButton buttonStore;
  private GuiButton buttonRestore;

  public GuiClientExpStore(EntityPlayer player, World world, int x, int y, int z) {
    this.player = player;
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
  public void drawScreen(int mouseX, int mouseY, float partialTicks) {
    super.drawDefaultBackground();
    drawGuiBackgroundLayer(mouseX, mouseY, partialTicks);
    super.drawScreen(mouseX, mouseY, partialTicks);
    GL11.glPushMatrix();
    GL11.glTranslatef((float) guiLeft, (float) guiTop, 0.0F);
    drawGuiForegroundLayer(mouseX, mouseY);
    GL11.glPopMatrix();
  }

  protected void drawGuiBackgroundLayer(int mouseX, int mouseY, float partialTicks) {
    mc.getTextureManager().bindTexture(backgroundLocation);
    drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize);
  }

  protected void drawGuiForegroundLayer(int mouseX, int mouseY) {
    Experience experienceStore = new Experience(tileEntity.getSoredExperiencePoints(player));
    Experience experiencePlayer = new Experience(player.experienceTotal);

    GL11.glPushMatrix();
    float scale = 0.125F;
    GL11.glScalef(scale, scale, scale);
    mc.getTextureManager().bindTexture(logoLocation);
    drawTexturedModalRect(40, 40, 0, 0, 256, 256);
    GL11.glPopMatrix();

    GL11.glPushMatrix();
    GL11.glScalef(1.2F, 1.2F, 1.2F);
    fontRendererObj.drawString(I18n.format("tile.expstore.expstore.gui.heading"), 32, 16, 4210752);
    GL11.glPopMatrix();

    GL11.glPushMatrix();
    GL11.glScalef(1.1F, 1.1F, 1.1F);
    fontRendererObj.drawString(I18n.format("tile.expstore.expstore.gui.player") + ": " + experiencePlayer.experienceTotal.toString(), 8, 40, 4210752);
    GL11.glPopMatrix();

    GL11.glPushMatrix();
    GL11.glTranslatef(10F, 60F, 0.0F);
    renderXpBar(experiencePlayer);
    GL11.glPopMatrix();

    GL11.glPushMatrix();
    GL11.glScalef(1.1F, 1.1F, 1.1F);
    fontRendererObj.drawString(I18n.format("tile.expstore.expstore.gui.store") + ": " + experienceStore.experienceTotal.toString(), 8, 70, 4210752);
    GL11.glPopMatrix();

    GL11.glPushMatrix();
    GL11.glTranslatef(10F, 90F, 0.0F);
    renderXpBar(experienceStore);
    GL11.glPopMatrix();
  }

  // experience bar rendering: copied from net.minecraft.client.gui.GuiIngame(209)
  private void renderXpBar(Experience exp) {
    mc.getTextureManager().bindTexture(Gui.icons);

    int j1 = exp.xpBarCap();

    int width = 182;

    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

    if(j1 > 0) {
      int l1 = (int) (exp.experience * (float) (width + 1));
      drawTexturedModalRect(0, 0, 0, 64, width, 5);

      if(l1 > 0) {
        drawTexturedModalRect(0, 0, 0, 69, l1, 5);
      }
    }

    if(exp.experienceLevel > 0) {
      int x = (width - fontRendererObj.getStringWidth(exp.experienceLevel.toString())) / 2;
      int y = -6;
      fontRendererObj.drawString(exp.experienceLevel.toString(), x + 1, y, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x - 1, y, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x, y + 1, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x, y - 1, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x, y, 8453920);
    }
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if(button.equals(buttonStore)) {
      PacketHandler.sendToServer(new ExperienceMessage(STORE, tileEntity));
    }
    if(button.equals(buttonRestore)) {
      PacketHandler.sendToServer(new ExperienceMessage(RESTORE, tileEntity));
    }
  }

  @Override
  public void updateScreen() {
    updateButtons();
  }

  private void updateButtons() {
    buttonStore.enabled = true;
    buttonRestore.enabled = true;
    if(player.experienceTotal == 0) {
      buttonStore.enabled = false;
    }
    if(tileEntity.getSoredExperiencePoints(player) == 0) {
      buttonRestore.enabled = false;
    }
  }

  @Override
  public void initGui() {
    guiLeft = (width - xSize) / 2;
    guiTop = (height - ySize) / 2;
    buttonList.add(buttonStore = new GuiButton(0, guiLeft + 10, guiTop + ySize - 30, xSize / 2 - 20, 20, "Store"));
    buttonList.add(buttonRestore = new GuiButton(0, guiLeft + 10 + (xSize / 2), guiTop + ySize - 30, xSize / 2 - 20, 20, "Restore"));
    updateButtons();
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

}
