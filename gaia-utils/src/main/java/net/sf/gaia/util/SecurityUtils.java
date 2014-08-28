package net.sf.gaia.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Classe utilitária para manipulações de segurança.
 * 
 * @author daniel.joppi
 * 
 */
public abstract class SecurityUtils {

	private static final Log logger = LogFactory.getLog(SecurityUtils.class);

	/**
	 * Método que descifra um senha que tenha sido cifrada pelo método
	 * SecurityUtils.encodeDecodablePassword(String)
	 * 
	 * @param encoded cifrada.
	 * @return senha descifrada.
	 * 
	 * @author daniel.joppi
	 * @since 26/04/2010
	 */
	public static String decodeDecodablePassword(String encoded) {
		byte b3[] = decodeAsBytes(encoded);
		int crc = b3[0] & 0xff;

		// Step 3 - CRC XOR
		int b2[] = new int[b3.length - 1];
		for (int i = 0; i < b2.length; i++) {
			b2[i] = ((b3[i + 1] & 0xff) ^ crc);
		}

		// Step 2 - Shuffle
		int b1[] = new int[b2.length];
		for (int i = 0; i < b1.length; i++) {
			b1[i] = b2[b2.length - i - 1];
		}

		// Step 1 - To XORed byte array
		char c1[] = new char[b1.length / 2];
		int len = -1;
		for (int i = 0; i < c1.length; i++) {
			int l = b1[i * 2];
			int h = b1[i * 2 + 1];
			int c = l | (h << 8);
			c1[i] = (char) (c ^ (0x4242 | i));

			if (c1[i] == 0) {
				len = i;
				break;
			}
		}

		if (len == -1)
			return new String(c1);
		else
			return new String(c1, 0, len);
	}

