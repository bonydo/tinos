package rina.applicationprocess.api;

import java.util.ArrayList;
import java.util.List;

/**
 * Defines a whatevercast name (or a name of a set of names).
 * In traditional architectures, sets that returned all members were called multicast; while 
 * sets that returned one member were called anycast.  It is not clear what sets that returned 
 * something in between were called.  With the more general definition here, these 
 * distinctions are unnecessary.
 * @author eduardgrasa
 *
 */
public class WhatevercastName {
	
	/** The name **/
	private String name = null;
	
	/** The members of the set **/
	private List<byte[]> setMembers = null;
	
	/** The rule to select one or more members from the set **/
	private String rule = null;
	
	public WhatevercastName(){
		setMembers = new ArrayList<byte[]>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRule() {
		return rule;
	}

	public void setRule(String rule) {
		this.rule = rule;
	}

	public List<byte[]> getSetMembers() {
		return setMembers;
	}

	public void setSetMembers(List<byte[]> setMembers) {
		this.setMembers = setMembers;
	}
	
	public boolean equals(Object object){
		if (object == null){
			return false;
		}
		
		if (!(object instanceof WhatevercastName)){
			return false;
		}
		
		if (this.getName().equals(((WhatevercastName)object).getName())){
			return true;
		}
		
		return false;
	}
}