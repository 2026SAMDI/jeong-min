package com.example.theamproject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    public static List<User> users = new ArrayList<>();

    // =========================
    // JSON 파일 경로
    // =========================
    public static final String FILE_PATH = "/Users/wjdals0314/Desktop/intellij/The-Am-project/users.json";

    // =========================
    // JSON 변환기
    // =========================
    public static ObjectMapper objectMapper = new ObjectMapper();

    // =========================
    // 암호화 도구 주입
    // =========================
    private final PasswordEncoder passwordEncoder;

    public UserController(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    // =========================
    // 서버 시작 시 로드 (빈 파일 예외 처리 추가)
    // =========================
    @PostConstruct
    public void loadUsers() {
        File file = new File(FILE_PATH);

        try {
            // 파일이 없거나 파일 크기가 0(비어있는 상태)인 경우 빈 배열로 초기화
            if (!file.exists() || file.length() == 0) {
                if (!file.exists()) {
                    file.createNewFile();
                }
                objectMapper.writeValue(file, users);
            }

            // JSON 읽기
            users = objectMapper.readValue(
                    file,
                    new TypeReference<List<User>>() {}
            );

            System.out.println("유저 데이터 로드 완료");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // =========================
    // 회원가입 (RequestBody 적용)
    // =========================
    @PostMapping("/register")
    public String register(@RequestBody UserRequest request) {

        for (User user : users) {
            if (user.userId.equals(request.getUserId())) {
                return "이미 존재하는 아이디";
            }
        }

        // 비밀번호를 암호화하여 저장
        String encodedPassword = passwordEncoder.encode(request.getPassword());
        users.add(new User(request.getUserId(), encodedPassword));
        saveUsers();

        return "회원가입 성공";
    }

    // =========================
    // 로그인 (RequestBody 적용)
    // =========================
    @PostMapping("/login")
    public String login(@RequestBody UserRequest request) {

        for (User user : users) {
            // matches(평문 비밀번호, 암호화된 비밀번호)로 비교
            if (user.userId.equals(request.getUserId()) &&
                    passwordEncoder.matches(request.getPassword(), user.password)) {
                return "로그인 성공";
            }
        }
        return "아이디 또는 비밀번호 오류";
    }

    // =========================
    // 점수 저장 (RequestBody 적용 및 검증)
    // =========================
    @PostMapping("/submit-score")
    public String submitScore(@RequestBody UserRequest request) {

        for (User user : users) {
            // 로그인 검증 로직 동일하게 적용 (특수문자 포함 비밀번호 정상 처리)
            if (user.userId.equals(request.getUserId()) &&
                    passwordEncoder.matches(request.getPassword(), user.password)) {

                // 최고 점수 저장
                if (request.getScore() > user.highScore) {
                    user.highScore = request.getScore();
                    saveUsers();
                    return "점수 저장 성공";
                }

                return "기존 점수가 더 높음";
            }
        }

        return "로그인 실패";
    }

    // =========================
    // 랭킹 조회 (유니티 파싱 호환용 객체 감싸기 적용)
    // =========================
    @GetMapping("/leaderboard")
    public LeaderboardResult leaderboard() {

        // 1. 점수 내림차순 정렬
        users.sort((a, b) -> Integer.compare(b.highScore, a.highScore));

        // 2. DTO 변환
        List<LeaderboardResponse> responseList = new ArrayList<>();
        for (User user : users) {
            responseList.add(new LeaderboardResponse(user.userId, user.highScore));
        }

        // 3. 유니티 JsonUtility가 인식할 수 있도록 중괄호 객체로 감싸서 반환
        return new LeaderboardResult(responseList);
    }

    // =========================
    // JSON 저장
    // =========================
    public static void saveUsers() {
        try {
            objectMapper
                    .writerWithDefaultPrettyPrinter()
                    .writeValue(new File(FILE_PATH), users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}