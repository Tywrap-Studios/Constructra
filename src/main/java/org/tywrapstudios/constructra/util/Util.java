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

package org.tywrapstudios.constructra.util;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import org.tywrapstudios.constructra.Constructra;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import static org.tywrapstudios.constructra.Constructra.id;

public class Util {
    protected static StackWalker STACK_WALKER = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE);

    public static String generateInitPhrase() {
        final List<String> phrases = List.of(
                "I love Config formatting version AAAA it reminds me of my mental health.",
                "Roses are red, violets are blue, unexpected \"{\" at line 32.",
                "garlic bread?",
                "Stop reading init messages, go build a factory!",
                "\"glorps glop bieb bobls\" - The Aliens saying this mod is fire",
                "UwUwUwUwUwUwUwUwUwUwUwUwU",
                "Stub your little toe.",
                "THE STORM IS COMING.",
                "Hier heb ik geen actieve herinnering aan",
                "Axolotls are better than @Tiazzzz",
                "your soulmate is out there somewhere, but god is probably preventing the meetup.",
                "On dit que pétrire c’est modeler , moi je dit que péter c’est démolir",
                "Jus Monika",
                "···· · ···· ·  −−··−−  −−· −−− − −·−· ···· ·−",
                "Leave your E kittens and go outside get a real size cartboard",
                "Policy free fantasy wheels!",
                "Who took the ram from the ramalamading-dong?",
                "Powered by org.tywrapstudios.constructra.util.Util#generateInitPhrase",
                " <- literally nothing ;-;",
                "I'm not a fusion reactor, I'm a black hole generator.",
                "God I hate ScreenHandlers"
        );

        return phrases.get(new Random().nextInt(phrases.size()));
    }

    public static String getModVer(String modId) {
        if (FabricLoader.getInstance().isModLoaded(modId)) {
            return FabricLoader.getInstance().getModContainer(modId).orElseThrow().getMetadata().getVersion().getFriendlyString();
        } else {
            return String.format("\"%s\" version not found, mod isn't loaded.", modId);
        }
    }

    public static void logInitialisation() {
        String str = String.format("%s: ready", STACK_WALKER.getCallerClass().getSimpleName());
        Constructra.LOGGER.debug(str);
    }

    public static RegistryKey<Item> itemKey(String s) {
        return RegistryKey.of(RegistryKeys.ITEM, id(s));
    }

    public static RegistryKey<Block> blockKey(String s) {
        return RegistryKey.of(RegistryKeys.BLOCK, id(s));
    }

    public static RegistryKey<LootTable> lootKey(String s) {
        return RegistryKey.of(RegistryKeys.LOOT_TABLE, id(s));
    }

    public static Optional<RegistryKey<LootTable>> optionalLootKey(String s) {
        return Optional.of(lootKey(s));
    }
}
