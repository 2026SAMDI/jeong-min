package com.example.theamproject;

import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {

    // =========================
    // 특정 유저 삭제
    // =========================
    @PostMapping("/delete-user")
    public String deleteUser(
            @RequestParam String userId
    ) {

        boolean removed =
                UserController.users.removeIf(
                        user ->
                                user.userId.trim()
                                        .equals(userId.trim())
                );

        if (removed) {

            UserController.saveUsers();

            return userId + " 삭제 완료";
        }

        return "유저 없음";
    }

    // =========================
    // 전체 초기화
    // =========================
    @PostMapping("/reset")
    public String resetLeaderboard() {

        UserController.users.clear();

        UserController.saveUsers();

        return "전체 초기화 완료";
    }

    // =========================
    // 점수 수정
    // =========================
    @PostMapping("/update-score")
    public String updateScore(
            @RequestParam String userId,
            @RequestParam int score
    ) {

        for (User user :
                UserController.users) {

            if (user.userId.equals(userId)) {

                user.highScore = score;

                UserController.saveUsers();

                return "점수 수정 완료";
            }
        }

        return "유저 없음";
    }

    // =========================
    // 비밀번호 변경
    // =========================
    @PostMapping("/change-password")
    public String changePassword(
            @RequestParam String userId,
            @RequestParam String newPassword
    ) {

        for (User user :
                UserController.users) {

            if (user.userId.equals(userId)) {

                user.password = newPassword;

                UserController.saveUsers();

                return "비밀번호 변경 완료";
            }
        }

        return "유저 없음";
    }
}