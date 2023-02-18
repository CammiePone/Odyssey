package dev.cammiescorner.odyssey.common.packets;

import com.mojang.serialization.Codec;
import dev.cammiescorner.odyssey.Odyssey;
import dev.cammiescorner.odyssey.common.utils.Dialogue;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import org.quiltmc.qsl.networking.api.PacketByteBufs;
import org.quiltmc.qsl.networking.api.PacketSender;
import org.quiltmc.qsl.networking.api.PlayerLookup;
import org.quiltmc.qsl.networking.api.ServerPlayNetworking;

import java.util.List;
import java.util.Map;

public class SyncDialoguesPacket {
	public static final Identifier ID = Odyssey.id("sync_dialogues");
	public static final Codec<Map<EntityType<?>, List<Dialogue>>> CODEC = Codec.unboundedMap(
			Registries.ENTITY_TYPE.getCodec(),
			Dialogue.CODEC.listOf()
	);

	public static void send(MinecraftServer server) {
		PacketByteBuf buf = PacketByteBufs.create();
		NbtCompound tag = new NbtCompound();

		tag.put("DialogueMap", CODEC.encodeStart(NbtOps.INSTANCE, Odyssey.DIALOGUES).getOrThrow(false, Odyssey.LOGGER::error));
		buf.writeNbt(tag);

		ServerPlayNetworking.send(PlayerLookup.all(server), ID, buf);
	}

	public static void handle(MinecraftClient client, ClientPlayNetworkHandler handler, PacketByteBuf buf, PacketSender sender) {
		NbtCompound tag = buf.readNbt();
		Map<EntityType<?>, List<Dialogue>> map = CODEC.parse(NbtOps.INSTANCE, tag.get("DialogueMap")).getOrThrow(false, Odyssey.LOGGER::error);

		client.execute(() -> {
			Odyssey.DIALOGUES.clear();
			Odyssey.DIALOGUES.putAll(map);
		});
	}
}
