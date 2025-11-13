# 같은 그림 찾기 — TODO 체크리스트

## 핵심 모델

* [ ] `Picture`

    * 필드:

        * `int id` — 그림 고유 id (짝이 같은 그림은 같은 id)
        * `String path` — 정적 리소스 경로 (ex. `images/dog.png`)
        * `boolean visible` — 현재 보이는 상태 (true=앞면 보임)
        * `int clickedCount` — 누적 클릭 수 (선택)
    * 메서드:

        * `void flip()` — visible 토글
        * getters/setters, `equals`/`hashCode`(필요시)
* [ ] `Game`

    * 필드:

        * `String gameId` — UUID
        * `int rows, cols`
        * `List<Picture> board` — 크기 `rows*cols`, index 기반
        * `int matchedCount`
        * `boolean completed`
        * `LocalDateTime createdAt`
    * 메서드:

        * `Picture getPicture(int index)`
        * `void setPicture(int index, Picture p)`
        * `boolean isCompleted()`
* [ ] `GameInfo` (조회용 요약)

    * `String gameId`, `int rows`, `int cols`, `int totalCount`, `int matchedCount`, `boolean completed`

---

## DTO (요청/응답)

* [ ] `PictureRequestDto`

    * `String gameId`
    * `int index`
    * `int id` (클라이언트 보유 id, 검증용 선택필드)
    * `String path` (검증용 선택필드)
* [ ] `PictureResponseDto`

    * `int index`
    * `int id`
    * `String path`
    * `boolean visible`
    * (선택) `boolean matched`
* [ ] `StartGameResponseDto`

    * `String gameId`
    * `List<PictureResponseDto> pictures` (초기 상태, 모두 `visible=false`)
* [ ] `GameInfoDto`

    * `GameInfo` 필드들

---

## 리포지토리 (in-memory)

* [ ] `GameRepository` (POJO 클래스, 싱글톤 권장)

    * 내부구조: `ConcurrentHashMap<String, Game> games`
    * 메서드:

        * `Game createGame(int rows, int cols)` — 새 게임 생성(랜덤 섞기 포함), `gameId` 반환
        * `Optional<Game> getGame(String gameId)` — 없으면 Optional.empty()
        * `void saveGame(Game game)` — map에 저장(업데이트)
        * `void deleteGame(String gameId)`
        * `List<GameInfo> listGames()`
    * (선택) 파일 영속화:

        * `void persistGameToFile(String gameId)` — JSON 직렬화
        * `Game loadGameFromFile(String gameId)`

---

## 서비스 (게임 로직, POJO)

* [ ] `PictureService` (순수 자바 객체)

    * 메서드:

        * `StartGameResponseDto startGame(int rows, int cols)`

            * 동작:

                * 가능한 그림 id 목록 준비(총 `rows*cols/2`쌍)
                * 각 id를 2개씩 만들어 리스트에 넣고 shuffle
                * `Picture` 객체 생성(visible=false) → `Game` 생성 → repository에 저장 → DTO 반환
        * `List<PictureResponseDto> checkSame(List<PictureRequestDto> requests)`

            * 동작:

                * 요청에서 `gameId` 확인(모든 원소 동일해야 함)
                * `Game` 조회, 존재하지 않으면 예외
                * 요청 인덱스들(보통 2개)을 `Game.board`에서 꺼냄
                * **검증 과정**

                    * 인덱스 유효성 체크
                    * (선택) 요청 path/id와 실제 path/id 비교 -> 불일치면 복구 시도 또는 예외
                * 같은 그림이면:

                    * 두 `Picture.visible = true;` (고정)
                    * `game.matchedCount += 2`
                * 다르면:

                    * 두 `Picture.visible = true` (응답에 보이게 한 뒤, UI가 짧은 딜레이 후 다시 `visible=false`로 바꿀 수 있음)
                    * **서비스에서 바로 `visible=false`로 되돌릴지** 설계 결정(권장: 서버는 authoritative하므로 `saveState` 호출로 최종 상태 반영)
                * 게임 완료 시 `game.completed = true`
                * repository.saveGame(game) 호출
                * 변경된 인덱스에 해당하는 `PictureResponseDto` 리스트 반환
        * `List<PictureResponseDto> saveState(List<PictureRequestDto> requests)`

            * 항상 호출되어 서버 상태 동기화 (clickedCount, visible 등 업데이트)
        * `GameInfo getGameInfo(String gameId)`
    * 동시성:

        * `checkSame`에서 `synchronized` 또는 `ReentrantLock`으로 `game` 단위 락 권장

---

## 예외 정의

