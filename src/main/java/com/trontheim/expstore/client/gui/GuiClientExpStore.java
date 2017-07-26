package com.trontheim.expstore.client.gui;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.block.tileentity.TileEntityExpStore;
import com.trontheim.expstore.block.tileentity.TileEntityExpStore.Experience;
import com.trontheim.expstore.network.PacketHandler;
import com.trontheim.expstore.network.packet.ExperienceMessage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.opengl.GL11;

import static com.trontheim.expstore.network.packet.ExperienceMessage.MODE.STORE;
import static com.trontheim.expstore.network.packet.ExperienceMessage.MODE.RESTORE;

public class GuiClientExpStore extends GuiScreen {

  private static final ResourceLocation backgroundLocation = new ResourceLocation(ExperienceStore.MODID, "textures/gui/background.png");

  private static final ResourceLocation widgetsLocation = new ResourceLocation(ExperienceStore.MODID, "textures/gui/widgets.png");

  private EntityPlayer player;
  private TileEntityExpStore tileEntity;

  /**
   * The X size of the gui window in pixels.
   */
  private int xSize = 256;
  /**
   * The Y size of the gui window in pixels.
   */
  private int ySize = 190;
  /**
   * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
   */
  private int guiLeft;
  /**
   * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
   */
  private int guiTop;

  private int amount = 0;

  private final int digits = 10;

  private UpDownButton[] buttonsInc = new UpDownButton[digits];
  private UpDownButton[] buttonsDec = new UpDownButton[digits];

