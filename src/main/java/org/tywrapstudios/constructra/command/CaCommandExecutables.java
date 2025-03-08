/*
 * MIT License
 *
 * Copyright (c) 2025 Tywrap Studios;
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

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
import org.tywrapstudios.constructra.api.resource.Resource;
import org.tywrapstudios.constructra.api.resource.ResourceManager;
import org.tywrapstudios.constructra.api.resource.ResourceNode;
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

        ResourceManager.Nodes.add(resource, pos, obstructed, ctx.getSource().getWorld());
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
