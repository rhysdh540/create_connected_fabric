package com.hlysine.create_connected;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.Collections;

import static com.hlysine.create_connected.CCTags.NameSpace.*;

public class CCTags {
    public static <T> TagKey<T> optionalTag(Registry<T> registry, ResourceLocation id) {
        return registry.tags().createOptionalTagKey(id, Collections.emptySet());
    }

    public static <T> TagKey<T> forgeTag(Registry<T> registry, String path) {
        return optionalTag(registry, new ResourceLocation("c", path));
    }

    public static TagKey<net.minecraft.world.level.block.Block> forgeBlockTag(String path) {
        return forgeTag(BuiltInRegistries.BLOCK, path);
    }

    public static TagKey<Item> fabricItemTag(String path) {
        return forgeTag(BuiltInRegistries.ITEM, path);
    }

    public static TagKey<Fluid> fabricFluidTag(String path) {
        return forgeTag(BuiltInRegistries.FLUID, path);
    }

    public enum NameSpace {

        MOD(CreateConnected.MODID, false, true),
        COPYCATS("copycats");

        public final String id;
        public final boolean optionalDefault;
        public final boolean alwaysDatagenDefault;

        NameSpace(String id) {
            this(id, true, false);
        }

        NameSpace(String id, boolean optionalDefault, boolean alwaysDatagenDefault) {
            this.id = id;
            this.optionalDefault = optionalDefault;
            this.alwaysDatagenDefault = alwaysDatagenDefault;
        }
    }

    public enum Items {

        // Tags for recipe compat with Create: Connected
        COPYCAT_BEAM(COPYCATS),
        COPYCAT_BLOCK(COPYCATS),
        COPYCAT_BOARD(COPYCATS),
        COPYCAT_BOX(COPYCATS),
        COPYCAT_CATWALK(COPYCATS),
        COPYCAT_FENCE(COPYCATS),
        COPYCAT_FENCE_GATE(COPYCATS),
        COPYCAT_SLAB(COPYCATS),
        COPYCAT_STAIRS(COPYCATS),
        COPYCAT_VERTICAL_STEP(COPYCATS),
        COPYCAT_WALL(COPYCATS);

        public final TagKey<Item> tag;
        public final boolean alwaysDatagen;

        Items() {
            this(MOD);
        }

        Items(NameSpace namespace) {
            this(namespace, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        Items(NameSpace namespace, String path) {
            this(namespace, path, namespace.optionalDefault, namespace.alwaysDatagenDefault);
        }

        Items(NameSpace namespace, boolean optional, boolean alwaysDatagen) {
            this(namespace, null, optional, alwaysDatagen);
        }

        Items(NameSpace namespace, String path, boolean optional, boolean alwaysDatagen) {
            ResourceLocation id = new ResourceLocation(namespace.id, path == null ? Lang.asId(name()) : path);
            if (optional) {
                tag = optionalTag(BuiltInRegistries.ITEM, id);
            } else {
                tag = TagKey.create(BuiltInRegistries.ITEM.key(), id);
            }
            this.alwaysDatagen = alwaysDatagen;
        }

        @SuppressWarnings("deprecation")
        public boolean matches(Item item) {
            return item.builtInRegistryHolder().is(tag);
        }

        public boolean matches(ItemStack stack) {
            return stack.is(tag);
        }

        private static void init() {
        }

    }

    public static void init() {
        Items.init();
    }
}
