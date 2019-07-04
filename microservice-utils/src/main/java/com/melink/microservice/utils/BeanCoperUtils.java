package com.melink.microservice.utils;

import net.sf.cglib.beans.BeanCopier;

public class BeanCoperUtils {

	private static BeanCoperUtils instance = new BeanCoperUtils();

	// Empty private constructor
	private BeanCoperUtils() {
	}

	public static BeanCoperUtils getInstance() {
		return instance;
	}

	private BeanCopier getBeanCopuer(Class<? extends Object> from, Class<? extends Object> to, boolean converter) {
		return BeanCopier.create(from, to, false);
	}

	public void copy(Object from, Object to) {
		if (from == null && to == null)
			return;
		getInstance().getBeanCopuer(from.getClass(), to.getClass(), false).copy(from, to, null);
	}

	public static void main(String args[]) {
		// System.out.println(getDate(new Date(), -1));
	}
}
