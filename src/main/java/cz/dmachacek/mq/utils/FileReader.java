/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.dmachacek.mq.utils;

import java.io.*;
import java.util.Scanner;

/**
 *
 * @author dmachace
 */
public class FileReader {

	public final String getFileContent(String fileName) {
		StringBuilder result = new StringBuilder("");
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());

		try (Scanner scanner = new Scanner(file)) {
			if (fileName.contains("xml")) {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line).append("\n");
				}
			} else {
				while (scanner.hasNextLine()) {
					String line = scanner.nextLine();
					result.append(line);
				}
			}

			scanner.close();

		} catch (IOException e) {
		}

		return result.toString();
	}

	public byte[] getFileContentByte(String fileName) throws IOException {
		File file = new File(getClass().getClassLoader().getResource(fileName).getFile());
		ByteArrayOutputStream ous = null;
		InputStream ios = null;
		try {
			byte[] buffer = new byte[4096];
			ous = new ByteArrayOutputStream();
			ios = new FileInputStream(file);
			int read = 0;
			while ((read = ios.read(buffer)) != -1) {
				ous.write(buffer, 0, read);
			}
		}finally {
			try {
				if (ous != null)
					ous.close();
			} catch (IOException e) {
			}

			try {
				if (ios != null)
					ios.close();
			} catch (IOException e) {
			}
		}
		return ous.toByteArray();
	}
}
