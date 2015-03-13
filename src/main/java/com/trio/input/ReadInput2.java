/*
 * (C) Quartet FS 2007-2015
 * ALL RIGHTS RESERVED. This material is the CONFIDENTIAL and PROPRIETARY
 * property of Quartet Financial Systems Limited. Any unauthorized use,
 * reproduction or transfer of this material is strictly prohibited
 */
package com.trio.input;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.trio.process.OutputWriter;
import com.trio.process.ResultServer;

/**
 *
 * @author Quartet FS
 */
public class ReadInput2 {

	public int numRows;

	public int numColumns ;

	public int unavalaibleSpacesNumber;

	public int groupsToCreate;

	public int serversNumber;

	public List<UnavalaibleSpace> unavalaibleSpaces;

	public List<Server> servers;

	/** Constructor
	 * 
	 */
	public ReadInput2() {}

	public void read() throws IOException{
		final List<String> lines = Files.readAllLines(Paths.get("src/main/resources/dc.in"), Charset.defaultCharset());
		String line0 = lines.get(0);
		String[] lineArray = line0.split(" ");
		numRows = Integer.parseInt(lineArray[0]);
		numColumns = Integer.parseInt(lineArray[1]);
		unavalaibleSpacesNumber = Integer.parseInt(lineArray[2]);
		groupsToCreate = Integer.parseInt(lineArray[3]);
		serversNumber =  Integer.parseInt(lineArray[4]);
		unavalaibleSpaces = new ArrayList<>();
		for (int i = 0; i < unavalaibleSpacesNumber; i++) {
			line0 = lines.get(i + 1);
			lineArray = line0.split(" ");
			unavalaibleSpaces.add(new UnavalaibleSpace(Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1])));
		}
		servers = new ArrayList<>();
		for (int i = 0; i < serversNumber; i++) {
			line0 = lines.get(i + 1 + unavalaibleSpacesNumber);
			lineArray = line0.split(" ");
			// Place and capacity
			servers.add(new Server(i, Integer.parseInt(lineArray[0]), Integer.parseInt(lineArray[1])));
		}
		Collections.sort(servers);
	}

	public static void main(String[] args) throws IOException {
		ReadInput2 read = new ReadInput2();
		read.read();
		System.out.println(read.servers);
		List<ResultServer> result = read.putServers();
		new OutputWriter().write(result);
	}

	public List<ResultServer> putServers() {
		final Datacenter dc = new Datacenter(numRows, numColumns, groupsToCreate);
		for (UnavalaibleSpace space : unavalaibleSpaces) {
			dc.putUnavailableSpace(space);
		}

		Set<Integer> addedServers = new HashSet<>();
		for (int i = 0; i < servers.size(); i++) {
			Server serv = servers.get(i);
			MAIN : for (int j = numRows - 1 ; j >= 0; j--) {
				if(dc.putServer(j, serv)){
					addedServers.add(i);
					break MAIN;
				}			
			}
		}

		for (int i = 0; i < servers.size(); i++) {
			if (!addedServers.contains(i)) {
				dc.result.add(new ResultServer(servers.get(i).id, false, 0,0,0));
			}
		}
		return dc.getResult();
	}


}
