package com.hlysine.create_connected.content.shearpin;

import com.hlysine.create_connected.CCBlocks;
import com.simibubi.create.content.kinetics.KineticNetwork;
import com.simibubi.create.content.kinetics.base.IRotate;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.ticks.TickPriority;

public class ShearPinBlockEntity extends BracketedKineticBlockEntity {

    static final int RANDOM_DELAY = 5;

    public ShearPinBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void updateFromNetwork(float maxStress, float currentStress, int networkSize) {
        super.updateFromNetwork(maxStress, currentStress, networkSize);

        if (IRotate.StressImpact.isEnabled()) {
            if (isOverStressed()) {
                if (level != null) {
                    level.scheduleTick(getBlockPos(), CCBlocks.SHEAR_PIN.get(), level.random.nextInt(RANDOM_DELAY), TickPriority.EXTREMELY_HIGH);
                }
            }
        }
    }

    @Override
    public void onSpeedChanged(float prevSpeed) {
        super.onSpeedChanged(prevSpeed);
        KineticNetwork network = getOrCreateNetwork();
        updateFromNetwork(capacity, stress, network == null ? 0 : network.getSize());
    }
}
