package com.example.theamproject;

import java.util.List;

public class LeaderboardResult {
    // 유니티 변수명과 매칭하기 편하도록 랭킹 리스트를 감싸는 필드
    private List<LeaderboardResponse> skiers; // 혹은 ranking, players 등 원하는 이름

    public LeaderboardResult(List<LeaderboardResponse> skiers) {
        this.skiers = skiers;
    }

    public List<LeaderboardResponse> getSkiers() {
        return skiers;
    }
}