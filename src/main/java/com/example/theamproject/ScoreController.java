package com.example.theamproject;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScoreController {

    public static class UserScore {
        public String userId;
        public int score;

        public UserScore(String userId, int score) {
            this.userId = userId;
            this.score = score;
        }
    }

    // 2. 임시 점수판 (정렬과 추가가 완벽히 지원되는 방식으로 수정 완료)
    private List<UserScore> leaderboard = new ArrayList<>() {{
    }};

    // 3. Unity 또는 Postman에서 점수 받기 (POST)
    @PostMapping("/submit-score")
    public String submitScore(@RequestParam String userId, @RequestParam int score) {
        // 리스트에 새로운 유저 기록 추가
        leaderboard.add(new UserScore(userId, score));

        // 점수가 높은 순서대로 내림차순 정렬 (이제 완벽하게 작동합니다)
        leaderboard.sort((a, b) -> Integer.compare(b.score, a.score));

        System.out.println(userId + "님의 점수 " + score + " 등록 완료!");
        return "Success";
    }

    // 4. 점수판 순위 보내주기 (GET)
    @GetMapping("/leaderboard")
    public List<UserScore> getLeaderboard() {
        return leaderboard;
    }
}