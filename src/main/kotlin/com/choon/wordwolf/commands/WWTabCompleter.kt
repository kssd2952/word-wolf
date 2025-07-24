package com.choon.wordwolf.commands

import com.choon.wordwolf.modules.WordWolf
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.TabCompleter
import org.bukkit.command.CommandSender

class WWTabCompleter : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender, command: Command, label: String, args: Array<out String>
    ): MutableList<String>? {
        if (command.name != "ww") return null

        return when (args.size) {
            1 -> listOf("join", "leave", "list", "rules", "start", "stop", "vote").filter {
                it.startsWith(
                    args[0],
                    ignoreCase = true
                )
            }
                .toMutableList()

            2 -> when (args[0]) {
                "join", "leave" -> Bukkit.getOnlinePlayers().map { it.name }
                    .filter { it.startsWith(args[1], ignoreCase = true) }.toMutableList()

                "rules" -> WordWolf.gameRules.keys.filter { it.startsWith(args[1], ignoreCase = true) }.toMutableList()

                else -> mutableListOf()
            }

            3 -> if (args[0] == "rules") {
                when (args[1]) {
                    "wolfSelfAware", "oneTurnGame", "wolfWordRelated", "wolfCanGuess", "debugMode" -> {
                        listOf("true", "false")
                            .filter { it.startsWith(args[2], ignoreCase = true) }
                            .toMutableList()
                    }

                    "wordTopic" -> {
                        listOf(
                            "sports",
                            "instruments",
                            "foods",
                            "animals",
                            "jobs",
                            "fruits",
                            "countries",
                            "colors",
                            "cities",
                            "subjects",
                            "RANDOM"
                        ).filter { it.startsWith(args[2], ignoreCase = true) }.toMutableList()
                    }

                    else -> mutableListOf()
                }
            } else {
                mutableListOf()
            }

            else -> mutableListOf()
        }

//        return listOf("join", "leave", "list", "rules")
    }
}