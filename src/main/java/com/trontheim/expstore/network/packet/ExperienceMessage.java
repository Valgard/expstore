package com.trontheim.expstore.network.packet;

import com.trontheim.expstore.block.tileentity.TileEntityExpStore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class ExperienceMessage implements IMessage {

  int mode;
  int serverX;
  int serverY;
  int serverZ;

  public enum MODE {
    STORE,
    RESTORE
  }

  public ExperienceMessage() {
  }

  public ExperienceMessage(MODE mode, TileEntityExpStore tileEntity) {
    this.mode = mode.ordinal();
    this.serverX = tileEntity.xCoord;
    this.serverY = tileEntity.yCoord;
    this.serverZ = tileEntity.zCoord;
  }

  public ExperienceMessage(int mode, TileEntityExpStore tileEntity) {
    this.mode = mode;
    this.serverX = tileEntity.xCoord;
    this.serverY = tileEntity.yCoord;
    this.serverZ = tileEntity.zCoord;
  }

  @Override
  public void fromBytes(ByteBuf buffer) {
    mode = buffer.readInt();
    serverX = buffer.readInt();
    serverY = buffer.readInt();
    serverZ = buffer.readInt();
  }

  @Override
  public void toBytes(ByteBuf buffer) {
    buffer.writeInt(mode);
    buffer.writeInt(serverX);
    buffer.writeInt(serverY);
    buffer.writeInt(serverZ);
  }

  public static class Handler extends AbstractBiDirectionalMessageHandler<ExperienceMessage> {

    @Override
    public IMessage handleClientMessage(EntityPlayer player, ExperienceMessage message, MessageContext ctx) {
      return handleMessage(player, message, ctx);
    }

    @Override
    public IMessage handleServerMessage(EntityPlayer player, ExperienceMessage message, MessageContext ctx) {
      return handleMessage(player, message, ctx);
    }


    private IMessage handleMessage(EntityPlayer player, ExperienceMessage message, MessageContext ctx) {
      TileEntityExpStore tileEntity = (TileEntityExpStore) player.worldObj.getTileEntity(message.serverX, message.serverY, message.serverZ);

      if(message.mode == MODE.STORE.ordinal()) {
        tileEntity.storeExperiencePoints(player);
      } else if (message.mode == MODE.RESTORE.ordinal()) {
        tileEntity.restoreExperiencePoints(player);
      }

      player.worldObj.setTileEntity(message.serverX, message.serverY, message.serverZ, tileEntity);

      if(ctx.side == Side.SERVER) {
        return new ExperienceMessage(message.mode, tileEntity);
      }

      return null;
    }
  }

}
