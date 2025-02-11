package org.tywrapstudios.constructra.api.block;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.piston.PistonBehavior;
import org.tywrapstudios.constructra.api.resource.ResourceNode;

/**
 * A block that can be used for Resources, as it's indestructible and has a few other forced treats that make it perfect to be inside a {@link ResourceNode}.
 * <p>It is generally suggested to use this, because although there are precautions in place as to not make the blocks inside one be broken, this method is prettier and generally safer.</p>
 */
public class ResourceBlock extends Block {
    public ResourceBlock(Settings settings) {
        super(settings
                .strength(-1.0f, 3600000.0f)
                .dropsNothing()
                .noBlockBreakParticles()
                .pistonBehavior(PistonBehavior.BLOCK)
                .allowsSpawning(Blocks::never));
    }
}
