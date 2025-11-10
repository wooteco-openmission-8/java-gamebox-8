package gamebox.find_same.picture.service.repository;

import gamebox.find_same.picture.service.entity.Picture;
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
            throw new IllegalArgumentException("[ERROR] 이미 존재하는 아이디 입니다.", e);
        }
    }

    public void put(Picture picture) {
        isIdExist(picture.getId());
        pictureMap.put(picture.getId(), picture);
    }

    private void isIdExist(int id) {
        if (pictureMap.containsKey(id)) {
            throw new KeyDuplicatedException("[ERROR] Picture with id " + id + " already exists");
        }
    }

    public void deleteById(int id) {
        isIdExist(id);
        pictureMap.remove(id);
    }

    public Collection<Picture> findAll(){
        return Collections.unmodifiableCollection(pictureMap.values());
    }
}
