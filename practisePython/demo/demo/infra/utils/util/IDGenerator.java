package demo.demo.infra.utils.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


/********************************************************************
 * class 
 * 
 * @author ex-zrh001 2019/12/31
 * @version  v1.0
 *******************************************************************/ 
public class IDGenerator {
	/**
	 * 生成主键（32位）
	 * 
	 * @return
	 */
	public static String generateID() {
		/*
		 * String rtnVal = Long.toHexString(System.currentTimeMillis()); rtnVal
		 * += UUID.randomUUID(); rtnVal = rtnVal.replaceAll("-", ""); return
		 * rtnVal.substring(0, 32);
		 */
		return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
	}

	/**
	 * 查看主键生成时间
	 * 
	 * @param id
	 */
	private static void printIDTime(String id) {
		String timeInfo = id.substring(0, 11);
		System.out.println(new Date(Long.parseLong(timeInfo, 16)));
	}


    /**
     * 生成随机订单号(业务场景+时间(精确到时分秒)+随机四位随机序列)VIP201805251103580072
     * 入参：保险名称：VIP
     * @author liuxh001  update:ex-zrh001
     * @return
     * 2018年5月7日
     ***************************************************************************
     */
    public static String getRandomOrderID(String applyName) {
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String time=sdf.format(new Date());
		String randomNum=RandomUtil.genRandomNumber(4);	
		String randomOrderId=applyName+time+randomNum;
		return randomOrderId;
    }
}
