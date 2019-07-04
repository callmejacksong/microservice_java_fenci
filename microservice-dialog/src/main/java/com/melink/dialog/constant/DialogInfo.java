package com.melink.dialog.constant;

public class DialogInfo {

    private String ocrInfo;
    private String textInfo;
    private String tulingInfo;
    private String randomInfo;
    private String imageText;


    public static String getInfoByOcr(String ocrText,String tulingText,String imageText) {
        return "OCR: "+ocrText+"/n;tuling: "+tulingText+"/n;imageText:"+imageText+"/n";
    }

    public static String getInfoById(String text, String tulingText, String imageText) {
        return "text: "+text+"/n;tuling: "+tulingText+"/n;imageText:"+imageText+"/n";
    }

    public String getOcrInfo() {
        return ocrInfo;
    }

    public void setOcrInfo(String ocrInfo) {
        this.ocrInfo = ocrInfo;
    }

    public String getTulingInfo() {
        return tulingInfo;
    }

    public void setTulingInfo(String tulingInfo) {
        this.tulingInfo = tulingInfo;
    }

    public String getRandomInfo() {
        return randomInfo;
    }

    public void setRandomInfo(String randomInfo) {
        this.randomInfo = randomInfo;
    }

    public String getImageText() {
        return imageText;
    }

    public void setImageText(String imageText) {
        this.imageText = imageText;
    }
}
