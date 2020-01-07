package mod.dalhome3.spaceexplorer;

import mod.dalhome3.spaceexplorer.init.ModItemGroups;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@EventBusSubscriber(modid = SpaceExplorer.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

    private static final Logger LOGGER = LogManager.getLogger(SpaceExplorer.MODID + " Mod Event Subscriber");
    @SubscribeEvent
    public static void onRegisterBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                setup(new Block(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.0F, 3.0F)), "example_ore")
        );

        LOGGER.debug("Registered Blocks");
    }

    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        event.getRegistry().registerAll(
                setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "example_item"),
                setup(new Item(new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP)), "blood_diamond")
        );

        // Automatically register BlockItems for all our Blocks
        // (We need to go over the entire registry so that we include any potential Registry Overrides)
        ForgeRegistries.BLOCKS.getValues().stream()
                // Filter out blocks that aren't from our mod
                .filter(block -> block.getRegistryName().getNamespace().equals(SpaceExplorer.MODID))
                // You can do extra filtering here if you don't want some blocks to have an BlockItem automatically registered for them
                // Register the BlockItem for the block
                .forEach(block -> {
                    // Make the properties, and make it so that the item will be on our ItemGroup (CreativeTab)
                    final Item.Properties properties = new Item.Properties().group(ModItemGroups.MOD_ITEM_GROUP);
                    // Create the new BlockItem with the block and it's properties
                    final BlockItem blockItem = new BlockItem(block, properties);
                    // Setup the new BlockItem with the block's registry name and register it
                    registry.register(setup(blockItem, block.getRegistryName()));
                });

        LOGGER.debug("Registered Items");
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final String name) {
        return setup(entry, new ResourceLocation(SpaceExplorer.MODID, name));
    }

    public static <T extends IForgeRegistryEntry<T>> T setup(final T entry, final ResourceLocation registryName) {
        entry.setRegistryName(registryName);
        return entry;
    }
}
