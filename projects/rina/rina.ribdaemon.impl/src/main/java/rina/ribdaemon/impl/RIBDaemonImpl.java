package rina.ribdaemon.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import rina.cdap.api.CDAPException;
import rina.cdap.api.CDAPSession;
import rina.cdap.api.CDAPSessionFactory;
import rina.cdap.api.message.CDAPMessage;
import rina.cdap.api.message.CDAPMessage.Opcode;
import rina.ipcprocess.api.IPCProcess;
import rina.ribdaemon.api.MessageSubscriber;
import rina.ribdaemon.api.MessageSubscription;
import rina.ribdaemon.api.RIBDaemon;
import rina.ribdaemon.api.RIBDaemonException;
import rina.ribdaemon.api.UpdateStrategy;

/**
 * RIBDaemon that stores the objects in memory
 * @author eduardgrasa
 *
 */
public class RIBDaemonImpl implements RIBDaemon{
	
	private static final Log log = LogFactory.getLog(RIBDaemonImpl.class);
	
	/** The IPCProcess where this RIB Daemon belongs **/
	private IPCProcess ipcProcess = null;
	
	/** All the message subscribers **/
	private Map<MessageSubscription, List<MessageSubscriber>> messageSubscribers = null;
	
	/** A simple in memory store **/
	private InMemoryStore store = null;
	
	/** Create, retrieve and delete CDAP sessions **/
	private CDAPSessionFactory cdapSessionFactory = null;
	
	public RIBDaemonImpl(){
		messageSubscribers = new HashMap<MessageSubscription, List<MessageSubscriber>>();
		store = new InMemoryStore();
	}

	public void setIPCProcess(IPCProcess ipcProcess) {
		this.ipcProcess = ipcProcess;
	}
	
	public void setCDAPSessionFactory(CDAPSessionFactory cdapSessionFactory){
		this.cdapSessionFactory = cdapSessionFactory;
	}

	/**
	 * Invoked by the RMT when it detects a CDAP message. The RIB Daemon has to process the CDAP message and, 
	 * if valid, it will either pass it to interested subscribers and/or write to storage and/or modify other 
	 * tasks data structures. It may be the case that the CDAP message is not addressed to an application 
	 * entity within this IPC process, then the RMT may decide to rely the message to the right destination 
	 * (after consulting an adequate forwarding table).
	 * @param cdapMessage
	 */
	public void cdapMessageDelivered(byte[] encodedCDAPMessage, int portId){
		CDAPMessage cdapMessage = null;
		CDAPSession cdapSession = null;
		
		//1 Deserialize the message
		try{
			cdapMessage = cdapSessionFactory.deserializeCDAPMessage(encodedCDAPMessage);
		}catch(CDAPException ex){
			log.error("Error decoding CDAP message: " + ex.getMessage());
			ex.printStackTrace();
			return;
		}
		
		//2 Look for the CDAP session it belongs to and update the state machine
		cdapSession = cdapSessionFactory.getCDAPSession(portId);
		
		switch(cdapMessage.getOpCode()){
		case M_CONNECT:
			if (cdapSession != null){
				log.error("Cannot open a new CDAP session that is already connected on the port_id "+portId);
			}else{
				//TODO need to create a new CDAP session, but first I 
				//have to see if I accept to do it? 
				//If I have to do no authentication, I can assume that, as the
				//flow was already setup, I already have permission to accept the 
				//CDAP connection.
			}
			break;
		case M_CONNECT_R:
			if (cdapSession == null){
				log.error("Cannot find an ongoing CDAP session for the portId "+portId);
			}else{
				
			}
			break;
		case M_RELEASE:
		case M_RELEASE_R:
			default:
		}
		
		
		//3 Process the message (send to subscribed people, maybe something else)
	}

