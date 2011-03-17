package rina.delimiting.impl;

import java.util.ArrayList;
import java.util.List;

import rina.delimiting.api.Delimiter;

/**
 * Assumes SDUs will have less than 2^32 -1 bytes (the length of the sdu length section will be 4 bytes as maximum)
 * @author eduardgrasa
 *
 */
public class DIFDelimiter implements Delimiter {

	/**
	 * Takes a list of raw sdus and produces a single delimited byte array, consisting in 
	 * the concatenation of the sdus followed by their encoded length: [length][sdu][length][sdu] ...
	 * @param rawSdus
	 * @return
	 */
	public synchronized byte[] getDelimitedSdus(List<byte[]> rawSdus) {
		List<byte[]> delimitedSdus = new ArrayList<byte[]>();
		byte[] delimitedSdu = null;
		int length =0;

		for(int i=0; i<rawSdus.size(); i++){
			delimitedSdu = getDelimitedSdu(rawSdus.get(i));
			delimitedSdus.add(delimitedSdu);
			length = length + delimitedSdu.length;
		}

		byte[] delimitedSdusArray = new byte[length];
		int index =0;

		for(int i=0; i<delimitedSdus.size(); i++){
			for(int j=0; j<delimitedSdus.get(i).length; j++){
				delimitedSdusArray[index+j] = delimitedSdus.get(i)[j];
			}
			index = index + delimitedSdus.get(i).length;
		}

		return delimitedSdusArray;
	}

	/**
	 * Takes a single rawSdu and produces a single delimited byte array, consisting in 
	 * [length][sdu]
	 * @param rawSdus
	 * @return
	 */
	public synchronized byte[] getDelimitedSdu(byte[] rawSdu){
		byte[] encodedSduLength = writeRawVarint32(rawSdu.length);
		byte[] delimitedSdu = new byte[encodedSduLength.length + rawSdu.length];
		for(int i=0; i<encodedSduLength.length; i++){
			delimitedSdu[i] = encodedSduLength[i];
		}

		for(int i=0; i<rawSdu.length; i++){
			delimitedSdu[i+encodedSduLength.length] = rawSdu[i];
		}

		return delimitedSdu;
	}

	/**
	 * Encode and write a varint.  {@code value} is treated as
	 * unsigned, so it won't be sign-extended if negative. The varint is the length
	 * of the SDU
	 */
	private byte[] writeRawVarint32(int value) {
		int numberOfBytes = 0;
		byte[] encodedLength = null;
		byte[] buffer = new byte[5];

		while (true) {
			if ((value & ~0x7F) == 0) {
				buffer[numberOfBytes] = (byte)value;
				numberOfBytes ++;
				encodedLength = new byte[numberOfBytes];
				for(int i=0; i<encodedLength.length; i++){
					encodedLength[i] = buffer[i];
				}
				return encodedLength;
			} else {
				buffer[numberOfBytes] = (byte)((value & 0x7F) | 0x80);
				numberOfBytes++;
				value >>>= 7;
			}
		}
	}


	/**
	 * Takes a delimited byte array ([length][sdu][length][sdu] ..) and extracts the sdus
	 * @param delimitedSdus
	 * @return
	 */
	public synchronized List<byte[]> getRawSdus(byte[] delimitedSdusArray) {
		int index = 0;
		List<byte[]> rawSdus = new ArrayList<byte[]>();
		byte[] currentSdu = null;
		int length = 0;
		int bytes = 0;
		
		while(index < delimitedSdusArray.length -1){
			length = readRawVarint32(index, delimitedSdusArray);
			bytes = getNumberOfBytes(length);
			currentSdu = new byte[length];
			for(int i=0; i<length; i++){
				currentSdu[i] = delimitedSdusArray[index + bytes + i];
			}
			rawSdus.add(currentSdu);
			index = index + bytes + length;
		}
		
		return rawSdus;
	}

	/**
	 * Read a raw Varint from the stream.  If larger than 32 bits, the interpretation 
	 * of the number will be totally wrong
	 */
	private int readRawVarint32(int index, byte[] delimitedSdusArray) {
		byte tmp = delimitedSdusArray[index];
		if (tmp >= 0) {
			return tmp;
		}
		int result = tmp & 0x7f;
		if ((tmp = delimitedSdusArray[index+1]) >= 0) {
			result |= tmp << 7;
		} else {
			result |= (tmp & 0x7f) << 7;
			if ((tmp = delimitedSdusArray[index+2]) >= 0) {
				result |= tmp << 14;
			} else {
				result |= (tmp & 0x7f) << 14;
				if ((tmp = delimitedSdusArray[index+3]) >= 0) {
					result |= tmp << 21;
				} else {
					result |= (tmp & 0x7f) << 21;
					result |= (tmp = delimitedSdusArray[index+4]) << 28;
				}
			}
		}
		return result;
	}
	
	/**
	 * Return the number of bytes required to encode a certain int using the varint schema
	 * @param value
	 * @return
	 */
	private int getNumberOfBytes(int value){
		if (value< 0){
			return 0;
		}
		
		if ((value>=0) && (value<128)){
			return 1;
		}
		
		if ((value>=128) && (value<16384)){
			return 2;
		}
		
		if ((value>=16384) && (value<2097152)){
			return 3;
		}
		
		if ((value>=2097152) && (value<268435456)){
			return 4;
		}
		
		return 5;
		
	}
}
