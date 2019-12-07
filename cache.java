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
	
	for(int i = 0; i < 16; i++){
		System.out.println(cache[i][0] + " " + cache[i][1] + " " + cache[i][2] + " " + cache[i][3]); 
	}


	}
	
}

