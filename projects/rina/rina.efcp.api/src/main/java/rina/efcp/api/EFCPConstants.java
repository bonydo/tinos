package rina.efcp.api;

/**
 * Contains the constants for DTP and DTCP, defined at DIF compile time
 * TODO move to a config file after the demo
 * @author eduardgrasa
 *
 */
public interface EFCPConstants {
	
	/**
	 * The length of QoS-id field in the DTP PCI, in bytes
	 */
	public static final int QoSidLength = 1;
	
	/**
	 * The length of the Port-id field in the DTP PCI, in bytes
	 */
	public static final int PortIdLength = 2;
	
	/**
	 * The length of the CEP-id field in the DTP PCI, in bytes
	 */
	public static final int CEPIdLength = 2;
	
	/**
	 * The length of the sequence number field in the DTP PCI, in bytes
	 */
	public static final int SequenceNumberLength = 2;
	
	/**
	 * The length of the address field in the DTP PCI, in bytes
	 */
	public static final int addressLength = 2;
	
	/**
	 * The length of the length field in the DTP PCI, in bytes
	 */
	public static final int lengthLength = 2;
	
	/**
	 * The maximum length allowed for an SDU in this DIF, in bytes
	 */
	public static final int maxSDUSize = 1000;
	
	/**
	 * The maximum length allowed for a PDU in this DIF, in bytes
	 */
	public static final int maxPDUSize = 1500;
	
	/**
	 * The length of the PCI, in bytes
	 */
	public static final int pciLength = 20;
	
	/**
	 * True if the PDUs in this DIF have CRC, TTL, and/or encryption. Since 
	 * headers are encrypted, not just user data, if any flow uses encryption, 
	 * all flows within the same DIF must do so and the same encryption 
	 * algorithm must be used for every PDU; we cannot identify which flow owns 
	 * a particular PDU until it has been decrypted.
	 */
	public static final boolean DIFIntegrity = false;
	
	/**
	 * This is true if multiple SDUs can be delimited and concatenated within 
	 * a single PDU.
	 */
	public static final boolean DIFConcatenation = true;
	
	/**
	 * This is true if multiple SDUs can be fragmented and reassembled within 
	 * a single PDU.
	 */
	public static final boolean DIFFragmentation = false;
}
