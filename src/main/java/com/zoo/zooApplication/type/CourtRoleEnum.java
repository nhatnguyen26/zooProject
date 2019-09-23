package com.zoo.zooApplication.type;

public enum CourtRoleEnum {
	OWNER(1),
	ADMIN(2);

	private int id;

	CourtRoleEnum(int id) {
		this.id = id;
	}

	public int getId() {
		return this.id;
	}

	public static CourtRoleEnum getFromId(Integer id) {
		if (id != null) {
			for (CourtRoleEnum value : values()) {
				if (value.getId() == id.intValue()) {
					return value;
				}
			}
		}
		return null;
	}
}
