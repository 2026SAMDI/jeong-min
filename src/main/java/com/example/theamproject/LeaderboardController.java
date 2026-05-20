package com.example.theamproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Arrays;
import java.util.List;

@RestController
public class LeaderboardController {

    // 유저의 이름과 점수를 담을 "설계도(클래스)"를 만듭니다.
    public static class ScoreData {
        public String playerName;
        public int score;

        public ScoreData(String playerName, int score) {
            this.playerName = playerName;
            this.score = score;
        }
    }

    // 주소창에 /leaderboard 라고 치면 이 부분이 실행
    @GetMapping("/leaderboard")
    public List<ScoreData> getLeaderboard() {
        // 임시로 가짜 1등, 2등 데이터를 만들어서 유니티에게 던져줍니다.
        return Arrays.asList(
                new ScoreData("2번 플레이어", 1500),
                new ScoreData("1번 플레이어", 800)
        );
    }
}