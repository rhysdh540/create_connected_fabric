package com.hlysine.create_connected;

import com.hlysine.create_connected.config.FeatureToggle;
import com.simibubi.create.AllCreativeModeTabs.TabInfo;

import com.tterrag.registrate.util.entry.ItemProviderEntry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.function.Supplier;

public class CCCreativeTabs {
    public static final List<ItemProviderEntry<?>> ITEMS = List.of(
            CCBlocks.ENCASED_CHAIN_COGWHEEL,
            CCBlocks.CRANK_WHEEL,
            CCBlocks.LARGE_CRANK_WHEEL,
            CCBlocks.INVERTED_CLUTCH,
            CCBlocks.INVERTED_GEARSHIFT,
            CCBlocks.PARALLEL_GEARBOX,
            CCItems.VERTICAL_PARALLEL_GEARBOX,
            CCBlocks.SIX_WAY_GEARBOX,
            CCItems.VERTICAL_SIX_WAY_GEARBOX,
            CCBlocks.BRASS_GEARBOX,
            CCItems.VERTICAL_BRASS_GEARBOX,
            CCBlocks.SHEAR_PIN,
            CCBlocks.OVERSTRESS_CLUTCH,
            CCBlocks.CENTRIFUGAL_CLUTCH,
            CCBlocks.FREEWHEEL_CLUTCH,
            CCBlocks.BRAKE,
            CCBlocks.ITEM_SILO,
            CCBlocks.FLUID_VESSEL,
            CCBlocks.CREATIVE_FLUID_VESSEL,
            CCBlocks.INVENTORY_ACCESS_PORT,
            CCBlocks.INVENTORY_BRIDGE,
            CCBlocks.SEQUENCED_PULSE_GENERATOR,
            CCItems.LINKED_TRANSMITTER,
            CCBlocks.EMPTY_FAN_CATALYST,
            CCBlocks.FAN_BLASTING_CATALYST,
            CCBlocks.FAN_SMOKING_CATALYST,
            CCBlocks.FAN_SPLASHING_CATALYST,
            CCBlocks.FAN_HAUNTING_CATALYST,
            CCBlocks.FAN_SEETHING_CATALYST,
            CCBlocks.FAN_FREEZING_CATALYST,
            CCBlocks.FAN_SANDING_CATALYST,
            CCBlocks.COPYCAT_BLOCK,
            CCBlocks.COPYCAT_SLAB,
            CCBlocks.COPYCAT_BEAM,
            CCBlocks.COPYCAT_VERTICAL_STEP,
            CCBlocks.COPYCAT_STAIRS,
            CCBlocks.COPYCAT_FENCE,
            CCBlocks.COPYCAT_FENCE_GATE,
            CCBlocks.COPYCAT_WALL,
            CCBlocks.COPYCAT_BOARD,
            CCItems.COPYCAT_BOX,
            CCItems.COPYCAT_CATWALK,
            CCItems.CONTROL_CHIP,
            CCItems.MUSIC_DISC_ELEVATOR,
            CCItems.MUSIC_DISC_INTERLUDE,
            CCBlocks.CHERRY_WINDOW,
            CCBlocks.BAMBOO_WINDOW,
            CCBlocks.CHERRY_WINDOW_PANE,
            CCBlocks.BAMBOO_WINDOW_PANE
    );

    public static final TabInfo MAIN = register("main", () -> FabricItemGroup.builder()
            .title(Component.translatable("itemGroup.create_connected.main"))
            .icon(CCBlocks.PARALLEL_GEARBOX::asStack)
//            .withTabsBefore(AllCreativeModeTabs.PALETTES_CREATIVE_TAB.getKey())
            .displayItems(new DisplayItemsGenerator(ITEMS))
            .build());

    private static TabInfo register(String name, Supplier<CreativeModeTab> supplier) {
        ResourceLocation id = CreateConnected.asResource(name);
        ResourceKey<CreativeModeTab> key = ResourceKey.create(Registries.CREATIVE_MODE_TAB, id);
        CreativeModeTab tab = supplier.get();
        Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, key, tab);
        return new TabInfo(key, tab);
    }

    private record DisplayItemsGenerator(
            List<ItemProviderEntry<?>> items) implements CreativeModeTab.DisplayItemsGenerator {
        @Override
        public void accept(@NotNull CreativeModeTab.ItemDisplayParameters params, @NotNull CreativeModeTab.Output output) {
            for (ItemProviderEntry<?> item : items) {
                if (FeatureToggle.isEnabled(item.getId())) {
                    output.accept(item);
                }
            }
        }
    }

    public static void register() {
    }
}
