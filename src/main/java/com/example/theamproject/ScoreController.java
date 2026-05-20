package com.example.theamproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

public class ScoreController {
    // 1. 유저 ID와 점수를 담을 간단한 구조체
    public static class UserScore {
        public String userId;
        public int score;

        public UserScore(String userId, int score) {
            this.userId = userId;
            this.score = score;
        }
    }

    // 2.임시 점수판 (나중에는 진짜 DB로 바꿔야함)
    private List<UserScore> leaderboard = new ArrayList<>();

    // 3. Unity에서 점수 받기 (POST)
    // @RequestParam을 사용하면 Unity의 WWWForm 데이터나 웹의 쿼리 파라미터를 쉽게 받을 수 있다.
    @PostMapping("/submit-score")
    public String submitScore(@RequestParam String userId, @RequestParam int score) {
        // 리스트에 새로운 유저 기록 추가
        leaderboard.add(new UserScore(userId,score));

        // 점수가 높은 순서대로 내림차순 정렬
        leaderboard.sort((a,b)->b.score - a.score);

        System.out.println(userId + "님의 점수" + score + "등록 완료!");
        return "Success";
    }
    // 4. 점수판 순위 보내주기 (GET)
    @GetMapping("/leaderboard")
    public List<UserScore> getLeaderboard() {
        // 서버에 저장된 점수 리스트를 JSON 형태로 예쁘게 변환해서 응답해 줍니다.
        return leaderboard;
    }
}
