import java.io.*;
import java.util.*;
import java.nio.*;
import java.util.regex.*;
import java.util.Scanner;
import java.io.FileInputStream;

public class bitshift{

	public static void main(String[] args){
		int addr = 16;

		int offset = 0x0003 & addr;

		int block = 0x0003 & (addr >> 2);

		int set = 0x0003 & (addr >> 4);

		int index = 0x000f & (addr >> 2);
		
		int tag = 0x001f & (addr >> 6); 

		System.out.println("num is " + addr);
		System.out.println("offset is " + offset);
		System.out.println("block is " + block);
		System.out.println("set is " + set);
		System.out.println("index is " + index);
		System.out.println("tag is " + tag);

	}

	public static void printLine(int wor, int addr, int tag, int index, int offset, int status, int memref){
                String access;
                String memstat;
                if (wor == 0){access = "read";}
                else{access = "write";}

                if (status == 0){memstat = "miss";}
                else{memstat = "hit";}

                System.out.printf("%6s%8s%6s%7s%8s%7s%8s%n", access, addr, tag, index, offset, memstat, memref);

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


}
