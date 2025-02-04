package org.tywrapstudios.constructra.api.resource.v1;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * General interface for a Resource.
 */
public interface Resource {
    Identifier identifier();
    ItemConvertible retrievableItem();
    Block harvestBlock();
    ResourceRarity rarity();

    default Text getName() {
        return Text.translatable(getTranslationKey());
    }

    default String getTranslationKey() {
        return "resource." + this.identifier().getNamespace() + "." + this.identifier().getPath();
    }
}
