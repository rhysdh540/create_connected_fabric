package com.hlysine.create_connected.datagen.recipes;

import com.hlysine.create_connected.CCBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllRecipeTypes;

@SuppressWarnings("unused")
public class CuttingRecipeGen extends ProcessingRecipeGen {

    GeneratedRecipe SHEAR_PIN = create(AllBlocks.SHAFT::get, b -> b.duration(200)
            .withCondition(new FeatureEnabledCondition(CCBlocks.SHEAR_PIN.getId()))
            .output(CCBlocks.SHEAR_PIN.get()));

    public CuttingRecipeGen(FabricDataOutput output) {
        super(output);
    }

    @Override
    protected AllRecipeTypes getRecipeType() {
        return AllRecipeTypes.CUTTING;
    }

}

