package net.lising.lib.encrypt.des;

public class SubKey {
  private String inKey;
  private StringBuffer keyBuf;
  private byte[] key=new byte[64];
  /*ʵ�������
  private static byte[] key={
  0,0,0,1,0,0,1,1, 0,0,1,1,0,1,0,0, 0,1,0,1,0,1,1,1, 0,1,1,1,1,0,0,1,
  1,0,0,1,1,0,1,1, 1,0,1,1,1,1,0,0, 1,1,0,1,1,1,1,1,1,1,1,1,0,0,0,1
  };
  */
  private byte[] kwork=new byte[56];
  private static byte[] shift={	1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };
  protected byte[] k1=new byte[48];
  protected byte[] k2=new byte[48];
  protected byte[] k3=new byte[48];
  protected byte[] k4=new byte[48];
  protected byte[] k5=new byte[48];
  protected byte[] k6=new byte[48];
  protected byte[] k7=new byte[48];
  protected byte[] k8=new byte[48];
  protected byte[] k9=new byte[48];
  protected byte[] k10=new byte[48];
  protected byte[] k11=new byte[48];
  protected byte[] k12=new byte[48];
  protected byte[] k13=new byte[48];
  protected byte[] k14=new byte[48];
  protected byte[] k15=new byte[48];
  protected byte[] k16=new byte[48];  
  protected byte[] kn=new byte[48];
  
