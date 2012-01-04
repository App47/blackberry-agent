package org.json;

import java.io.UnsupportedEncodingException;
import java.util.Vector;

public class StringUtils {

	static final String digits = "0123456789ABCDEF"; //$NON-NLS-1$

	public static boolean isNotEmpty(String str) {
		return str != null && str.length() > 0;
	}

	public static boolean isNotEmpty(String[] str) {
		return str != null && str.length > 0;
	}

	public static String replace(String src, String from, String to) {
		if (src == null || src.length() == 0 || from == null
				|| from.length() == 0) {
			return src;
		}

		int lenSrc = src.length();
		int lenFrom = from.length();
		int lenTo = to.length();
		int secIndex = 0;

		Vector matches = new Vector();
		int i = 0;

		for (i = 0; i < lenSrc; i++) {
			if (secIndex < from.length()
					&& src.charAt(i) == from.charAt(secIndex)) {
				if (secIndex == from.length()) { // found one
					secIndex = i - secIndex;
					matches.addElement(new Integer(secIndex));
					secIndex = 0;
					i--;
				} else {
					secIndex++;
				}
			} else if (secIndex == from.length()) { // found one
				secIndex = i - secIndex;
				matches.addElement(new Integer(secIndex));
				secIndex = 0;
				i--;
			} else {
				secIndex = 0;
			}
		}

		if (secIndex > 0 && secIndex == from.length()) {
			secIndex = i - secIndex;
			matches.addElement(new Integer(secIndex));
		}

		if (matches.size() > 0) {
			StringBuffer sb = new StringBuffer(src);
			lenSrc = matches.size();
			int start;
			int offset = 0;

			for (int j = 0; j < lenSrc; j++) {
				start = ((Integer) matches.elementAt(j)).intValue() + offset;
				sb = sb.delete(start, start + lenFrom);
				sb = sb.insert(start, to);

				offset += lenTo - lenFrom;
			}

			return sb.toString();
		}

		return src;
	}

	public static String urlEncode(String str) {
		if (str != null) {
			try {
				String result = StringUtils.encode(str, "UTF8");
				result = StringUtils.replace(result, "+", "%20");
				return result;
			} catch (UnsupportedEncodingException e) {
				return str;
			}
		} else {
			return null;
		}
	}

	/**
	 * Encodes the given string {@code s} in a x-www-form-urlencoded string
	 * using the specified encoding scheme {@code enc}.
	 * <p>
	 * All characters except letters ('a'..'z', 'A'..'Z') and numbers ('0'..'9')
	 * and characters '.', '-', '*', '_' are converted into their hexadecimal
	 * value prepended by '%'. For example: '#' -> %23. In addition, spaces are
	 * substituted by '+'
	 * </p>
	 * 
	 * @param s
	 *            the string to be encoded.
	 * @param enc
	 *            the encoding scheme to be used.
	 * @return the encoded string.
	 * @throws UnsupportedEncodingException
	 *             if the specified encoding scheme is invalid.
	 * @since Android 1.0
	 */
	public static String encode(String s, String enc)
			throws UnsupportedEncodingException {
		if (s == null || enc == null) {
			throw new NullPointerException();
		}
		// check for UnsupportedEncodingException
		"".getBytes(enc); //$NON-NLS-1$

		StringBuffer buf = new StringBuffer();
		int start = -1;
		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);
			if (ch == '@' || (ch >= 'a' && ch <= 'z')
					|| (ch >= 'A' && ch <= 'Z') || (ch >= '0' && ch <= '9')
					|| " .-*_".indexOf(ch) > -1) { //$NON-NLS-1$
				if (start >= 0) {
					convert(s.substring(start, i), buf, enc);
					start = -1;
				}
				if (ch != ' ') {
					buf.append(ch);
				} else {
					buf.append('+');
				}
			} else {
				if (start < 0) {
					start = i;
				}
			}
		}
		if (start >= 0) {
			convert(s.substring(start, s.length()), buf, enc);
		}
		return buf.toString();
	}

	private static void convert(String s, StringBuffer buf, String enc)
			throws UnsupportedEncodingException {
		byte[] bytes = s.getBytes(enc);

		int length = bytes.length;

		for (int j = 0; j < length; j++) {
			buf.append('%');
			buf.append(digits.charAt((bytes[j] & 0xf0) >> 4));
			buf.append(digits.charAt(bytes[j] & 0xf));
		}
	}

	public static String removeUnnecessaryWS(String s) {
		if (s == null) {
			throw new NullPointerException();
		}

		StringBuffer buf = new StringBuffer();
		char ws = 0;

		for (int i = 0; i < s.length(); i++) {
			char ch = s.charAt(i);

			if (ch == ' ' || ch == '\n' || ch == '\t') {
				if (ch != ws) {
					ws = ch;
					buf.append(ch);
				}
			} else {
				ws = 0;
				buf.append(ch);
			}
		}

		return buf.toString();
	}
}
