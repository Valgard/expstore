package com.trontheim.expstore.network.packet;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.network.PacketHandler;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.entity.player.EntityPlayer;

public abstract class AbstractMessageHandler<T extends IMessage> implements IMessageHandler<T, IMessage> {

  @Override
  public IMessage onMessage(T message, MessageContext ctx) {
    if(ctx.side.isClient()) {
      return handleClientMessage(ExperienceStore.proxy.getPlayerEntity(ctx), message, ctx);
    } else {
      return handleServerMessage(ExperienceStore.proxy.getPlayerEntity(ctx), message, ctx);
    }
  }

  /**
   * Handle a message received on the client side
   *
   * @return a message to send back to the Server, or null if no reply is necessary
   */
  public abstract IMessage handleClientMessage(EntityPlayer player, T message, MessageContext ctx);

  /**
   * Handle a message received on the server side
   *
   * @return a message to send back to the Client, or null if no reply is necessary
   */
  public abstract IMessage handleServerMessage(EntityPlayer player, T message, MessageContext ctx);

}
