package com.choon.wordwolf.modules

import org.bukkit.entity.Player

object WordWolf {
    val playerList: MutableList<Player> = mutableListOf()
    val gameRules = mutableMapOf<String, String>()

    init {
        gameRules["wolfSelfAware"] = "false" //울프는 본인이 라이어인 것을 알고 시작하는가?
        gameRules["oneTurnGame"] = "false" //한 번의 투표 후 게임이 종료되는가?
        gameRules["wolfCount"] = "1" //울프는 총 몇 명인가?
        gameRules["wordTopic"] = "RANDOM" //단어의 주제는 무엇인가?
        gameRules["wolfWordRelated"] = "true" //울프의 단어는 타 플레이어들의 것과 주제가 같은가?
        gameRules["maxRounds"] = "6" //최대 몇 번의 투표가 진행되는가?
        gameRules["wolfCanGuess"] = "true" //울프가 발각되었을 때 단어를 맞출 수 있는가?
    }
}