package jvm;

public class testOnBoolean {
    public static void main(String[] args) {
        //直接编译的话javac会报错，需要使用AsmTools(https://asm.ow2.io/)生成.asm，然后再生成.class
        //boolean在虚机中实际上是int类型，所以
        boolean 吃过饭没 = false;
        //boolean 吃过饭没 = 2;
        //if(2)
        if (吃过饭没) System.out.println("吃了");
        //if(1==2)
        if (true == 吃过饭没) System.out.println("真吃了");
    }
}
