package net.lising.lib.encrypt.des;
public class trying{
	private String src;
	private byte[] key=new byte[64];
	
	public trying(String src){
		this.src=src;
	}
	public void op(){
		int j;
		for (int i = 0; i<4; i++) {
	  		j=src.charAt(i);
	  		System.out.println (j);
	  		key[16*i+0]=(byte)((j/32768)%2);
	  		key[16*i+1]=(byte)((j/16384)%2);
	  		key[16*i+2]=(byte)((j/8192)%2);
	  		key[16*i+3]=(byte)((j/4096)%2);
	  		key[16*i+4]=(byte)((j/2048)%2);
	  		key[16*i+5]=(byte)((j/1024)%2);
	  		key[16*i+6]=(byte)((j/512)%2);
	  		key[16*i+7]=(byte)((j/256)%2);
	  		key[16*i+8]=(byte)((j/128)%2);
	  		key[16*i+9]=(byte)((j/64)%2);
	  		key[16*i+10]=(byte)((j/32)%2);
	  		key[16*i+11]=(byte)((j/16)%2);
	  		key[16*i+12]=(byte)((j/8)%2);
	  		key[16*i+13]=(byte)((j/4)%2);
	  		key[16*i+14]=(byte)((j/2)%2);
	  		key[16*i+15]=(byte)(j%2);
	  				
		}
		for (int i = 0; i<64; i++) {
			if(i%4==0) System.out.print(" ");
			if(i%16==0) System.out.println ();
			System.out.print(key[i]);
			
	    }
	}
	public void op1(){
		int j;
		char ch;
		String str=null;
		StringBuffer strbuf=new StringBuffer();
		for (int i = 0; i<4; i++) {
			j=0;
			j=32768*key[16*i+0]+16384*key[16*i+1]+8192*key[16*i+2]+4096*key[16*i+3]+
			  2048*key[16*i+4]+1024*key[16*i+5]+512*key[16*i+6]+256*key[16*i+7]+
			  128*key[16*i+8]+64*key[16*i+9]+32*key[16*i+10]+16*key[16*i+11]+
			  8*key[16*i+12]+4*key[16*i+13]+2*key[16*i+14]+key[16*i+15];
			 ch=(char)j;
			 strbuf.append(ch);
			 
	    }
	    System.out.println (strbuf.toString());
	}
	/*
	public static void main(String[] args){
		trying t=new trying("�Һ�AB");
		//trying t=new trying("ABCD");
		t.op();
		System.out.println ();
		t.op1();
	}
	*/
}