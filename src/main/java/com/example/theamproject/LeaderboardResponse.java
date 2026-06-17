package com.example.theamproject;

public class LeaderboardResponse {
    private String userId;
    private int highScore;

    public LeaderboardResponse(String userId, int highScore) {
        this.userId = userId;
        this.highScore = highScore;
    }

    // ⭐ 이 두 개의 Getter가 없으면 JSON 변환 시 {} 로 텅 비어버립니다!
    public String getUserId() {
        return userId;
    }

    public int getHighScore() {
        return highScore;
    }
}