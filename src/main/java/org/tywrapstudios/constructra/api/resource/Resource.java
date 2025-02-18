package org.tywrapstudios.constructra.api.resource;

import net.minecraft.block.Block;
import net.minecraft.item.ItemConvertible;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/**
 * General interface for a Resource.
 */
public interface Resource {
    Identifier getIdentifier();
    ItemConvertible getRetrievableItem();
    Block getHarvestBlock();
    ResourceRarity getRarity();

    default Text getName() {
        return Text.translatable(getTranslationKey());
    }

    default String getTranslationKey() {
        return "resource." + this.getIdentifier().getNamespace() + "." + this.getIdentifier().getPath();
    }
}
