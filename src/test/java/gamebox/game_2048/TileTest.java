package gamebox.game_2048;

import gamebox.game_2048.entity.Tile;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TileTest {
    @Test
    @DisplayName("숫자가 2 또는 4로 스폰되는지 확인")
    void validateSpawnNumber(){
        Tile tile = new Tile(0);
        tile.spawn();

        int value = tile.getNumber();

        assertThat(value).isIn(2, 4);
    }

    @Test
    @DisplayName("2가 90% 확률로 잘 스폰되는지 확인")
    void spawnTest(){
        int countTwo = 0;
        int trials = 1000;

        for (int i=0; i<trials; i++) {
            Tile tile = new Tile(0);
            tile.spawn();
            if (tile.getNumber() == 2){
                countTwo++;
            }
        }

        double ratio = (double) countTwo / trials;

        System.out.println("2 생성 비율 = " + ratio);

        assertThat(ratio).isBetween(0.8, 1.0);
    }
}