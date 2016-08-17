package com.teambr.luxetumbra.network

import com.teambr.luxetumbra.capabilities.player.SpellLevelCapability
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.fml.common.network.ByteBufUtils
import net.minecraftforge.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}

/**
  * This file was created for Lux-et-Umbra
  *
  * Lux-et-Umbra is licensed under the
  * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
  * http://creativecommons.org/licenses/by-nc-sa/4.0/
  *
  * @author Dyonovan
  * @since 8/16/2016
  */
class UpdateSpellLevelPacket extends IMessage with IMessageHandler[UpdateSpellLevelPacket, IMessage] {

    var entityPlayer: EntityPlayer = _
    var playerName = ""
    var spellLevel = 0

    def this(entityPlayer: EntityPlayer, spellLevel: Int) {
        this()
        this.entityPlayer = entityPlayer
        this.playerName = entityPlayer.getName
        this.spellLevel = spellLevel
    }
    override def toBytes(buf: ByteBuf): Unit = {
        buf.setInt(0, spellLevel)
        ByteBufUtils.writeUTF8String(buf, playerName)
    }

    override def fromBytes(buf: ByteBuf): Unit = {
        spellLevel = buf.getInt(0)
        playerName = ByteBufUtils.readUTF8String(buf)
    }

    override def onMessage(message: UpdateSpellLevelPacket, ctx: MessageContext): IMessage = {
        if (ctx.side.isClient) {
            val world = Minecraft.getMinecraft.theWorld
            val player = world.getPlayerEntityByName(message.playerName)
            player.getCapability(SpellLevelCapability.SPELL_LEVEL, null).setSpellLevel(message.spellLevel)
        }

        null
    }
}