	/**
	 * Gets one or more objects from the RIB, matching the information specified by objectClass, objectInstance, objectName and a template object.
	 * All the parameters but objectClass are optional.
	 * @param objectClass optional if objectInstance specified, mandatory otherwise. A string identifying the class of the object
	 * @param objectInstance optional if objectClass specified, mandatory otherwise. An id that uniquely identifies the object within a RIB
	 * @param objectName optional if objectClass specified, ignored otherwise. An id that uniquely identifies an object within an objectClass
	 * @param template optional (if objectClass + objectName or objectInstance specified, template is ignored). A template object whose fields
	 * have to match the requested object(s)
	 * @return null if no object matching the call arguments can be found, one or more objects otherwise
	 */
	public Object read(String objectClass, long objectInstance, String objectName, Object template) throws RIBDaemonException{
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Store an object to the RIB. This may cause a new object to be created in the RIB, or an existing object to be updated.
	 * @param objectClass optional if objectInstance specified, mandatory otherwise. A string identifying the class of the object
	 * @param objectInstance objectInstance optional if objectClass specified, mandatory otherwise. An id that uniquely identifies the object within a RIB
	 * @param objectName optional if objectClass specified, ignored otherwise. An id that uniquely identifies an object within an objectClass
	 * @param objectToWrite the object to be written to the RIB
	 * @throws RIBDaemonException if there are problems performing the "write" operation to the RIB
	 */
	public synchronized void write(String objectClass, long objectInstance, String objectName, Object objectToWrite) throws RIBDaemonException{
		// TODO Auto-generated method stub

	}

	/**
	 * Remove an object from the RIB
	 * @param objectClass optional if objectInstance specified, mandatory otherwise. A string identifying the class of the object
	 * @param objectInstance objectInstance optional if objectClass specified, mandatory otherwise. An id that uniquely identifies the object within a RIB
	 * @param objectName optional if objectClass specified, ignored otherwise. An id that uniquely identifies an object within an objectClass
	 * @throws RIBDaemonException if there are problems removinb the objects from the RIB
	 */
	public synchronized void remove(String objectClass, long objectInstance, String objectName) throws RIBDaemonException{
		
	}

	/**
	 * Send an information update, consisting on a set of CDAP messages, using the updateStrategy update strategy
	 * (on demand, scheduled)
	 * @param cdapMessages
	 * @param updateStrategy
	 */
	public synchronized void sendMessages(CDAPMessage[] cdapMessage, UpdateStrategy arg1) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Interested MessageSubscribers will be called when CDAP that comply with the 
	 * filter defined by the non-default attributes of the messageSubscription class are received.
	 * @param messageSubscription
	 * @param messageSubscriber
	 * @throws Exception if there's something wrong with the messageSubscription or messageSubscriber is null
	 */
	public synchronized void subscribeToMessages(MessageSubscription messageSubscription, MessageSubscriber messageSubscriber) throws RIBDaemonException {
		if (messageSubscription == null || messageSubscriber == null){
			throw new RIBDaemonException(RIBDaemonException.MALFORMED_MESSAGE_SUBSCRIPTION_REQUEST);
		}
		
		List<MessageSubscriber> subscribers = messageSubscribers.get(messageSubscription);
		if (subscribers == null){
			subscribers = new ArrayList<MessageSubscriber>();
			subscribers.add(messageSubscriber);
			messageSubscribers.put(messageSubscription, subscribers);
		}else{
			subscribers.add(messageSubscriber);
		}
	}

	/**
	 * MessageSubscribers will stop being called when CDAP messages that comply with the 
	 * filter defined by the non-default attributes of the messageSubscription class are received.
	 * @param messageSubscription
	 * @param messageSubscriber
	 * @throws Exception if there's something wrong with the messageSubscription or messageSubscriber is null, or the 
	 * messageSubscription does not exist
	 */
	public synchronized void unsubscribeFromMessages(MessageSubscription messageSubscription, MessageSubscriber messageSubscriber) throws RIBDaemonException {
		if (messageSubscription == null || messageSubscriber == null){
			throw new RIBDaemonException(RIBDaemonException.MALFORMED_MESSAGE_UNSUBSCRIPTION_REQUEST);
		}
		
		List<MessageSubscriber> subscribers = messageSubscribers.get(messageSubscription);
		if (subscribers == null){
			throw new RIBDaemonException(RIBDaemonException.SUBSCRIBER_WAS_NOT_SUBSCRIBED);
		}
		
		subscribers.remove(messageSubscriber);
		if (subscribers.size() == 0){
			messageSubscribers.remove(messageSubscription);
		}
	}

}
