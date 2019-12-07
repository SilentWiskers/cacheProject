import java.io.*;
import java.util.*;
import java.nio.*;
import java.util.regex.*;
import java.util.Scanner;
import javafx.util.Pair;


public class cache{

	private static int sets = 0;
	private static int set_size = 0;
	private static int line_size = 0;
	private static ArrayList<Integer> actions_val;
	private static ArrayList<Boolean> actions;//0 for read, 1 for write

	public static int read(String file_name){
		BufferedReader reader;
		int num_actions = 0;
		try{
			reader = new BufferedReader(new FileReader(file_name));

			/*
			 * Message to Roshan:
			 * Hi Roshan, I hope this message finds you well. Please have mercy on our program,
			 * it is the end of the semester and we have been working very hard.
			 */
			String line = reader.readLine();
			line = line.substring(line.indexOf(':') + 1);
			sets = Integer.parseInt(line);

			line = reader.readLine();
			line = line.substring(line.indexOf(':') + 1);
			set_size = Integer.parseInt(line);

			line = reader.readLine();
			line = line.substring(line.indexOf(':') + 1);
			line_size = Integer.parseInt(line);

			actions_val = new ArrayList<Integer>();
			actions = new ArrayList<Boolean>();

			line = reader.readLine();
			while(line != null){
				num_actions++;
				System.out.println(line);
				//actions.add(Integer.parseInt(line));
				actions.add( ((line.charAt(0)=='W') || (line.charAt(0)=='w')) ? new Boolean(true) : new Boolean (false) );
				System.out.println(5);
				actions_val.add(new Integer(Integer.parseInt(line.substring(2))));
				System.out.println(5);
				line = reader.readLine();
			}

			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}


		return num_actions;
	}


	public static void main(String[] args){
	
	int inputs[]=new int[20];


	int test = 7 % 4;
	//System.out.println(test);
	//int[][] cache= new int[sets][5];

	int offset_lim=4;
	int index_lim=16;
	int tag=0;
	int index=0;
	int offset=0;
	int num_actions = read(args[0]);
	int[][] cache= new int[sets][5];
	System.out.println("sets = " + sets);
	System.out.println("set_size = " + set_size);
	System.out.println("line_size = " + line_size);
	System.out.println("actions = " + actions);
	System.out.println("actions.size() = " + actions.size());
	for(int i = 0; i < actions.size(); i++)
		System.out.println("\t" + 
					( actions.get(i).booleanValue() ? 'W' : 'R')
					+ " " + actions_val.get(i));

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
	
}

