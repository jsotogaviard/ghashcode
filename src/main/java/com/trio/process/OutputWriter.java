/*
 * (C) Quartet FS 2007-2015
 * ALL RIGHTS RESERVED. This material is the CONFIDENTIAL and PROPRIETARY
 * property of Quartet Financial Systems Limited. Any unauthorized use,
 * reproduction or transfer of this material is strictly prohibited
 */
package com.trio.process;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Quartet FS
 */
public class OutputWriter {
	
	public void write(List<ResultServer> resultServer) throws IOException{
		
		File file = new File("src/main/resources/out.txt");
		 
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		
		Collections.sort(resultServer, new Comparator<ResultServer>() {
			@Override
			public int compare(ResultServer o1, ResultServer o2) {
				return Integer.compare(o1.id, o2.id);
			}
		});

		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		int cpt = 0;
		for (ResultServer server : resultServer) {
			 System.out.println(cpt + "-" + server.toString());
			bw.write(server.toString());
			bw.newLine();
			cpt++;
		}
		
		bw.close();

	}

}