  private UpDownButton buttonDirection;
  private UpDownButton buttonExecute;
  private UpDownButton buttonTakePoints;

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
    drawTexturedModalRect((width - xSize) / 2, (height - ySize) / 2, 0, 0, xSize, ySize - 4);
    drawTexturedModalRect((width - xSize) / 2, ((height - ySize) / 2) + (ySize - 4), 0, 252, xSize, 4);
  }

  protected void drawGuiForegroundLayer(int mouseX, int mouseY) {
    Experience experienceStore = new Experience(tileEntity.getStoredExperiencePoints(player));
    Experience experiencePlayer = new Experience(player.experienceTotal);

    // render logo
    GL11.glPushMatrix();
    mc.getTextureManager().bindTexture(widgetsLocation);
    drawTexturedModalRect(5, 5, 10, 0, 32, 32);
    GL11.glPopMatrix();

    // render direction gui element
    GL11.glPushMatrix();
    mc.getTextureManager().bindTexture(widgetsLocation);
    drawTexturedModalRect(193, 77, 43, 0, 37, 66);
    GL11.glPopMatrix();

    // render take points gui element
    GL11.glPushMatrix();
    mc.getTextureManager().bindTexture(widgetsLocation);
    drawTexturedModalRect(25, 81, 80, 0, 21, 58);
    GL11.glPopMatrix();

    // render heading
    int top = 16;
    GL11.glPushMatrix();
    GL11.glScalef(1.2F, 1.2F, 1.2F);
    fontRendererObj.drawString(I18n.format("tile.expstore.expstore.gui.heading"), 32, top, 4210752);
    GL11.glPopMatrix();


    // show player xp points
    top = 55;
    GL11.glPushMatrix();
    fontRendererObj.drawString(I18n.format("tile.expstore.expstore.gui.player") + ": " + StringUtils.leftPad(experiencePlayer.experienceTotal.toString(), digits, " "), 10, top, 4210752);
    GL11.glPopMatrix();

    // render player xp bar
    top+= 20;
    GL11.glPushMatrix();
    GL11.glTranslatef(10F, (float) top, 0.0F);
    renderXpBar(experiencePlayer);
    GL11.glPopMatrix();


    // render amount value
    top+= 15;
    int xGab = 15;
    int left = 172;
    // String sAmount = ((Integer) amount).toString();
    String sAmount = StringUtils.leftPad(((Integer) amount).toString(), digits, '0');
    GL11.glPushMatrix();
    GL11.glTranslatef(10F, (float) top, 0.0F);
    for(int index = digits - 1; index >= 0; index--) {
      String sDigit = ((Integer) Character.getNumericValue(sAmount.charAt(digits - index - 1))).toString();
      fontRendererObj.drawString(sDigit, left - (index * xGab) + (buttonsInc[index].width / 2) - (fontRendererObj.getStringWidth(sDigit) / 2), 16, 4210752);
    }
    GL11.glPopMatrix();


    // show store xp points
    top+= 50;
    GL11.glPushMatrix();
    GL11.glTranslatef(10F, (float) top, 0.0F);
    renderXpBar(experienceStore, true);
    GL11.glPopMatrix();

    // render store xp bar
    top+= 18;
    GL11.glPushMatrix();
    fontRendererObj.drawString(I18n.format("tile.expstore.expstore.gui.store") + ": " + StringUtils.leftPad(experienceStore.experienceTotal.toString(), digits, " "), 10, top, 4210752);
    GL11.glPopMatrix();
  }

  // experience bar rendering: copied from net.minecraft.client.gui.GuiIngame(209)
  private void renderXpBar(Experience exp) {
    renderXpBar(exp, false);
  }

  private void renderXpBar(Experience exp, boolean bottom) {
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
      int y = bottom ? 4 : -6;
      fontRendererObj.drawString(exp.experienceLevel.toString(), x + 1, y, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x - 1, y, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x, y + 1, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x, y - 1, 0);
      fontRendererObj.drawString(exp.experienceLevel.toString(), x, y, 8453920);
    }
  }

  @Override
  protected void actionPerformed(GuiButton button) {
    if(button.id == buttonExecute.id) {
      if(buttonDirection.getDown()) {
        PacketHandler.sendToServer(new ExperienceMessage(STORE, tileEntity, amount));
      } else {
        PacketHandler.sendToServer(new ExperienceMessage(RESTORE, tileEntity, amount));
      }
    }

    if(button.id == buttonDirection.id) {
      buttonDirection.setDown(!buttonDirection.getDown());
      buttonTakePoints.setDown(!buttonTakePoints.getDown());
    }

    if(button.id == buttonTakePoints.id) {
      amount = !buttonTakePoints.getDown() ? player.experienceTotal : tileEntity.getStoredExperiencePoints(player);
    }

    for(int index = digits - 1; index >= 0; index--) {
      if(button.id == buttonsInc[index].id) {
        amount+= Math.pow(10, index);
      }
      if(button.id == buttonsDec[index].id) {
        amount-= Math.pow(10, index);
      }
    }

    int maxAmount = buttonDirection.getDown() ? player.experienceTotal : tileEntity.getStoredExperiencePoints(player);
    if(maxAmount < amount) {
      amount = maxAmount;
    }
  }

  @Override
  public void updateScreen() {
    updateButtons();
  }

  private void updateButtons() {
    String sAmount = StringUtils.leftPad(((Integer) amount).toString(), digits, '0');

    buttonExecute.enabled = false;
    if(amount > 0) {
      buttonExecute.enabled = true;
    }

    int iMaxAmount = buttonDirection.getDown() ? player.experienceTotal : tileEntity.getStoredExperiencePoints(player);
    String sMaxAccount = StringUtils.leftPad(((Integer) iMaxAmount).toString(), digits, '0');

    if(iMaxAmount < amount) {
      amount = iMaxAmount;
    }

    boolean enabled = false;
    for(int index = digits - 1; index >= 0; index--) {
      buttonsInc[index].enabled = false;
      buttonsDec[index].enabled = false;

      if(Character.getNumericValue(sMaxAccount.charAt(digits - index - 1)) > 0 || enabled) {
        enabled = true;
        int iDigit = Character.getNumericValue(sAmount.charAt(digits - index - 1));
        if(iDigit < 9) {
          buttonsInc[index].enabled = true;
        }
        if(iDigit > 0) {
          buttonsDec[index].enabled = true;
        }
      }
    }
  }

  @Override
  public void initGui() {
    guiLeft = (width - xSize) / 2;
    guiTop = (height - ySize) / 2;

    int x = guiLeft + 202;
    int y = guiTop + (ySize / 2) + 10;
    buttonList.add(buttonDirection = new UpDownButton(3, x, y, true));
    buttonDirection.setUpCharacter("\u2B06"); // ⬆
    buttonDirection.setDownCharacter("\u2B07"); // ⬇

    x = guiLeft + 232;
    y = guiTop + (ySize / 2) + 10;
    buttonList.add(buttonExecute = new UpDownButton(4, x, y));
    buttonExecute.setUpCharacter("\u25B6"); // ▶

    x = guiLeft + 20;
    y = guiTop + (ySize / 2) + 10;
    buttonList.add(buttonTakePoints = new UpDownButton(5, x, y));
    buttonTakePoints.setUpCharacter("\u21B3"); // ↳
    buttonTakePoints.setDownCharacter("\u21B1"); // ↱

    int xGab = 15;
    int yGab = 30;
    x = guiLeft + 182;
    y = guiTop + 90;
    for(int index = digits - 1; index >= 0; index--) {
      buttonList.add(buttonsInc[index] = new UpDownButton(10 + index, x - (index * xGab), y));
      buttonList.add(buttonsDec[index] = new UpDownButton(20 + index, x - (index * xGab), y + yGab, true));
    }

    updateButtons();
  }

  @Override
  public boolean doesGuiPauseGame() {
    return false;
  }

  static class UpDownButton extends GuiButton {

    private boolean down;

    private String upChar = "\u25B2"; // ▲
    private String downChar = "\u25BC"; // ▼

    public UpDownButton(int id, int x, int y) {
      this(id, x, y, false);
    }

    public UpDownButton(int id, int x, int y, boolean down) {
      super(id, x, y, "");
      this.down = down;
      this.width = 10;
      this.height = 10;
    }

    public void setDown(boolean down) {
      this.down = down;
    }

    public boolean getDown() {
      return down;
    }

    public void setUpCharacter(String chr) {
      upChar = chr;
    }

    public void setDownCharacter(String chr) {
      downChar = chr;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawButton(Minecraft minecraft, int x, int y) {
      if (visible) {
        FontRenderer fontrenderer = minecraft.fontRenderer;
        minecraft.getTextureManager().bindTexture(GuiClientExpStore.widgetsLocation);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        field_146123_n = x >= xPosition && y >= yPosition && x < xPosition + width && y < yPosition + height;
        int hoverState = getHoverState(field_146123_n);
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        drawTexturedModalRect(xPosition, yPosition, 0, hoverState * 10, width / 2, height);
        drawTexturedModalRect(xPosition + width / 2, yPosition, 10 - width / 2, hoverState * 10, width / 2, height);
        mouseDragged(minecraft, x, y);
        int l = 14737632;

        if (packedFGColour != 0)
        {
          l = packedFGColour;
        }
        else if (!enabled)
        {
          l = 10526880;
        }
        else if (field_146123_n)
        {
          l = 16777120;
        }

        drawCenteredString(fontrenderer, !down ? upChar : downChar, xPosition + width / 2, yPosition + (height - 8) / 2, l);
      }
    }
  }

}
