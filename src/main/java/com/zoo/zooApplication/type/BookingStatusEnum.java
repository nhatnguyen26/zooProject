package com.zoo.zooApplication.type;

public enum BookingStatusEnum {

    RESERVED(1,"RESERVED"),
    CHECKED_IN(2,"CHECKED_IN"),
    CHECKED_OUT(3,"CHECKED_OUT"),
    CANCELLED(4,"CANCELLED"),
    HIDDEN(5,"HIDDEN");

    private int id;

    private String status;

    BookingStatusEnum(int id, String status){
        this.id = id;
        this.status = status;
    }
    public static BookingStatusEnum getFromId(Integer dbData) {
        if (dbData != null) {
            for (BookingStatusEnum value : values()) {
                if (value.getId() == dbData.intValue()) {
                    return value;
                }
            }
        }
        return null;
    }

    public int getId() {
        return id;
    }

    public String getString(){
        return status;
    }
}
