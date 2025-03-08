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

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.RegistryEntryReferenceArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import org.tywrapstudios.constructra.Constructra;
import org.tywrapstudios.constructra.config.ConstructraServerConfig;
import org.tywrapstudios.constructra.registry.CaRegistries;
import org.tywrapstudios.constructra.util.Util;

public class CaCommandImpl {
    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess access) {
        ConstructraServerConfig.CommandConfig cc = Constructra.config().commands;
        var constructraCommand = CommandManager
                .literal("constructra")
                .executes(CaCommandExecutables::execute).build();

        var caCommand = CommandManager
                .literal(cc.command_alias)
                .redirect(constructraCommand).build();

        var nodesCommand = CommandManager
                .literal("nodes")
                .requires(source -> source.hasPermissionLevel(cc.perm_lvl_nodes)).build();

        var flushCommand = CommandManager
                .literal("flush")
                .requires(source -> source.hasPermissionLevel(cc.perm_lvl_nodes_removal))
                .executes(CaCommandExecutables::flushNodes).build();

        var spawnCommand = CommandManager
                .literal("spawn").build();

        var posArg = CommandManager
                .argument("pos", BlockPosArgumentType.blockPos()).build();

        var typeArg = CommandManager
                .argument("type", RegistryEntryReferenceArgumentType.registryEntry(access, CaRegistries.Keys.RESOURCE)).build();

        var obsArg = CommandManager
                .argument("obstructed", BoolArgumentType.bool())
                .executes(CaCommandExecutables::spawnNode).build();

        var purgeCommand = CommandManager
                .literal("purge")
                .requires(source -> source.hasPermissionLevel(cc.perm_lvl_nodes_removal)).build();

        var posArg2 = CommandManager
                .argument("pos", BlockPosArgumentType.blockPos())
                .executes(ctx -> CaCommandExecutables.purgeNode(ctx, false, false)).build();

        var rangeArg = CommandManager
                .argument("range", IntegerArgumentType.integer(0))
                .executes(ctx -> CaCommandExecutables.purgeNode(ctx, true, false)).build();

        var removeBlockArg = CommandManager
                .argument("destroy_blocks", BoolArgumentType.bool())
                .executes(ctx -> CaCommandExecutables.purgeNode(ctx, true, true)).build();

        var removeBlockNoRangeArg = CommandManager
                .argument("destroy_blocks", BoolArgumentType.bool())
                .executes(ctx -> CaCommandExecutables.purgeNode(ctx, false, true)).build();

        var reloadCommand = CommandManager
                .literal("reload")
                .requires(source -> source.hasPermissionLevel(cc.perm_lvl_reload))
                .executes(CaCommandExecutables::reload).build();

        /* Root */
        dispatcher.getRoot().addChild(constructraCommand);
        dispatcher.getRoot().addChild(caCommand);
        /* Reload */
        constructraCommand.addChild(reloadCommand);
        /* Nodes */
        constructraCommand.addChild(nodesCommand);
        /* Nodes Flush */
        nodesCommand.addChild(flushCommand);
        /* Nodes Spawn */
        nodesCommand.addChild(spawnCommand);
        spawnCommand.addChild(posArg);
        posArg.addChild(typeArg);
        typeArg.addChild(obsArg);
        /* Nodes Purge */
        nodesCommand.addChild(purgeCommand);
        purgeCommand.addChild(posArg2);
        posArg2.addChild(rangeArg);
        posArg2.addChild(removeBlockNoRangeArg);
        rangeArg.addChild(removeBlockArg);

        Util.logInitialisation();
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient(CommandDispatcher<FabricClientCommandSource> dispatcher) {
        var constructraClientCommand = ClientCommandManager
                .literal("constructra-client").build();

        var caClientCommand = ClientCommandManager
                .literal("ca-client")
                .redirect(constructraClientCommand).build();

        var reloadCommand = ClientCommandManager
                .literal("reload")
                        .executes(CaCommandExecutables::reloadClient).build();

        /* Root */
        dispatcher.getRoot().addChild(constructraClientCommand);
        dispatcher.getRoot().addChild(caClientCommand);
        /* Reload */
        constructraClientCommand.addChild(reloadCommand);

        Util.logInitialisation();
    }
}
