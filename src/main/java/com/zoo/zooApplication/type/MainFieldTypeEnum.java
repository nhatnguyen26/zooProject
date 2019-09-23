package com.zoo.zooApplication.type;

public enum MainFieldTypeEnum {

    SOCCER_5(1, "SOCCER", false),
    SOCCER_7(2, "SOCCER", true),
    SOCCER_11(3, "SOCCER", true);

    // id for enum
    private int id;

    // type is this fieldType
    // for now is just SOCCER
    private String complexType;

    // if the field of this type allow combine from SMALLER field
    private boolean allowCombine;

    MainFieldTypeEnum(int id, String complexType, boolean allowCombine) {
        this.id = id;
        this.complexType = complexType;
        this.allowCombine = allowCombine;
    }

    public static MainFieldTypeEnum getFromId(Integer dbData) {
        if (dbData != null) {
            for (MainFieldTypeEnum value : values()) {
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

    public boolean isAllowCombine() {
        return allowCombine;
    }

    public String getComplexType() {
        return complexType;
    }

    // NOTE: this probably over engineering since only allow SOCCER, but can be useful
    public boolean canCombineFromType(MainFieldTypeEnum otherType) {
        if (!this.allowCombine || !this.complexType.equals(otherType.complexType)) {
            // do not allow combine from cross complex (for example, soccer field cannot be combine from basketball field
            return false;
        }

        // id within one complex type will be increasing, it doesn't make sense that size 7 is made from size 11
        return otherType.id < this.id;
    }
}
