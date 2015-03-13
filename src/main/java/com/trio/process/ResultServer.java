/*
 * (C) Quartet FS 2007-2015
 * ALL RIGHTS RESERVED. This material is the CONFIDENTIAL and PROPRIETARY
 * property of Quartet Financial Systems Limited. Any unauthorized use,
 * reproduction or transfer of this material is strictly prohibited
 */
package com.trio.process;

/**
 *
 * @author Quartet FS
 */
public class ResultServer {
	
	protected int row;
	
	protected int id;
	
	protected int column;
	
	protected int group;
	
	protected boolean used;

	/** Constructor
	 * @param row
	 * @param column
	 * @param group
	 */
	public ResultServer(int id, boolean used, int row, int column, int group) {
		super();
		this.row = row;
		this.column = column;
		this.group = group;
		this.used = used;
		this.id = id;
	}
	
	@Override
	public String toString() {
		if (used) {
			return row + " " + column + " " + group + " " ;
		} else {
			return "x";
		}
	}
	
	
	
	

}
