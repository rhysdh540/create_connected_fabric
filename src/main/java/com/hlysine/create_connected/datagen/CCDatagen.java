package com.hlysine.create_connected.datagen;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hlysine.create_connected.CCPonders;
import com.hlysine.create_connected.CCSoundEvents;
import com.hlysine.create_connected.CreateConnected;
import com.hlysine.create_connected.datagen.advancements.CCAdvancements;
import com.hlysine.create_connected.datagen.recipes.CCStandardRecipes;
import com.hlysine.create_connected.datagen.recipes.ProcessingRecipeGen;
import com.hlysine.create_connected.datagen.recipes.SequencedAssemblyGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.tterrag.registrate.providers.ProviderType;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiConsumer;

public class CCDatagen implements DataGeneratorEntrypoint {

    private static final CreateRegistrate REGISTRATE = CreateConnected.getRegistrate();

    public void onInitializeDataGenerator(FabricDataGenerator gen) {
        addExtraRegistrateData();

        Path existingResources = Paths.get(System.getProperty(ExistingFileHelper.EXISTING_RESOURCES));
        // fixme re-enable the existing file helper when porting lib's ResourcePackLoader.createPackForMod is fixed
        ExistingFileHelper helper = new ExistingFileHelper(
                Set.of(existingResources), Set.of("create"), false, null, null
        );

        FabricDataGenerator.Pack pack = gen.createPack();

        CreateConnected.getRegistrate().setupDatagen(pack, helper);

        // if client
        pack.addProvider(CCSoundEvents::provider);

        // if server
        pack.addProvider(CCAdvancements::new);
        pack.addProvider(CCStandardRecipes::new);
        pack.addProvider(SequencedAssemblyGen::new);
        pack.addProvider(ProcessingRecipeGen::registerAll);
    }

    private static void addExtraRegistrateData() {
        CCTagGen.addGenerators();

        REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;

            provideDefaultLang("interface", langConsumer);
            provideDefaultLang("tooltips", langConsumer);
            CCAdvancements.provideLang(langConsumer);
            CCSoundEvents.provideLang(langConsumer);
            providePonderLang(langConsumer);
        });
    }

    private static void provideDefaultLang(String fileName, BiConsumer<String, String> consumer) {
        String path = "assets/create_connected/lang/default/" + fileName + ".json";
        JsonElement jsonElement = FilesHelper.loadJsonResource(path);
        if (jsonElement == null) {
            throw new IllegalStateException(String.format("Could not find default lang file: %s", path));
        }
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        for (Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue().getAsString();
            consumer.accept(key, value);
        }
    }

    private static void providePonderLang(BiConsumer<String, String> consumer) {
        CCPonders.register();
        PonderLocalization.generateSceneLang();
        PonderLocalization.provideLang(CreateConnected.MODID, consumer);
    }
}

