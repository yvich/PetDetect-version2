package com.example.door;

public class doorItem {

    String doornameStr;
    int doorImg;

    public doorItem(String doornameStr,int doorImg)
    {
        this.doorImg=doorImg;
        this.doornameStr=doornameStr;
    }
    public String getDoornameStr()
    {
        return doornameStr;
    }
    public int getDoorImg()
    {
        return doorImg;
    }
}

//public class Item {
//
//    String birdListName;
//    int birdListImage;
//
//    public Item(String birdName,int birdImage)
//    {
//        this.birdListImage=birdImage;
//        this.birdListName=birdName;
//    }
//    public String getbirdName()
//    {
//        return birdListName;
//    }
//    public int getbirdImage()
//    {
//        return birdListImage;
//    }
//}
