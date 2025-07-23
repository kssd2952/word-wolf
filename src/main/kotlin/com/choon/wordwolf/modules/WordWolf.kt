package com.choon.wordwolf.modules

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.title.Title
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import java.time.Duration


class WordWolf : JavaPlugin() {
    companion object {
        val playerList: MutableList<Player> = mutableListOf()
        val gameRules = mutableMapOf<String, String>()
        val topics = mutableMapOf<String, List<String>>()

        var isGameStarted: Boolean = false
        var isVoteStarted: Boolean = false

        val topicList: List<String> = listOf(
            "sports", "instruments", "foods", "animals", "jobs", "fruits", "countries", "colors", "cities", "subjects"
        )

        fun startGame() {
            val wordList: List<String>? = if (gameRules["wordTopic"] == "RANDOM") {
                topics[topicList[(Math.random() * topicList.size).toInt()]]
            } else {
                topics[gameRules["wordTopic"]]
            }

            val playerWord = wordList?.get((Math.random() * wordList.size).toInt())
            var wolfWord = wordList?.get((Math.random() * wordList.size).toInt())
            while (wolfWord == playerWord) {
                wolfWord = wordList?.get((Math.random() * wordList.size).toInt())
            }

            for (player in playerList) {
                val title = Title.title(
                    Component.text("당신은 시민입니다!", NamedTextColor.GREEN),
                    Component.text("당신의 단어는 ${playerWord}입니다", NamedTextColor.GRAY),
                    Title.Times.times(Duration.ofMillis(500), Duration.ofSeconds(3), Duration.ofMillis(500))
                )
                player.showTitle(title)
            }
        }

        fun gameVote() {

        }

        fun gameStop(target: String) {

        }
    }

    init {
        logger.info("WordWolf object loaded")

        //기본 gameRules
        gameRules["wolfSelfAware"] = "false" //울프는 본인이 라이어인 것을 알고 시작하는가?
        gameRules["oneTurnGame"] = "false" //한 번의 투표 후 게임이 종료되는가?
        gameRules["wolfCount"] = "1" //울프는 총 몇 명인가?
        gameRules["wordTopic"] = "RANDOM" //단어의 주제는 무엇인가?
        gameRules["wolfWordRelated"] = "true" //울프의 단어는 타 플레이어들의 것과 주제가 같은가?
        gameRules["maxRounds"] = "6" //최대 몇 번의 투표가 진행되는가?
        gameRules["wolfCanGuess"] = "true" //울프가 발각되었을 때 단어를 맞출 수 있는가?

        topics["sports"] = listOf("축구", "농구", "야구", "테니스", "배구", "골프", "권투", "수영", "배드민턴", "하키", "럭비", "스키")
        topics["instruments"] =
            listOf("피아노", "기타", "바이올린", "드럼", "플루트", "색소폰", "트럼펫", "첼로", "클라리넷", "하모니카", "트롬본", "아코디언", "베이스", "우쿨렐레")
        topics["foods"] = listOf("피자", "햄버거", "초밥", "파스타", "스테이크", "샐러드", "국수", "라면", "샌드위치", "카레", "타코", "볶음밥")
        topics["animals"] = listOf("강아지", "고양이", "사자", "호랑이", "코끼리", "기린", "얼룩말", "토끼", "곰", "판다", "원숭이", "돌고래")
        topics["jobs"] = listOf("의사", "교사", "경찰", "소방관", "요리사", "엔지니어", "조종사", "간호사", "화가", "과학자", "농부", "변호사")
        topics["fruits"] = listOf("사과", "바나나", "포도", "오렌지", "수박", "파인애플", "딸기", "복숭아", "레몬", "망고", "체리", "키위")
        topics["countries"] = listOf("한국", "일본", "중국", "미국", "캐나다", "독일", "프랑스", "이탈리아", "브라질", "호주", "인도", "스페인")
        topics["colors"] = listOf("빨강", "파랑", "초록", "노랑", "검정", "하양", "주황", "보라", "분홍", "회색", "갈색", "청록")
        topics["cities"] = listOf("서울", "도쿄", "뉴욕", "파리", "런던", "베이징", "시드니", "베를린", "마드리드", "로마", "모스크바", "방콕")
        topics["subjects"] = listOf("수학", "과학", "역사", "영어", "지리", "미술", "음악", "물리", "화학", "생물", "철학", "경제", "도덕")
    }
}