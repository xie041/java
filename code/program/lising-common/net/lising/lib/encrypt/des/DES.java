package net.lising.lib.encrypt.des;

public class DES {
	/* Table - s1 */
	protected static byte[][] s1 = {
			{ 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
			{ 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
			{ 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
			{ 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 } };

	/* Table - s2 */
	protected static byte[][] s2 = {
			{ 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 },
			{ 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
			{ 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
			{ 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 } };

	/* Table - s3 */
	protected static byte[][] s3 = {
			{ 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 },
			{ 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
			{ 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
			{ 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 } };

	/* Table - s4 */
	protected static byte[][] s4 = {
			{ 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 },
			{ 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
			{ 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
			{ 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 } };

	/* Table - s5 */
	protected static byte[][] s5 = {
			{ 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 },
			{ 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
			{ 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
			{ 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 } };

	/* Table - s6 */
	protected static byte[][] s6 = {
			{ 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 },
			{ 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
			{ 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
			{ 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 } };

	/* Table - s7 */
	protected static byte[][] s7 = {
			{ 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 },
			{ 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
			{ 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
			{ 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 } };

	/* Table - s8 */
	protected static byte[][] s8 = {
			{ 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 },
			{ 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
			{ 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
			{ 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 } };
	// ���� S�в�����ȷ����Ӧ��4λ��������ݣ�
	protected static byte[] binary = { 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0,
			0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0,
			0, 1, 0, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 0, 0, 1, 1, 0, 1, 1,
			1, 1, 0, 1, 1, 1, 1 };

	// �洢��Ҫ���ܵ����Ļ�Ҫ���ܵ�����
	protected byte[] workData = new byte[64];
	protected String dataSrc;
	protected String dataDest;
	/*
	 * ʵ������� protected static byte[] workData={ 0,0,0,0,0,0,0,1,
	 * 0,0,1,0,0,0,1,1, 0,1,0,0,0,1,0,1, 0,1,1,0,0,1,1,1, 1,0,0,0,1,0,0,1,
	 * 1,0,1,0,1,0,1,1, 1,1,0,0,1,1,0,1, 1,1,1,0,1,1,1,1 };
	 */
	// ������ݱ任��ת
	protected byte[] bufout = new byte[64];
	// ������ɵ����Ļ������õ�����
	protected byte[] output = new byte[64];
	protected byte[] Ln = new byte[32];
	protected byte[] Rn = new byte[32];
	protected byte[] LR = new byte[32];
	protected byte[] ER = new byte[48];
	protected byte[] temp = new byte[32];
	protected byte[] result = new byte[8];
	protected byte[] ki;
	protected String key;
	protected int readLen;
	protected boolean encFlag;
	protected SubKey subKey;

	protected void convert4CharTo64bit(String data) {
		for (int i = 0; i < 64; i++) {
			workData[i] = 0;
		}
		/* Convert from 4-char(coded in unicode) data into 64-bit data */

		for (int i = 0; i < readLen; i++) {
			int j = data.charAt(i);
			workData[16 * i + 0] = (byte) ((j / 32768) % 2);
			workData[16 * i + 1] = (byte) ((j / 16384) % 2);
			workData[16 * i + 2] = (byte) ((j / 8192) % 2);
			workData[16 * i + 3] = (byte) ((j / 4096) % 2);
			workData[16 * i + 4] = (byte) ((j / 2048) % 2);
			workData[16 * i + 5] = (byte) ((j / 1024) % 2);
			workData[16 * i + 6] = (byte) ((j / 512) % 2);
			workData[16 * i + 7] = (byte) ((j / 256) % 2);
			workData[16 * i + 8] = (byte) ((j / 128) % 2);
			workData[16 * i + 9] = (byte) ((j / 64) % 2);
			workData[16 * i + 10] = (byte) ((j / 32) % 2);
			workData[16 * i + 11] = (byte) ((j / 16) % 2);
			workData[16 * i + 12] = (byte) ((j / 8) % 2);
			workData[16 * i + 13] = (byte) ((j / 4) % 2);
			workData[16 * i + 14] = (byte) ((j / 2) % 2);
			workData[16 * i + 15] = (byte) (j % 2);
		}
	}

	protected void convert64bitTo4Char() {
		int j;
		char ch;
		StringBuffer strbuf = new StringBuffer();
		for (int i = 0; i < 4; i++) {
			j = 0;
			j = 32768 * output[16 * i + 0] + 16384 * output[16 * i + 1] + 8192
					* output[16 * i + 2] + 4096 * output[16 * i + 3] + 2048
					* output[16 * i + 4] + 1024 * output[16 * i + 5] + 512
					* output[16 * i + 6] + 256 * output[16 * i + 7] + 128
					* output[16 * i + 8] + 64 * output[16 * i + 9] + 32
					* output[16 * i + 10] + 16 * output[16 * i + 11] + 8
					* output[16 * i + 12] + 4 * output[16 * i + 13] + 2
					* output[16 * i + 14] + output[16 * i + 15];
			ch = (char) j;
			strbuf.append(ch);
		}
		dataDest = strbuf.toString();
	}

	protected void IP() {
		/* Initial Permutation of Data */
		bufout[0] = workData[57];
		bufout[1] = workData[49];
		bufout[2] = workData[41];
		bufout[3] = workData[33];
		bufout[4] = workData[25];
		bufout[5] = workData[17];
		bufout[6] = workData[9];
		bufout[7] = workData[1];
		bufout[8] = workData[59];
		bufout[9] = workData[51];
		bufout[10] = workData[43];
		bufout[11] = workData[35];
		bufout[12] = workData[27];
		bufout[13] = workData[19];
		bufout[14] = workData[11];
		bufout[15] = workData[3];
		bufout[16] = workData[61];
		bufout[17] = workData[53];
		bufout[18] = workData[45];
		bufout[19] = workData[37];
		bufout[20] = workData[29];
		bufout[21] = workData[21];
		bufout[22] = workData[13];
		bufout[23] = workData[5];
		bufout[24] = workData[63];
		bufout[25] = workData[55];
		bufout[26] = workData[47];
		bufout[27] = workData[39];
		bufout[28] = workData[31];
		bufout[29] = workData[23];
		bufout[30] = workData[15];
		bufout[31] = workData[7];
		bufout[32] = workData[56];
		bufout[33] = workData[48];
		bufout[34] = workData[40];
		bufout[35] = workData[32];
		bufout[36] = workData[24];
		bufout[37] = workData[16];
		bufout[38] = workData[8];
		bufout[39] = workData[0];
		bufout[40] = workData[58];
		bufout[41] = workData[50];
		bufout[42] = workData[42];
		bufout[43] = workData[34];
		bufout[44] = workData[26];
		bufout[45] = workData[18];
		bufout[46] = workData[10];
		bufout[47] = workData[2];
		bufout[48] = workData[60];
		bufout[49] = workData[52];
		bufout[50] = workData[44];
		bufout[51] = workData[36];
		bufout[52] = workData[28];
		bufout[53] = workData[20];
		bufout[54] = workData[12];
		bufout[55] = workData[4];
		bufout[56] = workData[62];
		bufout[57] = workData[54];
		bufout[58] = workData[46];
		bufout[59] = workData[38];
		bufout[60] = workData[30];
		bufout[61] = workData[22];
		bufout[62] = workData[14];
		bufout[63] = workData[6];

	}

	protected void XOR(byte[] op1, byte[] op2) {
		int len = op1.length;
		for (int i = 0; i < len; i++) {
			op1[i] = (byte) (op1[i] ^ op2[i]);
		}
	}

	protected void expand32To48bit(byte[] op) {
		/* Permute - E */
		ER[0] = op[31];
		ER[1] = op[0];
		ER[2] = op[1];
		ER[3] = op[2];
		ER[4] = op[3];
		ER[5] = op[4];
		ER[6] = op[3];
		ER[7] = op[4];
		ER[8] = op[5];
		ER[9] = op[6];
		ER[10] = op[7];
		ER[11] = op[8];
		ER[12] = op[7];
		ER[13] = op[8];
		ER[14] = op[9];
		ER[15] = op[10];
		ER[16] = op[11];
		ER[17] = op[12];
		ER[18] = op[11];
		ER[19] = op[12];
		ER[20] = op[13];
		ER[21] = op[14];
		ER[22] = op[15];
		ER[23] = op[16];
		ER[24] = op[15];
		ER[25] = op[16];
		ER[26] = op[17];
		ER[27] = op[18];
		ER[28] = op[19];
		ER[29] = op[20];
		ER[30] = op[19];
		ER[31] = op[20];
		ER[32] = op[21];
		ER[33] = op[22];
		ER[34] = op[23];
		ER[35] = op[24];
		ER[36] = op[23];
		ER[37] = op[24];
		ER[38] = op[25];
		ER[39] = op[26];
		ER[40] = op[27];
		ER[41] = op[28];
		ER[42] = op[27];
		ER[43] = op[28];
		ER[44] = op[29];
		ER[45] = op[30];
		ER[46] = op[31];
		ER[47] = op[0];
	}

	protected void sBox() {
		/* 8 s-functions */
		int valindex;
		valindex = s1[2 * ER[0] + ER[5]][2 * (2 * (2 * ER[1] + ER[2]) + ER[3])
				+ ER[4]];
		valindex = valindex * 4;
		temp[0] = (byte) binary[0 + valindex];
		temp[1] = (byte) binary[1 + valindex];
		temp[2] = (byte) binary[2 + valindex];
		temp[3] = (byte) binary[3 + valindex];
		valindex = s2[2 * ER[6] + ER[11]][2 * (2 * (2 * ER[7] + ER[8]) + ER[9])
				+ ER[10]];
		valindex = valindex = valindex * 4;
		temp[4] = (byte) binary[0 + valindex];
		temp[5] = (byte) binary[1 + valindex];
		temp[6] = (byte) binary[2 + valindex];
		temp[7] = (byte) binary[3 + valindex];
		valindex = s3[2 * ER[12] + ER[17]][2
				* (2 * (2 * ER[13] + ER[14]) + ER[15]) + ER[16]];
		valindex = valindex = valindex * 4;
		temp[8] = (byte) binary[0 + valindex];
		temp[9] = (byte) binary[1 + valindex];
		temp[10] = (byte) binary[2 + valindex];
		temp[11] = (byte) binary[3 + valindex];
		valindex = s4[2 * ER[18] + ER[23]][2
				* (2 * (2 * ER[19] + ER[20]) + ER[21]) + ER[22]];
		valindex = valindex = valindex * 4;
		temp[12] = (byte) binary[0 + valindex];
		temp[13] = (byte) binary[1 + valindex];
		temp[14] = (byte) binary[2 + valindex];
		temp[15] = (byte) binary[3 + valindex];
		valindex = s5[2 * ER[24] + ER[29]][2
				* (2 * (2 * ER[25] + ER[26]) + ER[27]) + ER[28]];
		valindex = valindex = valindex * 4;
		temp[16] = (byte) binary[0 + valindex];
		temp[17] = (byte) binary[1 + valindex];
		temp[18] = (byte) binary[2 + valindex];
		temp[19] = (byte) binary[3 + valindex];
		valindex = s6[2 * ER[30] + ER[35]][2
				* (2 * (2 * ER[31] + ER[32]) + ER[33]) + ER[34]];
		valindex = valindex = valindex * 4;
		temp[20] = (byte) binary[0 + valindex];
		temp[21] = (byte) binary[1 + valindex];
		temp[22] = (byte) binary[2 + valindex];
		temp[23] = (byte) binary[3 + valindex];
		valindex = s7[2 * ER[36] + ER[41]][2
				* (2 * (2 * ER[37] + ER[38]) + ER[39]) + ER[40]];
		valindex = valindex = valindex * 4;
		temp[24] = (byte) binary[0 + valindex];
		temp[25] = (byte) binary[1 + valindex];
		temp[26] = (byte) binary[2 + valindex];
		temp[27] = (byte) binary[3 + valindex];
		valindex = s8[2 * ER[42] + ER[47]][2
				* (2 * (2 * ER[43] + ER[44]) + ER[45]) + ER[46]];
		valindex = valindex = valindex * 4;
		temp[28] = (byte) binary[0 + valindex];
		temp[29] = (byte) binary[1 + valindex];
		temp[30] = (byte) binary[2 + valindex];
		temp[31] = (byte) binary[3 + valindex];
	}

	protected void p() {
		/* Permute - P */
		Rn[0] = temp[15];
		Rn[1] = temp[6];
		Rn[2] = temp[19];
		Rn[3] = temp[20];
		Rn[4] = temp[28];
		Rn[5] = temp[11];
		Rn[6] = temp[27];
		Rn[7] = temp[16];
		Rn[8] = temp[0];
		Rn[9] = temp[14];
		Rn[10] = temp[22];
		Rn[11] = temp[25];
		Rn[12] = temp[4];
		Rn[13] = temp[17];
		Rn[14] = temp[30];
		Rn[15] = temp[9];
		Rn[16] = temp[1];
		Rn[17] = temp[7];
		Rn[18] = temp[23];
		Rn[19] = temp[13];
		Rn[20] = temp[31];
		Rn[21] = temp[26];
		Rn[22] = temp[2];
		Rn[23] = temp[8];
		Rn[24] = temp[18];
		Rn[25] = temp[12];
		Rn[26] = temp[29];
		Rn[27] = temp[5];
		Rn[28] = temp[21];
		Rn[29] = temp[10];
		Rn[30] = temp[3];
		Rn[31] = temp[24];

	}

	protected void IIP() {
		/* Inverse Initial Permutation */
		output[0] = bufout[39];
		output[1] = bufout[7];
		output[2] = bufout[47];
		output[3] = bufout[15];
		output[4] = bufout[55];
		output[5] = bufout[23];
		output[6] = bufout[63];
		output[7] = bufout[31];
		output[8] = bufout[38];
		output[9] = bufout[6];
		output[10] = bufout[46];
		output[11] = bufout[14];
		output[12] = bufout[54];
		output[13] = bufout[22];
		output[14] = bufout[62];
		output[15] = bufout[30];
		output[16] = bufout[37];
		output[17] = bufout[5];
		output[18] = bufout[45];
		output[19] = bufout[13];
		output[20] = bufout[53];
		output[21] = bufout[21];
		output[22] = bufout[61];
		output[23] = bufout[29];
		output[24] = bufout[36];
		output[25] = bufout[4];
		output[26] = bufout[44];
		output[27] = bufout[12];
		output[28] = bufout[52];
		output[29] = bufout[20];
		output[30] = bufout[60];
		output[31] = bufout[28];
		output[32] = bufout[35];
		output[33] = bufout[3];
		output[34] = bufout[43];
		output[35] = bufout[11];
		output[36] = bufout[51];
		output[37] = bufout[19];
		output[38] = bufout[59];
		output[39] = bufout[27];
		output[40] = bufout[34];
		output[41] = bufout[2];
		output[42] = bufout[42];
		output[43] = bufout[10];
		output[44] = bufout[50];
		output[45] = bufout[18];
		output[46] = bufout[58];
		output[47] = bufout[26];
		output[48] = bufout[33];
		output[49] = bufout[1];
		output[50] = bufout[41];
		output[51] = bufout[9];
		output[52] = bufout[49];
		output[53] = bufout[17];
		output[54] = bufout[57];
		output[55] = bufout[25];
		output[56] = bufout[32];
		output[57] = bufout[0];
		output[58] = bufout[40];
		output[59] = bufout[8];
		output[60] = bufout[48];
		output[61] = bufout[16];
		output[62] = bufout[56];
		output[63] = bufout[24];
	}

	public DES(String key) {
		this.key = key;
		subKey = new SubKey(key);
	}

	public String enc(String dataSrc, int readLen) {
		this.dataSrc = dataSrc;
		this.readLen = readLen;
		convert4CharTo64bit(dataSrc);
		IP();
		for (int i = 0; i < 32; i++) {
			Ln[i] = bufout[i]; // L0
			Rn[i] = bufout[32 + i]; // R0
		}
		for (int iter = 1; iter < 17; iter++) {
			for (int i = 0; i < 32; i++) {
				LR[i] = Ln[i];
				Ln[i] = Rn[i];
			}
			expand32To48bit(Rn); // Rn-1 expand to 48 bit save to ER[]
			switch (iter) {
			case 1:
				ki = subKey.k1;
				break;
			case 2:
				ki = subKey.k2;
				break;
			case 3:
				ki = subKey.k3;
				break;
			case 4:
				ki = subKey.k4;
				break;
			case 5:
				ki = subKey.k5;
				break;
			case 6:
				ki = subKey.k6;
				break;
			case 7:
				ki = subKey.k7;
				break;
			case 8:
				ki = subKey.k8;
				break;
			case 9:
				ki = subKey.k9;
				break;
			case 10:
				ki = subKey.k10;
				break;
			case 11:
				ki = subKey.k11;
				break;
			case 12:
				ki = subKey.k12;
				break;
			case 13:
				ki = subKey.k13;
				break;
			case 14:
				ki = subKey.k14;
				break;
			case 15:
				ki = subKey.k15;
				break;
			case 16:
				ki = subKey.k16;
				break;

			}

			XOR(ER, ki);
			sBox();
			p();
			XOR(Rn, LR);
		}

		for (int i = 0; i < 32; i++) {
			bufout[i] = Rn[i];
			bufout[32 + i] = Ln[i];
		}
		IIP();
		convert64bitTo4Char();
		return dataDest;
	}

	public String dec(String dataSrc, int readLen) {
		this.dataSrc = dataSrc;
		this.readLen = readLen;
		convert4CharTo64bit(dataSrc);
		IP();
		for (int i = 0; i < 32; i++) {
			Ln[i] = bufout[i]; // L0
			Rn[i] = bufout[32 + i]; // R0
		}
		for (int iter = 1; iter < 17; iter++) {
			for (int i = 0; i < 32; i++) {
				LR[i] = Ln[i];
				Ln[i] = Rn[i];
			}
			expand32To48bit(Rn); // Rn-1 expand to 48 bit save to ER[]
			switch (iter) {
			case 1:
				ki = subKey.k16;
				break;
			case 2:
				ki = subKey.k15;
				break;
			case 3:
				ki = subKey.k14;
				break;
			case 4:
				ki = subKey.k13;
				break;
			case 5:
				ki = subKey.k12;
				break;
			case 6:
				ki = subKey.k11;
				break;
			case 7:
				ki = subKey.k10;
				break;
			case 8:
				ki = subKey.k9;
				break;
			case 9:
				ki = subKey.k8;
				break;
			case 10:
				ki = subKey.k7;
				break;
			case 11:
				ki = subKey.k6;
				break;
			case 12:
				ki = subKey.k5;
				break;
			case 13:
				ki = subKey.k4;
				break;
			case 14:
				ki = subKey.k3;
				break;
			case 15:
				ki = subKey.k2;
				break;
			case 16:
				ki = subKey.k1;
				break;

			}
			XOR(ER, ki);
			sBox();
			p();
			XOR(Rn, LR);

		}

		for (int i = 0; i < 32; i++) {
			bufout[i] = Rn[i];
			bufout[32 + i] = Ln[i];
		}
		IIP();
		convert64bitTo4Char();
		return dataDest;
	}
}