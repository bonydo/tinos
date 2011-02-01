package rina.flowallocator.impl;

import java.util.Map;
import rina.ipcservice.api.AllocateRequest;
import rina.ipcservice.api.ApplicationProcessNamingInfo;
import rina.ipcservice.api.IPCService;
import rina.ribdaemon.api.MessageSubscriber;
import rina.ribdaemon.api.MessageSubscription;
import rina.flowallocator.api.ReleaseResources;
import rina.flowallocator.impl.FlowAllocatorInstance;


/** 
 * Implements the Flow Allocator
 */

public class FlowAllocator implements IPCService, ReleaseResources {
	
	private ApplicationProcessNamingInfo requestedAPinfo = null;
	private int portId = 0;
	//private QoSCube cube = null;
	private boolean result = false;
	private int FAid = 0;
	private FlowAllocatorInstance FAI = null;
	private boolean validAllocateRequest = false;
	private boolean validApplicationProcessNamingInfo = false;
	private MessageSubscription subscription = null;



	public FlowAllocator() {

	}

	public void submitAllocateRequest(AllocateRequest request) {
		try {
			validateRequest(request);
			
			request.setPort_id(assignPortId());
			FAI = new FlowAllocatorInstance();
			FAI.forwardAllocateRequest(request);
			
			subscription = new MessageSubscription();
			//subscribeToMessages(subscription, subscriber);
			
			// TODO check AllocateNotifyPolicy to see if you should return AllocateResponse 
			// if(AllocateNotifyPolicy)
				// deliverAllocateResponse(request.getRequestedAPinfo(), request.getPort_id(), true, "");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		else
//			deliverAllocateResponse(request.getRequestedAPinfo(), request.getPort_id(), false, "The format of request was not valid");
	}

	/**
	 * 
	 * Assigns a port-id for the specific AllocateRequest
	 */
	private int assignPortId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void submitAllocateResponse(int portId, boolean result) {
		// TODO Auto-generated method stub
		
	}

	public void submitDeallocate(int portId) {
		// TODO Auto-generated method stub
		
	}

	
	public void submitStatus(int portId) {
		// TODO Auto-generated method stub
		
	}

	public void submitTransfer(int portId, byte[] sdu, boolean result) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Validates an AllocateRequest: 
	 * 		- ApplicationProcessNamingInfo requestedAPinfo
	 * 		- int port_id
	 * 		- QoSCube cube
	 * 		- boolean result
	 * @param request
	 * @return yes if valid, no otherwise
	 * @throws Exception 
	 */
	public static void validateRequest(AllocateRequest request) throws Exception{
		validateApplicationProcessNamingInfo(request.getRequestedAPinfo());
		//validateQoScube(request.getCube());
			
	}


	public boolean submitTransfer(int portId, byte[] sdu) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public void register(
			ApplicationProcessNamingInfo applicationProcessNamingInfo) {
		// TODO Auto-generated method stub
		
	}
	
	public void unregister(
			ApplicationProcessNamingInfo applicationProcessNamingInfo) {
		// TODO Auto-generated method stub
		
	}

	
	/** 
	 * Validates the AP naming info (applicationProcessName, applicationProcessInstance, applicationEntityName and applicationEntityInstance)
	 * @param APnamingInfo
	 * @throws Exception
	 */
	public static void validateApplicationProcessNamingInfo(ApplicationProcessNamingInfo APnamingInfo) throws Exception{
		validateApplicationProcessName(APnamingInfo.getApplicationProcessName());
		validateApplicationProcessInstance(APnamingInfo.getApplicationProcessInstance());
		validateApplicationEntityName(APnamingInfo.getApplicationEntityName());
		validateApplicationEntityInstance(APnamingInfo.getApplicationEntityInstance());
	}
	
	/**
	 * Validates the applicationProcessName
	 * @param applicationProcessName
	 * @throws Exception
	 */
	private static void validateApplicationProcessName(String applicationProcessName) throws Exception
	{
		if (applicationProcessName.equals(null))
			throw new Exception("Application process name is empty");
	}
	
	
	/**
	 * Validates the applicationProcessInstance
	 * @param applicationProcessInstance
	 * @throws Exception
	 */
	private static void validateApplicationProcessInstance(String applicationProcessInstance) throws Exception
	{
			
	}
	
	
	
	/**
	 * Validates the applicationEntityName
	 * @param ApplicationEntityName
	 * @throws Exception
	 */
	private static void validateApplicationEntityName(String ApplicationEntityName) throws Exception
	{
		
	}
	
	
	
	/**
	 * Validates the applicationEntityInstance
	 * @param applicationEntityInstance
	 * @throws Exception
	 */
	private static void validateApplicationEntityInstance(String applicationEntityInstance) throws Exception
	{
		
	}
	
//	
//	public static void validateQoScube(QoSCube cube) throws Exception{
//		Map<String, Object> qos_cube = cube.getCube();
//		//TODO add check
//	}

	
	public void forwardDeAllocateRequest(int portId) {
		// TODO Auto-generated method stub
		
	}

	

}
