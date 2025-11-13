package gamebox.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class HashMakerTest {

    @Test
    @DisplayName("같은 값은 항상 같은 해쉬")
    void make() {
        String hashData = UUID.randomUUID().toString();

        String make = HashMaker.make(1, hashData);
        String make2 = HashMaker.make(1, hashData);

        assertThat(make).isEqualTo(make2);
    }
}