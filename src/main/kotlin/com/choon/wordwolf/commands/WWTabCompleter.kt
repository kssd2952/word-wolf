package com.choon.wordwolf.commands

import com.choon.wordwolf.modules.WordWolf
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.TabCompleter
import org.bukkit.command.CommandSender

class WWTabCompleter: TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        alias: String,
        args: Array<out String>
    ): MutableList<String>? {
        if (command.name != "wwcommands") return null

        return when (args.size) {
            // /wwcommands <여기>
            1 -> listOf("join", "leave", "list", "rules")
                .filter { it.startsWith(args[0], ignoreCase = true) }
                .toMutableList()

            // /wwcommands join <플레이어 이름>
            2 -> when (args[0].lowercase()) {
                "join", "leave" -> Bukkit.getOnlinePlayers()
                    .map { it.name }
                    .filter { it.startsWith(args[1], ignoreCase = true) }
                    .toMutableList()

                "rules" -> WordWolf.gameRules.keys
                    .filter { it.startsWith(args[1], ignoreCase = true) }
                    .toMutableList()

                else -> mutableListOf()
            }

            else -> mutableListOf()
        }
    }
}