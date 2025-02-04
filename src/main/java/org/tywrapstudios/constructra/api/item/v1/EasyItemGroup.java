package org.tywrapstudios.constructra.api.item.v1;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.tywrapstudios.constructra.Constructra.id;

public abstract class EasyItemGroup {
    public String id;
    public String langEntry;
    protected final List<ItemConvertible> ENTRIES = new ArrayList<>();
    private ItemConvertible ICON;

    /**
     * Extend this class to make a simple item group.
     * @param id the {@link Identifier} of the group.
     * @param icon the item that will serve as the icon of the group.
     * @param items a list of Items to add to the group
     */
    protected EasyItemGroup(Identifier id, ItemConvertible icon, List<? extends ItemConvertible> items) {
        this.id = id.getPath();
        this.ENTRIES.addAll(items);
        this.langEntry = String.format("itemGroup.%s.%s", id.getNamespace(), id.getPath());
        this.ICON = icon;
    }

    /**
     * The actual GROUP object that will be registered using {@code register()}
     */
    private final Supplier<ItemGroup> GROUP = () -> FabricItemGroup.builder()
            .displayName(Text.translatable(langEntry))
            .icon(() -> new ItemStack(ICON))
            .entries((displayContext, entries) -> {
                for (ItemConvertible item : ENTRIES) {
                    entries.add(item);
                }
            })
            .build();

    /**
     * The method used to register the group.
     */
    public void register() {
        Registry.register(Registries.ITEM_GROUP, RegistryKey.of(RegistryKeys.ITEM_GROUP, id(id)), get());
    }

    public ItemGroup get() {
        return GROUP.get();
    }
}
