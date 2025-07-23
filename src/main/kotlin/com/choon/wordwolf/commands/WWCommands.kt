package com.choon.wordwolf.commands

import com.choon.wordwolf.modules.WordWolf
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class WWCommands : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("This command can only be excuted by player")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("Usage: /ww <join|leave|list> [playerName]")
            return true
        }

        when (args[0].lowercase()) {
            "join" -> {
                if (args.size < 2) {
                    sender.sendMessage("Usage: /ww <join|leave|list> [playerName]")
                    return true
                }
                val targetPlayer: Player? = Bukkit.getPlayer(args[1])

                if (targetPlayer == null) {
                    sender.sendMessage("Incorrect usage")
                } else if (WordWolf.playerList.contains(targetPlayer)) {
                    sender.sendMessage("Player already joined the game")
                } else {
                    WordWolf.playerList.add(targetPlayer)
                    sender.sendMessage("${targetPlayer.name} has been added to the player list")
                }
            }

            "leave" -> {
                if (args.size < 2) {
                    sender.sendMessage("Usage: /ww <join|leave|list> [playerName]")
                    return true
                }
                val targetPlayer: Player? = Bukkit.getPlayer(args[1])

                if (targetPlayer == null) {
                    sender.sendMessage("Incorrect usage")
                } else if (!WordWolf.playerList.contains(targetPlayer)) {
                    sender.sendMessage("Player have not joined the game")
                } else {
                    WordWolf.playerList.remove(targetPlayer)
                    sender.sendMessage("${targetPlayer.name} has been removed from the player list")
                }
            }

            "rules" -> {
                if (args.size < 2) {
                    sender.sendMessage("Usage: /ww <join|leave|list> [playerName]")
                    return true
                } else if (args.size == 2) {
                    val currentRule = WordWolf.gameRules[args[1]]

                    if (currentRule == null) {
                        sender.sendMessage("There is no rule like that")
                    } else {
                        sender.sendMessage("Current setting for ${args[1]} is $currentRule")
                    }
                } else if (args.size == 3) {
                    val currentRule = WordWolf.gameRules[args[1]]

                    if (currentRule == null) {
                        sender.sendMessage("There is no rule like that")
                    } else {
                        WordWolf.gameRules[args[1]] = args[2]
                        sender.sendMessage("Set rule ${args[1]} to ${WordWolf.gameRules[args[1]]}")
                    }
                }
            }

            "list" -> {
                val nameList = WordWolf.playerList.joinToString(", ") { it.name }
                sender.sendMessage("Current players are: $nameList")
            }

            else -> {
                sender.sendMessage("Incorrect usage")
            }
        }

        return true
    }
}