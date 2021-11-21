package dev.turra.codenames.client.network;

import dev.turra.codenames.common.network.Packet;

public interface IPacketListener {

	void received(Packet p);

}
