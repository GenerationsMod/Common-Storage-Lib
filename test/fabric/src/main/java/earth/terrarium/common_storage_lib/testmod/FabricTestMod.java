package earth.terrarium.common_storage_lib.testmod;

import earth.terrarium.common_storage_lib.context.impl.PlayerContext;
import earth.terrarium.common_storage_lib.item.impl.SimpleItemStorage;
import earth.terrarium.common_storage_lib.item.impl.vanilla.VanillaDelegatingSlot;
import earth.terrarium.common_storage_lib.item.impl.vanilla.WrappedVanillaContainer;
import earth.terrarium.common_storage_lib.item.util.ItemStorageData;
import earth.terrarium.common_storage_lib.testmod.blocks.TransferTestBlock;
import earth.terrarium.common_storage_lib.testmod.items.TransferTestItem;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerBlockEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerEntityEvents;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ShulkerBoxMenu;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.minecraft.client.gui.components.toasts.TutorialToast.Icons.RIGHT_CLICK;

public class FabricTestMod {
    public static final TransferTestBlock TRANSFER_BLOCK = Registry.register(BuiltInRegistries.BLOCK, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "test_block"), new TransferTestBlock(BlockBehaviour.Properties.of()));
    public static final TransferTestItem TRANSFER_ITEM = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "test_item"), new TransferTestItem(new Item.Properties().stacksTo(1)));
    public static final BlockItem TRANSFER_BLOCK_ITEM = Registry.register(BuiltInRegistries.ITEM, ResourceLocation.fromNamespaceAndPath(TestMod.MOD_ID, "test_block"), new BlockItem(TRANSFER_BLOCK, new Item.Properties()));

    public static void init() {
        TestMod.init();

        UseItemCallback.EVENT.register(new UseItemCallback() {
            @Override
            public InteractionResultHolder<ItemStack> interact(Player player, Level level, InteractionHand interactionHand) {
                var stack = player.getItemInHand(interactionHand);

                if(stack.getItem() instanceof BlockItem blockItem && blockItem.getBlock() instanceof ShulkerBoxBlock) {
                    player.openMenu(new MenuProvider() {
                        @Override
                        public Component getDisplayName() {
                            return stack.getHoverName();
                        }

                        @Override
                        public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
                            return new ShulkerBoxMenu(i, inventory, of(stack, 27));
                        }
                    });

                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
                }

                return InteractionResultHolder.pass(stack);
            }
        });
    }



    public static Container of(ItemStack stack, int size) {
        var data = stack.getOrDefault(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        var container = new SimpleContainer(27);
        data.copyInto(container.items);
        container.addListener(container1 -> stack.set(DataComponents.CONTAINER, ItemContainerContents.fromItems(((SimpleContainer) container1).getItems())));

        return container;
    }
}
