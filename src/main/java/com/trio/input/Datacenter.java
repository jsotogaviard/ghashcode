package com.trio.input;

import java.util.ArrayList;
import java.util.List;

import com.trio.process.ResultServer;

/*
 * (C) Quartet FS 2015
 * ALL RIGHTS RESERVED. This material is the CONFIDENTIAL and PROPRIETARY
 * property of Quartet Financial Systems Limited. Any unauthorized use,
 * reproduction or transfer of this material is strictly prohibited
 */

/**
 * @author Quartet FS
 *
 */
public class Datacenter {

	boolean[][] map;

	List<ResultServer> result;

	private int groupIndex;

	private int nbMaxGroups;

	public Datacenter(int nbRows, int rowSize, int nbMaxGroups) {
		this.map = new boolean[nbRows][rowSize];
		this.result = new ArrayList<ResultServer>();
		this.groupIndex = 0;
		this.nbMaxGroups = nbMaxGroups;
	}

	public boolean mapAddServer(int row, int index, int size, int id) {
		if (checkAddServer(row, index, size)) {
			for (int i = 0; i < size; i++) {
				this.map[row][index + i] = true;
			}
			this.result.add(new ResultServer(id, true, row, index, (groupIndex++) % nbMaxGroups));
			return true;
		} else {
			throw new IllegalStateException(size + " --- " + id);
		}
	}

	public boolean checkAddServer(int row, int index, int size) {
		for (int i = 0; i < size; i++) {
			if (this.map[row][index + i]) {
				return false;
			}
		}
		return true;
	}

	public boolean putServer(int row, Server server){
		for (int columnIndex = 0; columnIndex < this.map[row].length - server.size; columnIndex++) {
			int cpt = 0;
			for (int j = 0; j < server.size; j++){
				if (!this.map[row][columnIndex + j]) {
					cpt++;
					if (cpt == server.size){
						// Found
						return mapAddServer(row, columnIndex, server.size, server.id);
					}
				} else {
					break;
				}
			}
		}
		return false;
	}

	public void putUnavailableSpace(UnavalaibleSpace space) {
		if (this.map[space.row][space.column]) {
			throw new IllegalStateException(space.row + " --- " + space.column);
		}
		this.map[space.row][space.column] = true;
	}

	public List<ResultServer> getResult() {
		return result;
	}

	public void printMap() {
//		System.out.println("map");
//		for (boolean[] a : map) {
//			System.out.println(Arrays.toString(a));
//		}
	}


}
