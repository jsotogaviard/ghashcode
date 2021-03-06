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
import java.util.List;
import java.util.ListIterator;

import com.trio.process.OutputWriter;
import com.trio.process.ResultServer;

/**
 *
 * @author Quartet FS
 */
public class ReadInput {
	
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
	public ReadInput() {}
	
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
		ReadInput read = new ReadInput();
		read.read();
		System.out.println(read.servers);
		List<ResultServer> result = read.putServers();
		read.writeResult(result);
	}

	public void writeResult(List<ResultServer> result) {
		for (Server s : servers) {
			result.add(new ResultServer(s.id, false, -1, -1, -1));
		}

		try {
			new OutputWriter().write(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<ResultServer> putServers() {
		final Datacenter dc = new Datacenter(numRows, numColumns, groupsToCreate);
		for (UnavalaibleSpace space : unavalaibleSpaces) {
			dc.putUnavailableSpace(space);
		}
		dc.printMap();
		int line = 0;
		final ListIterator<Server> it = servers.listIterator();
		int nbrFailures = 0;
		MAIN: while (it.hasNext() && nbrFailures < numRows) {
			Server s = it.next();
			int toRewind = 0;
			if (s.id == 11) {
				System.out.println("found");
			}
			boolean inWhile = false;
			while (!dc.putServer(line, s)) {
				s = findNextSizeServer(it, s.size);
				if (s == null) {
					++nbrFailures;
					continue MAIN;
				}
				++toRewind;
				inWhile = true;
			}
			if (s.id == 11) {
				System.out.println("found " + it);
			}
			if (!inWhile) {
				it.remove();
			}
			--toRewind;
			for (int i = 0; i < toRewind - 1; ++i) {
				it.previous();
			}
			++line;
			line = (line + numRows) % numRows;
		}

		return dc.getResult();
	}

	public Server findNextSizeServer(final ListIterator<Server> it, int size) {
		while(size >=0) {
			int toRewind = 0;
			try {
				while (it.hasNext()) {
					++toRewind;
					final Server s = it.next();
					if (s.size == size - 1) {
						it.remove();
						return s;
					}
				}
			} finally {
				for (int i = 0; i < toRewind - 1; ++i) {
					it.previous();
				}
			}
			size--;
		}

		return null;
	}

}