	/**
	 * Método que cifra um senha sem que ela possa ser desconvertida.
	 * @param password senha não cifrada.
	 * @return senha cifrada
	 * 
	 * @author daniel.joppi
	 * @since 03/04/2010
	 */
	public static String encodePassword(String password) {
		String senhaCrip = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA");
			byte b1[] = password.getBytes();
			for (int i = 0; i < b1.length; i++) {
				b1[i] ^= (0x42 | i);
			}
			md.update(b1);
			byte[] b2 = md.digest();
			senhaCrip = encodeAsString(b2);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e);
		}
		return senhaCrip;
	}

	/**
	 * Método que converte um array de bytes numa sequência de caracteres cifrada.
	 * @param b1 array de bytes.
	 * @return sequência de caracteres cifrada.
	 * 
	 * @author daniel.joppi
	 * @since 03/04/2010
	 */
	public static String encodeAsString(byte[] b1) {
		int size = (int) Math.ceil(b1.length * 8.0 / 6);
		int b2[] = new int[size];
		for (int i = 0; i < size; i++) {
			// 0 - 0:0x11111100|0xFC
			// 1 - 0:0x00000011|0x03 1:0x11110000|0xF0
			// 2 - 1:0x00001111|0x0F 2:0x11000000|0xC0
			// 3 - 2:0x00111111|0x3F
			int t = i % 4;
			int off = (i / 4 * 3);

			int bb0 = b1[off];
			int bb1 = (off + 1 < b1.length) ? b1[off + 1] : 0;
			int bb2 = (off + 2 < b1.length) ? b1[off + 2] : 0;

			if (t == 0)
				b2[i] = (bb0 & 0xFC) >> 2;
			else if (t == 1)
				b2[i] = ((bb0 & 0x03) << 4) | ((bb1 & 0xF0) >> 4);
			else if (t == 2)
				b2[i] = ((bb1 & 0x0F) << 2) | ((bb2 & 0xC0) >> 6);
			else if (t == 3)
				b2[i] = (bb2 & 0x3F);
		}

		// From int to valid char range
		// 046 . -> 058 : +12|12
		// 065 A -> 090 Z +26|38
		// 097 a -> 122 z +26|64
		char c[] = new char[size];
		for (int i = 0; i < size; i++) {
			if (b2[i] < 12)
				c[i] = (char) (b2[i] + 46);
			else if (b2[i] < 38)
				c[i] = (char) (b2[i] + 53);
			else
				c[i] = (char) (b2[i] + 59);
		}
		return new String(c);
	}

	/**
	 * Método que realiza a conversão sa sequência de caracteres num array de bytes.
	 * @param encoded sequeência de caracteres cifrados.
	 * @return array de bytes pre-descifrados.
	 * 
	 * @author daniel.joppi
	 * @since 03/04/2010
	 */
	public static byte[] decodeAsBytes(String encoded) {
		int b1[] = new int[encoded.length()];
		char c[] = encoded.toCharArray();
		for (int i = 0; i < b1.length; i++) {
			if (c[i] >= (59 + 38))
				b1[i] = (c[i] - 59);
			else if (c[i] >= (53 + 12))
				b1[i] = (c[i] - 53);
			else if (c[i] >= 46)
				b1[i] = (c[i] - 46);
			else
				b1[i] = 0;
		}

		int size = (int) Math.ceil(encoded.length() * 6.0 / 8);
		byte b2[] = new byte[size];
		for (int i = 0; i < size; i++) {
			// 0 - 0:0x00111111<<2 | 1:0x00110000>>4
			// 1 - 1:0x00001111<<4 | 2:0x00111100>>2
			// 2 - 2:0x00000011<<6 | 3:0x00111111

			int t = i % 3;
			int off = (i / 3 * 4);

			int bb0 = b1[off];
			int bb1 = (off + 1 < b1.length) ? b1[off + 1] : 0;
			int bb2 = (off + 2 < b1.length) ? b1[off + 2] : 0;
			int bb3 = (off + 3 < b1.length) ? b1[off + 3] : 0;

			if (t == 0)
				b2[i] = (byte) ((bb0 << 2) | ((bb1 & 0x30) >> 4));
			else if (t == 1)
				b2[i] = (byte) (((bb1 & 0x0F) << 4) | ((bb2 & 0x3C) >> 2));
			else if (t == 2)
				b2[i] = (byte) (((bb2 & 0x03) << 6) | bb3);
		}
		if (b2[b2.length - 1] == 0) {
			byte b3[] = new byte[b2.length - 1];
			for (int i = 0; i < b3.length; i++) {
				b3[i] = b2[i];
			}
			b2 = b3;
		}
		return b2;
	}

	/**
	 * Método que cifra um senha que pode ser descifrada futuramente.
	 * 
	 * @param password senha não cifrada.
	 * @return senha cifrada.
	 * 
	 * @author daniel.joppi
	 * @since 26/04/2010
	 */
	public static String encodeDecodablePassword(String password) {
		// Step 1 - To XORed byte array
		char c1[] = password.toCharArray();
		if (c1.length < 13) {
			char c2[] = new char[13];
			for (int i = 0; i < c1.length; i++) {
				c2[i] = c1[i];
			}
			for (int i = c1.length; i < c2.length; i++) {
				c2[i] = 0;
			}
			c1 = c2;
		}
		int b1[] = new int[c1.length * 2];
		for (int i = 0; i < c1.length; i++) {
			int c = c1[i] ^ (0x4242 | i);
			b1[i * 2] = (c & 0xff);
			b1[i * 2 + 1] = (c & 0xff00) >> 8;
		}

		int iCrc = 0;
		// Step 2 - Shuffle
		int b2[] = new int[b1.length];
		for (int i = 0; i < b2.length; i++) {
			b2[i] = b1[b2.length - i - 1];
			iCrc += b2[i];
		}
		int crc = (iCrc & 0xff);

		// Step 3 - CRC XOR
		byte b3[] = new byte[b2.length + 1];
		b3[0] = (byte) (crc & 0xff);
		for (int i = 0; i < b2.length; i++) {
			b3[i + 1] = (byte) ((b2[i] ^ crc) & 0xff);
		}

		return encodeAsString(b3);
	}
}
