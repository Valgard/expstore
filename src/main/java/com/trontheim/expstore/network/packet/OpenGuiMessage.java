package com.trontheim.expstore.network.packet;

import com.trontheim.expstore.ExperienceStore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class OpenGuiMessage implements IMessage {

  private int guiId;

  int serverX;
  int serverY;
  int serverZ;

  public OpenGuiMessage() {
  }

  public OpenGuiMessage(int guiId, int serverX, int serverY, int serverZ) {
    this.guiId = guiId;
    this.serverX = serverX;
    this.serverY = serverY;
    this.serverZ = serverZ;
  }

  @Override
  public void fromBytes(ByteBuf buffer) {
    guiId = buffer.readInt();
    serverX = buffer.readInt();
    serverY = buffer.readInt();
    serverZ = buffer.readInt();
  }

  @Override
  public void toBytes(ByteBuf buffer) {
    buffer.writeInt(guiId);
    buffer.writeInt(serverX);
    buffer.writeInt(serverY);
    buffer.writeInt(serverZ);
  }

  public static class Handler extends AbstractServerMessageHandler<OpenGuiMessage> {

    @Override
    public IMessage handleServerMessage(EntityPlayer player, OpenGuiMessage message, MessageContext ctx) {
      player.openGui(ExperienceStore.instance(), message.guiId, player.worldObj, message.serverX, message.serverY, message.serverZ);

      return null;
    }
  }

}
