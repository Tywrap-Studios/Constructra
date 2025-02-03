package org.tywrapstudios.constructra.api.resource.v1;

import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.function.ValueLists;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.config.ConstructraServerConfig;

import java.util.Random;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

/**
 * {@link ResourceNode}{@code s} can have a Purity, which directly inflicts at which rate Resources can be Harvested from the Node. These are the options.
 */
public enum ResourcePurity implements StringIdentifiable {
    NONE(0, "none", Formatting.RED),
    IMPURE(1, "impure", Formatting.DARK_AQUA),
    NORMAL(2, "normal", Formatting.GOLD),
    PURE(3, "pure", Formatting.LIGHT_PURPLE),;

    private final int index;
    private final String name;
    private final Formatting formatting;

    public static final IntFunction<ResourcePurity> ID_TO_VALUE = ValueLists.createIdToValueFunction(
            (ToIntFunction<ResourcePurity>) (value) -> value.index, values(), ValueLists.OutOfBoundsHandling.ZERO
    );
    public static final PacketCodec<ByteBuf, ResourcePurity> PACKET_CODEC = PacketCodecs.indexed(
            ID_TO_VALUE, (value) -> value.index
    );

    ResourcePurity(int index, String name, Formatting formatting) {
        this.index = index;
        this.name = name;
        this.formatting = formatting;
    }

    public int getIndex() {
        return this.index;
    }

    /**
     * @param index the index int of the Purity.
     * @return the purity of the corresponding index, returns NONE by default.
     */
    public static ResourcePurity indexed(int index) {
        return switch (index) {
            case 1 -> ResourcePurity.IMPURE;
            case 2 -> ResourcePurity.NORMAL;
            case 3 -> ResourcePurity.PURE;
            default -> ResourcePurity.NONE;
        };
    }

    /**
     * @param random the {@link Random} to work with when generating the int.
     * @return a Randomly chosen purity, handy for Random Generation.
     * @see #random()
     */
    public static ResourcePurity random(Random random) {
        int i = random.nextInt(3);
        return switch (i) {
            case 0 -> IMPURE;
            case 1 -> NORMAL;
            case 2 -> PURE;
            default -> NONE;
        };
    }

    public static ResourcePurity random() {
        return random(new Random());
    }

    /**
     * Similar to {@link #indexed(int)}, returns a Purity from their name.
     * <p> {@code "RANDOM"} can also be chosen to just return a Random Purity.
     * @param string the name of the purity.
     * @return the purity of the corresponding name, returns NONE by default.
     */
    public static ResourcePurity fromString(String string) {
        return switch (string) {
            case "IMPURE" -> IMPURE;
            case "NORMAL" -> NORMAL;
            case "PURE" -> PURE;
            case "RANDOM" -> random();
            default -> NONE;
        };
    }

    /**
     * @return the modifier for harvesting time per purity, specified in the Config.
     */
    public double getMiningTimeMultiplier() {
        ConstructraServerConfig.ResourceConfig.PurityModifiers cc = Constructra.config().resources.purity_modifiers;
        if (!cc.does_purity_affect_rate) return cc.normal;

        return switch (this) {
            case PURE -> cc.pure;
            case NORMAL -> cc.normal;
            case IMPURE -> cc.impure;
            case NONE -> 0.0f;
        };
    }

    /**
     * @return a {@link Text} element that is gotten from a translatable String: {@code "purity.name"}
     */
    public Text toText() {
        return Text.translatable("purity." + asString()).formatted(formatting);
    }

    @Override
    public String asString() {
        return name;
    }
}
