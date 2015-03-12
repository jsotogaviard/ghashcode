/*
 * (C) Quartet FS 2007-2015
 * ALL RIGHTS RESERVED. This material is the CONFIDENTIAL and PROPRIETARY
 * property of Quartet Financial Systems Limited. Any unauthorized use,
 * reproduction or transfer of this material is strictly prohibited
 */
package com.trio.process;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import com.trio.input.ReadInput;
import com.trio.input.Server;

/**
 *
 * @author Quartet FS
 */
public class ServerPosition {
	
	public static void main(String[] args) throws IOException {
		ReadInput read = new ReadInput();
		read.read();
		
		List<Server> servers = read.servers;
		Collections.sort(servers);
		
		
		
	}

}
