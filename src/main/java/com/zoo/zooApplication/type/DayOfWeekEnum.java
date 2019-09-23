package com.zoo.zooApplication.type;

public enum DayOfWeekEnum {

    SUN(0, "SUN"),
    MON(1, "MON"),
    TUE(2, "TUE"),
    WED(3, "WED"),
    THU(4, "THU"),
    FRI(5, "FRI"),
    SAT(6, "SAT");


    // id for enum
    private int id;

    private String day;

    DayOfWeekEnum(int id, String day) {
        this.id = id;
        this.day = day;
    }

    public int getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

}
