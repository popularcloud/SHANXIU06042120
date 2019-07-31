package com.lwc.shanxiu.utils;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Set;

/**
 * 
 * @author DavXu
 *
 */
public enum EmojiUtil implements Serializable {
	INSTANCE;

	private HashMap<String, String> emojiMap;

	private HashMap<String, String> getEmojiMap() {

		if (emojiMap == null) {
			emojiMap = new HashMap<String, String>();
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x84 }), "{:1:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x83 }), "{:2:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x80 }), "{:3:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x8a }), "{:4:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0xba, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:5:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0xba }), "{:5:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x89 }), "{:6:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x8d }), "{:7:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x98 }), "{:8:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x9a }), "{:9:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x97 }), "{:10:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x99 }), "{:11:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x9c }), "{:12:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x9d }), "{:13:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x9b }), "{:14:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb3 }), "{:15:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x81 }), "{:16:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x94 }), "{:17:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x8c }), "{:18:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x92 }), "{:19:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x9e }), "{:20:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa3 }), "{:21:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa2 }), "{:22:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x82 }), "{:23:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xad }), "{:24:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xaa }), "{:25:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa5 }), "{:26:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb0 }), "{:27:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x85 }), "{:28:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x93 }), "{:29:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa9 }), "{:30:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xab }), "{:31:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa8 }), "{:32:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb1 }), "{:33:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa0 }), "{:34:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa1 }), "{:35:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa4 }), "{:36:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x96 }), "{:37:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x86 }), "{:38:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x8b }), "{:39:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb7 }), "{:40:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x8e }), "{:41:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb4 }), "{:42:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb5 }), "{:43:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb2 }), "{:44:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x9f }), "{:45:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa6 }), "{:46:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xa7 }), "{:47:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x88 }), "{:48:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xbf }), "{:49:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xae }), "{:50:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xac }), "{:51:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x90 }), "{:52:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x95 }), "{:53:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xaf }), "{:54:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb6 }), "{:55:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x87 }), "{:56:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x8f }), "{:57:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0x91 }), "{:58:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb2 }), "{:59:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb3 }), "{:60:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xae }), "{:61:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb7 }), "{:62:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x82 }), "{:63:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb6 }), "{:64:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa6 }), "{:65:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa7 }), "{:66:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa8 }), "{:67:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa9 }), "{:68:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb4 }), "{:69:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb5 }), "{:70:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb1 }), "{:71:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xbc }), "{:72:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb8 }), "{:73:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xba }), "{:74:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb8 }), "{:75:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xbb }), "{:76:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xbd }), "{:77:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xbc }), "{:78:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x80 }), "{:79:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xbf }), "{:80:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xb9 }), "{:81:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x98, (byte) 0xbe }), "{:82:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb9 }), "{:83:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xba }), "{:84:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x88 }), "{:85:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x89 }), "{:86:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x8a }), "{:87:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x80 }), "{:88:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xbd }), "{:89:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa9 }), "{:90:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa5 }), "{:91:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0xa8 }), "{:92:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x9f }), "{:93:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xab }), "{:94:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa5 }), "{:95:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa2 }), "{:96:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa6 }), "{:97:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa7 }), "{:98:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa4 }), "{:99:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa8 }), "{:100:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x82 }), "{:101:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x80 }), "{:102:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x83 }), "{:103:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x85 }), "{:104:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x84 }), "{:105:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x8d }), "{:106:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x8e }), "{:107:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x8c }), "{:108:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x8a }), "{:109:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x8a }), "{:110:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x8c, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:111:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x8c }), "{:111:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x8b }), "{:112:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x8b }), "{:113:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x90 }), "{:114:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x86 }), "{:115:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x87 }), "{:116:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x89 }), "{:117:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x88 }), "{:118:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x8c }), "{:119:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x8f }), "{:120:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x9d, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:121:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x9d }), "{:121:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x8f }), "{:122:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xaa }), "{:123:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb6 }), "{:124:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x83 }), "{:125:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x83 }), "{:126:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xab }), "{:127:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xaa }), "{:128:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xac }), "{:129:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xad }), "{:130:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x8f }), "{:131:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x91 }), "{:132:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xaf }), "{:133:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x86 }), "{:134:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x85 }), "{:135:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x81 }), "{:136:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x8b }), "{:137:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x86 }), "{:138:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x87 }), "{:139:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x85 }), "{:140:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xb0 }), "{:141:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x8e }), "{:142:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x8d }), "{:143:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x99, (byte) 0x87 }), "{:144:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa9 }), "{:145:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x91 }), "{:146:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x92 }), "{:147:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x9f }), "{:148:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x9e }), "{:149:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa1 }), "{:150:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa0 }), "{:151:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa2 }), "{:152:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x95 }), "{:153:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x94 }), "{:154:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x9a }), "{:155:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x97 }), "{:156:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xbd }), "{:157:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x96 }), "{:158:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x98 }), "{:159:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x99 }), "{:160:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xbc }), "{:161:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x9c }), "{:162:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x9d }), "{:163:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x9b }), "{:164:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0x93 }), "{:165:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x80 }), "{:166:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x82 }), "{:167:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x84 }), "{:168:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x9b }), "{:169:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x99 }), "{:170:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x9c }), "{:171:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x9a }), "{:172:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0xa4, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:173:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0xa4 }), "{:173:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x94 }), "{:174:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x97 }), "{:175:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x93 }), "{:176:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x95 }), "{:177:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x96 }), "{:178:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x9e }), "{:179:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x98 }), "{:180:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x8c }), "{:181:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x8b }), "{:182:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x8d }), "{:183:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x8e }), "{:184:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa4 }), "{:185:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa5 }), "{:186:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xac }), "{:187:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xa3 }), "{:188:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xad }), "{:189:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb6 }), "{:190:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xba }), "{:191:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb1 }), "{:192:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xad }), "{:193:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb9 }), "{:194:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb0 }), "{:195:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb8 }), "{:196:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xaf }), "{:197:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa8 }), "{:198:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xbb }), "{:199:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb7 }), "{:200:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xbd }), "{:201:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xae }), "{:202:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x97 }), "{:203:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb5 }), "{:204:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x92 }), "{:205:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb4 }), "{:206:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x91 }), "{:207:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x98 }), "{:208:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xbc }), "{:209:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa7 }), "{:210:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa6 }), "{:211:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa4 }), "{:212:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa5 }), "{:213:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa3 }), "{:214:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x94 }), "{:215:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x8d }), "{:216:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa2 }), "{:217:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x9b }), "{:218:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x9d }), "{:219:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x9c }), "{:220:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x9e }), "{:221:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x8c }), "{:222:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x99 }), "{:223:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x9a }), "{:224:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa0 }), "{:225:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x9f }), "{:226:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xac }), "{:227:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb3 }), "{:228:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x8b }), "{:229:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x84 }), "{:230:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x8f }), "{:231:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x80 }), "{:232:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x83 }), "{:233:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x85 }), "{:234:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x87 }), "{:235:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x89 }), "{:236:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x8e }), "{:237:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x90 }), "{:238:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x93 }), "{:239:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x95 }), "{:240:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x96 }), "{:241:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x81 }), "{:242:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x82 }), "{:243:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xb2 }), "{:244:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa1 }), "{:245:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x8a }), "{:246:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xab }), "{:247:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xaa }), "{:248:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x86 }), "{:249:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0x88 }), "{:250:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xa9 }), "{:251:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x90, (byte) 0xbe }), "{:252:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x90 }), "{:253:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb8 }), "{:254:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb7 }), "{:255:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x80 }), "{:256:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb9 }), "{:257:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xbb }), "{:258:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xba }), "{:259:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x81 }), "{:260:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x83 }), "{:261:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x82 }), "{:262:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xbf }), "{:263:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xbe }), "{:264:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x84 }), "{:265:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb5 }), "{:266:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb4 }), "{:267:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb2 }), "{:268:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb3 }), "{:269:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb0 }), "{:270:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xb1 }), "{:271:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xbc }), "{:272:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x90 }), "{:273:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x9e }), "{:274:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x9d }), "{:275:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x9a }), "{:276:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x91 }), "{:277:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x92 }), "{:278:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x93 }), "{:279:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x94 }), "{:280:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x95 }), "{:281:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x96 }), "{:282:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x97 }), "{:283:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x98 }), "{:284:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x9c }), "{:285:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x9b }), "{:286:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x99 }), "{:287:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x8d }), "{:288:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x8e }), "{:289:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x8f }), "{:290:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x8b }), "{:291:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x8c }), "{:292:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xa0 }), "{:293:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xad,
			// (byte) 0x90, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:294:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xad,
			// (byte) 0x90 }), "{:294:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x80, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:295:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x80 }), "{:295:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0x85, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:296:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0x85 }), "{:296:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x81, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:297:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x81 }), "{:297:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xa1, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:298:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xa1 }), "{:298:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x94, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:299:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x94 }), "{:299:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x84, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:300:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x84 }), "{:300:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0x84, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:301:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0x84 }), "{:301:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x80 }), "{:302:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x81 }), "{:303:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x88 }), "{:304:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x8a }), "{:305:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x8d }), "{:306:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x9d }), "{:307:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x8e }), "{:308:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x92 }), "{:309:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x93 }), "{:310:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x8f }), "{:311:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x86 }), "{:312:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x87 }), "{:313:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x90 }), "{:314:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x91 }), "{:315:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x83 }), "{:316:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xbb }), "{:317:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x85 }), "{:318:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x84 }), "{:319:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x81 }), "{:320:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x8b }), "{:321:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x89 }), "{:322:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x8a }), "{:323:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x88 }), "{:324:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x8c }), "{:325:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xae }), "{:326:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa5 }), "{:327:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb7 }), "{:328:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb9 }), "{:329:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xbc }), "{:330:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xbf }), "{:331:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x80 }), "{:332:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xbd }), "{:333:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xbe }), "{:334:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xbb }), "{:335:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb1 }), "{:336:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x8e, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:337:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x8e }), "{:337:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x9e }), "{:338:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x9f }), "{:339:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa0 }), "{:340:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa1 }), "{:341:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xba }), "{:342:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xbb }), "{:343:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x8a }), "{:344:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x89 }), "{:345:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x88 }), "{:346:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x87 }), "{:347:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x94 }), "{:348:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x95 }), "{:349:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa2 }), "{:350:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa3 }), "{:351:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8f,
			// (byte) 0xb3 }), "{:352:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8c,
			// (byte) 0x9b, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:353:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8c,
			// (byte) 0x9b }), "{:353:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8f,
			// (byte) 0xb0 }), "{:354:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8c,
			// (byte) 0x9a, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:355:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8c,
			// (byte) 0x9a }), "{:355:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x93 }), "{:356:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x92 }), "{:357:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x8f }), "{:358:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x90 }), "{:359:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x91 }), "{:360:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x8e }), "{:361:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa1 }), "{:362:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa6 }), "{:363:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x86 }), "{:364:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x85 }), "{:365:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x8c }), "{:366:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x8b }), "{:367:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x8d }), "{:368:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9b, (byte) 0x81 }), "{:369:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9b, (byte) 0x80 }), "{:370:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xbf }), "{:371:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xbd }), "{:372:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa7 }), "{:373:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa9 }), "{:374:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa8 }), "{:375:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xaa }), "{:376:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xac }), "{:377:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa3 }), "{:378:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xab }), "{:379:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xaa }), "{:380:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x8a }), "{:381:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x89 }), "{:382:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb0 }), "{:383:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb4 }), "{:384:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb5 }), "{:385:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb7 }), "{:386:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb6 }), "{:387:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb3 }), "{:388:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb8 }), "{:389:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb2 }), "{:390:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa7 }), "{:391:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa5 }), "{:392:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa4 }), "{:393:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x89, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:394:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x89 }), "{:394:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa9 }), "{:395:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa8 }), "{:396:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xaf }), "{:397:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xab }), "{:398:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xaa }), "{:399:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xac }), "{:400:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xad }), "{:401:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xae }), "{:402:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xa6 }), "{:403:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x9d }), "{:404:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x84 }), "{:405:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x83 }), "{:406:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x91 }), "{:407:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x8a }), "{:408:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x88 }), "{:409:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x89 }), "{:410:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x9c }), "{:411:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x8b }), "{:412:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x85 }), "{:413:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x86 }), "{:414:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x87 }), "{:415:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x81 }), "{:416:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x82 }), "{:417:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x82, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:418:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x82 }), "{:418:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x8c }), "{:419:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x8e }), "{:420:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x92, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:421:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x92 }), "{:421:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x8f, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:422:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x8f }), "{:422:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x8f }), "{:423:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x90 }), "{:424:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x95 }), "{:425:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x97 }), "{:426:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x98 }), "{:427:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x99 }), "{:428:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x93 }), "{:429:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x94 }), "{:430:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x92 }), "{:431:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x9a }), "{:432:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x96 }), "{:433:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x96 }), "{:434:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x9b }), "{:435:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xac }), "{:436:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xad }), "{:437:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb0 }), "{:438:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa8 }), "{:439:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xac }), "{:440:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa4 }), "{:441:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa7 }), "{:442:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xbc }), "{:443:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb5 }), "{:444:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb6 }), "{:445:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb9 }), "{:446:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xbb }), "{:447:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xba }), "{:448:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb7 }), "{:449:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb8 }), "{:450:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x91, (byte) 0xbe }), "{:451:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xae }), "{:452:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x83, (byte) 0x8f }), "{:453:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb4 }), "{:454:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x80, (byte) 0x84, (byte) 0xef,
			// (byte) 0xb8, (byte) 0x8f }), "{:455:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x80, (byte) 0x84 }), "{:455:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb2 }), "{:456:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xaf }), "{:457:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x88 }), "{:458:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x80 }), "{:459:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xbd, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:460:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xbd }), "{:460:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xbe, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:461:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xbe }), "{:461:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xbe }), "{:462:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb1 }), "{:463:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x89 }), "{:464:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb3 }), "{:465:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xb3, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:466:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xb3 }), "{:466:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb5 }), "{:467:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb4 }), "{:468:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x81 }), "{:469:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x87 }), "{:470:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x86 }), "{:471:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xbf }), "{:472:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x82 }), "{:473:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x8a }), "{:474:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0x84 }), "{:475:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa3 }), "{:476:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x95, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:477:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x95 }), "{:477:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb5 }), "{:478:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb6 }), "{:479:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xbc }), "{:480:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xba }), "{:481:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xbb }), "{:482:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb8 }), "{:483:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb9 }), "{:484:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb7 }), "{:485:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb4 }), "{:486:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x95 }), "{:487:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x94 }), "{:488:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x9f }), "{:489:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x97 }), "{:490:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x96 }), "{:491:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x9d }), "{:492:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x9b }), "{:493:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa4 }), "{:494:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb1 }), "{:495:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa3 }), "{:496:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa5 }), "{:497:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x99 }), "{:498:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x98 }), "{:499:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x9a }), "{:500:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x9c }), "{:501:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb2 }), "{:502:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa2 }), "{:503:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa1 }), "{:504:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb3 }), "{:505:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x9e }), "{:506:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa9 }), "{:507:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xae }), "{:508:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa6 }), "{:509:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa8 }), "{:510:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa7 }), "{:511:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0x82 }), "{:512:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xb0 }), "{:513:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xaa }), "{:514:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xab }), "{:515:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xac }), "{:516:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xad }), "{:517:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xaf }), "{:518:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x8e }), "{:519:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x8f }), "{:520:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x8a }), "{:521:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x8b }), "{:522:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x92 }), "{:523:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x87 }), "{:524:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x89 }), "{:525:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x93 }), "{:526:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x91 }), "{:527:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x88 }), "{:528:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x8c }), "{:529:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x90 }), "{:530:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x8d }), "{:531:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0xa0 }), "{:532:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x86 }), "{:533:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8d, (byte) 0x85 }), "{:534:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0xbd }), "{:535:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa0 }), "{:536:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa1 }), "{:537:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xab }), "{:538:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa2 }), "{:539:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa3 }), "{:540:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa5 }), "{:541:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa6 }), "{:542:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xaa }), "{:543:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa9 }), "{:544:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa8 }), "{:545:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x92 }), "{:546:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xaa, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:547:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xaa }), "{:547:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xac }), "{:548:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa4 }), "{:549:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x87 }), "{:550:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x86 }), "{:551:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xaf }), "{:552:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xb0 }), "{:553:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xba, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:554:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xba }), "{:554:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xad }), "{:555:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x97, (byte) 0xbc }), "{:556:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x97, (byte) 0xbe }), "{:557:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x97, (byte) 0xbb }), "{:558:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x84 }), "{:559:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x85 }), "{:560:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x83 }), "{:561:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x97, (byte) 0xbd }), "{:562:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8c, (byte) 0x89 }), "{:563:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa0 }), "{:564:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa1 }), "{:565:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xb2, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:566:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xb2 }), "{:566:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa2 }), "{:567:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa2 }), "{:568:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xb5, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:569:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xb5 }), "{:569:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa4 }), "{:570:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa3 }), "{:571:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0x93, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:572:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0x93 }), "{:572:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x80 }), "{:573:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x88, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:574:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x88 }), "{:574:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xba }), "{:575:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x81 }), "{:576:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x82 }), "{:577:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x8a }), "{:578:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x89 }), "{:579:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x9e }), "{:580:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x86 }), "{:581:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x84 }), "{:582:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x85 }), "{:583:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x88 }), "{:584:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x87 }), "{:585:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x9d }), "{:586:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x8b }), "{:587:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x83 }), "{:588:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x8e }), "{:589:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x8c }), "{:590:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x8d }), "{:591:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x99 }), "{:592:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x98 }), "{:593:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x97 }), "{:594:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x95 }), "{:595:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x96 }), "{:596:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x9b }), "{:597:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x9a }), "{:598:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa8 }), "{:599:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x93 }), "{:600:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x94 }), "{:601:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x92 }), "{:602:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x91 }), "{:603:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x90 }), "{:604:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb2 }), "{:605:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa1 }), "{:606:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x9f }), "{:607:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa0 }), "{:608:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x9c }), "{:609:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x88 }), "{:610:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0x8f }), "{:611:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xab }), "{:612:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa6 }), "{:613:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa5 }), "{:614:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xa0, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:615:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xa0 }), "{:615:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa7 }), "{:616:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb0 }), "{:617:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xbd, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:618:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0xbd }), "{:618:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xae }), "{:619:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xb0 }), "{:620:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa8, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:621:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa8 }), "{:621:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x97, (byte) 0xbf }), "{:622:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xaa }), "{:623:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xad }), "{:624:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0x8d }), "{:625:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xa9 }), "{:626:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xaf, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xb5 }), "{:627:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xb0, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xb7 }), "{:628:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xa9, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xaa }), "{:629:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xa8, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xb3 }), "{:630:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xba, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xb8 }), "{:631:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xab, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xb7 }), "{:632:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xaa, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xb8 }), "{:633:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xae, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xb9 }), "{:634:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xb7, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xba }), "{:635:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x87, (byte) 0xac, (byte) 0xf0,
			// (byte) 0x9f, (byte) 0x87, (byte) 0xa7 }), "{:636:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x31, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:637:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x32, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:638:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x33, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:639:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x34, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:640:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x35, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:641:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x36, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:642:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x37, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:643:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x38, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:644:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x39, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:645:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x30, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:646:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x9f }), "{:647:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa2 }), "{:648:}");
			// emojiMap.put(new String(new byte[] { (byte) 0x23, (byte) 0xe2,
			// (byte) 0x83, (byte) 0xa3 }), "{:649:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa3 }), "{:650:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x86, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:651:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x86 }), "{:651:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x87, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:652:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x87 }), "{:652:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x85, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:653:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x85 }), "{:653:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9e,
			// (byte) 0xa1, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:654:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9e,
			// (byte) 0xa1 }), "{:654:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa0 }), "{:655:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa1 }), "{:656:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xa4 }), "{:657:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x97, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:658:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x97 }), "{:658:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x96, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:659:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x96 }), "{:659:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x98, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:660:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x98 }), "{:660:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x99, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:661:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x99 }), "{:661:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x94, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:662:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x94 }), "{:662:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x95, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:663:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0x95 }), "{:663:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x84 }), "{:664:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0x80, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:665:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0x80 }), "{:665:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x96,
			// (byte) 0xb6, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:666:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x96,
			// (byte) 0xb6 }), "{:666:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xbc }), "{:667:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xbd }), "{:668:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0xa9, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:669:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0xa9 }), "{:669:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0xaa, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:670:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x86,
			// (byte) 0xaa }), "{:670:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x84,
			// (byte) 0xb9, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:671:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x84,
			// (byte) 0xb9 }), "{:671:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8f,
			// (byte) 0xaa }), "{:672:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8f,
			// (byte) 0xa9 }), "{:673:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8f,
			// (byte) 0xab }), "{:674:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x8f,
			// (byte) 0xac }), "{:675:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xa4,
			// (byte) 0xb5, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:676:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xa4,
			// (byte) 0xb5 }), "{:676:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xa4,
			// (byte) 0xb4, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:677:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xa4,
			// (byte) 0xb4 }), "{:677:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x97 }), "{:678:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x80 }), "{:679:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x81 }), "{:680:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x82 }), "{:681:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x95 }), "{:682:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x99 }), "{:683:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x92 }), "{:684:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x93 }), "{:685:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x96 }), "{:686:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb6 }), "{:687:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8e, (byte) 0xa6 }), "{:688:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0x81 }), "{:689:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xaf, (byte) 0xef,
			// (byte) 0xb8, (byte) 0x8f }), "{:690:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xaf }), "{:690:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb3 }), "{:691:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb5 }), "{:692:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb4 }), "{:693:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb2 }), "{:694:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x89, (byte) 0x90 }), "{:695:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb9 }), "{:696:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xba }), "{:697:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb6 }), "{:698:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0x9a, (byte) 0xef,
			// (byte) 0xb8, (byte) 0x8f }), "{:699:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0x9a }), "{:699:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xbb }), "{:700:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb9 }), "{:701:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xba }), "{:702:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xbc }), "{:703:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xbe }), "{:704:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb0 }), "{:705:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xae }), "{:706:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x85, (byte) 0xbf, (byte) 0xef,
			// (byte) 0xb8, (byte) 0x8f }), "{:707:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x85, (byte) 0xbf }), "{:707:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xbf, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:708:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xbf }), "{:708:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xad }), "{:709:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb7 }), "{:710:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0xb8 }), "{:711:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x88, (byte) 0x82 }), "{:712:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x93,
			// (byte) 0x82, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:713:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x93,
			// (byte) 0x82 }), "{:713:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9b, (byte) 0x82 }), "{:714:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9b, (byte) 0x84 }), "{:715:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9b, (byte) 0x85 }), "{:716:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9b, (byte) 0x83 }), "{:717:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x89, (byte) 0x91 }), "{:718:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe3, (byte) 0x8a,
			// (byte) 0x99, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:719:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe3, (byte) 0x8a,
			// (byte) 0x99 }), "{:719:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe3, (byte) 0x8a,
			// (byte) 0x97, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:720:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe3, (byte) 0x8a,
			// (byte) 0x97 }), "{:720:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x91 }), "{:721:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x98 }), "{:722:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x94 }), "{:723:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xab }), "{:724:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x9e }), "{:725:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb5 }), "{:726:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xaf }), "{:727:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb1 }), "{:728:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb3 }), "{:729:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb7 }), "{:730:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x9a, (byte) 0xb8 }), "{:731:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0x94, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:732:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0x94 }), "{:732:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0xb3, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:733:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0xb3 }), "{:733:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x87, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:734:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x87 }), "{:734:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x8e }), "{:735:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x85 }), "{:736:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0xb4, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:737:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0xb4 }), "{:737:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0x9f }), "{:738:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x9a }), "{:739:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb3 }), "{:740:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x93, (byte) 0xb4 }), "{:741:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x85, (byte) 0xb0 }), "{:742:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x85, (byte) 0xb1 }), "{:743:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x86, (byte) 0x8e }), "{:744:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x85, (byte) 0xbe }), "{:745:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xa0 }), "{:746:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9e,
			// (byte) 0xbf }), "{:747:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xbb, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:748:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xbb }), "{:748:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x88, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:749:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x88 }), "{:749:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x89, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:750:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x89 }), "{:750:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8a, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:751:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8a }), "{:751:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8b, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:752:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8b }), "{:752:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8c, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:753:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8c }), "{:753:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8d, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:754:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8d }), "{:754:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8e, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:755:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8e }), "{:755:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8f, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:756:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x8f }), "{:756:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x90, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:757:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x90 }), "{:757:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x91, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:758:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x91 }), "{:758:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x92, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:759:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x92 }), "{:759:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x93, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:760:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0x93 }), "{:760:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9b,
			// (byte) 0x8e }), "{:761:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xaf }), "{:762:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x8f, (byte) 0xa7 }), "{:763:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb9 }), "{:764:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb2 }), "{:765:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xb1 }), "{:766:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xc2, (byte) 0xa9 }),
			// "{:767:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xc2, (byte) 0xae }),
			// "{:768:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x84,
			// (byte) 0xa2 }), "{:769:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x8c }), "{:770:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x80,
			// (byte) 0xbc, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:771:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x80,
			// (byte) 0xbc }), "{:771:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x81,
			// (byte) 0x89, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:772:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x81,
			// (byte) 0x89 }), "{:772:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x97, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:773:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x97 }), "{:773:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x93 }), "{:774:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x95 }), "{:775:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9d,
			// (byte) 0x94 }), "{:776:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xad,
			// (byte) 0x95, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:777:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xad,
			// (byte) 0x95 }), "{:777:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x9d }), "{:778:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x9a }), "{:779:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x99 }), "{:780:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x9b }), "{:781:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x9c }), "{:782:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x83 }), "{:783:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x9b }), "{:784:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa7 }), "{:785:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x90 }), "{:786:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x9c }), "{:787:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x91 }), "{:788:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x9d }), "{:789:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x92 }), "{:790:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x9e }), "{:791:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x93 }), "{:792:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x9f }), "{:793:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x94 }), "{:794:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa0 }), "{:795:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x95 }), "{:796:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x96 }), "{:797:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x97 }), "{:798:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x98 }), "{:799:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x99 }), "{:800:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0x9a }), "{:801:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa1 }), "{:802:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa2 }), "{:803:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa3 }), "{:804:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa4 }), "{:805:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa5 }), "{:806:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x95, (byte) 0xa6 }), "{:807:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x96, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:808:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x96 }), "{:808:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9e,
			// (byte) 0x95 }), "{:809:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9e,
			// (byte) 0x96 }), "{:810:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9e,
			// (byte) 0x97 }), "{:811:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa0, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:812:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa0 }), "{:812:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa5, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:813:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa5 }), "{:813:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa3, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:814:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa3 }), "{:814:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa6, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:815:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x99,
			// (byte) 0xa6 }), "{:815:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xae }), "{:816:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x92, (byte) 0xaf }), "{:817:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x94, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:818:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9c,
			// (byte) 0x94 }), "{:818:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x91, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:819:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x98,
			// (byte) 0x91 }), "{:819:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x98 }), "{:820:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0x97 }), "{:821:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9e,
			// (byte) 0xb0 }), "{:822:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe3, (byte) 0x80,
			// (byte) 0xb0 }), "{:823:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe3, (byte) 0x80,
			// (byte) 0xbd, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:824:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe3, (byte) 0x80,
			// (byte) 0xbd }), "{:824:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb1 }), "{:825:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbc, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:826:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbc }), "{:826:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbb, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:827:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbb }), "{:827:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbe, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:828:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbe }), "{:828:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbd, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:829:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x97,
			// (byte) 0xbd }), "{:829:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x96,
			// (byte) 0xaa, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:830:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x96,
			// (byte) 0xaa }), "{:830:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x96,
			// (byte) 0xab, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:831:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x96,
			// (byte) 0xab }), "{:831:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xba }), "{:832:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb2 }), "{:833:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb3 }), "{:834:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xab, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:835:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xab }), "{:835:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xaa, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:836:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0x9a,
			// (byte) 0xaa }), "{:836:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb4 }), "{:837:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb5 }), "{:838:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xbb }), "{:839:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x9c, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:840:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x9c }), "{:840:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x9b, (byte) 0xef, (byte) 0xb8,
			// (byte) 0x8f }), "{:841:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xe2, (byte) 0xac,
			// (byte) 0x9b }), "{:841:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb6 }), "{:842:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb7 }), "{:843:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb8 }), "{:844:}");
			// emojiMap.put(new String(new byte[] { (byte) 0xf0, (byte) 0x9f,
			// (byte) 0x94, (byte) 0xb9 }), "{:845:}");

			// emojiMap.put("♊️", "{:751:}");
			// emojiMap.put("✌️", "{:111:}");
			// emojiMap.put("♋️", "{:752:}");
			// emojiMap.put("🏃", "{:125:}");
			// emojiMap.put("✏️", "{:422:}");
			// emojiMap.put("🏂", "{:473:}");
			// emojiMap.put("🏁", "{:469:}");
			// emojiMap.put("🏀", "{:459:}");
			// emojiMap.put("🎿", "{:472:}");
			// emojiMap.put("🎾", "{:462:}");
			// emojiMap.put("🎽", "{:157:}");
			// emojiMap.put("🎼", "{:443:}");
			// emojiMap.put("🏊", "{:474:}");
			// emojiMap.put("♌️", "{:753:}");
			// emojiMap.put("🏉", "{:464:}");
			// emojiMap.put("🏈", "{:458:}");
			// emojiMap.put("🏇", "{:470:}");
			// emojiMap.put("🏆", "{:471:}");
			// emojiMap.put("⚓️", "{:572:}");
			// emojiMap.put("🇫🇷", "{:632:}");
			// emojiMap.put("🏄", "{:475:}");
			// emojiMap.put("☕️", "{:477:}");
			// emojiMap.put("🏡", "{:537:}");
			// emojiMap.put("🏠", "{:536:}");
			// emojiMap.put("🏣", "{:540:}");
			// emojiMap.put("🏢", "{:539:}");
			// emojiMap.put("🏩", "{:544:}");
			// emojiMap.put("♍️", "{:754:}");
			// emojiMap.put("🏨", "{:545:}");
			// emojiMap.put("🏫", "{:538:}");
			// emojiMap.put("🏪", "{:543:}");
			// emojiMap.put("🏥", "{:541:}");
			// emojiMap.put("🏤", "{:549:}");
			// emojiMap.put("🏧", "{:763:}");
			// emojiMap.put("🏦", "{:542:}");
			// emojiMap.put("🏰", "{:553:}");
			// emojiMap.put("🏬", "{:548:}");
			// emojiMap.put("🏭", "{:555:}");
			// emojiMap.put("🏮", "{:619:}");
			// emojiMap.put("🏯", "{:552:}");
			// emojiMap.put("♎️", "{:755:}");
			// emojiMap.put("✉️", "{:394:}");
			// emojiMap.put("©", "{:767:}");
			// emojiMap.put("®", "{:768:}");
			// emojiMap.put("♏️", "{:756:}");
			// emojiMap.put("✈️", "{:574:}");
			// emojiMap.put("☔️", "{:299:}");
			// emojiMap.put("♐️", "{:757:}");
			// emojiMap.put("☑️", "{:819:}");
			// emojiMap.put("♑️", "{:758:}");
			// emojiMap.put("♒️", "{:759:}");
			// emojiMap.put("🌌", "{:292:}");
			// emojiMap.put("🌍", "{:288:}");
			// emojiMap.put("🌎", "{:289:}");
			// emojiMap.put("🌏", "{:290:}");
			// emojiMap.put("🌐", "{:273:}");
			// emojiMap.put("🌑", "{:277:}");
			// emojiMap.put("🌒", "{:278:}");
			// emojiMap.put("🌓", "{:279:}");
			// emojiMap.put("🌔", "{:280:}");
			// emojiMap.put("🌕", "{:281:}");
			// emojiMap.put("🌖", "{:282:}");
			// emojiMap.put("🌗", "{:283:}");
			// emojiMap.put("🌘", "{:284:}");
			// emojiMap.put("🌙", "{:287:}");
			// emojiMap.put("🌚", "{:276:}");
			// emojiMap.put("✖️", "{:808:}");
			// emojiMap.put("⁉️", "{:772:}");
			// emojiMap.put("🌛", "{:286:}");
			// emojiMap.put("🌁", "{:303:}");
			// emojiMap.put("🌀", "{:302:}");
			// emojiMap.put("🌃", "{:561:}");
			// emojiMap.put("🌂", "{:167:}");
			// emojiMap.put("🌅", "{:560:}");
			// emojiMap.put("🌄", "{:559:}");
			// emojiMap.put("🌇", "{:550:}");
			// emojiMap.put("🌆", "{:551:}");
			// emojiMap.put("🌉", "{:563:}");
			// emojiMap.put("⬜️", "{:840:}");
			// emojiMap.put("🌈", "{:304:}");
			// emojiMap.put("🌋", "{:291:}");
			// emojiMap.put("🌊", "{:305:}");
			// emojiMap.put("🌲", "{:268:}");
			// emojiMap.put("🌳", "{:269:}");
			// emojiMap.put("🌰", "{:270:}");
			// emojiMap.put("🌱", "{:271:}");
			// emojiMap.put("🌷", "{:255:}");
			// emojiMap.put("🌴", "{:267:}");
			// emojiMap.put("🌵", "{:266:}");
			// emojiMap.put("🌺", "{:259:}");
			// emojiMap.put("🌻", "{:258:}");
			// emojiMap.put("🌸", "{:254:}");
			// emojiMap.put("🌹", "{:257:}");
			// emojiMap.put("🌟", "{:93:}");
			// emojiMap.put("🌞", "{:274:}");
			// emojiMap.put("🌝", "{:275:}");
			// emojiMap.put("🌜", "{:285:}");
			// emojiMap.put("㊙️", "{:719:}");
			// emojiMap.put("🌠", "{:293:}");
			// emojiMap.put("⬛️", "{:841:}");
			// emojiMap.put("🚱", "{:728:}");
			// emojiMap.put("☝️", "{:121:}");
			// emojiMap.put("🚲", "{:605:}");
			// emojiMap.put("🚳", "{:729:}");
			// emojiMap.put("🚴", "{:468:}");
			// emojiMap.put("🚭", "{:709:}");
			// emojiMap.put("🚮", "{:706:}");
			// emojiMap.put("🅿️", "{:707:}");
			// emojiMap.put("🚯", "{:727:}");
			// emojiMap.put("🚰", "{:705:}");
			// emojiMap.put("🚹", "{:701:}");
			// emojiMap.put("🚺", "{:702:}");
			// emojiMap.put("🚻", "{:700:}");
			// emojiMap.put("🚼", "{:703:}");
			// emojiMap.put("🚵", "{:467:}");
			// emojiMap.put("🚶", "{:124:}");
			// emojiMap.put("🚷", "{:730:}");
			// emojiMap.put("🚸", "{:731:}");
			// emojiMap.put("🚢", "{:568:}");
			// emojiMap.put("🚡", "{:606:}");
			// emojiMap.put("🚤", "{:570:}");
			// emojiMap.put("🚣", "{:571:}");
			// emojiMap.put("🚞", "{:580:}");
			// emojiMap.put("🚝", "{:586:}");
			// emojiMap.put("🚠", "{:608:}");
			// emojiMap.put("🚟", "{:607:}");
			// emojiMap.put("🚪", "{:376:}");
			// emojiMap.put("🚩", "{:626:}");
			// emojiMap.put("🚬", "{:377:}");
			// emojiMap.put("🚫", "{:724:}");
			// emojiMap.put("🚦", "{:613:}");
			// emojiMap.put("🚥", "{:614:}");
			// emojiMap.put("🚨", "{:599:}");
			// emojiMap.put("🚧", "{:616:}");
			// emojiMap.put("⌛", "{:353:}");
			// emojiMap.put("⌚", "{:355:}");
			// emojiMap.put("🛄", "{:715:}");
			// emojiMap.put("🛃", "{:717:}");
			// emojiMap.put("🛂", "{:714:}");
			// emojiMap.put("🛁", "{:369:}");
			// emojiMap.put("🛀", "{:370:}");
			// emojiMap.put("🇩🇪", "{:629:}");
			// emojiMap.put("🚿", "{:371:}");
			// emojiMap.put("🚾", "{:704:}");
			// emojiMap.put("🚽", "{:372:}");
			// emojiMap.put("🛅", "{:716:}");
			// emojiMap.put("✒️", "{:421:}");
			// emojiMap.put("🎍", "{:306:}");
			// emojiMap.put("⏳", "{:352:}");
			// emojiMap.put("🎌", "{:325:}");
			// emojiMap.put("⏰", "{:354:}");
			// emojiMap.put("🎏", "{:311:}");
			// emojiMap.put("🎎", "{:308:}");
			// emojiMap.put("🎑", "{:315:}");
			// emojiMap.put("🎐", "{:314:}");
			// emojiMap.put("🎓", "{:310:}");
			// emojiMap.put("🎒", "{:309:}");
			// emojiMap.put("🎄", "{:319:}");
			// emojiMap.put("⏫", "{:674:}");
			// emojiMap.put("🎅", "{:318:}");
			// emojiMap.put("⏪", "{:672:}");
			// emojiMap.put("🎆", "{:312:}");
			// emojiMap.put("⏩", "{:673:}");
			// emojiMap.put("🎇", "{:313:}");
			// emojiMap.put("🎈", "{:324:}");
			// emojiMap.put("🎉", "{:322:}");
			// emojiMap.put("🎊", "{:323:}");
			// emojiMap.put("🎋", "{:321:}");
			// emojiMap.put("⏬", "{:675:}");
			// emojiMap.put("🍼", "{:480:}");
			// emojiMap.put("ℹ️", "{:671:}");
			// emojiMap.put("🎀", "{:166:}");
			// emojiMap.put("⛔️", "{:732:}");
			// emojiMap.put("🎁", "{:320:}");
			// emojiMap.put("🎂", "{:512:}");
			// emojiMap.put("🎃", "{:316:}");
			// emojiMap.put("🎷", "{:449:}");
			// emojiMap.put("🎶", "{:445:}");
			// emojiMap.put("🎵", "{:444:}");
			// emojiMap.put("🎴", "{:454:}");
			// emojiMap.put("🎻", "{:447:}");
			// emojiMap.put("🎺", "{:448:}");
			// emojiMap.put("🎹", "{:446:}");
			// emojiMap.put("🎸", "{:450:}");
			// emojiMap.put("🎯", "{:457:}");
			// emojiMap.put("🎮", "{:452:}");
			// emojiMap.put("🎭", "{:624:}");
			// emojiMap.put("🎬", "{:440:}");
			// emojiMap.put("🎳", "{:465:}");
			// emojiMap.put("🎲", "{:456:}");
			// emojiMap.put("🎱", "{:463:}");
			// emojiMap.put("🎰", "{:620:}");
			// emojiMap.put("🎦", "{:688:}");
			// emojiMap.put("⭐", "{:294:}");
			// emojiMap.put("🎧", "{:442:}");
			// emojiMap.put("🎤", "{:441:}");
			// emojiMap.put("🎥", "{:327:}");
			// emojiMap.put("⭕", "{:777:}");
			// emojiMap.put("🎪", "{:623:}");
			// emojiMap.put("🎫", "{:612:}");
			// emojiMap.put("🎨", "{:439:}");
			// emojiMap.put("🎩", "{:145:}");
			// emojiMap.put("🎢", "{:567:}");
			// emojiMap.put("🎣", "{:476:}");
			// emojiMap.put("🎠", "{:564:}");
			// emojiMap.put("🎡", "{:565:}");
			// emojiMap.put("✔️", "{:818:}");
			// emojiMap.put("🍙", "{:498:}");
			// emojiMap.put("🍘", "{:499:}");
			// emojiMap.put("🍛", "{:493:}");
			// emojiMap.put("🍚", "{:500:}");
			// emojiMap.put("🍕", "{:487:}");
			// emojiMap.put("🍔", "{:488:}");
			// emojiMap.put("🍗", "{:490:}");
			// emojiMap.put("🍖", "{:491:}");
			// emojiMap.put("🍑", "{:527:}");
			// emojiMap.put("🍐", "{:530:}");
			// emojiMap.put("🍓", "{:526:}");
			// emojiMap.put("🍒", "{:523:}");
			// emojiMap.put("🍍", "{:531:}");
			// emojiMap.put("🍌", "{:529:}");
			// emojiMap.put("🍏", "{:520:}");
			// emojiMap.put("🍎", "{:519:}");
			// emojiMap.put("🍈", "{:528:}");
			// emojiMap.put("🍉", "{:525:}");
			// emojiMap.put("🍊", "{:521:}");
			// emojiMap.put("🍋", "{:522:}");
			// emojiMap.put("🍄", "{:265:}");
			// emojiMap.put("♈️", "{:749:}");
			// emojiMap.put("🍅", "{:534:}");
			// emojiMap.put("㊙", "{:719:}");
			// emojiMap.put("🍆", "{:533:}");
			// emojiMap.put("🍇", "{:524:}");
			// emojiMap.put("㊗", "{:720:}");
			// emojiMap.put("🍀", "{:256:}");
			// emojiMap.put("🍁", "{:260:}");
			// emojiMap.put("🍂", "{:262:}");
			// emojiMap.put("🍃", "{:261:}");
			// emojiMap.put("🌼", "{:272:}");
			// emojiMap.put("🌽", "{:535:}");
			// emojiMap.put("🌾", "{:264:}");
			// emojiMap.put("🌿", "{:263:}");
			// emojiMap.put("🍻", "{:482:}");
			// emojiMap.put("🍺", "{:481:}");
			// emojiMap.put("⬅", "{:653:}");
			// emojiMap.put("🍹", "{:484:}");
			// emojiMap.put("⬆", "{:651:}");
			// emojiMap.put("🍸", "{:483:}");
			// emojiMap.put("⬇", "{:652:}");
			// emojiMap.put("🍷", "{:485:}");
			// emojiMap.put("🍶", "{:479:}");
			// emojiMap.put("🍵", "{:478:}");
			// emojiMap.put("🍴", "{:486:}");
			// emojiMap.put("🍳", "{:505:}");
			// emojiMap.put("🍲", "{:502:}");
			// emojiMap.put("🍱", "{:495:}");
			// emojiMap.put("🍰", "{:513:}");
			// emojiMap.put("🍯", "{:518:}");
			// emojiMap.put("🍮", "{:508:}");
			// emojiMap.put("🍭", "{:517:}");
			// emojiMap.put("🍬", "{:516:}");
			// emojiMap.put("🍪", "{:514:}");
			// emojiMap.put("🍫", "{:515:}");
			// emojiMap.put("🍨", "{:510:}");
			// emojiMap.put("🍩", "{:507:}");
			// emojiMap.put("🍦", "{:509:}");
			// emojiMap.put("🍧", "{:511:}");
			// emojiMap.put("♉️", "{:750:}");
			// emojiMap.put("🍤", "{:494:}");
			// emojiMap.put("㊗️", "{:720:}");
			// emojiMap.put("🍥", "{:497:}");
			// emojiMap.put("🍢", "{:503:}");
			// emojiMap.put("⬜", "{:840:}");
			// emojiMap.put("🍣", "{:496:}");
			// emojiMap.put("🍠", "{:532:}");
			// emojiMap.put("🍡", "{:504:}");
			// emojiMap.put("🍞", "{:506:}");
			// emojiMap.put("🍟", "{:489:}");
			// emojiMap.put("⬛", "{:841:}");
			// emojiMap.put("🍜", "{:501:}");
			// emojiMap.put("🍝", "{:492:}");
			// emojiMap.put("😂", "{:23:}");
			// emojiMap.put("😁", "{:16:}");
			// emojiMap.put("😄", "{:1:}");
			// emojiMap.put("😃", "{:2:}");
			// emojiMap.put("🗾", "{:557:}");
			// emojiMap.put("🗽", "{:562:}");
			// emojiMap.put("😀", "{:3:}");
			// emojiMap.put("🗿", "{:622:}");
			// emojiMap.put("😊", "{:4:}");
			// emojiMap.put("😉", "{:6:}");
			// emojiMap.put("😌", "{:18:}");
			// emojiMap.put("😋", "{:39:}");
			// emojiMap.put("😆", "{:38:}");
			// emojiMap.put("😅", "{:28:}");
			// emojiMap.put("😈", "{:48:}");
			// emojiMap.put("⁉", "{:772:}");
			// emojiMap.put("😇", "{:56:}");
			// emojiMap.put("😑", "{:58:}");
			// emojiMap.put("😒", "{:19:}");
			// emojiMap.put("😓", "{:29:}");
			// emojiMap.put("🈲", "{:694:}");
			// emojiMap.put("😔", "{:17:}");
			// emojiMap.put("🈳", "{:691:}");
			// emojiMap.put("😍", "{:7:}");
			// emojiMap.put("😎", "{:41:}");
			// emojiMap.put("😏", "{:57:}");
			// emojiMap.put("😐", "{:52:}");
			// emojiMap.put("🈯", "{:690:}");
			// emojiMap.put("😙", "{:11:}");
			// emojiMap.put("🈸", "{:711:}");
			// emojiMap.put("😚", "{:9:}");
			// emojiMap.put("🈹", "{:696:}");
			// emojiMap.put("😛", "{:14:}");
			// emojiMap.put("🈺", "{:697:}");
			// emojiMap.put("😜", "{:12:}");
			// emojiMap.put("😕", "{:53:}");
			// emojiMap.put("🈴", "{:693:}");
			// emojiMap.put("😖", "{:37:}");
			// emojiMap.put("🈵", "{:692:}");
			// emojiMap.put("😗", "{:10:}");
			// emojiMap.put("🈶", "{:698:}");
			// emojiMap.put("😘", "{:8:}");
			// emojiMap.put("🈷", "{:710:}");
			// emojiMap.put("🈂", "{:712:}");
			// emojiMap.put("🈁", "{:689:}");
			// emojiMap.put("◀️", "{:665:}");
			// emojiMap.put("🗻", "{:558:}");
			// emojiMap.put("🈚", "{:699:}");
			// emojiMap.put("🗼", "{:556:}");
			// emojiMap.put("⚡️", "{:298:}");
			// emojiMap.put("🇯🇵", "{:627:}");
			// emojiMap.put("‼", "{:771:}");
			// emojiMap.put("🚉", "{:579:}");
			// emojiMap.put("🚊", "{:578:}");
			// emojiMap.put("🚋", "{:587:}");
			// emojiMap.put("🚌", "{:590:}");
			// emojiMap.put("🚅", "{:583:}");
			// emojiMap.put("🚆", "{:581:}");
			// emojiMap.put("‼️", "{:771:}");
			// emojiMap.put("🚇", "{:585:}");
			// emojiMap.put("🚈", "{:584:}");
			// emojiMap.put("🚁", "{:576:}");
			// emojiMap.put("🚂", "{:577:}");
			// emojiMap.put("🚃", "{:588:}");
			// emojiMap.put("🚄", "{:582:}");
			// emojiMap.put("♠️", "{:812:}");
			// emojiMap.put("🚀", "{:573:}");
			// emojiMap.put("⭕️", "{:777:}");
			// emojiMap.put("🚚", "{:598:}");
			// emojiMap.put("🚙", "{:592:}");
			// emojiMap.put("🚜", "{:609:}");
			// emojiMap.put("🚛", "{:597:}");
			// emojiMap.put("🚖", "{:596:}");
			// emojiMap.put("🚕", "{:595:}");
			// emojiMap.put("🚘", "{:593:}");
			// emojiMap.put("🚗", "{:594:}");
			// emojiMap.put("🚒", "{:602:}");
			// emojiMap.put("🚑", "{:603:}");
			// emojiMap.put("🚔", "{:601:}");
			// emojiMap.put("🚓", "{:600:}");
			// emojiMap.put("🚎", "{:589:}");
			// emojiMap.put("🚍", "{:591:}");
			// emojiMap.put("🚐", "{:604:}");
			// emojiMap.put("🚏", "{:611:}");
			// emojiMap.put("☁️", "{:297:}");
			// emojiMap.put("🙅", "{:135:}");
			// emojiMap.put("🙆", "{:134:}");
			// emojiMap.put("🙇", "{:144:}");
			// emojiMap.put("🙈", "{:85:}");
			// emojiMap.put("🙉", "{:86:}");
			// emojiMap.put("🙊", "{:87:}");
			// emojiMap.put("🙋", "{:137:}");
			// emojiMap.put("🙌", "{:119:}");
			// emojiMap.put("😽", "{:77:}");
			// emojiMap.put("↪️", "{:670:}");
			// emojiMap.put("😾", "{:82:}");
			// emojiMap.put("😿", "{:80:}");
			// emojiMap.put("🙀", "{:79:}");
			// emojiMap.put("⚠️", "{:615:}");
			// emojiMap.put("🙎", "{:142:}");
			// emojiMap.put("🙍", "{:143:}");
			// emojiMap.put("🙏", "{:120:}");
			// emojiMap.put("😧", "{:47:}");
			// emojiMap.put("😨", "{:32:}");
			// emojiMap.put("😥", "{:26:}");
			// emojiMap.put("☀️", "{:295:}");
			// emojiMap.put("😦", "{:46:}");
			// emojiMap.put("😫", "{:31:}");
			// emojiMap.put("😬", "{:51:}");
			// emojiMap.put("😩", "{:30:}");
			// emojiMap.put("😪", "{:25:}");
			// emojiMap.put("😟", "{:45:}");
			// emojiMap.put("😠", "{:34:}");
			// emojiMap.put("↩️", "{:669:}");
			// emojiMap.put("😝", "{:13:}");
			// emojiMap.put("😞", "{:20:}");
			// emojiMap.put("😣", "{:21:}");
			// emojiMap.put("😤", "{:36:}");
			// emojiMap.put("😡", "{:35:}");
			// emojiMap.put("😢", "{:22:}");
			// emojiMap.put("😸", "{:75:}");
			// emojiMap.put("😷", "{:40:}");
			// emojiMap.put("😶", "{:55:}");
			// emojiMap.put("😵", "{:43:}");
			// emojiMap.put("😼", "{:78:}");
			// emojiMap.put("😻", "{:76:}");
			// emojiMap.put("😺", "{:74:}");
			// emojiMap.put("😹", "{:81:}");
			// emojiMap.put("😰", "{:27:}");
			// emojiMap.put("😯", "{:54:}");
			// emojiMap.put("😮", "{:50:}");
			// emojiMap.put("😭", "{:24:}");
			// emojiMap.put("😴", "{:42:}");
			// emojiMap.put("😳", "{:15:}");
			// emojiMap.put("🉑", "{:718:}");
			// emojiMap.put("😲", "{:44:}");
			// emojiMap.put("😱", "{:33:}");
			// emojiMap.put("🉐", "{:695:}");
			// emojiMap.put("🔓", "{:356:}");
			// emojiMap.put("🔔", "{:348:}");
			// emojiMap.put("🔑", "{:360:}");
			// emojiMap.put("🔒", "{:357:}");
			// emojiMap.put("🔏", "{:358:}");
			// emojiMap.put("🔐", "{:359:}");
			// emojiMap.put("🔍", "{:368:}");
			// emojiMap.put("🔎", "{:361:}");
			// emojiMap.put("🔛", "{:781:}");
			// emojiMap.put("🔜", "{:782:}");
			// emojiMap.put("🔙", "{:780:}");
			// emojiMap.put("🔚", "{:779:}");
			// emojiMap.put("🔗", "{:821:}");
			// emojiMap.put("🔘", "{:820:}");
			// emojiMap.put("🔕", "{:349:}");
			// emojiMap.put("🔖", "{:434:}");
			// emojiMap.put("🔄", "{:664:}");
			// emojiMap.put("🔃", "{:783:}");
			// emojiMap.put("🔂", "{:681:}");
			// emojiMap.put("🔁", "{:680:}");
			// emojiMap.put("🔀", "{:679:}");
			// emojiMap.put("🔌", "{:366:}");
			// emojiMap.put("🔋", "{:367:}");
			// emojiMap.put("🔊", "{:344:}");
			// emojiMap.put("🔉", "{:345:}");
			// emojiMap.put("🔈", "{:346:}");
			// emojiMap.put("🔇", "{:347:}");
			// emojiMap.put("🔆", "{:364:}");
			// emojiMap.put("🔅", "{:365:}");
			// emojiMap.put("📱", "{:336:}");
			// emojiMap.put("📲", "{:390:}");
			// emojiMap.put("📳", "{:740:}");
			// emojiMap.put("📴", "{:741:}");
			// emojiMap.put("📭", "{:401:}");
			// emojiMap.put("📮", "{:402:}");
			// emojiMap.put("♓️", "{:760:}");
			// emojiMap.put("📯", "{:397:}");
			// emojiMap.put("📰", "{:438:}");
			// emojiMap.put("📹", "{:329:}");
			// emojiMap.put("📺", "{:342:}");
			// emojiMap.put("📻", "{:343:}");
			// emojiMap.put("📼", "{:330:}");
			// emojiMap.put("📵", "{:726:}");
			// emojiMap.put("📶", "{:687:}");
			// emojiMap.put("📷", "{:328:}");
			// emojiMap.put("📢", "{:350:}");
			// emojiMap.put("📡", "{:341:}");
			// emojiMap.put("☎️", "{:337:}");
			// emojiMap.put("📤", "{:393:}");
			// emojiMap.put("📣", "{:351:}");
			// emojiMap.put("📞", "{:338:}");
			// emojiMap.put("📝", "{:404:}");
			// emojiMap.put("📠", "{:340:}");
			// emojiMap.put("📟", "{:339:}");
			// emojiMap.put("📪", "{:399:}");
			// emojiMap.put("📩", "{:395:}");
			// emojiMap.put("📬", "{:400:}");
			// emojiMap.put("📫", "{:398:}");
			// emojiMap.put("📦", "{:403:}");
			// emojiMap.put("🇨🇳", "{:630:}");
			// emojiMap.put("📥", "{:392:}");
			// emojiMap.put("📨", "{:396:}");
			// emojiMap.put("📧", "{:391:}");
			// emojiMap.put("📏", "{:423:}");
			// emojiMap.put("📐", "{:424:}");
			// emojiMap.put("📍", "{:625:}");
			// emojiMap.put("📎", "{:420:}");
			// emojiMap.put("📓", "{:429:}");
			// emojiMap.put("📔", "{:430:}");
			// emojiMap.put("📑", "{:407:}");
			// emojiMap.put("📒", "{:431:}");
			// emojiMap.put("📗", "{:426:}");
			// emojiMap.put("📘", "{:427:}");
			// emojiMap.put("📕", "{:425:}");
			// emojiMap.put("📖", "{:433:}");
			// emojiMap.put("📛", "{:435:}");
			// emojiMap.put("📜", "{:411:}");
			// emojiMap.put("📙", "{:428:}");
			// emojiMap.put("📚", "{:432:}");
			// emojiMap.put("〰", "{:823:}");
			// emojiMap.put("📀", "{:332:}");
			// emojiMap.put("💿", "{:331:}");
			// emojiMap.put("💾", "{:334:}");
			// emojiMap.put("💽", "{:333:}");
			// emojiMap.put("📄", "{:405:}");
			// emojiMap.put("📃", "{:406:}");
			// emojiMap.put("📂", "{:417:}");
			// emojiMap.put("📁", "{:416:}");
			// emojiMap.put("📈", "{:409:}");
			// emojiMap.put("📇", "{:415:}");
			// emojiMap.put("📆", "{:414:}");
			// emojiMap.put("📅", "{:413:}");
			// emojiMap.put("📌", "{:419:}");
			// emojiMap.put("〽", "{:824:}");
			// emojiMap.put("📋", "{:412:}");
			// emojiMap.put("📊", "{:408:}");
			// emojiMap.put("📉", "{:410:}");
			// emojiMap.put("💭", "{:189:}");
			// emojiMap.put("💮", "{:816:}");
			// emojiMap.put("💯", "{:817:}");
			// emojiMap.put("💰", "{:383:}");
			// emojiMap.put("🃏", "{:453:}");
			// emojiMap.put("💱", "{:766:}");
			// emojiMap.put("💲", "{:765:}");
			// emojiMap.put("💳", "{:388:}");
			// emojiMap.put("💴", "{:384:}");
			// emojiMap.put("⭐️", "{:294:}");
			// emojiMap.put("💵", "{:385:}");
			// emojiMap.put("💶", "{:387:}");
			// emojiMap.put("💷", "{:386:}");
			// emojiMap.put("ℹ", "{:671:}");
			// emojiMap.put("💸", "{:389:}");
			// emojiMap.put("💹", "{:764:}");
			// emojiMap.put("💺", "{:575:}");
			// emojiMap.put("💻", "{:335:}");
			// emojiMap.put("💼", "{:161:}");
			// emojiMap.put("™", "{:769:}");
			// emojiMap.put("💞", "{:179:}");
			// emojiMap.put("💝", "{:307:}");
			// emojiMap.put("💠", "{:746:}");
			// emojiMap.put("💟", "{:738:}");
			// emojiMap.put("💢", "{:96:}");
			// emojiMap.put("💡", "{:362:}");
			// emojiMap.put("💤", "{:99:}");
			// emojiMap.put("💣", "{:378:}");
			// emojiMap.put("💦", "{:97:}");
			// emojiMap.put("💥", "{:95:}");
			// emojiMap.put("💨", "{:100:}");
			// emojiMap.put("💧", "{:98:}");
			// emojiMap.put("💪", "{:123:}");
			// emojiMap.put("💩", "{:90:}");
			// emojiMap.put("💬", "{:187:}");
			// emojiMap.put("💫", "{:94:}");
			// emojiMap.put("🆙", "{:683:}");
			// emojiMap.put("🆘", "{:722:}");
			// emojiMap.put("🆚", "{:739:}");
			// emojiMap.put("🆕", "{:682:}");
			// emojiMap.put("🆔", "{:723:}");
			// emojiMap.put("🆗", "{:678:}");
			// emojiMap.put("🆖", "{:686:}");
			// emojiMap.put("🆑", "{:721:}");
			// emojiMap.put("🆓", "{:685:}");
			// emojiMap.put("🆒", "{:684:}");
			// emojiMap.put("🆎", "{:744:}");
			// emojiMap.put("🕥", "{:806:}");
			// emojiMap.put("🕦", "{:807:}");
			// emojiMap.put("🕧", "{:785:}");
			// emojiMap.put("🕡", "{:802:}");
			// emojiMap.put("🕢", "{:803:}");
			// emojiMap.put("🕣", "{:804:}");
			// emojiMap.put("🕤", "{:805:}");
			// emojiMap.put("🕝", "{:789:}");
			// emojiMap.put("🕞", "{:791:}");
			// emojiMap.put("🕟", "{:793:}");
			// emojiMap.put("🅾", "{:745:}");
			// emojiMap.put("🕠", "{:795:}");
			// emojiMap.put("🅿", "{:707:}");
			// emojiMap.put("🕘", "{:799:}");
			// emojiMap.put("↘", "{:660:}");
			// emojiMap.put("🕗", "{:798:}");
			// emojiMap.put("↙", "{:661:}");
			// emojiMap.put("🕖", "{:797:}");
			// emojiMap.put("🕕", "{:796:}");
			// emojiMap.put("🕜", "{:787:}");
			// emojiMap.put("🕛", "{:784:}");
			// emojiMap.put("🕚", "{:801:}");
			// emojiMap.put("🕙", "{:800:}");
			// emojiMap.put("🕐", "{:786:}");
			// emojiMap.put("🕔", "{:794:}");
			// emojiMap.put("↔", "{:662:}");
			// emojiMap.put("🕓", "{:792:}");
			// emojiMap.put("↕", "{:663:}");
			// emojiMap.put("🕒", "{:790:}");
			// emojiMap.put("🅱", "{:743:}");
			// emojiMap.put("↖", "{:659:}");
			// emojiMap.put("🕑", "{:788:}");
			// emojiMap.put("🅰", "{:742:}");
			// emojiMap.put("↗", "{:658:}");
			// emojiMap.put("🔽", "{:668:}");
			// emojiMap.put("🔶", "{:842:}");
			// emojiMap.put("🔵", "{:838:}");
			// emojiMap.put("🔸", "{:844:}");
			// emojiMap.put("🔷", "{:843:}");
			// emojiMap.put("🔺", "{:832:}");
			// emojiMap.put("🔹", "{:845:}");
			// emojiMap.put("🔼", "{:667:}");
			// emojiMap.put("🔻", "{:839:}");
			// emojiMap.put("🔮", "{:326:}");
			// emojiMap.put("🔭", "{:437:}");
			// emojiMap.put("🔰", "{:617:}");
			// emojiMap.put("🔯", "{:762:}");
			// emojiMap.put("🔲", "{:833:}");
			// emojiMap.put("🔱", "{:825:}");
			// emojiMap.put("🔴", "{:837:}");
			// emojiMap.put("🔳", "{:834:}");
			// emojiMap.put("🔥", "{:91:}");
			// emojiMap.put("🔦", "{:363:}");
			// emojiMap.put("↪", "{:670:}");
			// emojiMap.put("🔧", "{:373:}");
			// emojiMap.put("↩", "{:669:}");
			// emojiMap.put("🔨", "{:375:}");
			// emojiMap.put("🔩", "{:374:}");
			// emojiMap.put("🔪", "{:380:}");
			// emojiMap.put("🔫", "{:379:}");
			// emojiMap.put("⤵", "{:676:}");
			// emojiMap.put("🔬", "{:436:}");
			// emojiMap.put("⤴", "{:677:}");
			// emojiMap.put("🔝", "{:778:}");
			// emojiMap.put("🔞", "{:725:}");
			// emojiMap.put("🔟", "{:647:}");
			// emojiMap.put("🔠", "{:655:}");
			// emojiMap.put("🔡", "{:656:}");
			// emojiMap.put("🔢", "{:648:}");
			// emojiMap.put("🔣", "{:650:}");
			// emojiMap.put("🔤", "{:657:}");
			// emojiMap.put("🇺🇸", "{:631:}");
			// emojiMap.put("⛪️", "{:547:}");
			// emojiMap.put("☺", "{:5:}");
			// emojiMap.put("☎", "{:337:}");
			// emojiMap.put("Ⓜ️", "{:713:}");
			// emojiMap.put("☀", "{:295:}");
			// emojiMap.put("☁", "{:297:}");
			// emojiMap.put("☝", "{:121:}");
			// emojiMap.put("☕", "{:477:}");
			// emojiMap.put("☔", "{:299:}");
			// emojiMap.put("☑", "{:819:}");
			// emojiMap.put("♨", "{:621:}");
			// emojiMap.put("🀄", "{:455:}");
			// emojiMap.put("♠", "{:812:}");
			// emojiMap.put("♣", "{:814:}");
			// emojiMap.put("♥", "{:813:}");
			// emojiMap.put("♦", "{:815:}");
			// emojiMap.put("♻", "{:748:}");
			// emojiMap.put("♿", "{:708:}");
			// emojiMap.put("↔️", "{:662:}");
			// emojiMap.put("♊", "{:751:}");
			// emojiMap.put("🐆", "{:249:}");
			// emojiMap.put("♋", "{:752:}");
			// emojiMap.put("🐅", "{:234:}");
			// emojiMap.put("♈", "{:749:}");
			// emojiMap.put("🐈", "{:250:}");
			// emojiMap.put("♉", "{:750:}");
			// emojiMap.put("🐇", "{:235:}");
			// emojiMap.put("♎", "{:755:}");
			// emojiMap.put("🐊", "{:246:}");
			// emojiMap.put("♏", "{:756:}");
			// emojiMap.put("🐉", "{:236:}");
			// emojiMap.put("♌", "{:753:}");
			// emojiMap.put("🐌", "{:222:}");
			// emojiMap.put("♍", "{:754:}");
			// emojiMap.put("🐋", "{:229:}");
			// emojiMap.put("🐀", "{:232:}");
			// emojiMap.put("🐂", "{:243:}");
			// emojiMap.put("🐁", "{:242:}");
			// emojiMap.put("🐄", "{:230:}");
			// emojiMap.put("🐃", "{:233:}");
			// emojiMap.put("🐕", "{:240:}");
			// emojiMap.put("🐖", "{:241:}");
			// emojiMap.put("🐗", "{:203:}");
			// emojiMap.put("🐘", "{:208:}");
			// emojiMap.put("🐙", "{:223:}");
			// emojiMap.put("🐚", "{:224:}");
			// emojiMap.put("🐛", "{:218:}");
			// emojiMap.put("🐜", "{:220:}");
			// emojiMap.put("♓", "{:760:}");
			// emojiMap.put("🐍", "{:216:}");
			// emojiMap.put("♒", "{:759:}");
			// emojiMap.put("🐎", "{:237:}");
			// emojiMap.put("♑", "{:758:}");
			// emojiMap.put("🐏", "{:231:}");
			// emojiMap.put("♐", "{:757:}");
			// emojiMap.put("🐐", "{:238:}");
			// emojiMap.put("🐑", "{:207:}");
			// emojiMap.put("🐒", "{:205:}");
			// emojiMap.put("🐓", "{:239:}");
			// emojiMap.put("🐔", "{:215:}");
			// emojiMap.put("🐣", "{:214:}");
			// emojiMap.put("🐤", "{:212:}");
			// emojiMap.put("🐡", "{:245:}");
			// emojiMap.put("🐢", "{:217:}");
			// emojiMap.put("⚡", "{:298:}");
			// emojiMap.put("🐟", "{:226:}");
			// emojiMap.put("🐠", "{:225:}");
			// emojiMap.put("⚠", "{:615:}");
			// emojiMap.put("🐝", "{:219:}");
			// emojiMap.put("🐞", "{:221:}");
			// emojiMap.put("🐫", "{:247:}");
			// emojiMap.put("🐬", "{:227:}");
			// emojiMap.put("🐩", "{:251:}");
			// emojiMap.put("🐪", "{:248:}");
			// emojiMap.put("🐧", "{:210:}");
			// emojiMap.put("🐨", "{:198:}");
			// emojiMap.put("⚫", "{:835:}");
			// emojiMap.put("🐥", "{:213:}");
			// emojiMap.put("⚪", "{:836:}");
			// emojiMap.put("🐦", "{:211:}");
			// emojiMap.put("🐴", "{:206:}");
			// emojiMap.put("🐳", "{:228:}");
			// emojiMap.put("🐲", "{:244:}");
			// emojiMap.put("⬅️", "{:653:}");
			// emojiMap.put("🐱", "{:192:}");
			// emojiMap.put("🐰", "{:195:}");
			// emojiMap.put("🐯", "{:197:}");
			// emojiMap.put("🐮", "{:202:}");
			// emojiMap.put("🐭", "{:193:}");
			// emojiMap.put("0⃣", "{:646:}");
			// emojiMap.put("🐼", "{:209:}");
			// emojiMap.put("🐻", "{:199:}");
			// emojiMap.put("⚽", "{:460:}");
			// emojiMap.put("🐺", "{:191:}");
			// emojiMap.put("⚾", "{:461:}");
			// emojiMap.put("🐹", "{:194:}");
			// emojiMap.put("🐸", "{:196:}");
			// emojiMap.put("🐷", "{:200:}");
			// emojiMap.put("🐶", "{:190:}");
			// emojiMap.put("🐵", "{:204:}");
			// emojiMap.put("👂", "{:101:}");
			// emojiMap.put("👃", "{:103:}");
			// emojiMap.put("👄", "{:105:}");
			// emojiMap.put("🐽", "{:201:}");
			// emojiMap.put("🐾", "{:252:}");
			// emojiMap.put("👀", "{:102:}");
			// emojiMap.put("👉", "{:117:}");
			// emojiMap.put("👊", "{:109:}");
			// emojiMap.put("👋", "{:112:}");
			// emojiMap.put("👌", "{:108:}");
			// emojiMap.put("▶️", "{:666:}");
			// emojiMap.put("👅", "{:104:}");
			// emojiMap.put("👆", "{:115:}");
			// emojiMap.put("👇", "{:116:}");
			// emojiMap.put("👈", "{:118:}");
			// emojiMap.put("👒", "{:147:}");
			// emojiMap.put("👑", "{:146:}");
			// emojiMap.put("👔", "{:154:}");
			// emojiMap.put("👓", "{:165:}");
			// emojiMap.put("👎", "{:107:}");
			// emojiMap.put("👍", "{:106:}");
			// emojiMap.put("⚓", "{:572:}");
			// emojiMap.put("👐", "{:114:}");
			// emojiMap.put("👏", "{:122:}");
			// emojiMap.put("👚", "{:155:}");
			// emojiMap.put("👙", "{:160:}");
			// emojiMap.put("👜", "{:162:}");
			// emojiMap.put("👛", "{:164:}");
			// emojiMap.put("👖", "{:158:}");
			// emojiMap.put("👕", "{:153:}");
			// emojiMap.put("👘", "{:159:}");
			// emojiMap.put("👗", "{:156:}");
			// emojiMap.put("👟", "{:148:}");
			// emojiMap.put("👠", "{:151:}");
			// emojiMap.put("👝", "{:163:}");
			// emojiMap.put("👞", "{:149:}");
			// emojiMap.put("👣", "{:188:}");
			// emojiMap.put("👤", "{:185:}");
			// emojiMap.put("👡", "{:150:}");
			// emojiMap.put("❤️", "{:173:}");
			// emojiMap.put("👢", "{:152:}");
			// emojiMap.put("👧", "{:66:}");
			// emojiMap.put("👨", "{:67:}");
			// emojiMap.put("👥", "{:186:}");
			// emojiMap.put("👦", "{:65:}");
			// emojiMap.put("⛪", "{:547:}");
			// emojiMap.put("👫", "{:127:}");
			// emojiMap.put("👬", "{:129:}");
			// emojiMap.put("👩", "{:68:}");
			// emojiMap.put("👪", "{:128:}");
			// emojiMap.put("👰", "{:141:}");
			// emojiMap.put("👯", "{:133:}");
			// emojiMap.put("🇪🇸", "{:633:}");
			// emojiMap.put("2⃣", "{:638:}");
			// emojiMap.put("👮", "{:61:}");
			// emojiMap.put("⛲", "{:566:}");
			// emojiMap.put("👭", "{:130:}");
			// emojiMap.put("⛳", "{:466:}");
			// emojiMap.put("👴", "{:69:}");
			// emojiMap.put("⬇️", "{:652:}");
			// emojiMap.put("👳", "{:60:}");
			// emojiMap.put("⛵", "{:569:}");
			// emojiMap.put("👲", "{:59:}");
			// emojiMap.put("👱", "{:71:}");
			// emojiMap.put("👸", "{:73:}");
			// emojiMap.put("👷", "{:62:}");
			// emojiMap.put("👶", "{:64:}");
			// emojiMap.put("⛺", "{:554:}");
			// emojiMap.put("👵", "{:70:}");
			// emojiMap.put("👼", "{:72:}");
			// emojiMap.put("👻", "{:317:}");
			// emojiMap.put("⛽", "{:618:}");
			// emojiMap.put("👺", "{:84:}");
			// emojiMap.put("👹", "{:83:}");
			// emojiMap.put("👽", "{:89:}");
			// emojiMap.put("👾", "{:451:}");
			// emojiMap.put("👿", "{:49:}");
			// emojiMap.put("💀", "{:88:}");
			// emojiMap.put("💁", "{:136:}");
			// emojiMap.put("💂", "{:63:}");
			// emojiMap.put("⛅", "{:296:}");
			// emojiMap.put("💃", "{:126:}");
			// emojiMap.put("⛄", "{:301:}");
			// emojiMap.put("💄", "{:168:}");
			// emojiMap.put("💅", "{:140:}");
			// emojiMap.put("💆", "{:138:}");
			// emojiMap.put("💇", "{:139:}");
			// emojiMap.put("💈", "{:610:}");
			// emojiMap.put("💉", "{:382:}");
			// emojiMap.put("⛎", "{:761:}");
			// emojiMap.put("💊", "{:381:}");
			// emojiMap.put("💋", "{:182:}");
			// emojiMap.put("💌", "{:181:}");
			// emojiMap.put("💎", "{:184:}");
			// emojiMap.put("1⃣", "{:637:}");
			// emojiMap.put("💍", "{:183:}");
			// emojiMap.put("💐", "{:253:}");
			// emojiMap.put("💏", "{:131:}");
			// emojiMap.put("💒", "{:546:}");
			// emojiMap.put("💑", "{:132:}");
			// emojiMap.put("⛔", "{:732:}");
			// emojiMap.put("💔", "{:174:}");
			// emojiMap.put("💓", "{:176:}");
			// emojiMap.put("⬆️", "{:651:}");
			// emojiMap.put("💖", "{:178:}");
			// emojiMap.put("💕", "{:177:}");
			// emojiMap.put("💘", "{:180:}");
			// emojiMap.put("🇬🇧", "{:636:}");
			// emojiMap.put("💗", "{:175:}");
			// emojiMap.put("💚", "{:172:}");
			// emojiMap.put("💙", "{:170:}");
			// emojiMap.put("💜", "{:171:}");
			// emojiMap.put("💛", "{:169:}");
			// emojiMap.put("✴", "{:737:}");
			// emojiMap.put("✳", "{:733:}");
			// emojiMap.put("⚫️", "{:835:}");
			// emojiMap.put("4⃣", "{:640:}");
			// emojiMap.put("✨", "{:92:}");
			// emojiMap.put("♥️", "{:813:}");
			// emojiMap.put("⛳️", "{:466:}");
			// emojiMap.put("↙️", "{:661:}");
			// emojiMap.put("✔", "{:818:}");
			// emojiMap.put("✖", "{:808:}");
			// emojiMap.put("3⃣", "{:639:}");
			// emojiMap.put("✒", "{:421:}");
			// emojiMap.put("✌", "{:111:}");
			// emojiMap.put("✏", "{:422:}");
			// emojiMap.put("✈", "{:574:}");
			// emojiMap.put("✉", "{:394:}");
			// emojiMap.put("✊", "{:110:}");
			// emojiMap.put("✋", "{:113:}");
			// emojiMap.put("♦️", "{:815:}");
			// emojiMap.put("✅", "{:736:}");
			// emojiMap.put("⛲️", "{:566:}");
			// emojiMap.put("✂", "{:418:}");
			// emojiMap.put("6⃣", "{:642:}");
			// emojiMap.put("♣️", "{:814:}");
			// emojiMap.put("❤", "{:173:}");
			// emojiMap.put("⚪️", "{:836:}");
			// emojiMap.put("❓", "{:774:}");
			// emojiMap.put("❕", "{:775:}");
			// emojiMap.put("❔", "{:776:}");
			// emojiMap.put("❗", "{:773:}");
			// emojiMap.put("◻️", "{:827:}");
			// emojiMap.put("❌", "{:770:}");
			// emojiMap.put("❎", "{:735:}");
			// emojiMap.put("5⃣", "{:641:}");
			// emojiMap.put("❄", "{:300:}");
			// emojiMap.put("❇", "{:734:}");
			// emojiMap.put("↖️", "{:659:}");
			// emojiMap.put("➰", "{:822:}");
			// emojiMap.put("☺️", "{:5:}");
			// emojiMap.put("➿", "{:747:}");
			// emojiMap.put("➡", "{:654:}");
			// emojiMap.put("◼️", "{:826:}");
			// emojiMap.put("8⃣", "{:644:}");
			// emojiMap.put("↕️", "{:663:}");
			// emojiMap.put("➕", "{:809:}");
			// emojiMap.put("➖", "{:810:}");
			// emojiMap.put("➗", "{:811:}");
			// emojiMap.put("✴️", "{:737:}");
			// emojiMap.put("7⃣", "{:643:}");
			// emojiMap.put("◽️", "{:829:}");
			// emojiMap.put("✳️", "{:733:}");
			// emojiMap.put("↘️", "{:660:}");
			// emojiMap.put("⛵️", "{:569:}");
			// emojiMap.put("◾️", "{:828:}");
			// emojiMap.put("〽️", "{:824:}");
			// emojiMap.put("↗️", "{:658:}");
			// emojiMap.put("➡️", "{:654:}");
			// emojiMap.put("🈚️", "{:699:}");
			// emojiMap.put("♨️", "{:621:}");
			// emojiMap.put("9⃣", "{:645:}");
			// emojiMap.put("⛺️", "{:554:}");
			// emojiMap.put("⛅️", "{:296:}");
			// emojiMap.put("♿️", "{:708:}");
			// emojiMap.put("⛄️", "{:301:}");
			// emojiMap.put("▪️", "{:830:}");
			// emojiMap.put("❗️", "{:773:}");
			// emojiMap.put("▫️", "{:831:}");
			// emojiMap.put("🇮🇹", "{:634:}");
			// emojiMap.put("Ⓜ", "{:713:}");
			// emojiMap.put("🇰🇷", "{:628:}");
			// emojiMap.put("⚾️", "{:461:}");
			// emojiMap.put("⛽️", "{:618:}");
			// emojiMap.put("🇷🇺", "{:635:}");
			// emojiMap.put("✂️", "{:418:}");
			// emojiMap.put("⚽️", "{:460:}");
			// emojiMap.put("#⃣", "{:649:}");
			// emojiMap.put("⤴️", "{:677:}");
			// emojiMap.put("🈯️", "{:690:}");
			// emojiMap.put("⤵️", "{:676:}");
			// emojiMap.put("❇️", "{:734:}");
			// emojiMap.put("♻️", "{:748:}");
			// emojiMap.put("⌛️", "{:353:}");
			// emojiMap.put("❄️", "{:300:}");
			// emojiMap.put("🀄️", "{:455:}");
			// emojiMap.put("▶", "{:666:}");
			// emojiMap.put("▫", "{:831:}");
			// emojiMap.put("▪", "{:830:}");
			// emojiMap.put("◀", "{:665:}");
			// emojiMap.put("◾", "{:828:}");
			// emojiMap.put("◼", "{:826:}");
			// emojiMap.put("◽", "{:829:}");
			// emojiMap.put("◻", "{:827:}");
			// emojiMap.put("⌚️", "{:355:}");

			emojiMap.put("♊️", " ");
			emojiMap.put("✌️", " ");
			emojiMap.put("♋️", " ");
			emojiMap.put("🏃", " ");
			emojiMap.put("✏️", " ");
			emojiMap.put("🏂", " ");
			emojiMap.put("🏁", " ");
			emojiMap.put("🏀", " ");
			emojiMap.put("🎿", " ");
			emojiMap.put("🎾", " ");
			emojiMap.put("🎽", " ");
			emojiMap.put("🎼", " ");
			emojiMap.put("🏊", " ");
			emojiMap.put("🏉", " ");
			emojiMap.put("♌️", " ");
			emojiMap.put("🏈", " ");
			emojiMap.put("🏇", " ");
			emojiMap.put("🇫🇷", " ");
			emojiMap.put("⚓️", " ");
			emojiMap.put("🏆", " ");
			emojiMap.put("🏄", " ");
			emojiMap.put("☕️", " ");
			emojiMap.put("🏡", " ");
			emojiMap.put("🏠", " ");
			emojiMap.put("🏣", " ");
			emojiMap.put("🏢", " ");
			emojiMap.put("🏩", " ");
			emojiMap.put("♍️", " ");
			emojiMap.put("🏨", " ");
			emojiMap.put("🏫", " ");
			emojiMap.put("🏪", " ");
			emojiMap.put("🏥", " ");
			emojiMap.put("🏤", " ");
			emojiMap.put("🏧", " ");
			emojiMap.put("🏦", " ");
			emojiMap.put("🏰", " ");
			emojiMap.put("🏬", " ");
			emojiMap.put("🏭", " ");
			emojiMap.put("🏮", " ");
			emojiMap.put("🏯", " ");
			emojiMap.put("♎️", " ");
			emojiMap.put("✉️", " ");
			emojiMap.put("©", " ");
			emojiMap.put("®", " ");
			emojiMap.put("♏️", " ");
			emojiMap.put("✈️", " ");
			emojiMap.put("☔️", " ");
			emojiMap.put("♐️", " ");
			emojiMap.put("☑️", " ");
			emojiMap.put("♑️", " ");
			emojiMap.put("♒️", " ");
			emojiMap.put("🌌", " ");
			emojiMap.put("🌍", " ");
			emojiMap.put("🌎", " ");
			emojiMap.put("🌏", " ");
			emojiMap.put("🌐", " ");
			emojiMap.put("🌑", " ");
			emojiMap.put("🌒", " ");
			emojiMap.put("🌓", " ");
			emojiMap.put("🌔", " ");
			emojiMap.put("🌕", " ");
			emojiMap.put("🌖", " ");
			emojiMap.put("🌗", " ");
			emojiMap.put("🌘", " ");
			emojiMap.put("🌙", " ");
			emojiMap.put("🌚", " ");
			emojiMap.put("🌛", " ");
			emojiMap.put("⁉️", " ");
			emojiMap.put("✖️", " ");
			emojiMap.put("🌁", " ");
			emojiMap.put("🌀", " ");
			emojiMap.put("🌃", " ");
			emojiMap.put("🌂", " ");
			emojiMap.put("🌅", " ");
			emojiMap.put("🌄", " ");
			emojiMap.put("🌇", " ");
			emojiMap.put("🌆", " ");
			emojiMap.put("🌉", " ");
			emojiMap.put("🌈", " ");
			emojiMap.put("⬜️", " ");
			emojiMap.put("🌋", " ");
			emojiMap.put("🌊", " ");
			emojiMap.put("🌲", " ");
			emojiMap.put("🌳", " ");
			emojiMap.put("🌰", " ");
			emojiMap.put("🌱", " ");
			emojiMap.put("🌷", " ");
			emojiMap.put("🌴", " ");
			emojiMap.put("🌵", " ");
			emojiMap.put("🌺", " ");
			emojiMap.put("🌻", " ");
			emojiMap.put("🌸", " ");
			emojiMap.put("🌹", " ");
			emojiMap.put("🌟", " ");
			emojiMap.put("🌞", " ");
			emojiMap.put("🌝", " ");
			emojiMap.put("🌜", " ");
			emojiMap.put("㊙️", " ");
			emojiMap.put("🌠", " ");
			emojiMap.put("⬛️", " ");
			emojiMap.put("🚱", " ");
			emojiMap.put("☝️", " ");
			emojiMap.put("🚲", " ");
			emojiMap.put("🚳", " ");
			emojiMap.put("🚴", " ");
			emojiMap.put("🚭", " ");
			emojiMap.put("🚮", " ");
			emojiMap.put("🅿️", " ");
			emojiMap.put("🚯", " ");
			emojiMap.put("🚰", " ");
			emojiMap.put("🚹", " ");
			emojiMap.put("🚺", " ");
			emojiMap.put("🚻", " ");
			emojiMap.put("🚼", " ");
			emojiMap.put("🚵", " ");
			emojiMap.put("🚶", " ");
			emojiMap.put("🚷", " ");
			emojiMap.put("🚸", " ");
			emojiMap.put("🚢", " ");
			emojiMap.put("🚡", " ");
			emojiMap.put("🚤", " ");
			emojiMap.put("🚣", " ");
			emojiMap.put("🚞", " ");
			emojiMap.put("🚝", " ");
			emojiMap.put("🚠", " ");
			emojiMap.put("🚟", " ");
			emojiMap.put("🚪", " ");
			emojiMap.put("🚩", " ");
			emojiMap.put("🚬", " ");
			emojiMap.put("🚫", " ");
			emojiMap.put("🚦", " ");
			emojiMap.put("🚥", " ");
			emojiMap.put("🚨", " ");
			emojiMap.put("🚧", " ");
			emojiMap.put("⌛", " ");
			emojiMap.put("⌚", " ");
			emojiMap.put("🛄", " ");
			emojiMap.put("🛃", " ");
			emojiMap.put("🛂", " ");
			emojiMap.put("🛁", " ");
			emojiMap.put("🛀", " ");
			emojiMap.put("🇩🇪", " ");
			emojiMap.put("🚿", " ");
			emojiMap.put("🚾", " ");
			emojiMap.put("🚽", " ");
			emojiMap.put("🛅", " ");
			emojiMap.put("✒️", " ");
			emojiMap.put("🎍", " ");
			emojiMap.put("⏳", " ");
			emojiMap.put("🎌", " ");
			emojiMap.put("⏰", " ");
			emojiMap.put("🎏", " ");
			emojiMap.put("🎎", " ");
			emojiMap.put("🎑", " ");
			emojiMap.put("🎐", " ");
			emojiMap.put("🎓", " ");
			emojiMap.put("🎒", " ");
			emojiMap.put("🎄", " ");
			emojiMap.put("⏫", " ");
			emojiMap.put("🎅", " ");
			emojiMap.put("⏪", " ");
			emojiMap.put("🎆", " ");
			emojiMap.put("⏩", " ");
			emojiMap.put("🎇", " ");
			emojiMap.put("🎈", " ");
			emojiMap.put("🎉", " ");
			emojiMap.put("🎊", " ");
			emojiMap.put("🎋", " ");
			emojiMap.put("⏬", " ");
			emojiMap.put("🍼", " ");
			emojiMap.put("ℹ️", " ");
			emojiMap.put("🎀", " ");
			emojiMap.put("⛔️", " ");
			emojiMap.put("🎁", " ");
			emojiMap.put("🎂", " ");
			emojiMap.put("🎃", " ");
			emojiMap.put("🎷", " ");
			emojiMap.put("🎶", " ");
			emojiMap.put("🎵", " ");
			emojiMap.put("🎴", " ");
			emojiMap.put("🎻", " ");
			emojiMap.put("🎺", " ");
			emojiMap.put("🎹", " ");
			emojiMap.put("🎸", " ");
			emojiMap.put("🎯", " ");
			emojiMap.put("🎮", " ");
			emojiMap.put("🎭", " ");
			emojiMap.put("🎬", " ");
			emojiMap.put("🎳", " ");
			emojiMap.put("🎲", " ");
			emojiMap.put("🎱", " ");
			emojiMap.put("🎰", " ");
			emojiMap.put("🎦", " ");
			emojiMap.put("🎧", " ");
			emojiMap.put("⭐", " ");
			emojiMap.put("🎤", " ");
			emojiMap.put("🎥", " ");
			emojiMap.put("🎪", " ");
			emojiMap.put("⭕", " ");
			emojiMap.put("🎫", " ");
			emojiMap.put("🎨", " ");
			emojiMap.put("🎩", " ");
			emojiMap.put("🎢", " ");
			emojiMap.put("🎣", " ");
			emojiMap.put("🎠", " ");
			emojiMap.put("🎡", " ");
			emojiMap.put("🍙", " ");
			emojiMap.put("✔️", " ");
			emojiMap.put("🍘", " ");
			emojiMap.put("🍛", " ");
			emojiMap.put("🍚", " ");
			emojiMap.put("🍕", " ");
			emojiMap.put("🍔", " ");
			emojiMap.put("🍗", " ");
			emojiMap.put("🍖", " ");
			emojiMap.put("🍑", " ");
			emojiMap.put("🍐", " ");
			emojiMap.put("🍓", " ");
			emojiMap.put("🍒", " ");
			emojiMap.put("🍍", " ");
			emojiMap.put("🍌", " ");
			emojiMap.put("🍏", " ");
			emojiMap.put("🍎", " ");
			emojiMap.put("🍈", " ");
			emojiMap.put("🍉", " ");
			emojiMap.put("🍊", " ");
			emojiMap.put("🍋", " ");
			emojiMap.put("🍄", " ");
			emojiMap.put("🍅", " ");
			emojiMap.put("♈️", " ");
			emojiMap.put("🍆", " ");
			emojiMap.put("㊙", " ");
			emojiMap.put("🍇", " ");
			emojiMap.put("🍀", " ");
			emojiMap.put("㊗", " ");
			emojiMap.put("🍁", " ");
			emojiMap.put("🍂", " ");
			emojiMap.put("🍃", " ");
			emojiMap.put("🌼", " ");
			emojiMap.put("🌽", " ");
			emojiMap.put("🌾", " ");
			emojiMap.put("🌿", " ");
			emojiMap.put("🍻", " ");
			emojiMap.put("⬅", " ");
			emojiMap.put("🍺", " ");
			emojiMap.put("⬆", " ");
			emojiMap.put("🍹", " ");
			emojiMap.put("⬇", " ");
			emojiMap.put("🍸", " ");
			emojiMap.put("🍷", " ");
			emojiMap.put("🍶", " ");
			emojiMap.put("🍵", " ");
			emojiMap.put("🍴", " ");
			emojiMap.put("🍳", " ");
			emojiMap.put("🍲", " ");
			emojiMap.put("🍱", " ");
			emojiMap.put("🍰", " ");
			emojiMap.put("🍯", " ");
			emojiMap.put("🍮", " ");
			emojiMap.put("🍭", " ");
			emojiMap.put("🍬", " ");
			emojiMap.put("🍪", " ");
			emojiMap.put("🍫", " ");
			emojiMap.put("🍨", " ");
			emojiMap.put("🍩", " ");
			emojiMap.put("🍦", " ");
			emojiMap.put("🍧", " ");
			emojiMap.put("🍤", " ");
			emojiMap.put("♉️", " ");
			emojiMap.put("🍥", " ");
			emojiMap.put("㊗️", " ");
			emojiMap.put("🍢", " ");
			emojiMap.put("🍣", " ");
			emojiMap.put("⬜", " ");
			emojiMap.put("🍠", " ");
			emojiMap.put("🍡", " ");
			emojiMap.put("🍞", " ");
			emojiMap.put("🍟", " ");
			emojiMap.put("🍜", " ");
			emojiMap.put("⬛", " ");
			emojiMap.put("🍝", " ");
			emojiMap.put("😂", " ");
			emojiMap.put("😁", " ");
			emojiMap.put("😄", " ");
			emojiMap.put("😃", " ");
			emojiMap.put("🗾", " ");
			emojiMap.put("🗽", " ");
			emojiMap.put("😀", " ");
			emojiMap.put("🗿", " ");
			emojiMap.put("😊", " ");
			emojiMap.put("😉", " ");
			emojiMap.put("😌", " ");
			emojiMap.put("😋", " ");
			emojiMap.put("😆", " ");
			emojiMap.put("😅", " ");
			emojiMap.put("😈", " ");
			emojiMap.put("😇", " ");
			emojiMap.put("⁉", " ");
			emojiMap.put("😑", " ");
			emojiMap.put("😒", " ");
			emojiMap.put("🈲", " ");
			emojiMap.put("😓", " ");
			emojiMap.put("🈳", " ");
			emojiMap.put("😔", " ");
			emojiMap.put("😍", " ");
			emojiMap.put("😎", " ");
			emojiMap.put("😏", " ");
			emojiMap.put("🈯", " ");
			emojiMap.put("😐", " ");
			emojiMap.put("🈸", " ");
			emojiMap.put("😙", " ");
			emojiMap.put("🈹", " ");
			emojiMap.put("😚", " ");
			emojiMap.put("🈺", " ");
			emojiMap.put("😛", " ");
			emojiMap.put("😜", " ");
			emojiMap.put("🈴", " ");
			emojiMap.put("😕", " ");
			emojiMap.put("🈵", " ");
			emojiMap.put("😖", " ");
			emojiMap.put("🈶", " ");
			emojiMap.put("😗", " ");
			emojiMap.put("🈷", " ");
			emojiMap.put("😘", " ");
			emojiMap.put("🈂", " ");
			emojiMap.put("🈁", " ");
			emojiMap.put("◀️", " ");
			emojiMap.put("🈚", " ");
			emojiMap.put("🗻", " ");
			emojiMap.put("🗼", " ");
			emojiMap.put("⚡️", " ");
			emojiMap.put("🇯🇵", " ");
			emojiMap.put("‼", " ");
			emojiMap.put("🚉", " ");
			emojiMap.put("🚊", " ");
			emojiMap.put("🚋", " ");
			emojiMap.put("🚌", " ");
			emojiMap.put("🚅", " ");
			emojiMap.put("🚆", " ");
			emojiMap.put("🚇", " ");
			emojiMap.put("‼️", " ");
			emojiMap.put("🚈", " ");
			emojiMap.put("🚁", " ");
			emojiMap.put("🚂", " ");
			emojiMap.put("🚃", " ");
			emojiMap.put("🚄", " ");
			emojiMap.put("♠️", " ");
			emojiMap.put("🚀", " ");
			emojiMap.put("🚚", " ");
			emojiMap.put("⭕️", " ");
			emojiMap.put("🚙", " ");
			emojiMap.put("🚜", " ");
			emojiMap.put("🚛", " ");
			emojiMap.put("🚖", " ");
			emojiMap.put("🚕", " ");
			emojiMap.put("🚘", " ");
			emojiMap.put("🚗", " ");
			emojiMap.put("🚒", " ");
			emojiMap.put("🚑", " ");
			emojiMap.put("🚔", " ");
			emojiMap.put("🚓", " ");
			emojiMap.put("🚎", " ");
			emojiMap.put("🚍", " ");
			emojiMap.put("🚐", " ");
			emojiMap.put("🚏", " ");
			emojiMap.put("🙅", " ");
			emojiMap.put("☁️", " ");
			emojiMap.put("🙆", " ");
			emojiMap.put("🙇", " ");
			emojiMap.put("🙈", " ");
			emojiMap.put("🙉", " ");
			emojiMap.put("🙊", " ");
			emojiMap.put("🙋", " ");
			emojiMap.put("🙌", " ");
			emojiMap.put("↪️", " ");
			emojiMap.put("😽", " ");
			emojiMap.put("😾", " ");
			emojiMap.put("😿", " ");
			emojiMap.put("🙀", " ");
			emojiMap.put("⚠️", " ");
			emojiMap.put("🙎", " ");
			emojiMap.put("🙍", " ");
			emojiMap.put("🙏", " ");
			emojiMap.put("😧", " ");
			emojiMap.put("😨", " ");
			emojiMap.put("😥", " ");
			emojiMap.put("☀️", " ");
			emojiMap.put("😦", " ");
			emojiMap.put("😫", " ");
			emojiMap.put("😬", " ");
			emojiMap.put("😩", " ");
			emojiMap.put("😪", " ");
			emojiMap.put("😟", " ");
			emojiMap.put("😠", " ");
			emojiMap.put("↩️", " ");
			emojiMap.put("😝", " ");
			emojiMap.put("😞", " ");
			emojiMap.put("😣", " ");
			emojiMap.put("😤", " ");
			emojiMap.put("😡", " ");
			emojiMap.put("😢", " ");
			emojiMap.put("😸", " ");
			emojiMap.put("😷", " ");
			emojiMap.put("😶", " ");
			emojiMap.put("😵", " ");
			emojiMap.put("😼", " ");
			emojiMap.put("😻", " ");
			emojiMap.put("😺", " ");
			emojiMap.put("😹", " ");
			emojiMap.put("😰", " ");
			emojiMap.put("😯", " ");
			emojiMap.put("😮", " ");
			emojiMap.put("😭", " ");
			emojiMap.put("😴", " ");
			emojiMap.put("😳", " ");
			emojiMap.put("🉑", " ");
			emojiMap.put("😲", " ");
			emojiMap.put("😱", " ");
			emojiMap.put("🉐", " ");
			emojiMap.put("🔓", " ");
			emojiMap.put("🔔", " ");
			emojiMap.put("🔑", " ");
			emojiMap.put("🔒", " ");
			emojiMap.put("🔏", " ");
			emojiMap.put("🔐", " ");
			emojiMap.put("🔍", " ");
			emojiMap.put("🔎", " ");
			emojiMap.put("🔛", " ");
			emojiMap.put("🔜", " ");
			emojiMap.put("🔙", " ");
			emojiMap.put("🔚", " ");
			emojiMap.put("🔗", " ");
			emojiMap.put("🔘", " ");
			emojiMap.put("🔕", " ");
			emojiMap.put("🔖", " ");
			emojiMap.put("🔄", " ");
			emojiMap.put("🔃", " ");
			emojiMap.put("🔂", " ");
			emojiMap.put("🔁", " ");
			emojiMap.put("🔀", " ");
			emojiMap.put("🔌", " ");
			emojiMap.put("🔋", " ");
			emojiMap.put("🔊", " ");
			emojiMap.put("🔉", " ");
			emojiMap.put("🔈", " ");
			emojiMap.put("🔇", " ");
			emojiMap.put("🔆", " ");
			emojiMap.put("🔅", " ");
			emojiMap.put("📱", " ");
			emojiMap.put("📲", " ");
			emojiMap.put("📳", " ");
			emojiMap.put("📴", " ");
			emojiMap.put("📭", " ");
			emojiMap.put("📮", " ");
			emojiMap.put("♓️", " ");
			emojiMap.put("📯", " ");
			emojiMap.put("📰", " ");
			emojiMap.put("📹", " ");
			emojiMap.put("📺", " ");
			emojiMap.put("📻", " ");
			emojiMap.put("📼", " ");
			emojiMap.put("📵", " ");
			emojiMap.put("📶", " ");
			emojiMap.put("📷", " ");
			emojiMap.put("📢", " ");
			emojiMap.put("📡", " ");
			emojiMap.put("☎️", " ");
			emojiMap.put("📤", " ");
			emojiMap.put("📣", " ");
			emojiMap.put("📞", " ");
			emojiMap.put("📝", " ");
			emojiMap.put("📠", " ");
			emojiMap.put("📟", " ");
			emojiMap.put("📪", " ");
			emojiMap.put("📩", " ");
			emojiMap.put("📬", " ");
			emojiMap.put("📫", " ");
			emojiMap.put("📦", " ");
			emojiMap.put("🇨🇳", " ");
			emojiMap.put("📥", " ");
			emojiMap.put("📨", " ");
			emojiMap.put("📧", " ");
			emojiMap.put("📏", " ");
			emojiMap.put("📐", " ");
			emojiMap.put("📍", " ");
			emojiMap.put("📎", " ");
			emojiMap.put("📓", " ");
			emojiMap.put("📔", " ");
			emojiMap.put("📑", " ");
			emojiMap.put("📒", " ");
			emojiMap.put("📗", " ");
			emojiMap.put("📘", " ");
			emojiMap.put("📕", " ");
			emojiMap.put("📖", " ");
			emojiMap.put("📛", " ");
			emojiMap.put("📜", " ");
			emojiMap.put("📙", " ");
			emojiMap.put("📚", " ");
			emojiMap.put("〰", " ");
			emojiMap.put("📀", " ");
			emojiMap.put("💿", " ");
			emojiMap.put("💾", " ");
			emojiMap.put("💽", " ");
			emojiMap.put("📄", " ");
			emojiMap.put("📃", " ");
			emojiMap.put("📂", " ");
			emojiMap.put("📁", " ");
			emojiMap.put("📈", " ");
			emojiMap.put("📇", " ");
			emojiMap.put("📆", " ");
			emojiMap.put("📅", " ");
			emojiMap.put("📌", " ");
			emojiMap.put("〽", " ");
			emojiMap.put("📋", " ");
			emojiMap.put("📊", " ");
			emojiMap.put("📉", " ");
			emojiMap.put("💭", " ");
			emojiMap.put("💮", " ");
			emojiMap.put("💯", " ");
			emojiMap.put("💰", " ");
			emojiMap.put("🃏", " ");
			emojiMap.put("💱", " ");
			emojiMap.put("💲", " ");
			emojiMap.put("💳", " ");
			emojiMap.put("💴", " ");
			emojiMap.put("⭐️", " ");
			emojiMap.put("💵", " ");
			emojiMap.put("💶", " ");
			emojiMap.put("💷", " ");
			emojiMap.put("ℹ", " ");
			emojiMap.put("💸", " ");
			emojiMap.put("💹", " ");
			emojiMap.put("💺", " ");
			emojiMap.put("💻", " ");
			emojiMap.put("💼", " ");
			emojiMap.put("™", " ");
			emojiMap.put("💞", " ");
			emojiMap.put("💝", " ");
			emojiMap.put("💠", " ");
			emojiMap.put("💟", " ");
			emojiMap.put("💢", " ");
			emojiMap.put("💡", " ");
			emojiMap.put("💤", " ");
			emojiMap.put("💣", " ");
			emojiMap.put("💦", " ");
			emojiMap.put("💥", " ");
			emojiMap.put("💨", " ");
			emojiMap.put("💧", " ");
			emojiMap.put("💪", " ");
			emojiMap.put("💩", " ");
			emojiMap.put("💬", " ");
			emojiMap.put("💫", " ");
			emojiMap.put("🆙", " ");
			emojiMap.put("🆘", " ");
			emojiMap.put("🆚", " ");
			emojiMap.put("🆕", " ");
			emojiMap.put("🆔", " ");
			emojiMap.put("🆗", " ");
			emojiMap.put("🆖", " ");
			emojiMap.put("🆑", " ");
			emojiMap.put("🆓", " ");
			emojiMap.put("🆒", " ");
			emojiMap.put("🆎", " ");
			emojiMap.put("🕥", " ");
			emojiMap.put("🕦", " ");
			emojiMap.put("🕧", " ");
			emojiMap.put("🕡", " ");
			emojiMap.put("🕢", " ");
			emojiMap.put("🕣", " ");
			emojiMap.put("🕤", " ");
			emojiMap.put("🕝", " ");
			emojiMap.put("🕞", " ");
			emojiMap.put("🕟", " ");
			emojiMap.put("🅾", " ");
			emojiMap.put("🕠", " ");
			emojiMap.put("🅿", " ");
			emojiMap.put("🕘", " ");
			emojiMap.put("↘", " ");
			emojiMap.put("🕗", " ");
			emojiMap.put("↙", " ");
			emojiMap.put("🕖", " ");
			emojiMap.put("🕕", " ");
			emojiMap.put("🕜", " ");
			emojiMap.put("🕛", " ");
			emojiMap.put("🕚", " ");
			emojiMap.put("🕙", " ");
			emojiMap.put("🕐", " ");
			emojiMap.put("🕔", " ");
			emojiMap.put("↔", " ");
			emojiMap.put("🕓", " ");
			emojiMap.put("↕", " ");
			emojiMap.put("🕒", " ");
			emojiMap.put("🅱", " ");
			emojiMap.put("↖", " ");
			emojiMap.put("🕑", " ");
			emojiMap.put("🅰", " ");
			emojiMap.put("↗", " ");
			emojiMap.put("🔽", " ");
			emojiMap.put("🔶", " ");
			emojiMap.put("🔵", " ");
			emojiMap.put("🔸", " ");
			emojiMap.put("🔷", " ");
			emojiMap.put("🔺", " ");
			emojiMap.put("🔹", " ");
			emojiMap.put("🔼", " ");
			emojiMap.put("🔻", " ");
			emojiMap.put("🔮", " ");
			emojiMap.put("🔭", " ");
			emojiMap.put("🔰", " ");
			emojiMap.put("🔯", " ");
			emojiMap.put("🔲", " ");
			emojiMap.put("🔱", " ");
			emojiMap.put("🔴", " ");
			emojiMap.put("🔳", " ");
			emojiMap.put("🔥", " ");
			emojiMap.put("🔦", " ");
			emojiMap.put("↪", " ");
			emojiMap.put("🔧", " ");
			emojiMap.put("↩", " ");
			emojiMap.put("🔨", " ");
			emojiMap.put("🔩", " ");
			emojiMap.put("🔪", " ");
			emojiMap.put("🔫", " ");
			emojiMap.put("⤵", " ");
			emojiMap.put("🔬", " ");
			emojiMap.put("⤴", " ");
			emojiMap.put("🔝", " ");
			emojiMap.put("🔞", " ");
			emojiMap.put("🔟", " ");
			emojiMap.put("🔠", " ");
			emojiMap.put("🔡", " ");
			emojiMap.put("🔢", " ");
			emojiMap.put("🔣", " ");
			emojiMap.put("🔤", " ");
			emojiMap.put("🇺🇸", " ");
			emojiMap.put("⛪️", " ");
			emojiMap.put("☺", " ");
			emojiMap.put("☎", " ");
			emojiMap.put("Ⓜ️", " ");
			emojiMap.put("☀", " ");
			emojiMap.put("☁", " ");
			emojiMap.put("☝", " ");
			emojiMap.put("☕", " ");
			emojiMap.put("☔", " ");
			emojiMap.put("☑", " ");
			emojiMap.put("♨", " ");
			emojiMap.put("🀄", " ");
			emojiMap.put("♠", " ");
			emojiMap.put("♣", " ");
			emojiMap.put("♥", " ");
			emojiMap.put("♦", " ");
			emojiMap.put("♻", " ");
			emojiMap.put("♿", " ");
			emojiMap.put("↔️", " ");
			emojiMap.put("♊", " ");
			emojiMap.put("🐆", " ");
			emojiMap.put("♋", " ");
			emojiMap.put("🐅", " ");
			emojiMap.put("♈", " ");
			emojiMap.put("🐈", " ");
			emojiMap.put("♉", " ");
			emojiMap.put("🐇", " ");
			emojiMap.put("♎", " ");
			emojiMap.put("🐊", " ");
			emojiMap.put("♏", " ");
			emojiMap.put("🐉", " ");
			emojiMap.put("♌", " ");
			emojiMap.put("🐌", " ");
			emojiMap.put("♍", " ");
			emojiMap.put("🐋", " ");
			emojiMap.put("🐀", " ");
			emojiMap.put("🐂", " ");
			emojiMap.put("🐁", " ");
			emojiMap.put("🐄", " ");
			emojiMap.put("🐃", " ");
			emojiMap.put("🐕", " ");
			emojiMap.put("🐖", " ");
			emojiMap.put("🐗", " ");
			emojiMap.put("🐘", " ");
			emojiMap.put("🐙", " ");
			emojiMap.put("🐚", " ");
			emojiMap.put("🐛", " ");
			emojiMap.put("🐜", " ");
			emojiMap.put("♓", " ");
			emojiMap.put("🐍", " ");
			emojiMap.put("♒", " ");
			emojiMap.put("🐎", " ");
			emojiMap.put("♑", " ");
			emojiMap.put("🐏", " ");
			emojiMap.put("♐", " ");
			emojiMap.put("🐐", " ");
			emojiMap.put("🐑", " ");
			emojiMap.put("🐒", " ");
			emojiMap.put("🐓", " ");
			emojiMap.put("🐔", " ");
			emojiMap.put("🐣", " ");
			emojiMap.put("🐤", " ");
			emojiMap.put("🐡", " ");
			emojiMap.put("🐢", " ");
			emojiMap.put("⚡", " ");
			emojiMap.put("🐟", " ");
			emojiMap.put("🐠", " ");
			emojiMap.put("⚠", " ");
			emojiMap.put("🐝", " ");
			emojiMap.put("🐞", " ");
			emojiMap.put("🐫", " ");
			emojiMap.put("🐬", " ");
			emojiMap.put("🐩", " ");
			emojiMap.put("🐪", " ");
			emojiMap.put("🐧", " ");
			emojiMap.put("🐨", " ");
			emojiMap.put("⚫", " ");
			emojiMap.put("🐥", " ");
			emojiMap.put("⚪", " ");
			emojiMap.put("🐦", " ");
			emojiMap.put("🐴", " ");
			emojiMap.put("🐳", " ");
			emojiMap.put("🐲", " ");
			emojiMap.put("⬅️", " ");
			emojiMap.put("🐱", " ");
			emojiMap.put("🐰", " ");
			emojiMap.put("🐯", " ");
			emojiMap.put("🐮", " ");
			emojiMap.put("🐭", " ");
			emojiMap.put("0⃣", " ");
			emojiMap.put("🐼", " ");
			emojiMap.put("🐻", " ");
			emojiMap.put("⚽", " ");
			emojiMap.put("🐺", " ");
			emojiMap.put("⚾", " ");
			emojiMap.put("🐹", " ");
			emojiMap.put("🐸", " ");
			emojiMap.put("🐷", " ");
			emojiMap.put("🐶", " ");
			emojiMap.put("🐵", " ");
			emojiMap.put("👂", " ");
			emojiMap.put("👃", " ");
			emojiMap.put("👄", " ");
			emojiMap.put("🐽", " ");
			emojiMap.put("🐾", " ");
			emojiMap.put("👀", " ");
			emojiMap.put("👉", " ");
			emojiMap.put("👊", " ");
			emojiMap.put("👋", " ");
			emojiMap.put("👌", " ");
			emojiMap.put("▶️", " ");
			emojiMap.put("👅", " ");
			emojiMap.put("👆", " ");
			emojiMap.put("👇", " ");
			emojiMap.put("👈", " ");
			emojiMap.put("👒", " ");
			emojiMap.put("👑", " ");
			emojiMap.put("👔", " ");
			emojiMap.put("👓", " ");
			emojiMap.put("👎", " ");
			emojiMap.put("👍", " ");
			emojiMap.put("⚓", " ");
			emojiMap.put("👐", " ");
			emojiMap.put("👏", " ");
			emojiMap.put("👚", " ");
			emojiMap.put("👙", " ");
			emojiMap.put("👜", " ");
			emojiMap.put("👛", " ");
			emojiMap.put("👖", " ");
			emojiMap.put("👕", " ");
			emojiMap.put("👘", " ");
			emojiMap.put("👗", " ");
			emojiMap.put("👟", " ");
			emojiMap.put("👠", " ");
			emojiMap.put("👝", " ");
			emojiMap.put("👞", " ");
			emojiMap.put("👣", " ");
			emojiMap.put("👤", " ");
			emojiMap.put("❤️", " ");
			emojiMap.put("👡", " ");
			emojiMap.put("👢", " ");
			emojiMap.put("👧", " ");
			emojiMap.put("👨", " ");
			emojiMap.put("👥", " ");
			emojiMap.put("⛪", " ");
			emojiMap.put("👦", " ");
			emojiMap.put("👫", " ");
			emojiMap.put("👬", " ");
			emojiMap.put("👩", " ");
			emojiMap.put("👪", " ");
			emojiMap.put("👰", " ");
			emojiMap.put("2⃣", " ");
			emojiMap.put("🇪🇸", " ");
			emojiMap.put("👯", " ");
			emojiMap.put("⛲", " ");
			emojiMap.put("👮", " ");
			emojiMap.put("⛳", " ");
			emojiMap.put("👭", " ");
			emojiMap.put("⬇️", " ");
			emojiMap.put("👴", " ");
			emojiMap.put("⛵", " ");
			emojiMap.put("👳", " ");
			emojiMap.put("👲", " ");
			emojiMap.put("👱", " ");
			emojiMap.put("👸", " ");
			emojiMap.put("👷", " ");
			emojiMap.put("⛺", " ");
			emojiMap.put("👶", " ");
			emojiMap.put("👵", " ");
			emojiMap.put("👼", " ");
			emojiMap.put("⛽", " ");
			emojiMap.put("👻", " ");
			emojiMap.put("👺", " ");
			emojiMap.put("👹", " ");
			emojiMap.put("👽", " ");
			emojiMap.put("👾", " ");
			emojiMap.put("👿", " ");
			emojiMap.put("💀", " ");
			emojiMap.put("💁", " ");
			emojiMap.put("💂", " ");
			emojiMap.put("💃", " ");
			emojiMap.put("⛅", " ");
			emojiMap.put("💄", " ");
			emojiMap.put("⛄", " ");
			emojiMap.put("💅", " ");
			emojiMap.put("💆", " ");
			emojiMap.put("💇", " ");
			emojiMap.put("💈", " ");
			emojiMap.put("💉", " ");
			emojiMap.put("💊", " ");
			emojiMap.put("⛎", " ");
			emojiMap.put("💋", " ");
			emojiMap.put("💌", " ");
			emojiMap.put("1⃣", " ");
			emojiMap.put("💎", " ");
			emojiMap.put("💍", " ");
			emojiMap.put("💐", " ");
			emojiMap.put("💏", " ");
			emojiMap.put("💒", " ");
			emojiMap.put("💑", " ");
			emojiMap.put("💔", " ");
			emojiMap.put("⛔", " ");
			emojiMap.put("⬆️", " ");
			emojiMap.put("💓", " ");
			emojiMap.put("💖", " ");
			emojiMap.put("💕", " ");
			emojiMap.put("🇬🇧", " ");
			emojiMap.put("💘", " ");
			emojiMap.put("💗", " ");
			emojiMap.put("💚", " ");
			emojiMap.put("💙", " ");
			emojiMap.put("💜", " ");
			emojiMap.put("💛", " ");
			emojiMap.put("✴", " ");
			emojiMap.put("✳", " ");
			emojiMap.put("⚫️", " ");
			emojiMap.put("4⃣", " ");
			emojiMap.put("✨", " ");
			emojiMap.put("♥️", " ");
			emojiMap.put("⛳️", " ");
			emojiMap.put("↙️", " ");
			emojiMap.put("✔", " ");
			emojiMap.put("✖", " ");
			emojiMap.put("3⃣", " ");
			emojiMap.put("✒", " ");
			emojiMap.put("✌", " ");
			emojiMap.put("✏", " ");
			emojiMap.put("✈", " ");
			emojiMap.put("✉", " ");
			emojiMap.put("✊", " ");
			emojiMap.put("✋", " ");
			emojiMap.put("♦️", " ");
			emojiMap.put("✅", " ");
			emojiMap.put("⛲️", " ");
			emojiMap.put("✂", " ");
			emojiMap.put("6⃣", " ");
			emojiMap.put("♣️", " ");
			emojiMap.put("❤", " ");
			emojiMap.put("⚪️", " ");
			emojiMap.put("❓", " ");
			emojiMap.put("❕", " ");
			emojiMap.put("❔", " ");
			emojiMap.put("❗", " ");
			emojiMap.put("◻️", " ");
			emojiMap.put("❌", " ");
			emojiMap.put("5⃣", " ");
			emojiMap.put("❎", " ");
			emojiMap.put("❄", " ");
			emojiMap.put("❇", " ");
			emojiMap.put("↖️", " ");
			emojiMap.put("☺️", " ");
			emojiMap.put("➰", " ");
			emojiMap.put("➿", " ");
			emojiMap.put("➡", " ");
			emojiMap.put("◼️", " ");
			emojiMap.put("8⃣", " ");
			emojiMap.put("↕️", " ");
			emojiMap.put("➕", " ");
			emojiMap.put("➖", " ");
			emojiMap.put("➗", " ");
			emojiMap.put("✴️", " ");
			emojiMap.put("7⃣", " ");
			emojiMap.put("◽️", " ");
			emojiMap.put("↘️", " ");
			emojiMap.put("✳️", " ");
			emojiMap.put("⛵️", " ");
			emojiMap.put("◾️", " ");
			emojiMap.put("〽️", " ");
			emojiMap.put("↗️", " ");
			emojiMap.put("➡️", " ");
			emojiMap.put("🈚️", " ");
			emojiMap.put("9⃣", " ");
			emojiMap.put("♨️", " ");
			emojiMap.put("⛺️", " ");
			emojiMap.put("⛅️", " ");
			emojiMap.put("♿️", " ");
			emojiMap.put("⛄️", " ");
			emojiMap.put("▪️", " ");
			emojiMap.put("❗️", " ");
			emojiMap.put("▫️", " ");
			emojiMap.put("🇮🇹", " ");
			emojiMap.put("Ⓜ", " ");
			emojiMap.put("🇰🇷", " ");
			emojiMap.put("⚾️", " ");
			emojiMap.put("⛽️", " ");
			emojiMap.put("🇷🇺", " ");
			emojiMap.put("✂️", " ");
			emojiMap.put("⚽️", " ");
			emojiMap.put("#⃣", " ");
			emojiMap.put("⤴️", " ");
			emojiMap.put("🈯️", " ");
			emojiMap.put("⤵️", " ");
			emojiMap.put("❇️", " ");
			emojiMap.put("♻️", " ");
			emojiMap.put("⌛️", " ");
			emojiMap.put("❄️", " ");
			emojiMap.put("🀄️", " ");
			emojiMap.put("▶", " ");
			emojiMap.put("▫", " ");
			emojiMap.put("▪", " ");
			emojiMap.put("◀", " ");
			emojiMap.put("◾", " ");
			emojiMap.put("◼", " ");
			emojiMap.put("◽", " ");
			emojiMap.put("◻", " ");
			emojiMap.put("⌚️", " ");

		}

		return emojiMap;

	}

	public String emoji2Ubb(String str) {
		if (null != str) {
			Set<String> emojiSet = getEmojiMap().keySet();
			for (String emoji : emojiSet) {
				str = str.replace(emoji, emojiMap.get(emoji));
			}
		}
		return filterEmoji(str);
	}

	public boolean containEmojiUbb(String str) {
		if (null != str) {
			Set<String> emojiSet = getEmojiMap().keySet();
			for (String emoji : emojiSet) {
				if (emoji.equals(str)) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean isNotEmojiCharacter(char codePoint) {
		return (codePoint == 0x0) || (codePoint == 0x9) || (codePoint == 0xA) || (codePoint == 0xD) || ((codePoint >= 0x20) && (codePoint <= 0xD7FF))
				|| ((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) || ((codePoint >= 0x10000) && (codePoint <= 0x10FFFF));
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 * 
	 * @param source
	 * @return
	 */
	public String filterEmoji(String source) {
		StringBuilder buf = new StringBuilder();
		int len = source.length();
		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isNotEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			}
		}
		return buf.toString();
	}

	public static void main(String[] args) throws UnsupportedEncodingException {
		// \xF0\x9F\x98\x84\xF0\x9F
		// \xF0\x9F\x8F\xA9
		// f0 9f 98 9c
		String str = new String(new byte[] { (byte) 0xf0, (byte) 0x9f, (byte) 0x8F, (byte) 0xA9 });
		String strb = "🏩";
		// LogUtil.i(EmojiUtil.INSTANCE.getEmojiMap());
	}
}
