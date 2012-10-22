package rina.efcp.impl.events;

/**
 * Interface for classes that encapsulate the 
 * information of an EFCP event (SDU posted to N flow, ...)
 * @author eduardgrasa
 *
 */
public interface EFCPEvent {
	
	public static final byte IPC_PROCESS_STOPPED_EVENT = 0;
	public static final byte SDU_DELIVERED_FROM_N_PORT = 1;
	public static final byte PDU_DELIVERED_FROM_RMT = 2;

	/**
	 * Returns the ID of the event
	 * @return
	 */
	public byte getId();
}
