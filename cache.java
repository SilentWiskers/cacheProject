import java.io.*;
import java.util.*;
import java.nio.*;
import java.util.regex.*;
import java.util.Scanner;
import javafx.util.Pair;
import java.lang.Math;


public class cache{

	private static int sets = 0;
	private static int set_size = 0;
	private static int line_size = 0;
	private static ArrayList<Integer> actions_val;
	private static ArrayList<Boolean> actions;//0 for read, 1 for write

	public static void printHeader(int set, int setSize, int lineSize){
		System.out.println("Sets: " + set);
        	System.out.println("Cache Configuration");
        	System.out.println("");
        	System.out.println("        " + set + " " + setSize + "-way set associative entries");
        	System.out.println("        of line size " + lineSize + " bytes");
        	System.out.println("");
        	System.out.printf("%6s%8s%6s%7s%8s%7s%8s%n", "Access", "Address", "Tag", "Index", "Offset", "Status", "Memrefs");
        	System.out.println("------ -------- ----- -----  ------ ------ -------");
	}

	public static int log2(int x)
	{
    		return (int) (Math.log(x) / Math.log(2));
	}

	public static int read(String file_name){
		BufferedReader reader;
		int num_actions = 0;
		try{
			reader = new BufferedReader(new FileReader(file_name));

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
				//System.out.println(line);
				//actions.add(Integer.parseInt(line));
				actions.add( ((line.charAt(0)=='W') || (line.charAt(0)=='w')) ? new Boolean(true) : new Boolean (false) );
				//System.out.println(5);
				actions_val.add(new Integer(Integer.parseInt(line.substring(2),16)));
				//System.out.println(5);
				line = reader.readLine();
			}

			reader.close();
		}catch (IOException e) {
			e.printStackTrace();
		}


		return num_actions;
	}

	public static void printLine(int wor, int addr, int tag, int index, int offset, int status, int memref){
                String access;
                String memstat;
		String hexaddr;
                if (wor == 0){access = "read";}
                else{access = "write";}

                if (status == 0){memstat = "miss";}
                else{memstat = "hit";}
		hexaddr = Integer.toHexString(addr);

                System.out.printf("%6s%8s%7s%6s%8s%7s%8s%n", access, hexaddr, tag, index, offset, memstat, memref);

        }

        public static void printStat(int hit, int miss,  int access){
                System.out.println("");
                System.out.println("Simulation Summary Statistics");
                System.out.println("-----------------------------");
                System.out.println("Total hits       : " + hit);
                System.out.println("Total misses     : " + miss);
                System.out.println("Total accessess  : " + access);

                float hitr = (hit/(float)access);
                float missr = (miss/(float)access);
                System.out.println("Hit ratio        : " + hitr);
                System.out.println("Miss ratio       : " + missr);
        }


	public static void main(String[] args){
		
		int inputs[]=new int[20];
	
		int num_actions = read(args[0]);

		int[][] cache = new int[sets][line_size];
		int[][] cache_LRU = new int[sets][line_size];

		int hits = 0, misses = 0, accesses = 0;

		//System.out.println("sets = " + sets);
		//System.out.println("set_size = " + set_size);
		//System.out.println("line_size = " + line_size);
		//System.out.println("actions = " + actions);
		//System.out.println("actions.size() = " + actions.size());

		//for(int i = 0; i < actions.size(); i++)
			//System.out.println("\t" + 
						//( actions.get(i).booleanValue() ? 'W' : 'R')
						//+ " " + actions_val.get(i));
	
		for(int set = 0; set < sets; set++)
			for(int block = 0; block < 4; block++){
				cache[set][block] = -1;
				cache_LRU[set][block] = 1;
			}
		
		printHeader(sets, set_size, line_size);
	
		int addr, write, offset, block, set, index, tag, hit;
		for(int i = 0; i < num_actions; i++){
			accesses++;
			addr = actions_val.get(i);
			write = ( actions.get(i).booleanValue() ? 1 : 0);

			int n_offset_bits = log2(line_size);
			int n_block_bits = log2(set_size);
			int n_set_bits = log2(sets);
	
			offset = (line_size-1) & addr;
			//offset = 0x0003 & addr;
			block = (set_size-1) & (addr >> n_offset_bits);
			set = (sets/set_size-1) & (addr >> (n_offset_bits+n_block_bits));
			index = ((int) Math.pow(2,(n_set_bits+1))-1) & (addr >> n_offset_bits);
			if(index>=256)
				index %= 256;
			tag = 8191 & (addr >> (n_offset_bits+n_set_bits)); 
			//System.out.println(n_offset_bits+n_block_bits+n_set_bits); 
	
			if((cache[set][0]==(addr-offset))
			|| (cache[set][1]==(addr-offset))
			|| (cache[set][2]==(addr-offset))
			|| (cache[set][3]==(addr-offset))
			){//hit
				hits++;
				hit = 1;
				cache_LRU[set][block] = 1;//make this LRU

				for(int j = 0; j < line_size; j++)//increment the rest
					if(j!=block)
						cache_LRU[set][block] += 1;
			}else{//miss
				//System.out.println("set = " + set);

				misses++;
				hit = 0;
				int max = cache_LRU[set][0];//max value in cache_LRU
				int max_i = 0;//index of what is replaced

				//System.out.println("max_i = " + max_i);
				//System.out.println("max = " + max);
				//System.out.println("set = " + set);
				for(int j = 1; j < line_size; j++){//determine the LRU
					//System.out.println("if(" + cache_LRU[set][j] + " > " + max + "){");
					if(cache_LRU[set][j] > max){
						max_i = j;
						max = cache_LRU[set][j];
					}
				}
				//System.out.println("max_i = " + max_i);
				//System.out.println("max = " + max);

				cache_LRU[set][max_i] = 1;//make this LRU
				cache[set][max_i] = (addr-offset);

				//System.out.println("accesses = " + accesses);
				for(int j = 0; j < line_size; j++)//increment the rest
					if((j!=max_i) )//&& (accesses<line_size))
						cache_LRU[set][j] += 1;
				
				//System.out.println("max_i = " + max_i);
				//System.out.println("max = " + max);

			}
			//System.out.println("LRU");
				//System.out.println("\t0 : " + cache_LRU[set][0]);
				//System.out.println("\t1 : " + cache_LRU[set][1]);
				//System.out.println("\t2 : " + cache_LRU[set][2]);
				//System.out.println("\t3 : " + cache_LRU[set][3]);

			//System.exit(1);

			printLine(write,addr,tag,index,offset,hit, (hit==1) ? 0 : 1);
	
			//if(write==1)
				//System.out.print("write");
			//else
				//System.out.print("read");
	
			//System.out.println("\taddr = " + addr);
			//System.out.println("\toffset = " + offset);
			//System.out.println("\tblock = " + block);
			//System.out.println("\tset = " + set);
			//System.out.println("\tindex = " + index);
			//System.out.println("\ttag = " + tag);
			//System.out.println();
			//System.out.println("\t" + n_offset_bits + " bits for the offset");
			//System.out.println("\tn_block_bits+n_set_bits-1 = " + (n_block_bits+n_set_bits-1));
			//System.out.println("\tMath.pow(2,n_block_bits+n_set_bits)-1 = " + (Math.pow(2,(n_block_bits+n_set_bits))-1));
			//System.out.println("\t" + n_block_bits + " bits for the block");
			//System.out.println("\t" + n_set_bits + " bits for the set");
			//System.out.println();
			//System.out.println();
			//System.out.print(" 0x0003 = " + 0x0003);
			//System.out.println();


		}
		printStat(hits,misses,accesses);
	}

	
}

