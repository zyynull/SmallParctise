package demo.demo.infra.enums;

import com.cmrh.uw.common.util.StringUtil;

public enum ReferenceTypeEnum {
	
	QUANTITY("定量","0"),QUALITY("定性","1");
	
	private String name;
	
	private String index;
	
	ReferenceTypeEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }
	
	public String getName() {
        return name;
    }

    public String getIndex() {
        return index;
    }
    
    public static String getIndex(String name) {
    	if(StringUtil.isNotEmpty(name)) {
    		for (ReferenceTypeEnum referenceTypeEnum :ReferenceTypeEnum.values()) {
        		if (name.equals(referenceTypeEnum.getName())) {
        			return referenceTypeEnum.getIndex();
        		}
        	}
    	}
    	return null;
    }
    
    public static String getName(String index) {
    	if(StringUtil.isNotEmpty(index)) {
    		for (ReferenceTypeEnum referenceTypeEnum :ReferenceTypeEnum.values()) {
        		if (index.equals(referenceTypeEnum.getIndex())) {
        			return referenceTypeEnum.getName();
        		}
        	}
    	}
    	return null;
    }
}