* [ ] `GameNotFoundException extends RuntimeException`
* [ ] `InvalidIndexException`
* [ ] `PathMismatchException`
* [ ] `GameStateConflictException`

---

## UI (Swing)

* [ ] **UI - View (Swing Frame / Panel)**

    * n*m 그리드(예: `JPanel` + `GridLayout`)로 버튼/카드 생성
    * 카드마다 index 보관(버튼 `putClientProperty("index", idx)`)
    * 카드 앞면/뒷면 그리기(이미지 또는 색)
* [ ] **UI - Controller (이벤트 핸들러)**

    * 내부 상태:

        * `Optional<Integer> firstOpenIndex` (한 장 열린 상태)
        * `boolean isWaitingForServer` (서버 응답 대기 중 입력 차단)
    * 클릭 흐름:

        1. 클릭 → index 가져오기
        2. 로컬에서 카드 앞면 보이기 (`visible=true` in UI only)
        3. 만약 `firstOpenIndex` 비어있으면 `firstOpenIndex = index` (서버 호출 없음)
        4. 만약 `firstOpenIndex` 존재하면:

        * `isWaitingForServer = true`
        * 두 카드의 `PictureRequestDto`를 만들어 `service`(또는 네트워크)로 보냄
        * 서버 응답 수신:

            * 응답에서 `visible`/`matched`를 읽어 UI 동기화
            * 만약 `matched=false`이면 짧게(예: 700ms) 보여주고 다시 뒤집기
        * `firstOpenIndex = empty`, `isWaitingForServer = false`
* [ ] **네트워크(또는 로컬 호출)**

    * 이 구현은 로컬 모드(동일 프로세스) 또는 네트워크 모드(REST/소켓)로 사용할 수 있음. 현재는 **로컬 호출(함수 호출)** 권장(POJO 환경).
* [ ] **UI 고려사항**

    * 응답 대기 시 다른 카드 클릭 금지
    * 게임 완료 시 모달 표시
    * 에러 발생 시 재시작 유도

---

## 동시성 / 안전성

* [ ] `GameRepository`는 `ConcurrentHashMap`
* [ ] 상태 변경 시 `synchronized(game)` 또는 `Lock`으로 보호
* [ ] `checkSame`에서 race condition 방지(동일 인덱스에 대해 동시에 처리되지 않도록)

---

## 테스트 계획

* [ ] 단위테스트: `startGame(rows,cols)` — 올바른 크기/짝 구성 확인
* [ ] 단위테스트: `checkSame(같은 인덱스 쌍)` — matchedCount 증가, visible 상태
* [ ] 단위테스트: `checkSame(다른 인덱스 쌍)` — visible 되었다가 다시 뒤집히는지(혹은 상태 일관성)
* [ ] 동시성 테스트: 두 스레드가 동시에 같은 게임에서 `checkSame` 호출 시 상태 무결성 유지
* [ ] 복원 테스트(파일 영속화 사용 시)

---

## 샘플 클래스/메서드 시그니처

* [ ] `Picture`

```java
public class Picture {
    private final int id;
    private final String path;
    private boolean visible;
    private int clickedCount;

    public Picture(int id, String path) {
        this.id = id;
        this.path = path;
        this.visible = false;
        this.clickedCount = 0;
    }

    public void flip() { this.visible = !this.visible; }
    // getters / setters
}
```

* [ ] `GameRepository`

```java
public class GameRepository {
    private final ConcurrentHashMap<String, Game> games = new ConcurrentHashMap<>();

    public Game createGame(int rows, int cols) { ... } // returns new Game and stores it
    public Optional<Game> getGame(String id) { return Optional.ofNullable(games.get(id)); }
    public void saveGame(Game game) { games.put(game.getGameId(), game); }
}
```
---

## 설계 의사결정 체크리스트 (반드시 결정해야 할 것들)

* [ ] 서버(서비스)가 실패한 매칭(=틀림) 상황에서 `visible` 상태를 **즉시 되돌릴지** 아니면 **클라이언트가 되돌릴지** (권장: 서버가 authoritative로 최종 상태 저장, 클라이언트는 보여주다가 서버 상태로 동기화)
* [ ] 게임 식별자(`gameId`)를 **클라이언트가 보관할지** (권장: 보관, 재접속 시 사용)
* [ ] 로컬 모드(POJO 직접 호출) / 네트워크 모드(REST) 중 어느 것부터 구현할지 (권장: 로컬 모드 먼저)
* [ ] 영속화(파일) 여부와 저장 주기
* [ ] 에러 복구 전략 (예: path mismatch 시 게임 재시작 또는 특정 카드만 복구)

---
