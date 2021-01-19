package demo.demo.infra.utils.constant;

public abstract class SystemConstant {
    public static final String Y = "Y";
    public static final String N = "N";

    public static final String SUCCESS = "SUCCESS";
    public static final String FAIL = "FAIL";

    public static final String ONE = "1";
    public static final String TWO = "2";
    public static final String THREE = "3";
    public static final String FOUR = "4";

    public interface  HospitalStatus{
        //合作中
        String IN_COOPERATION ="0";
        //停止合作
        String  STOP_COOPERATION ="1";
        //暂停合作
        String  SUSPENSION_COOPERATION ="2";

    }


    public interface  TaskStatus{
        //未开始
        String  NOT_YET  ="0";
        //暂存
        String  TEMPORARILY ="1";
        //已完成
        String  DONE ="2";
        //撤销
        String CANCEL="3";
        //已领取
        String RECEIVED = "4";
        //空回销
        String EMPTY_WRITE_OFF = "5";

    }
}
