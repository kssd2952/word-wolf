package com.choon.wordwolf.modules

import org.bukkit.entity.Player

object WordWolf {
    val playerList: MutableList<Player> = mutableListOf()
    val gameRules = mutableMapOf<String, String>()

    init {
        gameRules["wolfSelfAware"] = "false"
        gameRules["oneTurnGame"] = "false"
    }
}