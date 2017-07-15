package com.trontheim.expstore.block.tileentity;

import com.google.gson.Gson;
import com.trontheim.expstore.ExperienceStore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;

import java.util.HashMap;
import java.util.UUID;

public class TileEntityExpStore extends TileEntity {

  private ExperienceStorage experienceStorage = new ExperienceStorage();

  public boolean storeExperiencePoints(EntityPlayer player) {
    if(0 == player.experienceTotal) {
      return false;
    }

    Integer amount = player.experienceTotal;

    if(!experienceStorage.addExperiencePoints(player, amount)) {
      return false;
    }

    player.addExperienceLevel(-(player.experienceLevel + 1));

    player.addChatComponentMessage(new ChatComponentText("experience points stored: " + amount.toString()));

    return true;
  }

  public boolean restoreExperiencePoints(EntityPlayer player) {
    if(0 == experienceStorage.getExperiencePoints(player)) {
      return false;
    }

    Integer amount = experienceStorage.getExperiencePoints(player);

    if(!experienceStorage.addExperiencePoints(player, -amount)) {
      return false;
    }

    player.addExperience(amount);

    player.addChatComponentMessage(new ChatComponentText("experience points restored: " + amount.toString()));

    return true;
  }

  public Integer getSoredExperiencePoints(EntityPlayer player) {
    return experienceStorage.getExperiencePoints(player);
  }

  @Override
  public Packet getDescriptionPacket() {
    NBTTagCompound nbttagcompound = new NBTTagCompound();
    writeToNBT(nbttagcompound);
    return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 0, nbttagcompound);
  }

  @Override
  public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
    NBTTagCompound tag = pkt.func_148857_g();
    readFromNBT(tag);
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    super.readFromNBT(compound);
    experienceStorage = new Gson().fromJson(compound.getString(ExperienceStore.MODID + ":TileEntityExpStore"), ExperienceStorage.class);
    if(experienceStorage == null) {
      experienceStorage = new ExperienceStorage();
    }
  }

  @Override
  public void writeToNBT(NBTTagCompound compound) {
    super.writeToNBT(compound);
    compound.setString(ExperienceStore.MODID + ":TileEntityExpStore", new Gson().toJson(experienceStorage));
  }

  private static class ExperienceStorage {

    private HashMap<UUID, Integer> experience = new HashMap<UUID, Integer>();

    private boolean addExperiencePoints(EntityPlayer player, Integer amount) {
      if(!experience.containsKey(player.getUniqueID())) {
        experience.put(player.getUniqueID(), 0);
      }

      // get current stored experience
      Integer currentAmount = experience.get(player.getUniqueID());

      // check max integer value to avoid exceptions
      int maxAmount = Integer.MAX_VALUE - currentAmount;
      if(amount > maxAmount) {
        amount = maxAmount;
      }

      // check min integer value to avoid exceptions
      int minAmount = Integer.MIN_VALUE + currentAmount;
      if(amount < minAmount) {
        amount = minAmount;
      }

      // change experience
      currentAmount += amount;

      // check if stored experience lesser than zero and set it to null
      if(currentAmount < 0) {
        currentAmount = 0;
      }

      experience.put(player.getUniqueID(), currentAmount);

      return true;
    }

    private Integer getExperiencePoints(EntityPlayer player) {
      if(!experience.containsKey(player.getUniqueID())) {
        experience.put(player.getUniqueID(), 0);
      }

      return experience.get(player.getUniqueID());
    }

  }

}
