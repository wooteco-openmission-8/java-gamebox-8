package gamebox.game_samepic.picture.service.entity;

public class Picture {

    private String id;
    private boolean visible;
    private int checkCount;
    private String title;
    private String path;
    private String group;

    private Picture() {
    }

    public static class Builder {
        private final Picture picture = new Picture();

        public Builder id(String id) {
            picture.id = id;
            return this;
        }

        public Builder visible(boolean visible) {
            picture.visible = visible;
            return this;
        }

        public Builder checkCount(int checkCount) {
            picture.checkCount = checkCount;
            return this;
        }

        public Builder title(String title) {
            picture.title = title;
            return this;
        }

        public Builder path(String path) {
            picture.path = path;
            return this;
        }

        public Builder group(String group) {
            picture.group = group;
            return this;
        }

        public Picture build() {
            return picture;
        }
    }

    public String getId() {
        return id;
    }

    public boolean isVisible() {
        return visible;
    }

    public int getCheckCount() {
        return checkCount;
    }

    public String getTitle() {
        return title;
    }

    public String getPath() {
        return path;
    }

    public String getGroup() {
        return group;
    }
}
