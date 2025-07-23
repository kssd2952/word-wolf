package com.choon.wordwolf

import com.choon.wordwolf.commands.WWCommands
import com.choon.wordwolf.modules.WordWolf
import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    override fun onEnable() {
        logger.info("Wordwolf 0.1.0 enabled")
        getCommand("ww")?.setExecutor(WWCommands())
    }
}

fun main() {
//    print(WordWolf.gameRules["asd"])
}