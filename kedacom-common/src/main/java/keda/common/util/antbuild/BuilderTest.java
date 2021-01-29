package keda.common.util.antbuild;
import java.util.HashMap;

/**
 * 编译入口
 * @author root
 *
 */
public class BuilderTest {
	public static void main(String[] args) {

		//不具有依赖关系的工程
		String[] baseProjects = {"initapplet", "kedacommon", "kedatrialresource", "kedanet", "kedafile"};
		
		//需要单独编译的工程(可以单独build和发布)
		String[] singleDeployPro = {"initapplet"};
		
		//具有依赖关系的工程
		HashMap<String, String[]> dependsMap = new HashMap<String, String[]>();
		dependsMap.put("kedacascade", new String[]{"kedacommon", "kedanet", "kedafile"});
		dependsMap.put("kedaplatform", new String[]{"kedacommon", "kedanet"});
		dependsMap.put("kedadevicetree", new String[]{"kedacommon", "kedaplatform", "kedatrialresource"});
		dependsMap.put("kedaemap", new String[]{"kedacommon", "kedanet", "kedaplatform", "kedatrialresource"});
		dependsMap.put("kedacom-sims", new String[]{"kedacommon", "kedaplatform"});
		dependsMap.put("ccms-court", new String[]{"kedacommon","ccmswebcommon"});
		
		dependsMap.put("ccms-devsync", new String[]{"kedacommon", "kedaplatform", "kedacom-sims"});
		dependsMap.put("ccmswebcommon", new String[]{"kedacommon", "kedanet", "kedaplatform", "kedacascade", "ccms-devsync", "kedacom-sims"});
		dependsMap.put("ccmscommon", new String[]{"kedacommon", "kedanet", "kedacascade", "kedaplatform", "kedaemap", "ccmswebcommon","ccms-court"});

		//需要发布为Applet的工程
//		String[] appletProjects = new String[]{"", "", ""};
		
		BuilderUtil builder = new BuilderUtil();
		builder.setBuilderName("builder");
		builder.setWebName("ccmsweb");
		builder.setProject4Base(baseProjects);
		builder.setProject(dependsMap);
		builder.setProject4SingleDeploy(singleDeployPro);
//		builder.setProject4Applet(appletProjects);
		builder.start();
	}
}
