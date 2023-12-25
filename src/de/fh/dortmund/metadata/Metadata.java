package de.fh.dortmund.metadata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class Metadata {

	public void writeMetatdata(String filename) {

		Path path1 = new File(filename).toPath();
		try {
			BasicFileAttributes attr = Files.readAttributes(path1, BasicFileAttributes.class);
			writeMetadatatoLogs("Size of " + filename + " is " + attr.size());
			writeMetadatatoLogs(filename + " was last modified on  " + attr.lastModifiedTime().toString());
			writeMetadatatoLogs(filename + " is a regular file - " + attr.isRegularFile());
		} catch (IOException e) {
			writeMetadatatoLogs("Error while writing to log file " + e.getMessage());
		}
	}

	private void writeMetadatatoLogs(String msg) {
		try (BufferedWriter fr = new BufferedWriter(new FileWriter("Metadata.log", true))) {
			fr.write(msg);
			fr.newLine();
		} catch (IOException e) {
			System.out.println("Error while writing to log file " + e.getMessage());
		}
	}

}
