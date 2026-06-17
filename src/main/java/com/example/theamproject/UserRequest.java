package com.example.theamproject;

public class UserRequest {
    private String userId;
    private String password;
    private int score; // 점수 저장 API 등에서 사용할 수 있도록 추가

    // Getter & Setter (Spring이 JSON 데이터를 여기에 맵핑하려면 필수입니다)
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}