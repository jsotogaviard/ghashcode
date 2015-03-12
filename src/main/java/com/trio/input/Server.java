/*
 * (C) Quartet FS 2007-2015
 * ALL RIGHTS RESERVED. This material is the CONFIDENTIAL and PROPRIETARY
 * property of Quartet Financial Systems Limited. Any unauthorized use,
 * reproduction or transfer of this material is strictly prohibited
 */
package com.trio.input;

/**
 *
 * @author Quartet FS
 */
public class Server implements Comparable<Server>{
	
	public int size;
	public int capacity;
	/** Constructor
	 * @param size
	 * @param capacity
	 */
	public Server(int size, int capacity) {
		super();
		this.size = size;
		this.capacity = capacity;
	}
	@Override
	public int compareTo(Server o) {
		if(this.capacity - o.capacity < 0){
			return -1;
		} else if(this.capacity - o.capacity > 0){
			return 1;
		} else {
			return 0;
		}
	}
	@Override
	public String toString() {
		return "Server [size=" + this.size + ", capacity=" + this.capacity
				+ "]";
	}
	
	

}
