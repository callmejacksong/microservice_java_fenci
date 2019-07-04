package com.melink.sop.api.models;

public interface EnumFactory {

	enum SDKType implements EnumFactory {
		IM("im", 1), REAMRK("remark", 2), EO("eo", 3), ABROAD("abroad", 4),OPEN("open",5),FREE("free",6);
		private String name;
		private int index;

		private SDKType(String name, int index) {
			this.name = name;
			this.index = index;
		}

		public static Integer getIndex(String name) {
			if(name == null)return null;
			for (SDKType c : SDKType.values()) {
				if (c.name.equals(name)) {
					return c.index;
				}
			}
			return null;
		}

		public static String getName(Integer index) {
			if(index == null)return null;
			for (SDKType c : SDKType.values()) {
				if (c.index == index) {
					return c.name;
				}
			}
			return null;
		}
	}

}