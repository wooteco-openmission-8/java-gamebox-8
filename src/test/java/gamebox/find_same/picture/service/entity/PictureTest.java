package gamebox.find_same.picture.service.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PictureTest {

    @Test
    @DisplayName("Picture 빌더 테스트")
    void pictureBuilderTest() {
        Picture picture = new Picture.Builder()
                .id(1)
                .checkCount(0)
                .visible(false)
                .path("/path")
                .title("title")
                .build();
        assertThat(picture).isInstanceOf(Picture.class);
    }
}