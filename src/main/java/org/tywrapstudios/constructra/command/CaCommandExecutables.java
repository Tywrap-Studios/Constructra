package org.tywrapstudios.constructra.command;

import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.ConstructraClient;
import org.tywrapstudios.constructra.api.resource.v1.Resource;
import org.tywrapstudios.constructra.api.resource.v1.ResourceManager;
import org.tywrapstudios.constructra.api.resource.v1.ResourceNode;
import org.tywrapstudios.constructra.registry.CaRegistries;

import java.util.List;

public class CaCommandExecutables {
    protected static int reload (CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();
        source.sendFeedback(() -> Text.translatable("commands.reload.success"), true);
        Constructra.CONFIG_MANAGER.loadConfig();
        return 1;
    }

    protected static int flushNodes(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();
        source.sendFeedback(() -> Text.translatable("text.constructra.command.flush").formatted(Formatting.RED), true);
        ResourceManager.Nodes.flush("Flushed from command by " + source.getName(), ctx.getSource().getWorld());
        return 1;
    }

    protected static int purgeNode(CommandContext<ServerCommandSource> ctx, boolean hasRange, boolean hasBlockBool) throws CommandSyntaxException {
        BlockPos pos = BlockPosArgumentType.getLoadedBlockPos(ctx,"pos");
        int range = hasRange ? IntegerArgumentType.getInteger(ctx,"range") : 1;
        boolean destroy = hasBlockBool && BoolArgumentType.getBool(ctx, "destroy_blocks");
        ServerCommandSource source = ctx.getSource();
        int initialNodeListSize = ResourceManager.Nodes.getOrCreateState(source.getWorld()).getNodes().size();
        source.sendFeedback(() -> Text.translatable("text.constructra.command.purge_start", range).formatted(Formatting.GREEN), true);
        List<ResourceNode<?>> purgedNodes = ResourceManager.Nodes.purge(pos, range, destroy, source.getWorld(), resourceNode -> source.sendFeedback(() -> Text.translatable("text.constructra.command.purge", resourceNode.getResource().getName(), resourceNode.getCentre().toShortString()).formatted(Formatting.RED), true));
        MutableText endText = purgedNodes.isEmpty() ? Text.translatable("text.constructra.command.purge_end_empty") : purgedNodes.size() == initialNodeListSize ? Text.translatable("text.constructra.command.purge_end_inefficient", purgedNodes.size()) : Text.translatable("text.constructra.command.purge_end", purgedNodes.size(), initialNodeListSize, initialNodeListSize - purgedNodes.size());
        source.sendFeedback(() -> endText.formatted(Formatting.GREEN), true);
        return 1;
    }

    protected static int spawnNode(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        if (!ctx.getSource().getWorld().getRegistryKey().equals(World.OVERWORLD)) {
            Constructra.LOGGER.warn("Attempted to manually place ResourceNode in non-overworld World. Skipping.");
            ctx.getSource().sendFeedback(() -> Text.translatable("text.constructra.command.error.non-overworld").formatted(Formatting.RED), true);
            return 0;
        }
        BlockPos pos = BlockPosArgumentType.getLoadedBlockPos(ctx,"pos");
        Resource resource = RegistryEntryReferenceArgumentType.getRegistryEntry(ctx, "type", CaRegistries.Keys.RESOURCE).value();
        boolean obstructed = BoolArgumentType.getBool(ctx,"obstructed");

        ResourceManager.Nodes.addNode(resource, pos, obstructed, ctx.getSource().getWorld());
        return 1;
    }

    protected static int execute(CommandContext<ServerCommandSource> ctx) {
        ServerCommandSource source = ctx.getSource();
        source.sendFeedback(() -> Text.translatable("text.constructra.command.constructra", source.getName()), false);
        return 1;
    }

    /* CLIENT COMMANDS */

    @Environment(EnvType.CLIENT)
    protected static int reloadClient (CommandContext<FabricClientCommandSource> ctx) {
        FabricClientCommandSource source = ctx.getSource();
        source.sendFeedback(Text.translatable("commands.reload.success"));
        ConstructraClient.CONFIG_MANAGER.loadConfig();
        return 1;
    }
}
