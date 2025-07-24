package com.choon.wordwolf.modules

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.NotNull
import java.time.Duration


class WordWolf {
    companion object {
        val playerList: MutableList<Player> = mutableListOf()
        val gameRules = mutableMapOf<String, String>()
        val topics = mutableMapOf<String, List<String>>()

        var firstSetted: Boolean = false
        var isGameStarted: Boolean = false
        var isVoteStarted: Boolean = false

        val topicNames: List<String> = listOf(
            "sports", "instruments", "foods", "animals", "jobs", "fruits", "countries", "colors", "cities", "subjects"
        )

        fun startGame() {
            val wordList: List<String>? = if (gameRules["wordTopic"] == "RANDOM") {
                topics[topicNames[(Math.random() * topicNames.size).toInt()]]
            } else {
                topics[gameRules["wordTopic"]]
            }

            val playerWord = wordList?.get((Math.random() * wordList.size).toInt())
            var wolfWord = wordList?.get((Math.random() * wordList.size).toInt())
            while (wolfWord == playerWord) {
                wolfWord = wordList?.get((Math.random() * wordList.size).toInt())
            }

            val title = Title.title(
                Component.text("당신은 시민입니다!", NamedTextColor.GREEN),
                Component.text("당신의 단어는 ${playerWord}입니다!", NamedTextColor.GRAY),
                Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
            )
            for (player in playerList) {
                player.showTitle(title)
            }
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage("워드울프 게임이 시작되었습니다!")
            }
        }

        fun gameVote() {
            val title = Title.title(
                Component.text("투표 시간입니다!", NamedTextColor.GREEN),
                Component.text("제일 의심가는 플레이어를 때려주세요", NamedTextColor.GRAY),
                Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
            )
            for (player in playerList) {
                player.showTitle(title)
            }
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage("투표가 시작되었습니다!")
            }
        }

        fun gameStop(target: String) {
            if (target == "game") {
                val title = Title.title(
                    Component.text("게임이 종료되었습니다!", NamedTextColor.GREEN),
                    Component.text("울프 이름", NamedTextColor.GRAY),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
                )
                for (player in playerList) {
                    player.showTitle(title)
                }
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendMessage("워드울프 게임이 종료되었습니다!")
                }
            } else if (target == "vote") {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendMessage("투표가 종료되었습니다!")
                }
            }
        }
    }
}