# 🎮 2048

##  게임 규칙 (Game Rules)

- 게임 보드는 4x4 격자입니다.

1. 시작 시, 무작위 두 칸에 2 또는 4가 생성됩니다.
2. 방향키(↑, ↓, ←, →) 입력 시:

   - 모든 타일이 해당 방향으로 이동합니다.
   - 같은 숫자의 타일이 충돌하면 합쳐져 두 배의 숫자가 됩니다.
   - 한 번의 이동에서 같은 타일은 한 번만 합칠 수 있습니다.

3. 이동 후 빈 칸이 있다면, 무작위로 2 또는 4가 새로 생성됩니다.

4. 더 이상 이동할 수 있는 공간이 없다면 게임이 종료됩니다.
5. 2048 타일을 만들면 승리합니다.

##  기능 구현 목록

### 1. 게임 인터페이스 (2048)

- [x] `start()` 호출 시 게임 시작
- [x] `getName()` 호출 시 `"2048"` 반환

---

### 2. 타일 클래스 (Tile)

- [x] 숫자 값(`int number`) 저장
- [x] 병합 여부(`boolean merged`) 기록
- [x] 빈 칸 여부 확인 메서드 `isEmpty()` 구현
- [x] 다른 타일과 병합 가능 여부 확인 메서드 `canMergeWith(Tile other)` 구현
- [x] 병합 처리 메서드 `merge()` 구현 (값 두 배, merged=true)
- [x] 새 값 생성 메서드 `spawn(int value)` 구현 (빈 칸에 2 또는 4 값 생성)
- [x] 값 변경 시 배경색/텍스트 색상 반환 메서드 `getBackgroundColor()`, `getTextColor()` 구현
- [x] 병합 여부 초기화 메서드 `resetMerged()` 구현 (턴 종료 후 호출)

### 2-1. 타일 타입 Enum (TileType)

- [x] 타일 값별 enum 상수 정의 (EMPTY, TWO, FOUR, ... TWO_THOUSAND_FORTY_EIGHT)
- [x] 각 enum 상수에 값(int value) 저장
- [x] 각 enum 상수에 배경색(Color backgroundColor) 저장
- [x] 각 enum 상수에 텍스트 색상(Color textColor) 저장
- [x] 숫자 값으로 TileType 찾는 메서드 `fromValue(int number)` 구현
- [x] 배경색 반환 메서드 `getBackgroundColor()` 구현
- [x] 텍스트 색상 반환 메서드 `getTextColor()` 구현

---

---

### 3. 보드 클래스 (Board)

- [ ] Tile[][] 배열로 보드 상태 저장
- [ ] 보드 초기화 (빈 칸 + 시작 타일 2개 스폰)
- [ ] 방향별 이동/병합 메서드 구현 (up, down, left, right)
    - [ ] 이동 시 빈 칸으로 이동
    - [ ] merge 가능한 타일끼리 병합
    - [ ] merge 후 merged 플래그 처리
    - [ ] merge된 값 반환 (GameModel에서 점수 계산용)
- [ ] 이동 가능 여부 체크 메서드 구현 (게임 종료 판단용)
- [ ] 빈 칸 리스트 반환 메서드 구현 (새 타일 스폰용)
- [ ] 랜덤 타일 스폰 메서드 구현 (값 2 또는 4)
- [ ] 보드 리셋 메서드 구현 (턴 종료 후 merged 플래그 초기화)
- [ ] 보드 상태 반환/조회 메서드 구현 (View에서 그리기용)

---

### 4. 모델 (2048Model)

- [ ] Board 객체 포함
- [ ] 현재 점수(int score) 관리
- [ ] 이동/병합 메서드 제공 (up, down, left, right)
    - [ ] Board 이동/merge 호출
    - [ ] 이동 성공 시 Board의 새 타일 스폰 메서드 호출
    - [ ] Board에서 반환된 merge 값으로 점수 갱신
- [ ] 게임 종료 여부 체크 메서드 제공 (더 이상 이동/merge 불가)
- [ ] 보드 상태 반환 메서드 제공 (View에서 그리기용)
- [ ] 점수 반환 메서드 제공
- [ ] 모델 초기화/리셋 메서드 제공 (Board 초기화 + 점수 0)

---

### 5. 컨트롤러 (2048Controller)

- [ ] GameModel 객체 포함
- [ ] GameView 객체 포함
- [ ] KeyListener를 GameView에 등록
    - [ ] 방향키(up/down/left/right) 입력 시 GameModel 이동/병합 호출
    - [ ] 이동 성공 시 View repaint 호출
    - [ ] 이동 불가 시 상태 유지
- [ ] 게임 종료 또는 승리 판단 후 View에 메시지 전달
- [ ] “New Game” 버튼 클릭 시 GameModel 초기화 + View 리셋
- [ ] 점수/게임 상태 변경 시 View 업데이트 호출

---

### 6. 뷰 (2048View)

- [ ] JPanel 상속, Swing 기반 화면 구성
- [ ] paintComponent(Graphics g)에서 Board(Tile[][]) 상태 렌더링
- [ ] 타일 숫자 값에 따라 배경색/글자색/폰트 크기 적용
- [ ] 상단에 현재 점수 및 최고 점수 표시
- [ ] 승리(2048 달성) 또는 게임 종료(더 이상 이동 불가) 메시지 출력
- [ ] 키보드 이벤트를 Controller로 전달
- [ ] “New Game” 버튼 클릭 이벤트를 Controller로 전달
- [ ] 보드 및 타일 픽셀 위치/크기 계산용 유틸 메서드
- [ ] 타일 색상/글자색 계산 유틸 (Tile enum 또는 메서드 참조)

---