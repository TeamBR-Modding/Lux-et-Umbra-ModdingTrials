package com.teambr.luxetumbra.network;

import com.teambr.luxetumbra.lib.Constants;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/**
 * This file was created for Lux-et-Umbra
 * <p>
 * Lux-et-Umbra is licensed under the
 * Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International License:
 * http://creativecommons.org/licenses/by-nc-sa/4.0/
 *
 * @author Dyonovan
 * @since 8/16/2016
 */
public class PacketDispatcher {

    public static SimpleNetworkWrapper net;
    private static int nextPacketID = 0;

    public static void initPackets() {
        net = NetworkRegistry.INSTANCE.newSimpleChannel(Constants.MOD_ID().toUpperCase());

        registerMessage(UpdateSpellLevelPacket.class, UpdateSpellLevelPacket.class);
    }

    @SuppressWarnings("unchecked")
    private static void registerMessage(Class packet, Class message) {
        net.registerMessage(packet, message, nextPacketID, Side.CLIENT);
        net.registerMessage(packet, message, nextPacketID, Side.SERVER);
        nextPacketID++;
    }
}
