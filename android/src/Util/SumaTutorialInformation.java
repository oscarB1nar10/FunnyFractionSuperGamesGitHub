package Util;

public class SumaTutorialInformation {

    private String title;
    private String information;
    private int mainImage;
    private int optionalImg;

    public SumaTutorialInformation(){

    }

    public SumaTutorialInformation(String title, String information, int mainImage, int optionalImg) {
        this.title = title;
        this.information = information;
        this.mainImage = mainImage;
        this.optionalImg = optionalImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public int getMainImage() {
        return mainImage;
    }

    public void setMainImage(int mainImage) {
        this.mainImage = mainImage;
    }

    public int getOptionalImg() {
        return optionalImg;
    }

    public void setOptionalImg1(int optionalImg) {
        this.optionalImg = optionalImg;
    }
}
