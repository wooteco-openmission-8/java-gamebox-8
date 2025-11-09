package gamebox.game2048;

import java.awt.Color;

/**
 * 2048 게임의 개별 타일을 나타내는 클래스
 */
public class Tile {
    private int number;        // 타일의 숫자 값 (0은 빈 칸)
    private boolean merged;    // 현재 턴에 병합되었는지 여부

    /**
     * 특정 값을 가진 타일 생성
     * @param number 타일의 숫자 값 (0은 빈 칸, 2~2048 사이의 2의 거듭제곱)
     * @throws IllegalArgumentException 유효하지 않은 값인 경우
     */
    public Tile(int number) {
        if (!isValidTileValue(number)) {
            throw new IllegalArgumentException(
                    "타일 값은 0 또는 2~2048 사이의 2의 거듭제곱이어야 합니다: " + number
            );
        }
        this.number = number;
        this.merged = false;
    }

    /**
     * 유효한 타일 값인지 확인
     * @param value 확인할 값
     * @return 0이거나 2~2048 사이의 2의 거듭제곱이면 true
     */
    private boolean isValidTileValue(int value) {
        if (value == 0) return true;
        if (value < 2 || value > 2048) return false;

        // 2의 거듭제곱인지 확인
        // 비트 연산: value & (value - 1) == 0 이면 2의 거듭제곱
        return (value & (value - 1)) == 0;
    }

    /**
     * 타일의 숫자 값 반환
     */
    public int getNumber() {
        return number;
    }


    /**
     * 빈 칸인지 확인
     * @return 빈 칸이면 true
     */
    public boolean isEmpty() {
        return number == 0;
    }
    
    /**
     * 다른 타일과 병합 가능한지 확인
     * @param other 비교할 타일
     * @return 병합 가능하면 true
     */
    public boolean canMergeWith(Tile other) {
        if (other == null) return false;
        if (this.isEmpty() || other.isEmpty()) return false;
        if (this.merged || other.merged) return false;
        return this.number == other.number;
    }
    
    /**
     * 타일을 병합 처리 (값을 두 배로 만들고 병합 플래그 설정)
     * @return 병합된 값 (점수 계산용)
     */
    public int merge() {
        this.number *= 2;
        this.merged = true;
        return this.number;
    }
    
    /**
     * 빈 칸에 새로운 값 생성
     * @param value 생성할 값 (2 또는 4)
     */
    public void spawn(int value) {
        if (isEmpty()) {
            this.number = value;
            this.merged = false;
        }
    }
    
    /**
     * 타일의 배경색 반환
     * @return 타일 값에 따른 배경색
     */
    public Color getBackgroundColor() {
        TileValue tileValue = TileValue.fromValue(this.number);
        return tileValue.getBackground();
    }

    /**
     * 타일의 텍스트 색상 반환
     * @return 타일 값에 따른 텍스트 색상
     */
    public Color getTextColor() {
        TileValue tileValue = TileValue.fromValue(this.number);
        return tileValue.getTextColor();
    }
    
    /**
     * 병합 플래그 초기화 (턴 종료 후 호출)
     */
    public void resetMerged() {
        this.merged = false;
    }

    @Override
    public String toString() {
        return isEmpty() ? "." : String.valueOf(number);
    }
}