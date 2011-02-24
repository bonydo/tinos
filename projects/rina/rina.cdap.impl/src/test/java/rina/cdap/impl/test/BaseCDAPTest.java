package rina.cdap.impl.test;

import org.junit.Before;

import rina.cdap.api.CDAPException;
import rina.cdap.api.message.CDAPMessage;
import rina.cdap.api.message.CDAPMessage.AuthTypes;
import rina.cdap.impl.CDAPSessionImpl;
import rina.cdap.impl.googleprotobuf.GoogleProtocolBufWireMessageProvider;

public abstract class BaseCDAPTest {
	
	protected CDAPSessionImpl sendingCDAPSession = null;
	protected CDAPSessionImpl receivingCDAPSession = null;
	
	@Before
	public void setup(){
		sendingCDAPSession = new CDAPSessionImpl();
		sendingCDAPSession.setWireMessageProvider(new GoogleProtocolBufWireMessageProvider());
		receivingCDAPSession = new CDAPSessionImpl();
		receivingCDAPSession.setWireMessageProvider(new GoogleProtocolBufWireMessageProvider());
	}
	
	protected void connect() throws CDAPException{
		byte[] message = null;
		CDAPMessage cdapMessage = null;
		
		cdapMessage = CDAPMessage.getOpenConnectionRequestMessage(0, AuthTypes.AUTH_NONE, null, null, "mock", null, "B", "234", "mock", "123", "A", 0);
		message = sendingCDAPSession.serializeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		cdapMessage = receivingCDAPSession.messageReceived(message);
		cdapMessage = CDAPMessage.getOpenConnectionResponseMessage(0, AuthTypes.AUTH_NONE, null, "234", "mock", "123", "A", 0, null, "899", "mock", "677", "B", 0);
		message = receivingCDAPSession.serializeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		cdapMessage = sendingCDAPSession.messageReceived(message);
	}
	
	protected void disconnectWithResponse() throws CDAPException{
		byte[] message = null;
		CDAPMessage cdapMessage = null;
		
		cdapMessage = CDAPMessage.getReleaseConnectionRequestMessage(null, 1, "234", "mock");
		message = sendingCDAPSession.serializeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		cdapMessage = receivingCDAPSession.messageReceived(message);
		cdapMessage = CDAPMessage.getReleaseConnectionResponseMessage(null, 1, 0, null, "899", "mock");
		message = receivingCDAPSession.serializeNextMessageToBeSent(cdapMessage);
		receivingCDAPSession.messageSent(cdapMessage);
		
		cdapMessage = sendingCDAPSession.messageReceived(message);
	}
	
	protected void disconnectWithoutResponse() throws CDAPException{
		byte[] message = null;
		CDAPMessage cdapMessage = null;
		
		cdapMessage = CDAPMessage.getReleaseConnectionRequestMessage(null, 0, "234", "mock");
		message = sendingCDAPSession.serializeNextMessageToBeSent(cdapMessage);
		sendingCDAPSession.messageSent(cdapMessage);
		
		cdapMessage = receivingCDAPSession.messageReceived(message);
	}

}