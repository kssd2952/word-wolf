package com.choon.wordwolf.modules

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.TextComponent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.Bukkit
import org.bukkit.ChatColor
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

            val wolfCount = gameRules.getOrDefault("wolfCount", "1").toIntOrNull() ?: 1
            val wolfList = playerList.shuffled().take(wolfCount)

            if (gameRules["wolfSelfAware"] == "true") {
                for (player in playerList) {
                    if (wolfList.contains(player)) {
                        val subTitle = Component.text(
                            "당신의 단어는", NamedTextColor.GRAY
                        ).append(
                            Component.text(
                                "${wolfWord}", NamedTextColor.RED
                            )
                        ).append(
                            Component.text(
                                "입니다!", NamedTextColor.GRAY
                            )
                        )

                        val title = Title.title(
                            Component.text("당신은 울프입니다!", NamedTextColor.GREEN), subTitle, Title.Times.times(
                                Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500)
                            )
                        )
                        player.showTitle(title)
                    } else {
                        val subTitle = Component.text(
                            "당신의 단어는", NamedTextColor.GRAY
                        ).append(
                            Component.text(
                                "${playerWord}", NamedTextColor.GREEN
                            )
                        ).append(
                            Component.text(
                                "입니다!", NamedTextColor.GRAY
                            )
                        )

                        val title = Title.title(
                            Component.text("당신은 시민입니다!", NamedTextColor.GREEN), subTitle, Title.Times.times(
                                Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500)
                            )
                        )
                        player.showTitle(title)
                    }
                }
            } else {
                for (player in playerList) {
                    val subTitle = Component.text(
                        "당신의 단어는", NamedTextColor.GRAY
                    ).append(
                        Component.text(
                            "${if (wolfList.contains(player)) wolfWord else playerWord}", NamedTextColor.BLUE
                        )
                    ).append(
                        Component.text(
                            "입니다!", NamedTextColor.GRAY
                        )
                    )

                    val title = Title.title(
                        Component.text("게임이 시작되었습니다!", NamedTextColor.GREEN), subTitle, Title.Times.times(
                            Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500)
                        )
                    )
                    player.showTitle(title)
                }
            }

            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage(Component.text("워드울프 게임이 시작되었습니다!", NamedTextColor.RED))
            }
        }

        fun gameVote() {
            val title = Title.title(
                Component.text("투표 시간입니다!", NamedTextColor.YELLOW),
                Component.text("제일 의심가는 플레이어를 때려주세요!", NamedTextColor.GRAY),
                Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
            )
            for (player in playerList) {
                player.showTitle(title)
            }
            for (player in Bukkit.getOnlinePlayers()) {
                player.sendMessage(
                    Component.text("투표가 시작되었습니다!", NamedTextColor.YELLOW)
                )
            }
        }

        fun gameStop(target: String) {
            if (target == "game") {
                val title = Title.title(
                    Component.text("게임이 종료되었습니다!", NamedTextColor.RED),
                    Component.text("울프 이름", NamedTextColor.GRAY),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
                )
                for (player in playerList) {
                    player.showTitle(title)
                }
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Component.text("워드울프 게임이 종료되었습니다!", NamedTextColor.RED))
                }
            } else if (target == "vote") {
                for (player in Bukkit.getOnlinePlayers()) {
                    player.sendMessage(Component.text("투표가 종료되었습니다!", NamedTextColor.YELLOW))
                }
            }
        }
    }
}