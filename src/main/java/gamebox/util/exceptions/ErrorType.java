package gamebox.util.exceptions;

public enum ErrorType {
    // 같은 그림 찾기
    INVALID_BOARD_SIZE("보드판은 짝수형이어야 합니다."),
    NOT_ENOUGH_PICTURES("그림의 개수가 충분하지 않습니다."),
    GAME_NOT_STARTED("게임이 시작되지 않았습니다."),
    DUPLICATED_PICTURE_ID("이미 존재하는 아이디 입니다."),
    INVALID_INDEX("유효하지 않은 카드 인덱스입니다: "),
    NOT_EXIST_PICTURE("Picture를 찾을 수 없습니다. ID: ");

    private final String message;

    ErrorType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return "[ERROR] " + this.message;
    }
}