  public SubKey(String inKey) {
  	
  	byte j;
  	this.inKey=inKey;
  	int len=inKey.length();
  	
  	keyBuf=new StringBuffer(inKey);
  	if (len<8){ 
  		for (int i = 1; i<=8-len; i++)
  			keyBuf.append("?");
	}	
  	inKey=keyBuf.toString();
  	
  	for (int i = 0; i<8; i++) {
  		j=(byte)(inKey.charAt(i));
  		key[8*i]=(byte)((j/128)%2);
  		key[8*i+1]=(byte)((j/64)%2);
  		key[8*i+2]=(byte)((j/32)%2);
  		key[8*i+3]=(byte)((j/16)%2);
  		key[8*i+4]=(byte)((j/8)%2);
  		key[8*i+5]=(byte)((j/4)%2);
  		key[8*i+6]=(byte)((j/2)%2);
  		key[8*i+7]=(byte)(j%2);
	}
	/* Initial Permutation of Key */ 
	kwork[ 0] = key[56]; 
	kwork[ 1] = key[48]; 
	kwork[ 2] = key[40]; 
	kwork[ 3] = key[32]; 
	kwork[ 4] = key[24]; 
	kwork[ 5] = key[16]; 
	kwork[ 6] = key[ 8]; 
	kwork[ 7] = key[ 0]; 
	kwork[ 8] = key[57]; 
	kwork[ 9] = key[49]; 
	kwork[10] = key[41]; 
	kwork[11] = key[33]; 
	kwork[12] = key[25]; 
	kwork[13] = key[17]; 
	kwork[14] = key[ 9]; 
	kwork[15] = key[ 1]; 
	kwork[16] = key[58]; 
	kwork[17] = key[50]; 
	kwork[18] = key[42]; 
	kwork[19] = key[34]; 
	kwork[20] = key[26]; 
	kwork[21] = key[18]; 
	kwork[22] = key[10]; 
	kwork[23] = key[ 2]; 
	kwork[24] = key[59]; 
	kwork[25] = key[51]; 
	kwork[26] = key[43]; 
	kwork[27] = key[35]; 
	kwork[28] = key[62]; 
	kwork[29] = key[54]; 
	kwork[30] = key[46]; 
	kwork[31] = key[38]; 
	kwork[32] = key[30]; 
	kwork[33] = key[22]; 
	kwork[34] = key[14]; 
	kwork[35] = key[ 6]; 
	kwork[36] = key[61]; 
	kwork[37] = key[53]; 
	kwork[38] = key[45]; 
	kwork[39] = key[37]; 
	kwork[40] = key[29]; 
	kwork[41] = key[21]; 
	kwork[42] = key[13]; 
	kwork[43] = key[ 5]; 
	kwork[44] = key[60]; 
	kwork[45] = key[52]; 
	kwork[46] = key[44]; 
	kwork[47] = key[36]; 
	kwork[48] = key[28]; 
	kwork[49] = key[20]; 
	kwork[50] = key[12]; 
	kwork[51] = key[ 4]; 
	kwork[52] = key[27]; 
	kwork[53] = key[19]; 
	kwork[54] = key[11]; 
	kwork[55] = key[ 3]; 
	
	/* �Ӽ���㿪ʼ*/ 
	byte nbrofshift;
	byte temp1,temp2;
	for (int iter = 0; iter<16; iter++) {
		nbrofshift = shift[iter]; 
		for (int i = 0; i < (int) nbrofshift; i++) { 
			temp1 = kwork[0]; 
			temp2 = kwork[28]; 
			for (int k = 0; k < 27; k++) { 
				kwork[k] = kwork[k+1]; 
				kwork[k+28] = kwork[k+29]; 
			} 
			kwork[27] = temp1; 
			kwork[55] = temp2; 
		} 
		/* Permute kwork - PC2 */ 
		kn[ 0] = kwork[13]; 
		kn[ 1] = kwork[16]; 
		kn[ 2] = kwork[10]; 
		kn[ 3] = kwork[23]; 
		kn[ 4] = kwork[ 0]; 
		kn[ 5] = kwork[ 4]; 
		kn[ 6] = kwork[ 2]; 
		kn[ 7] = kwork[27]; 
		kn[ 8] = kwork[14]; 
		kn[ 9] = kwork[ 5]; 
		kn[10] = kwork[20]; 
		kn[11] = kwork[ 9]; 
		kn[12] = kwork[22]; 
		kn[13] = kwork[18]; 
		kn[14] = kwork[11]; 
		kn[15] = kwork[ 3]; 
		kn[16] = kwork[25]; 
		kn[17] = kwork[ 7]; 
		kn[18] = kwork[15]; 
		kn[19] = kwork[ 6]; 
		kn[20] = kwork[26]; 
		kn[21] = kwork[19]; 
		kn[22] = kwork[12]; 
		kn[23] = kwork[ 1]; 
		kn[24] = kwork[40]; 
		kn[25] = kwork[51]; 
		kn[26] = kwork[30]; 
		kn[27] = kwork[36]; 
		kn[28] = kwork[46]; 
		kn[29] = kwork[54]; 
		kn[30] = kwork[29]; 
		kn[31] = kwork[39]; 
		kn[32] = kwork[50]; 
		kn[33] = kwork[44]; 
		kn[34] = kwork[32]; 
		kn[35] = kwork[47]; 
		kn[36] = kwork[43]; 
		kn[37] = kwork[48]; 
		kn[38] = kwork[38]; 
		kn[39] = kwork[55]; 
		kn[40] = kwork[33]; 
		kn[41] = kwork[52]; 
		kn[42] = kwork[45]; 
		kn[43] = kwork[41]; 
		kn[44] = kwork[49]; 
		kn[45] = kwork[35]; 
		kn[46] = kwork[28]; 
		kn[47] = kwork[31]; 
		/*
		for (int i = 0; i<48; i++) {
			if (i%6==0) System.out.print(" ");
			System.out.print(kn[i]);
	    }
		System.out.println ();
		*/
		switch(iter){
			case 0:
				for (int k = 0; k<48;k++) { k1[k]=kn[k]; }
				break;
			case 1:
				for (int k = 0; k<48;k++) { k2[k]=kn[k]; }
				break;
			case 2:
				for (int k = 0; k<48;k++) { k3[k]=kn[k]; }
				break;
			case 3:
				for (int k = 0; k<48;k++) { k4[k]=kn[k]; }
				break;
			case 4:
				for (int k = 0; k<48;k++) { k5[k]=kn[k]; }
				break;
			case 5:
				for (int k = 0; k<48;k++) { k6[k]=kn[k]; }
				break;
			case 6:
				for (int k = 0; k<48;k++) { k7[k]=kn[k]; }
				break;
			case 7:
				for (int k = 0; k<48;k++) { k8[k]=kn[k]; }
				break;
			case 8:
				for (int k = 0; k<48;k++) { k9[k]=kn[k]; }
				break;
			case 9:
				for (int k = 0; k<48;k++) { k10[k]=kn[k]; }
				break;
			case 10:
				for (int k = 0; k<48;k++) { k11[k]=kn[k]; }
				break;
			case 11:
				for (int k = 0; k<48;k++) { k12[k]=kn[k]; }
				break;
			case 12:
				for (int k = 0; k<48;k++) { k13[k]=kn[k]; }
				break;
			case 13:
				for (int k = 0; k<48;k++) { k14[k]=kn[k]; }
				break;
			case 14:
				for (int k = 0; k<48;k++) { k15[k]=kn[k]; }
				break;
			case 15:
				for (int k = 0; k<48;k++) { k16[k]=kn[k]; }
				break;
		}
	}
  }
}