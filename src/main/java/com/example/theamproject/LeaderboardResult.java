package com.example.theamproject;

import java.util.List;

public class LeaderboardResult {
    private List<LeaderboardResponse> skiers;

    public LeaderboardResult(List<LeaderboardResponse> skiers) {
        this.skiers = skiers;
    }

    // ⭐ 여기도 Getter 필수!
    public List<LeaderboardResponse> getSkiers() {
        return skiers;
    }
}