package com.choon.wordwolf

import com.choon.wordwolf.commands.WWCommands
import com.choon.wordwolf.commands.WWTabCompleter
import com.choon.wordwolf.modules.WordWolf
import org.bukkit.plugin.java.JavaPlugin
import org.w3c.dom.events.EventListener

class Main : JavaPlugin() {
    override fun onEnable() {
        logger.info("Wordwolf 0.1.0 enabled")

        getCommand("ww")?.setExecutor(WWCommands())
        getCommand("ww")?.tabCompleter = WWTabCompleter()
    }
}

fun main() {
//    print(WordWolf.gameRules["asd"])
//    print("1".toInt())
}