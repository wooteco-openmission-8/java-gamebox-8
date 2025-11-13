package gamebox.find_same.picture.service.repository;

import gamebox.find_same.picture.service.entity.Picture;
import gamebox.util.exceptions.ErrorType;
import gamebox.util.exceptions.KeyDuplicatedException;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PictureRepository {
    private final Map<Integer, Picture> pictureMap = new HashMap<>();
    private static PictureRepository pictureRepository;

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

    private void isIdExist(int id) {
        if (pictureMap.containsKey(id)) {
            throw new KeyDuplicatedException(ErrorType.DUPLICATED_PICTURE_ID.getMessage());
        }
    }

    public void deleteById(int id) {
        isIdExist(id);
        pictureMap.remove(id);
    }

    public Picture findById(int id) {
        Picture picture = pictureMap.get(id);
        if (picture == null) {
            throw new IllegalArgumentException(ErrorType.NOT_EXIST_PICTURE.getMessage() + id);
        }
        return picture;
    }

    public Collection<Picture> findAll(){
        return Collections.unmodifiableCollection(pictureMap.values());
    }
}
