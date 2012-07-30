package com.Rules;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class system_messages {
	private static final String BUNDLE_NAME = "com.Rules.system_messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);

	private system_messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
