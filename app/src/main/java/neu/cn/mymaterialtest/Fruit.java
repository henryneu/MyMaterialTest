package neu.cn.mymaterialtest;

/**
 * Created by neuHenry on 2017/6/8.
 */

public class Fruit {

    private int imageId;

    private String imageName;

    public Fruit(int imageId, String imageName) {
        this.imageId = imageId;
        this.imageName = imageName;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageName() {
        return imageName;
    }
}
