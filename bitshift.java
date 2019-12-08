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



}
