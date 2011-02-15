package org.harper.frm.core.tools.insert;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.harper.frm.core.tools.insert.BinaryInserter;
import org.harper.frm.core.tools.sort.HeapSorter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;



public class BinaryInserterTest {
	List<String> values = new ArrayList<String>();
	List<String> blank = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		for (int i = 0; i < 10; i++) {
			Random len = new Random(i);
			int length = (int) (len.nextDouble() * 5);
			String str = "";
			for (int j = 0; j < length; j++) {
				str += (char) ('a' + len.nextDouble() * 25);
			}
			values.add(str);
		}
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testBlankInsert() {
		new BinaryInserter().insert("add", blank);
	}

	@Test
	public void testInsertTListOfTComparatorOfT() {
		new HeapSorter(true).sort(values);
		new BinaryInserter().insert("asdfwe", values);
		
		for(int i = 0 ; i < values.size()-1; i ++ ) {
			assertTrue(values.get(i).compareTo(values.get(i+1))<=0);
		}
	}

	@Test
	public void testInsertTListOfTComparatorOfT2() {
		new HeapSorter(true).sort(values);
		
		new BinaryInserter().insert(values.get(values.size()-1), values,false,null);
		
		for(int i = 0 ; i < values.size()-1; i ++ ) {
			assertTrue(values.get(i).compareTo(values.get(i+1))<=0);
		}
	}

}
