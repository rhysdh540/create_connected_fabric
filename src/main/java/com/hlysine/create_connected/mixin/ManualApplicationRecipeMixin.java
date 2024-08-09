package com.hlysine.create_connected.mixin;

import com.hlysine.create_connected.config.CServer;
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ManualApplicationRecipe.class)
public class ManualApplicationRecipeMixin {
    @Inject(
            method = "manualApplicationRecipesApplyInWorld",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;shrink(I)V")
    )
    private static void craftingRemainingItemOnApplication(Player player, Level level, InteractionHand hand, BlockHitResult hitResult, CallbackInfoReturnable<InteractionResult> cir) {
        if (!CServer.ApplicationRemainingItemFix.get()) return;

        ItemStack heldItem = player.getItemInHand(hand);
        ItemStack leftover = heldItem.hasCraftingRemainingItem() ? heldItem.getCraftingRemainingItem() : ItemStack.EMPTY;

        heldItem.shrink(1);

        if (heldItem.isEmpty()) {
            player.setItemInHand(hand, leftover);
        } else {
            heldItem.grow(1); // Create shrinks the stack again after this inject
            if (!player.getInventory().add(leftover)) {
                player.drop(leftover, false);
            }
        }
    }
}
