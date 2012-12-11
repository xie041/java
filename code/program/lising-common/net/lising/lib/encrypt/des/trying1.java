package net.lising.lib.encrypt.des;

import java.io.*;
import java.nio.*;
import java.nio.channels.FileChannel;

public class trying1{
	private String srcFileName;
	private String destFileName;
	private File srcFile;
	private File destFile;

	private void analyzePath(){
		
		String dirName;
		int pos=srcFileName.lastIndexOf("/");
		dirName=srcFileName.substring(0,pos);
		File dir=new File(dirName);
		if (!dir.exists()){
			System.err.println(dirName+" is not exist");
			System.exit(1);
		}else if(!dir.isDirectory()){
			System.err.println(dirName+" is not a directory");
			System.exit(1);
		}
		
		pos=destFileName.lastIndexOf("/");
		dirName=destFileName.substring(0,pos);
		dir=new File(dirName);
		if (!dir.exists()){
			if(!dir.mkdirs()){
				System.out.println ("can not creat directory:"+dirName);
				System.exit(1);
			}
		}else if(!dir.isDirectory()){
			System.err.println(dirName+" is not a directory");
			System.exit(1);
		}
	}
		private static int replenish(FileChannel channel,ByteBuffer buf) throws IOException{
		long byteLeft=channel.size()-channel.position();
		if(byteLeft==0L)
			return -1;
		buf.position(0);
		buf.limit(buf.position()+(byteLeft<8 ? (int)byteLeft :8));
		return channel.read(buf);
	}

	private void file(){
		FileOutputStream outputFile=null;
		try {
			outputFile=new FileOutputStream(srcFile,true);
			//outputFile.
			//System.out.println ("File stream creat successfully.");
	    }catch (java.io.FileNotFoundException e) {
	    	e.printStackTrace(System.err);
	    }
	    FileChannel outChannel=outputFile.getChannel();
	    try{
	    
		    if(outChannel.size()%2!=0){
				ByteBuffer bufTemp=ByteBuffer.allocate(1);
				bufTemp.put((byte)36);
				bufTemp.flip();			
				outChannel.position(outChannel.size());
				outChannel.write(bufTemp);
				//outChannel.
		    	bufTemp.clear();		
			}
		}catch(Exception ex){
			
		}
		FileInputStream inFile=null;
		try{
			inFile=new FileInputStream(srcFile);
		}catch(java.io.FileNotFoundException e){
			e.printStackTrace(System.err);
			System.exit(1);
		}
		outputFile=null;
		try {
			outputFile=new FileOutputStream(destFile,true);
			//outputFile.
			//System.out.println ("File stream creat successfully.");
	    }catch (java.io.FileNotFoundException e) {
	    	e.printStackTrace(System.err);
	    }
	    
	    FileChannel inChannel=inFile.getChannel();
		outChannel=outputFile.getChannel();
		
		
		ByteBuffer inBuf=ByteBuffer.allocate(8);
		ByteBuffer outBuf=ByteBuffer.allocate(8);
		
		try{
			String srcStr;
			String destStr;
			while(true){
			
				if (replenish(inChannel,inBuf)==-1) break;
				srcStr=((ByteBuffer)(inBuf.flip())).asCharBuffer().toString();
				inBuf.clear();			
			    outBuf.clear();
			    if (srcStr.length()==4){
			    	for (int i = 0; i<4; i++) {
	    				outBuf.putChar(srcStr.charAt(i));
	    			}
			    	outBuf.flip();
			    }else{
			    	outBuf.position(0);
			    	outBuf.limit(2*srcStr.length());
			    	for (int i = 0; i<srcStr.length(); i++) {
	    				outBuf.putChar(srcStr.charAt(i));
	    			}
			    	outBuf.flip();
			    }
			    
			    try {
	    			outChannel.write(outBuf);
	    			outBuf.clear();
	    		}catch (java.io.IOException ex) {
	    			ex.printStackTrace(System.err);
	    		}
	    		
			    
			}
			System.out.println (inChannel.size());
	    	System.out.println (outChannel.size());
	    	
			System.out.println ("EoF reached.");
			inFile.close();
			outputFile.close();
		}catch(java.io.IOException e){
			e.printStackTrace(System.err);
			System.exit(1);
		}
	}
	
	public trying1(String srcFileName,String destFileName){
		this.srcFileName=srcFileName;
		this.destFileName=destFileName;		
		analyzePath();
		srcFile=new File(srcFileName);
		destFile=new File(destFileName);
	}
	
	public static void main(String [] args){
		trying1 try1=new trying1("D:/��Ϣ��ȫ/des1/trying.java","d:/1111.java");
		try1.file();
	}
}