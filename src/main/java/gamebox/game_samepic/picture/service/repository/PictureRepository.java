package gamebox.game_samepic.picture.service.repository;

import gamebox.game_samepic.picture.service.entity.Picture;
import gamebox.util.exceptions.ErrorType;
import gamebox.util.exceptions.KeyDuplicatedException;

import java.util.*;

public class PictureRepository {
    private final Map<String, Picture> pictureMap = new HashMap<>();
    private static PictureRepository pictureRepository;

    private PictureRepository() {}

    public static PictureRepository getInstance() {
        if (pictureRepository == null) {
            pictureRepository = new PictureRepository();
        }
        return pictureRepository;
    }

    public Picture save(Picture picture) {
        try {
            pictureMap.put(picture.getId(), picture);
            return picture;
        } catch (KeyDuplicatedException e) {
            throw new IllegalArgumentException(ErrorType.DUPLICATED_PICTURE_ID.getMessage(), e);
        }
    }

    public void put(Picture picture) {
        isIdExist(picture.getId());
        pictureMap.put(picture.getId(), picture);
    }

    private void isIdExist(String id) {
        if (pictureMap.containsKey(id)) {
            throw new KeyDuplicatedException(ErrorType.DUPLICATED_PICTURE_ID.getMessage());
        }
    }

    public void deleteById(String id) {
        pictureMap.remove(id);
    }

    public Picture findById(String id) {
        Picture picture = pictureMap.get(id);
        if (picture == null) {
            throw new IllegalArgumentException(ErrorType.NOT_EXIST_PICTURE.getMessage() + id);
        }
        return picture;
    }

    public List<Picture> findAll(){
        return pictureMap.values().stream().toList();
    }
    public List<String> findAllIds(){
        return new ArrayList<>(pictureMap.keySet());
    }
}
