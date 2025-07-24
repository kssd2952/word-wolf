package com.choon.wordwolf.commands

import com.choon.wordwolf.modules.WordWolf
import com.choon.wordwolf.modules.WordWolf.Companion.gameRules
import com.choon.wordwolf.modules.WordWolf.Companion.playerList
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import kotlin.text.toInt

class WWCommands : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("해당 명령어는 플레이어만 사용 가능합니다")
            return true
        }

        if (args.isEmpty()) {
            sender.sendMessage("잘못된 사용방법입니다")
            return true
        }

        when (args[0].lowercase()) {
            "join" -> {
                if (args.size < 2) {
                    sender.sendMessage("잘못된 사용방법입니다")
                    return true
                }
                val targetPlayer: Player? = Bukkit.getPlayer(args[1])

                if (targetPlayer == null) {
                    sender.sendMessage("잘못된 사용방법입니다")
                } else if (playerList.contains(targetPlayer)) {
                    sender.sendMessage("해당 플레이어는 이미 참여되었습니다")
                } else {
                    playerList.add(targetPlayer)
                    sender.sendMessage("플레이어 ${targetPlayer.name}가 참여했습니다")
                }
            }

            "leave" -> {
                if (args.size < 2) {
                    sender.sendMessage("잘못된 사용방법입니다")
                    return true
                }
                val targetPlayer: Player? = Bukkit.getPlayer(args[1])

                if (targetPlayer == null) {
                    sender.sendMessage("잘못된 사용방법입니다")
                } else if (!playerList.contains(targetPlayer)) {
                    sender.sendMessage("해당 플레이어가 참여되어 있지 않습니다")
                } else {
                    playerList.remove(targetPlayer)
                    sender.sendMessage("플레이어 ${targetPlayer.name}를 참여 리스트에서 제거했습니다")
                }
            }

            "rules" -> {
                if (args.size < 2) {
                    sender.sendMessage("잘못된 사용방법입니다")
                    return true
                } else if (args.size == 2) {
                    val currentRule = gameRules[args[1]]

                    if (currentRule == null) {
                        sender.sendMessage("해당 규칙을 찾을 수 없습니다")
                    } else {
                        sender.sendMessage("현재 ${args[1]} 게임 규칙은 ${currentRule}입니다")
                    }
                } else if (args.size == 3) {
                    val currentRule = gameRules[args[1]]

                    if (currentRule == null) {
                        sender.sendMessage("해당 규칙을 찾을 수 없습니다")
                    } else if (gameRules[args[1]] == args[2]) {
                        sender.sendMessage("게임 규칙 ${args[2]}는 이미 ${gameRules[args[1]]}입니다")
                    } else if (args[2] == "") {
                        sender.sendMessage("잘못된 사용방법입니다")
                    } else {
                        gameRules[args[1]] = args[2]
                        sender.sendMessage("게임 규칙 ${args[1]}을 ${gameRules[args[1]]}로 변경했습니다")
                    }
                }
            }

            "list" -> {
                val nameList = playerList.joinToString(", ") { it.name }
                sender.sendMessage("현재 참여 플레이어 목록: $nameList")
            }

            "start" -> {
                if (WordWolf.isGameStarted) {
                    sender.sendMessage("게임이 이미 시작되었습니다")
                } else if (gameRules["debugMode"] == "true") {
                    WordWolf.isGameStarted = true
                    WordWolf.startGame()
                } else if (playerList.size <= gameRules.getOrDefault("wolfCount", "1").toInt()) {
                    sender.sendMessage("설정된 울프의 수가 플레이어 수 이상입니다")
                } else {
                    WordWolf.isGameStarted = true
                    WordWolf.startGame()
                }
            }

            "stop" -> {
                if (WordWolf.isVoteStarted) {
                    WordWolf.isVoteStarted = false
                    WordWolf.gameStop("vote")
                } else if (WordWolf.isGameStarted) {
                    WordWolf.isGameStarted = false
                    WordWolf.gameStop("game")
                } else {
                    sender.sendMessage("게임이나 투표가 시작되지 않았습니다")
                    return true
                }
            }

            "vote" -> {
                if (!WordWolf.isGameStarted) {
                    sender.sendMessage("게임이 시작되지 않았습니다")
                } else if (WordWolf.isVoteStarted) {
                    sender.sendMessage("투표가 이미 시작되었습니다")
                } else {
                    WordWolf.isVoteStarted = true
                    WordWolf.gameVote()
                }
            }

            else -> {
                sender.sendMessage("잘못된 사용방법입니다")
            }
        }

        return true
    }
}