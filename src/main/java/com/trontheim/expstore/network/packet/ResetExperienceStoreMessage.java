package com.trontheim.expstore.network.packet;

import com.trontheim.expstore.block.tileentity.TileEntityExpStore;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import cpw.mods.fml.relauncher.Side;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;

public class ResetExperienceStoreMessage implements IMessage {

  int serverX;
  int serverY;
  int serverZ;

  public ResetExperienceStoreMessage() {
  }

  public ResetExperienceStoreMessage(int x, int y, int z) {
    this.serverX = x;
    this.serverY = y;
    this.serverZ = z;
  }

  @Override
  public void fromBytes(ByteBuf buffer) {
    serverX = buffer.readInt();
    serverY = buffer.readInt();
    serverZ = buffer.readInt();
  }

  @Override
  public void toBytes(ByteBuf buffer) {
    buffer.writeInt(serverX);
    buffer.writeInt(serverY);
    buffer.writeInt(serverZ);
  }

  public static class Handler extends AbstractBiDirectionalMessageHandler<ResetExperienceStoreMessage> {

    @Override
    public IMessage handleClientMessage(EntityPlayer player, ResetExperienceStoreMessage message, MessageContext ctx) {
      return handleMessage(player, message, ctx);
    }

    @Override
    public IMessage handleServerMessage(EntityPlayer player, ResetExperienceStoreMessage message, MessageContext ctx) {
      return handleMessage(player, message, ctx);
    }

    private IMessage handleMessage(EntityPlayer player, ResetExperienceStoreMessage message, MessageContext ctx) {
      TileEntityExpStore tileEntity = (TileEntityExpStore) player.worldObj.getTileEntity(message.serverX, message.serverY, message.serverZ);

      tileEntity.resetStore(player);

      player.worldObj.setTileEntity(message.serverX, message.serverY, message.serverZ, tileEntity);

      if(ctx.side == Side.SERVER) {
        return new ResetExperienceStoreMessage(tileEntity.xCoord, tileEntity.yCoord, tileEntity.zCoord);
      }

      return null;
    }
  }

}
