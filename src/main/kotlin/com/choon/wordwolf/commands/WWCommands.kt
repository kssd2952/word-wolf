package com.choon.wordwolf.commands

import com.choon.wordwolf.modules.WordWolf
import com.choon.wordwolf.modules.WordWolf.Companion.gameRules
import com.choon.wordwolf.modules.WordWolf.Companion.playerList
import com.choon.wordwolf.modules.WordWolf.Companion.topics
import org.bukkit.Bukkit
import org.bukkit.Server
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
                if (!WordWolf.firstSetted) {
                    sender.sendMessage("/ww set 커맨드를 먼저 입력해주세요")
                } else if (WordWolf.isGameStarted) {
                    sender.sendMessage("게임이 이미 시작되었습니다")
                } else if (gameRules["debugMode"] == "true") {
                    WordWolf.isGameStarted = true
                    WordWolf.startGame()
                } else if (playerList.size < 3) {
                    sender.sendMessage("참여 인원이 너무 적습니다")
                } else if (playerList.size <= gameRules.getOrDefault("wolfCount", "1").toInt()) {
                    gameRules["wolfCount"] = "1"
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

            "set" -> {
                //기본 gameRules
                gameRules["wolfSelfAware"] = "true" //울프는 본인이 라이어인 것을 알고 시작하는가?
                gameRules["wolfCount"] = "1" //울프는 총 몇 명인가?
                gameRules["wordTopic"] = "RANDOM" //단어의 주제는 무엇인가?
                gameRules["wolfWordRelated"] = "true" //울프의 단어는 타 플레이어들의 것과 주제가 같은가?
                gameRules["maxRounds"] = "INF" //최대 몇 번의 투표가 진행되는가?
                gameRules["wolfCanGuess"] = "true" //울프가 발각되었을 때 단어를 맞출 수 있는가?
                gameRules["debugMode"] = "false" //개발 테스트용 디버그 모드

                topics["sports"] = listOf("축구", "농구", "야구", "테니스", "배구", "골프", "권투", "수영", "배드민턴", "하키", "럭비", "스키")
                topics["instruments"] =
                    listOf(
                        "피아노",
                        "기타",
                        "바이올린",
                        "드럼",
                        "플루트",
                        "색소폰",
                        "트럼펫",
                        "첼로",
                        "클라리넷",
                        "하모니카",
                        "트롬본",
                        "아코디언",
                        "베이스",
                        "우쿨렐레"
                    )
                topics["foods"] = listOf("피자", "햄버거", "초밥", "파스타", "스테이크", "샐러드", "국수", "라면", "샌드위치", "카레", "타코", "볶음밥")
                topics["animals"] = listOf("강아지", "고양이", "사자", "호랑이", "코끼리", "기린", "얼룩말", "토끼", "곰", "판다", "원숭이", "돌고래")
                topics["jobs"] = listOf("의사", "교사", "경찰", "소방관", "요리사", "엔지니어", "조종사", "간호사", "화가", "과학자", "농부", "변호사")
                topics["fruits"] = listOf("사과", "바나나", "포도", "오렌지", "수박", "파인애플", "딸기", "복숭아", "레몬", "망고", "체리", "키위")
                topics["countries"] =
                    listOf("한국", "일본", "중국", "미국", "캐나다", "독일", "프랑스", "이탈리아", "브라질", "호주", "인도", "스페인")
                topics["colors"] = listOf("빨강", "파랑", "초록", "노랑", "검정", "하양", "주황", "보라", "분홍", "회색", "갈색", "청록")
                topics["cities"] = listOf("서울", "도쿄", "뉴욕", "파리", "런던", "베이징", "시드니", "베를린", "마드리드", "로마", "모스크바", "방콕")
                topics["subjects"] =
                    listOf("수학", "과학", "역사", "영어", "지리", "미술", "음악", "물리", "화학", "생물", "철학", "경제", "도덕")

                WordWolf.firstSetted = true
                sender.sendMessage("게임을 기본 설정으로 변경했습니다")
            }

            else -> {
                sender.sendMessage("잘못된 사용방법입니다")
            }
        }

        return true
    }
}