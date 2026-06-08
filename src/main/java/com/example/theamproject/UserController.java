package com.example.theamproject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    // =========================
    // 유저 리스트
    // =========================
    public static List<User> users =
            new ArrayList<>();

    // =========================
    // JSON 파일 경로
    // =========================
    public static final String FILE_PATH =
            "/Users/wjdals0314/Desktop/intellij/The-Am-project/users.json";

    // =========================
    // JSON 변환기
    // =========================
    public static ObjectMapper objectMapper =
            new ObjectMapper();

    // =========================
    // 서버 시작 시 로드
    // =========================
    @PostConstruct
    public void loadUsers() {

        File file = new File(FILE_PATH);

        try {

            // 파일 없으면 생성
            if (!file.exists()) {

                file.createNewFile();

                objectMapper.writeValue(
                        file,
                        users
                );
            }

            // JSON 읽기
            users =
                    objectMapper.readValue(
                            file,
                            new TypeReference<List<User>>() {}
                    );

            System.out.println(
                    "유저 데이터 로드 완료"
            );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    // =========================
    // 회원가입
    // =========================
    @PostMapping("/register")
    public String register(
            @RequestParam String userId,
            @RequestParam String password
    ) {

        // 중복 검사
        for (User user : users) {

            if (user.userId.equals(userId)) {

                return "이미 존재하는 아이디";
            }
        }

        users.add(
                new User(userId, password)
        );

        saveUsers();

        return "회원가입 성공";
    }

    // =========================
    // 로그인
    // =========================
    @PostMapping("/login")
    public String login(
            @RequestParam String userId,
            @RequestParam String password
    ) {

        for (User user : users) {

            if (user.userId.equals(userId)
                    && user.password.equals(password)) {

                return "로그인 성공";
            }
        }

        return "아이디 또는 비밀번호 오류";
    }

    // =========================
    // 점수 저장
    // =========================
    @PostMapping("/submit-score")
    public String submitScore(
            @RequestParam String userId,
            @RequestParam String password,
            @RequestParam int score
    ) {

        for (User user : users) {

            // 로그인 검증
            if (user.userId.equals(userId)
                    && user.password.equals(password)) {

                // 최고 점수 저장
                if (score > user.highScore) {

                    user.highScore = score;

                    saveUsers();

                    return "점수 저장 성공";
                }

                return "기존 점수가 더 높음";
            }
        }

        return "로그인 실패";
    }

    // =========================
    // 랭킹 조회
    // =========================
    @GetMapping("/leaderboard")
    public List<User> leaderboard() {

        users.sort(
                (a, b) ->
                        Integer.compare(
                                b.highScore,
                                a.highScore
                        )
        );

        return users;
    }

    // =========================
    // JSON 저장
    // =========================
    public static void saveUsers() {

        try {

            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(
                            new File(FILE_PATH),
                            users
                    );

        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}