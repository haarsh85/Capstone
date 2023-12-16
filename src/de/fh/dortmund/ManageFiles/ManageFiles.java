package de.fh.dortmund.ManageFiles;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ManageFiles {

	private BufferedWriter fr1;

	public void deleteFile(String file) {

		try {
			Files.delete(getPath(file));
			printLogs1(file + " is now deleted.");
		} catch (IOException e) {
			printLogs1(e.getMessage());
		}
	}

	public void moveFile(String file, String newPath) {
		Path path = Paths.get(newPath);
		try {
			Files.move(getPath(file), path, StandardCopyOption.REPLACE_EXISTING);
			printLogs1(file + " is now moved to new Path: " + newPath);
		} catch (IOException e) {
			printLogs1(e.getMessage());
		}
	}

	public void archieve(String file) {
		String[] fileFirstName = file.split(Pattern.quote("."));
		try {
			ZipOutputStream zos = new ZipOutputStream(
					new FileOutputStream(fileFirstName[0] + LocalDateTime.now().getDayOfMonth() + "-"
							+ LocalDateTime.now().getMonthValue() + "-" + LocalDateTime.now().getYear() + ".zip"));
			File fileToZip = new File(file);
			FileInputStream fis = new FileInputStream(fileToZip);
			ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
			zos.putNextEntry(zipEntry);
			byte[] bytes = new byte[1024];
			int length;
			while ((length = fis.read(bytes)) >= 0) {
				zos.write(bytes, 0, length);
			}
			printLogs1(file + " is not archieved");
			zos.close();
			fis.close();
		} catch (IOException e) {
			printLogs1(e.getMessage());
		}
	}

	public void printLogs1(String msg) {

		try {
			fr1 = new BufferedWriter(new FileWriter("ManageFile.txt", true));
			fr1.write(msg);
			fr1.newLine();
			fr1.close();
		} catch (IOException e) {
			System.out.println("Error while writing to log file" + e.getMessage());
		}
	}

	public Path getPath(String file) {
		return (new File(file).toPath());
	}

}
