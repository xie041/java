package net.lising.core.util;

import java.util.Date;

public enum DataTypeEnumUtil {
	PACKAGE_INT {
		@Override
		public Class<?> findClass() {
			return Integer.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Integer.valueOf(obj + "");
		}
	},
	INT {
		@Override
		public Class<?> findClass() {
			return int.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Integer.parseInt(obj + "");
		}
	},
	PACKAGE_LONG {
		@Override
		public Class<?> findClass() {
			return Long.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Long.valueOf(obj + "");
		}
	},
	LONG {
		@Override
		public Class<?> findClass() {
			return long.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Long.parseLong(obj + "");
		}
	}

	,
	PACKAGE_DOUBLE {
		@Override
		public Class<?> findClass() {
			return Double.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Double.valueOf(obj + "");
		}
	},
	DOUBLE {
		@Override
		public Class<?> findClass() {
			return double.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Double.parseDouble(obj + "");
		}
	}

	,
	PACKAGE_FLOAT {
		@Override
		public Class<?> findClass() {
			return Float.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Float.valueOf(obj + "");
		}
	},
	FLOAT {
		@Override
		public Class<?> findClass() {
			return float.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Float.parseFloat(obj + "");
		}
	},
	PACKAGE_CHAR {
		@Override
		public Class<?> findClass() {
			return Character.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Character.valueOf((obj + "").charAt(0));
		}
	},
	CHAR {
		@Override
		public Class<?> findClass() {
			return char.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return (obj + "").charAt(0);
		}
	},
	PACKAGE_BYTE {
		@Override
		public Class<?> findClass() {
			return Byte.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Byte.valueOf(obj + "");
		}
	},
	BYTE {
		@Override
		public Class<?> findClass() {
			return byte.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Byte.parseByte(obj + "");
		}
	},
	PACKAGE_BOOLEAN {
		@Override
		public Class<?> findClass() {
			return Boolean.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Boolean.valueOf(obj + "");
		}
	},
	BOOLEAN {
		@Override
		public Class<?> findClass() {
			return boolean.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Boolean.parseBoolean(obj + "");
		}
	},
	PACKAGE_SHORT {
		@Override
		public Class<?> findClass() {
			return Short.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Short.valueOf(obj + "");
		}
	},
	SHORT {
		@Override
		public Class<?> findClass() {
			return short.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return Short.parseShort(obj + "");
		}
	},
	STRING {
		@Override
		public Class<?> findClass() {
			return String.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return obj + "";
		}
	},
	DATE {
		@Override
		public Class<?> findClass() {
			return Date.class;
		}

		@Override
		public Object convertDataType(Object obj) {
			return (Date)obj;
		}
	};
	public abstract Class<?> findClass();

	public abstract Object convertDataType(Object obj);

}
