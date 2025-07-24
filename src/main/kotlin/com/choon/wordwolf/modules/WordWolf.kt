package com.choon.wordwolf.modules

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.time.Duration


class WordWolf {
    companion object {
        val playerList: MutableList<Player> = mutableListOf()
        val gameRules = mutableMapOf<String, String>()
        val topics = mutableMapOf<String, List<String>>()

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
                Component.text("당신의 단어는 ${playerWord}입니다", NamedTextColor.GRAY),
                Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
            )
            for (player in playerList) {
                player.showTitle(title)
            }
        }

        fun gameVote() {

        }

        fun gameStop(target: String) {

        }
    }
}