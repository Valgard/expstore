package com.trontheim.expstore.network;

import com.trontheim.expstore.ExperienceStore;
import com.trontheim.expstore.network.packet.*;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

public class PacketHandler {

  /**
   * The SimpleNetworkWrapper instance is used both to register and send packets.
   * Since I will be adding wrapper methods, this field is private, but you should
   * make it public if you plan on using it directly.
   */
  private static final SimpleNetworkWrapper dispatcher = NetworkRegistry.INSTANCE.newSimpleChannel(ExperienceStore.MODID);

  private static byte packetId = 0;

  /**
   * Call this during pre-init or loading and register all of your packets (messages) here
   */
  public static final void registerPackets() {
    PacketHandler.registerMessage(OpenGuiMessage.Handler.class, OpenGuiMessage.class);
    PacketHandler.registerMessage(ExperienceMessage.Handler.class, ExperienceMessage.class);
    PacketHandler.registerMessage(ResetExperienceStoreMessage.Handler.class, ResetExperienceStoreMessage.class);
  }

  private static final void registerMessage(Class handlerClass, Class messageClass) {
    if(AbstractBiDirectionalMessageHandler.class.isAssignableFrom(handlerClass)) {
      PacketHandler.registerMessage(handlerClass, messageClass, Side.CLIENT);
      PacketHandler.registerMessage(handlerClass, messageClass, Side.SERVER);
    } else {
      Side side = AbstractClientMessageHandler.class.isAssignableFrom(handlerClass) ? Side.CLIENT : Side.SERVER;
      PacketHandler.registerMessage(handlerClass, messageClass, side);
    }
  }

  /**
   * Registers a message and message handler
   */
  private static final void registerMessage(Class handlerClass, Class messageClass, Side side) {
    PacketHandler.dispatcher.registerMessage(handlerClass, messageClass, packetId++, side);
  }

  /**
   * Send this message to the specified player.
   * See {@link SimpleNetworkWrapper#sendTo(IMessage, EntityPlayerMP)}
   */
  public static final void sendTo(IMessage message, EntityPlayerMP player) {
    PacketHandler.dispatcher.sendTo(message, player);
  }

  /**
   * Sends a message to everyone within a certain range of the player provided.
   */
  public static final void sendToAllAround(IMessage message, EntityPlayer player, double range) {
    PacketHandler.sendToAllAround(message, player.worldObj.provider.dimensionId, player.posX, player.posY, player.posZ, range);
  }

  /**
   * Sends a message to everyone within a certain range of the coordinates in the same dimension.
   */
  public static final void sendToAllAround(IMessage message, int dimension, double x, double y, double z, double range) {
    PacketHandler.sendToAllAround(message, new NetworkRegistry.TargetPoint(dimension, x, y, z, range));
  }

  /**
   * Send this message to everyone within a certain range of a point.
   * See {@link SimpleNetworkWrapper#sendToAllAround(IMessage, NetworkRegistry.TargetPoint)}
   */
  public static final void sendToAllAround(IMessage message, NetworkRegistry.TargetPoint point) {
    PacketHandler.dispatcher.sendToAllAround(message, point);
  }

  /**
   * Send this message to everyone within the supplied dimension.
   * See {@link SimpleNetworkWrapper#sendToDimension(IMessage, int)}
   */
  public static final void sendToDimension(IMessage message, int dimensionId) {
    PacketHandler.dispatcher.sendToDimension(message, dimensionId);
  }

  /**
   * Send this message to the server.
   * See {@link SimpleNetworkWrapper#sendToServer(IMessage)}
   */
  public static final void sendToServer(IMessage message) {
    PacketHandler.dispatcher.sendToServer(message);
  }

}
