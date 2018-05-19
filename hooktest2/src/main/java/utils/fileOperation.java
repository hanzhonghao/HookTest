package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class fileOperation {
	public static boolean exist(String file){
		return (new File(file)).exists();
	}
	
	public static int writeString(String file, String str, int mode){
		//mode:0:create
		//1:overwrite
		File outputFile=new File(file);
		if(outputFile.exists() && mode==0){
			return -1;
		}
	    java.io.PrintWriter output;
		try {
			output = new java.io.PrintWriter(outputFile);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			return -2;
		}
		output.print(str);
		output.close();
		return 0;
	}
	
	public static String readString(String file){
		String inputStr="";
		File inputFile=new File(file);
		Scanner input;
        try {
			input=new Scanner(inputFile);
			while(input.hasNext()){
				inputStr+=input.next();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		input.close();
		return inputStr;
	}
	
	public static int writeBytes(String file, byte[] bytes){
		java.io.ObjectOutputStream out;
		try {
			out = new java.io.ObjectOutputStream(
					new java.io.FileOutputStream(file));
			out.writeObject(bytes);
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -2;
		}
		
		return 0;
	}
	
	public static byte[] readBytes(String file){
		byte[] r = null;
		java.io.ObjectInputStream in;
		try {
			in = new java.io.ObjectInputStream(
					new java.io.FileInputStream(file));
			r=(byte[]) in.readObject();
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return r;
	}

	//删除文件
	public static void delFile(String file){
		File fileChild = new File(file);
		if(fileChild.isFile()){
			fileChild.delete();
		}
	}
}
