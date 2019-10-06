package com.zoo.zooApplication.type;

public enum BookingStatusEnum {

    RESERVED(1,"RESERVED"),
    CHECKIN(2,"CHECK IN"),
    CHECKOUT(3,"CHECK OUT"),
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
