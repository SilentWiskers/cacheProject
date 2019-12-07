import java.io.*;
import java.util.*;
import java.nio.*;
import java.util.regex.*;
import java.util.Scanner;
import java.io.FileInputStream;


public class cache{


	public static void main(String[] args){
	
	int inputs[]=new int[20];

	String thisLine = null;
      		int count = 0;
      		try {
         	   
         	   BufferedReader br = new BufferedReader(new FileReader(args[0]));
         
         	   while ((thisLine = br.readLine()) != null) {
		      inputs[count] = Integer.parseInt(thisLine);
		      count++;
         	   }       
      		}catch(Exception e) {
        	   e.printStackTrace();
      		}

	int test = 7 % 4;
	//System.out.println(test);
	int sets = inputs[0];
	int[][] cache= new int[sets][5];

	int offset_lim=4;
	int index_lim=16;
	int tag=0;
	int index=0;
	int offset=0;

	for(int addr = 0; addr < sets; addr++){
		if(offset==offset_lim){
			offset = 0;
			index++;
		}
		if(index==index_lim){
			index = 0;
			tag++;
		}
		cache[addr][0] = addr;
		cache[addr][1] = tag;
		cache[addr][2] = index;
		cache[addr][3] = offset;
		cache[addr][4] = 0;
		offset++;
	}
	
	printHeader(sets, offset_lim, offset_lim);
	
	//while
	int addr = 8;
	int start = 0;
	int begin = 0;
	if(cache[addr][4] == 0){
		//print miss
		if(addr < offset_lim){
			start = offset_lim % addr;
		}
		else{
			start = addr % offset_lim;
		}
		begin = addr-start;
		for(int i = 0; i < 4; i++){
			cache[begin][4] = 1;
			begin++;
		}
	}
	else if (cache[addr][4] == 1){
		//print hit	
	}
	

	}

	public static void printHeader(int set, int setSize, int lineSize){
		System.out.println("Sets: " + set);
        	System.out.println("Cache Configuration");
        	System.out.println("");
        	System.out.println("        " + set + " " + setSize + "-way set associative entries");
        	System.out.println("        of line size " + lineSize + " bytes");
        	System.out.println("");
        	System.out.printf("%6s%8s%6s%7s%8s%7s%8s%n", "Access", "Address", "Tag", "Index", "Offset", "Status", "Memrefs");
        	System.out.println("------ -------- ------ ----- ------ ------ -------");
	}
	
}

