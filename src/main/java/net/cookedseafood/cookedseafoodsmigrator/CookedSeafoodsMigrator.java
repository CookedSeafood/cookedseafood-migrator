package net.cookedseafood.cookedseafoodsmigrator;

import java.util.HashMap;
import java.util.Map;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.NbtComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtString;
import net.minecraft.util.ActionResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CookedSeafoodsMigrator implements ModInitializer {
	public static final String MOD_ID = "cookedseafoods-migrator";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final String ROGUE_SWORD_ITEM_NAME_1_1_12 = "Rogue Sword";
	public static final String ENDER_STAFF_ITEM_NAME_1_0_5 = "Ender Staff";
	public static final String ROGUE_SWORD_CUSTOM_ID_1_3_0 = "rogue_sword:rogue_sword";
	public static final String ENDER_STAFF_CUSTOM_ID_1_2_0 = "ender_staff:ender_staff";

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.

		LOGGER.info("[CookedSeafood's-Migrator] Loaded!");

		UseItemCallback.EVENT.register((player, world, hand) -> {
			ItemStack stack = player.getStackInHand(hand);
			String itemName = stack.getItemName().getString();
			String customId = stack.getCustomId();

			if (ROGUE_SWORD_ITEM_NAME_1_1_12.equals(itemName) && !ROGUE_SWORD_CUSTOM_ID_1_3_0.equals(customId)) {
				stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
					stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
						new NbtCompound(
							new HashMap<>(
								Map.<String, NbtElement>of(
									"id",
									NbtString.of(ROGUE_SWORD_CUSTOM_ID_1_3_0)
								)
							)
						)
					)
				));
			} else if (ENDER_STAFF_ITEM_NAME_1_0_5.equals(itemName) && !ENDER_STAFF_CUSTOM_ID_1_2_0.equals(customId)) {
				stack.set(DataComponentTypes.CUSTOM_DATA, NbtComponent.of(
					stack.getOrDefault(DataComponentTypes.CUSTOM_DATA, NbtComponent.DEFAULT).copyNbt().copyFrom(
						new NbtCompound(
							new HashMap<>(
								Map.<String, NbtElement>of(
									"id",
									NbtString.of(ENDER_STAFF_CUSTOM_ID_1_2_0)
								)
							)
						)
					)
				));
			}

			return ActionResult.PASS;
		});
	}
}
