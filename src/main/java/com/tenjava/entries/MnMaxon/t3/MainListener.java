package com.tenjava.entries.MnMaxon.t3;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkPopulateEvent;

import com.tenjava.entries.MnMaxon.Dungeon.Dungeon;
import com.tenjava.entries.MnMaxon.Village.Village;

public class MainListener implements Listener {
	@EventHandler
	public static void newChunk(ChunkLoadEvent e) {
		if (e.isNewChunk()) {
			YamlConfiguration cfg = Config.load("Config.yml");
			cfg = Config.setConfigDefault(cfg, "Villages.Max Chunks");
			cfg = Config.setConfigDefault(cfg, "Villages.Percent of chunks");
			cfg = Config.setConfigDefault(cfg, "Dungeons.Max Chunks");
			cfg = Config.setConfigDefault(cfg, "Dungeons.Percent of chunks");
			ArrayList<Chunk> villageChunks = new ArrayList<Chunk>();
			ArrayList<Chunk> dungeonChunks = new ArrayList<Chunk>();
			if (cfg.getDouble("Villages.Percent of chunks") > 0.0
					&& Math.rint(Math.random() * 100.0 / cfg.getDouble("Villages.Percent of chunks")) == 0
					&& cfg.getInt("Villages.Max Chunks") > 0) {
				Bukkit.broadcastMessage("A new Village has been discovered!");
				villageChunks.add(e.getChunk());
				int size = cfg.getInt("Villages.Max Chunks") / 2;
				size = (int) (size + Math.rint((cfg.getInt("Villages.Max Chunks") - size) * Math.random()));
				while (villageChunks.size() < size)
					villageChunks = getSurroundingChunks(
							villageChunks.get((int) (Math.random() * (villageChunks.size() - 1))), villageChunks, size);
				new Village(villageChunks);
			}
			if (cfg.getDouble("Dungeons.Percent of chunks") > 0.0
					&& Math.rint(Math.random() * 100.0 / cfg.getDouble("Dungeons.Percent of chunks")) == 0
					&& cfg.getInt("Dungeons.Max Chunks") > 0) {
				dungeonChunks.add(e.getChunk());
				int size = cfg.getInt("Dungeons.Max Chunks") / 2;
				size = (int) (size + Math.rint((cfg.getInt("Dungeons.Max Chunks") - size) * Math.random()));
				while (dungeonChunks.size() < size)
					dungeonChunks = getSurroundingChunks(
							dungeonChunks.get((int) (Math.random() * (dungeonChunks.size() - 1))), dungeonChunks, size);
				new Dungeon(dungeonChunks);
			}
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		e.getPlayer().sendMessage("1");
		if (e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
			e.getPlayer().sendMessage("2");
			YamlConfiguration cfg = Config.load("Config.yml");
			cfg = Config.setConfigDefault(cfg, "Villages.Max Chunks");
			cfg = Config.setConfigDefault(cfg, "Villages.Percent of chunks");
			cfg = Config.setConfigDefault(cfg, "Dungeons.Max Chunks");
			cfg = Config.setConfigDefault(cfg, "Dungeons.Percent of chunks");
			ArrayList<Chunk> villageChunks = new ArrayList<Chunk>();
			ArrayList<Chunk> dungeonChunks = new ArrayList<Chunk>();
			if (true) {
				e.getPlayer().sendMessage("3");
				Bukkit.broadcastMessage("A new Village has been discovered!");
				villageChunks.add(e.getPlayer().getLocation().getChunk());
				int size = cfg.getInt("Villages.Max Chunks") / 2;
				size = (int) (size + Math.rint((cfg.getInt("Villages.Max Chunks") - size) * Math.random()));
				while (villageChunks.size() < size)
					villageChunks = getSurroundingChunks(
							villageChunks.get((int) (Math.random() * (villageChunks.size() - 1))), villageChunks, size);
				new Village(villageChunks);
			}
			if (cfg.getDouble("Dungeons.Percent of chunks") > 0.0
					&& Math.rint(Math.random() * 100.0 / cfg.getDouble("Dungeons.Percent of chunks")) == 0
					&& cfg.getInt("Dungeons.Max Chunks") > 0) {
				dungeonChunks.add(e.getPlayer().getLocation().getChunk());
				int size = cfg.getInt("Dungeons.Max Chunks") / 2;
				size = (int) (size + Math.rint((cfg.getInt("Dungeons.Max Chunks") - size) * Math.random()));
				while (dungeonChunks.size() < size)
					dungeonChunks = getSurroundingChunks(
							dungeonChunks.get((int) (Math.random() * (dungeonChunks.size() - 1))), dungeonChunks, size);
				new Dungeon(dungeonChunks);
			}
		}
	}

	@EventHandler
	public void onPopulate(ChunkPopulateEvent e) {
		for (int x = 0; x <= 16; x++) {
			for (int z = 0; z <= 16; z++) {
				for (int y = 0; y <= 256; y++) {
					if (e.getChunk().getBlock(x, y, z).getType().equals(Material.IRON_ORE)
							|| e.getChunk().getBlock(x, y, z).getType().equals(Material.GOLD_ORE)
							|| e.getChunk().getBlock(x, y, z).getType().equals(Material.DIAMOND_ORE)
							|| e.getChunk().getBlock(x, y, z).getType().equals(Material.COAL_ORE))
						e.getChunk().getBlock(x, y, z).setType(Material.STONE);
				}
			}
		}
	}

	private static ArrayList<Chunk> getSurroundingChunks(Chunk chunk, ArrayList<Chunk> selectedChunks, int size) {
		if (!selectedChunks.contains(chunk.getWorld().getChunkAt(chunk.getX() + 1, chunk.getZ()))
				&& Math.rint(Math.random()) == 1 && selectedChunks.size() <= size)
			selectedChunks.add(chunk.getWorld().getChunkAt(chunk.getX() + 1, chunk.getZ()));

		if (!selectedChunks.contains(chunk.getWorld().getChunkAt(chunk.getX() - 1, chunk.getZ()))
				&& Math.rint(Math.random()) == 1 && selectedChunks.size() <= size)
			selectedChunks.add(chunk.getWorld().getChunkAt(chunk.getX() - 1, chunk.getZ()));

		if (!selectedChunks.contains(chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ()))
				&& Math.rint(Math.random()) == 1 && selectedChunks.size() <= size)
			selectedChunks.add(chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() + 1));

		if (!selectedChunks.contains(chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() - 1))
				&& Math.rint(Math.random()) == 1 && selectedChunks.size() <= size)
			selectedChunks.add(chunk.getWorld().getChunkAt(chunk.getX(), chunk.getZ() - 1));
		return selectedChunks;
	}
}