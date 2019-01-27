package Util;

public class SumaTutorialInformation {

    private String title;
    private String information;
    private int mainImage;
    private int optionalImg1;
    private int optionalImg2;
    private int optionalImg3;

    public SumaTutorialInformation(){

    }

    public SumaTutorialInformation(String title, String information, int mainImage, int optionalImg1, int optionalImg2, int optionalImg3) {
        this.title = title;
        this.information = information;
        this.mainImage = mainImage;
        this.optionalImg1 = optionalImg1;
        this.optionalImg2 = optionalImg2;
        this.optionalImg3 = optionalImg3;
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

    public int getOptionalImg1() {
        return optionalImg1;
    }

    public void setOptionalImg1(int optionalImg1) {
        this.optionalImg1 = optionalImg1;
    }

    public int getOptionalImg2() {
        return optionalImg2;
    }

    public void setOptionalImg2(int optionalImg2) {
        this.optionalImg2 = optionalImg2;
    }

    public int getOptionalImg3() {
        return optionalImg3;
    }

    public void setOptionalImg3(int optionalImg3) {
        this.optionalImg3 = optionalImg3;
    }
}
