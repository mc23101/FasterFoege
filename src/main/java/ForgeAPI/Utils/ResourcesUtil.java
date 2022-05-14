package ForgeAPI.Utils;

public class ResourcesUtil {
    public static String getResourcesPath(String path){
        String s=ResourcesUtil.class.getResource("/"+path).getPath();
        System.out.println(s);
        return s;
    }
}
