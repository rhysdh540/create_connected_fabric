package com.hlysine.create_connected.datagen;

import com.hlysine.create_connected.CCItems;
import com.hlysine.create_connected.CreateConnected;
import com.simibubi.create.foundation.data.TagGen;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;

public class CCTagGen {
    public static void addGenerators() {
        CreateConnected.getRegistrate().addDataGenerator(ProviderType.ITEM_TAGS, CCTagGen::genItemTags);
    }

    private static void genItemTags(RegistrateTagsProvider<Item> provIn) {
        TagGen.CreateTagsProvider<Item> prov = new TagGen.CreateTagsProvider<>(provIn, Item::builtInRegistryHolder);

        prov.tag(ItemTags.CREEPER_DROP_MUSIC_DISCS)
                .add(CCItems.MUSIC_DISC_ELEVATOR.get());
    }
}
