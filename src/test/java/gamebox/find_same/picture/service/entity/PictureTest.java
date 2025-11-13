package gamebox.find_same.picture.service.entity;

import gamebox.util.HashMaker;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class PictureTest {

    @Test
    @DisplayName("Picture 빌더 테스트")
    void pictureBuilderTest() {
        String hash = UUID.randomUUID().toString();
        Picture picture = new Picture.Builder()
                .id(HashMaker.make(1, hash))
                .checkCount(0)
                .visible(false)
                .path("/path")
                .title("title")
                .build();
        assertThat(picture).isInstanceOf(Picture.class);
    }
}