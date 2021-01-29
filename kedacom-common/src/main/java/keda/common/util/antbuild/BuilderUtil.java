package keda.common.util.antbuild;

import java.text.MessageFormat;
import java.util.*;

/**
 * 工程打包
 * 需要配置的项为webName,builderName
 * @author root
 *
 */
public class BuilderUtil {
	
	
	/**需要配置的项：主工程名称*/
	private  String webName = "";
	
	/**需要配置的项：编译工程名称*/
	private  String builderName = "builder";
	
	/**独立工程：不依赖其它工程*/
	private List<String> baseProject = new ArrayList<String>();
	/**
	 * 具有依赖关系的工程。key=工程名称, value=依赖的工程名称
	 */
	private HashMap<String, String[]> project = new HashMap<String, String[]>();
	/**
	 * <pre>
	 * 支持单独编译工程列表。 
	 * 工程需要在{@link #baseProjectArray}或{@link #project}中定义
	 * </pre>
	 * */
	private String[] singleDeployProject;

	/**
	 * <pre>
	 * 设置需要发布为WebService的工程。 
	 * 工程需要在{@link #baseProjectArray}或{@link #project}中定义
	 * </pre>
	 * */
	private String[] webserviceProjects;
	
	/**
	 * 编译脚本所在工程的名称。
	 */
	
	/**
	 * 用于Applet的jar的存放路径.
	 */
	private  String jarTargetPath = "";

	/**
	 * 指示是否全部发布为Applet工程，默认值：true。
	 */
	private boolean allPubToApplet = true;
	/**
	 * 需要发布为Applet的工程
	 */
	private Set<String> appletProjects = new HashSet<String>();
	
	private List<String> projectList = new ArrayList<String>();
	/**
	 * SQL脚本的路径。格式为：pmsres/SQL
	 */
	private List<String> sqlPaths = new ArrayList<String>();
	

	/**
	 * 开始生成编译脚本。
	 */
	public void start(){
		jarTargetPath = "${project_"+webName+"}/WebContent/jars/";
		Iterator<String> it = project.keySet().iterator();
		
		while(it.hasNext()){
			String dependPro = (String) it.next();
			String[] bases = project.get(dependPro);
			if(!projectList.contains(dependPro)){
				projectList.add(dependPro);
			}
			for (int i = 0; i < bases.length; i++) {
				if(!baseProject.contains(bases[i]) && !projectList.contains(dependPro)){
					baseProject.add(bases[i]);
				}
			}
			
		}
		project.put(webName, getAllBaseProjects());
		projectList.add(webName);
		
		start0();
	}
	
	private void start0(){
		
		startXml();				//开始
		
		init();					//初始化：定义变量和文件目录
		ready();				//准备：编译目录和编译时间
		buildBaseProject();		//编译工程：基础工程
		buildDependProject();	//编译工程：被依赖的工程
		buildSigleProject();	//编译工程：需要单独编译的工程（包括主WEB工程）
		copySql();				//拷贝数据库脚本
		
		endXml();				//结束
	}
	
	/**
	 * @deprecated replace by {@link #start()}
	 */
	public void init1(){
		getParamter();
		start0();
	}

	/**
	 * @deprecated replace by {@link #start()}
	 */
	public void init2(){
		start();
	}
	
	private String[] getAllBaseProjects(){
		String[] str = new String[baseProject.size()+projectList.size()];
		int i = 0;
		for (String base : baseProject) {
			str[i] = base;
			i++;
		}
		for (String pro : projectList) {
			str[i] = pro;
			i++;
		}
		return str;
	}
	
	private void getParamter(){
		println("base project name:");
		Scanner scan = new Scanner(System.in);
		String line;
		
		int i = 1;
		while(scan.hasNext()){
			line = scan.nextLine();
			if("end".equals(line))
				break;
			if(i == 1){
				String[] baseList = line.split(" ");
				for (int j = 0; j < baseList.length; j++) {
					baseProject.add(baseList[j]);
				}				
			}
			if(i%2 == 0){
				if(webName.equals(line))
					project.put(webName, getAllBaseProjects());
				if(baseProject.contains(line))
					baseProject.remove(line);
				projectList.add(line);
				println("depends project:");				
			}
			else{
				if(i != 1){
					String[] list = line.split(" ");
					project.put(projectList.get(projectList.size()-1), list);
					for (int j = 0; j < list.length; j++) {
						if(!baseProject.contains(list[j]) && !projectList.contains(list[j])){
							baseProject.add(list[j]);
						}
					}
				}
				println("project name:");		
			}
			i++;
		}
		scan.close();
	}
	
	private void init(){

		println("<target name=\"init\">",1);
		println("<property name=\"path_build\" value=\"${basedir}/"+builderName+"/build\" />", 2);
		println("<property name=\"path_disk\" value=\"${basedir}/"+builderName+"/disk\" />", 2);
		println("<property name=\"path_sign\" value=\"${basedir}/kedares/sign\" />", 2);
		println("<property name=\"path_lib\" value=\"${basedir}/kedares/lib\" />", 2);
		
		for (String basePro : baseProject) {
			println("<property name=\"project_"+basePro+"\" value=\"${basedir}/"+basePro+"\" />", 2);
		}
		
		for (String pro : projectList) {
			println("<property name=\"project_"+pro+"\" value=\"${basedir}/"+pro+"\" />", 2);
		}
		
		println("<property name=\"file_versionfile\" value=\"${path_build}/version.property\" />", 2);

		println("</target>", 1);
	}
	
	private void println(String str){
		System.out.println(str);
	}
	
	/**
	 * 
	 * @param str
	 * @param len 缩进级别
	 */
	private void println(String str,int len){
		String blank = " ";
		for (int i = 0; i < len*2; i++) {
			blank += " ";
		}
	/*	String format = MessageFormat.format("{0}%s", blank);
		System.out.printf(format,str);*/
		System.out.println(blank+str); 
	}
	
	private void startXml(){
		String defPrj = "build-" + webName;
		println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
		String root = MessageFormat.format("<project name=\"main\" default=\"{0}\" basedir=\"..\">", defPrj);
		println(root);
	}
	
	private void endXml(){
		println("</project>");
	}
	
	
	private void clean(){
		println("<target name=\"clean\">",1);
		
		println("<delete dir=\"${path_build}\"/>",2);
//		print("<delete dir=\"${path_disk}\"/>",2);
		println("<delete file=\"${path_disk}/"+ webName + ".war\" />",2);
		
		//清除Applet需要的jar
		for (String p : baseProject) {
			if(allPubToApplet || appletProjects.contains(p)){
				println("<delete file=\"" + jarTargetPath + p + ".jar\" />", 2);
			}
		}
		for (String p : projectList) {
			if(allPubToApplet || appletProjects.contains(p)){
				println("<delete file=\"" + jarTargetPath + p + ".jar\" />", 2);
			}
		}
		
		//删除WebService发布
		if(webserviceProjects != null){
			for(String p : webserviceProjects){
				println("<delete file=\"${project_" + webName + "}/WebContent/WEB-INF/services/" + p + ".aar\" />", 2);
			}
		}
	     
		println("</target>",1);
	}
	
	private void initBulidTime(){
		println("<target name=\"initBulidTime\">",1);
		
		println("<delete file=\"${file_versionfile}\" />",2);
		println("<touch file=\"${file_versionfile}\" />", 2);
		println("<tstamp>",2);
		println("<format property=\"dstamp\" pattern=\"yyyy-MM-dd HH:mm:ss\"/>",3);
		println("</tstamp>",2);
		println("<echo file=\"${file_versionfile}\" message=\"${dstamp}\"/>",2);
		
		println("</target>",1);
	}
	
	
	private void ready(){
		
		//清除旧目录
		clean();
		
		//准备新目录
		println("<target name=\"ready\" depends=\"init,clean\">",1);
		
		println("<mkdir dir=\"${path_build}\" />", 2);
		println("<mkdir dir=\"${path_build}/lib\" />", 2);
		println("<mkdir dir=\"${path_disk}\" />", 2);
		
		println("<antcall target=\"initBulidTime\"></antcall>", 2);
		
		println("</target>", 1);

		//编译时间
		initBulidTime();
	}
	
	//不依赖其它的工程
	private void buildBaseProject(){
		for (String base : baseProject) {
			println("<target name=\"init-"+base+"\" depends=\"ready\">",1);
			println("<mkdir dir=\"${path_build}/"+base+"\"/>",2);
			println("</target>",1);
			

			println("<target name=\"compile-"+base+"\" depends=\"init-"+base+"\">",1);
			println("<javac srcdir=\"${project_"+base+"}/src\" destdir=\"${path_build}/"+base+"\" encoding=\"utf-8\" includeantruntime=\"false\">",2);
			println("<classpath>",3);
			println("<fileset dir=\"${path_lib}\">",4);
			println("<include name=\"**/*.jar\"/>",5);
			println("</fileset>",4);
			println("</classpath>",3);
			println("</javac>",2);
			println("</target>",1);
			
			println("<target name=\"build-"+base+"\" depends=\"compile-"+base+"\">",1);
			println("<copy todir=\"${path_build}/"+base+"\">",2);
			println("<fileset dir=\"${project_"+base+"}/src\">",3);
			println("<exclude name=\"**/*.java\"/>",4);
			println("</fileset>",3);
			println("</copy>",2);
			println("<jar destfile=\"${path_build}/lib/"+base+".jar\" basedir=\"${path_build}/"+base+"\"/>",2);
			println("<signjar jar=\"${path_build}/lib/"+base+".jar\" alias=\"kedainquest\" keystore=\"${path_sign}/keystore\" storepass=\"123456\" lazy=\"false\" />",2);
			println("</target>",1);
			
		}
	}
	
	//依赖其它的工程
	private void buildDependProject(){
		for (String pro : projectList) {
			String[] list = project.get(pro);
			StringBuffer sb = new StringBuffer();
			sb.append("init-"+pro);
			for (int i = 0; i < list.length; i++) {
				sb.append(",");
				sb.append("build-"+list[i]);
			}
			String depends = sb.toString();
			if(pro.equals(webName)){
				//Web工程
				buildWebProject(depends);
			}else{
				//普通工程
				println("<target name=\"init-"+pro+"\" depends=\"ready\">",1);
				println("<mkdir dir=\"${path_build}/"+pro+"\"/>",2);
				println("</target>",1);
				

				println("<target name=\"compile-"+pro+"\" depends=\""+depends+"\">",1);
				println("<javac srcdir=\"${project_"+pro+"}/src\" destdir=\"${path_build}/"+pro+"\" encoding=\"utf-8\" includeantruntime=\"false\">",2);
				println("<classpath>",3);
				
				for (int i = 0; i < list.length; i++) {
					println("<pathelement location=\"${path_build}/lib/"+list[i]+".jar\"/>",4);
				}
				
				println("<fileset dir=\"${path_lib}\">",4);
				println("<include name=\"**/*.jar\"/>",5);
				println("</fileset>",4);
				println("</classpath>",3);
				println("</javac>",2);
				println("</target>",1);
				
				println("<target name=\"build-"+pro+"\" depends=\"compile-"+pro+"\">",1);
				println("<copy todir=\"${path_build}/"+pro+"\">",2);
				println("<fileset dir=\"${project_"+pro+"}/src\">",3);
				println("<exclude name=\"**/*.java\"/>",4);
				println("</fileset>",3);
				println("</copy>",2);
				println("<jar destfile=\"${path_build}/lib/"+pro+".jar\" basedir=\"${path_build}/"+pro+"\"/>",2);
				println("<signjar jar=\"${path_build}/lib/"+pro+".jar\" alias=\"kedainquest\" keystore=\"${path_sign}/keystore\" storepass=\"123456\" lazy=\"false\" />",2);
				println("</target>",1);
			}
			
		}
	}
	
	//编译Web工程
	private void buildWebProject(String depends){
		String pro = webName;
		//准备
		println("<target name=\"init-"+pro+"\" depends=\"ready\">",1);
		println("<mkdir dir=\"${path_build}/"+pro+"\" />",2);
		println("<mkdir dir=\"${path_build}/"+pro+"/WEB-INF\" />",2);
		println("<mkdir dir=\"${path_build}/"+pro+"/WEB-INF/classes\" />",2);
		println("</target>",1);
		
		//编译
		println("<target name=\"compile-"+pro+"\" depends=\""+depends+"\">",1);
		println("<copy file=\"${file_versionfile}\" todir=\"${project_"+pro+"}/src\"/>",2);
		println("<javac srcdir=\"${project_"+pro+"}/src\" destdir=\"${path_build}/"+pro+"/WEB-INF/classes\" encoding=\"utf-8\" includeantruntime=\"false\">",2);
		println("<classpath>",3);
		println("<fileset dir=\"${path_lib}\">",4);
		println("<include name=\"**/*.jar\"/>",5);
		println("</fileset>",4);
		println("<fileset dir=\"${path_build}\">",4);
		println("<include name=\"**/*.jar\"/>",5);
		println("</fileset>",4);
		println("</classpath>",3);
		println("</javac>",2);
		println("</target>",1);
		
		//构建目录
		println("<target name=\"build-"+pro+"\" depends=\"compile-"+pro+"\">",1);
		println("<copy todir=\"${path_build}/"+pro+"/WEB-INF/classes\">",2);
		println("<fileset dir=\"${project_"+pro+"}/src\">",3);
		println("<exclude name=\"**/*.java\"/>",4);
		println("</fileset>",3);
		println("</copy>",2);
		println("<copy todir=\"${path_build}/"+pro+"\">",2);
		println("<fileset dir=\"${project_"+pro+"}/WebContent\">",3);
		println("<exclude name=\"**/*.java\"/>",4);
		println("</fileset>",3);
		println("</copy>",2);
		
		//web工程依赖jar包　位于 WEB-INF/lib
		println("<copy todir=\"${path_build}/"+pro+"/WEB-INF/lib\">",2);
		println("<fileset dir=\"${path_build}/lib\">",3);
		println("<include name=\"**/*.jar\"/>",4);
		println("</fileset>",3);
		println("</copy>",2);
		
		if(allPubToApplet){
			//发布为Applet：全部
			println("<copy todir=\"${path_build}/"+pro+"/jars\">",2);
			println("<fileset dir=\"${path_build}/lib\">",3);
			println("<include name=\"**/*.jar\"/>",4);
			println("</fileset>",3);
			println("</copy>",2);
			
			println("<copy todir=\"${project_" + pro + "}/WebContent/jars\">", 2);
			println("<fileset dir=\"${path_build}/lib\">", 3);
			println("<include name=\"**/*.jar\"/>",4);
			println("</fileset>", 3);
			println("</copy>", 2);
		} else{
			
			//发布为Applet：指定
			for(String ap : appletProjects){
//				println("<copy file=\"${path_build}/lib/" + ap + ".jar\" todir=\"${path_build}/" + pro + "/WEB-INF/lib/\" />", 2);
				println("<copy file=\"${path_build}/lib/" + ap + ".jar\" todir=\"${path_build}/" + pro + "/jars/\" />", 2);
				println("<copy file=\"${path_build}/lib/" + ap + ".jar\" todir=\"${project_" + pro + "}/WebContent/jars/\" />", 2);
			}
		}
		if(webserviceProjects != null){
			//发布WebService工程
			for(String p : webserviceProjects){
//				println("<copy file=\"${path_build}/lib/" + p + ".jar\" todir=\"${path_build}/" + webName + "/WEB-INF/services/\" />", 2);
//				println("<move file=\"${path_build}/" + webName + "/WEB-INF/services/" + p + ".jar\" tofile=\"${path_build}/" + webName + "/WEB-INF/services/" + p + ".aar\" />", 2);
//				println("<copy file=\"${path_build}/lib/" + p + ".jar\" todir=\"${project_" + webName + "}/WebContent/WEB-INF/services/\" />", 2);
//				println("<move file=\"${project_" + webName + "}/WebContent/WEB-INF/services/" + p + ".jar\" tofile=\"${project_" + webName + "}/WebContent/WEB-INF/services/" + p + ".aar\" />", 2);
				println("<copy file=\"${path_build}/lib/" + p + ".jar\" tofile=\"${path_build}/" + webName + "/WEB-INF/services/" + p + ".aar\" />", 2);
				println("<copy file=\"${path_build}/lib/" + p + ".jar\" tofile=\"${project_" + webName + "}/WebContent/WEB-INF/services/" + p + ".aar\" />", 2);
			}
		}
		//打包并签名WAR
		println("<jar destfile=\"${path_disk}/"+pro+".war\" basedir=\"${path_build}/"+pro+"\"/>",2);
		println("<signjar jar=\"${path_disk}/"+pro+".war\" alias=\"kedainquest\" keystore=\"${path_sign}/keystore\" storepass=\"123456\" lazy=\"false\" />",2);
		

		println("<antcall target=\"copySQL\"></antcall>", 2);
		println("<delete dir=\"${path_build}\"/>",2); //删除build目录
		
		println("</target>",1);
	}
	
	private void buildSigleProject(){
		if(singleDeployProject != null && singleDeployProject.length > 0){
			for (int i = 0; i < singleDeployProject.length; i++) {
				println("<target name=\"singleDeploy-"+singleDeployProject[i]+"\" depends=\"build-"+singleDeployProject[i]+"\">",1);
				println("<copy todir=\"${path_build}/"+webName+"/WEB-INF/lib\">",2);
				println("<fileset dir=\"${path_build}/lib\">",3);
				println("<include name=\"**/"+singleDeployProject[i]+".jar\"/>",4);
				println("</fileset>",3);
				println("</copy>",2);
				println("<copy todir=\"${path_build}/"+webName+"/jars\">",2);
				println("<fileset dir=\"${path_build}/lib\">",3);
				println("<include name=\"**/"+singleDeployProject[i]+".jar\"/>",4);
				println("</fileset>",3);
				println("</copy>",2);
				println("<copy  todir=\"${project_"+webName+"}/WebContent/jars\">",2);
				println("<fileset dir=\"${path_build}/lib\">",3);
				println("<include name=\"**/"+singleDeployProject[i]+".jar\"/>",4);
				println("</fileset>",3);
				println("</copy>",2);
				println("</target>",1);
			}
		}
	}
	
	/**
	 * 生成编译脚本：拷贝数据库脚本
	 */
	private void copySql(){
		
		println("<target name=\"copySQL\"  depends=\"init\">", 1);
		println("<delete dir=\"${path_disk}/SQL\" />", 2);
		if(sqlPaths.size() > 0){
			println("<mkdir dir=\"${path_disk}/SQL\" />", 2);
			println("<copy todir=\"${path_disk}/SQL\">", 2);
			for(String path : sqlPaths){
				println("<fileset dir=\"" + path + "\">", 3);
				println("<include name=\"*.sql\"/>", 4);
				println("<include name=\"*.SQL\"/>", 4);
				println("</fileset>", 3);
			}
			println("</copy>", 2);
		}
		println("</target>", 1);
	}
	
	
	/**
	 * @deprecated 这是一个空的方法
	 * @param builderProjectName
	 */
	public void setBuilderProjectName(String builderProjectName) {
	}

	/**
	 * @deprecated 重命名为{@link #setProject4SingleDeploy(String[])}
	 */
	public void setSingleDeployPro(String[] singleDeployPro) {
		this.setProject4SingleDeploy(singleDeployPro);
	}
	/**
	 * 设置支持单独编译的工程。
	 * @param singleDeployPro
	 */
	public void setProject4SingleDeploy(String[] projects){
		this.singleDeployProject = projects;
	}
	/**
	 * 设置具有依赖关系的工程
	 * @param project
	 * @see #setBaseProjectArray(String[])
	 */
	public void setProject(HashMap<String, String[]> project) {
		this.project = project;
	}

	/**
	 * @deprecated 重命名为{@link #setProject4Base(String[])}
	 */
	public void setBaseProjectArray(String[] baseProjectArray) {
		this.setProject4Base(baseProjectArray);
	}
	/**
	 * 设置不具有依赖关系的工程
	 * @param projects
	 * @see #setProject(HashMap)
	 */
	public void setProject4Base(String[] projects) {
		if(projects != null){
			for (int i = 0; i < projects.length; i++) {
				baseProject.add(projects[i]);
			}
		}
	}
	
	/**
	 * 设置主工程的名称
	 * @param webName
	 */
	public void setWebName(String webName) {
		this.webName = webName;
	}
	
	/**
	 * 设置编译脚本所在工程的名称。
	 * @param builderName
	 */
	public void setBuilderName(String builderName) {
		this.builderName = builderName;
	}
	/**
	 * 设置需要发布为WebService的工程。
	 * @param wsServerPrj
	 */
	public void setWsServerPrj(String[] wsServerPrj) {
		this.webserviceProjects = wsServerPrj;
	}

	/**
	 * @deprecated 方法重命名为{@link #setProject4Applet(String[])}
	 * @param appletProjects
	 */
	public void setAppletProjects(String[] appletProjects) {
		this.setProject4Applet(appletProjects);
	}


	/**
	 * 设置需要发布为Applet的工程。这些工程的目标文件(jar)将被复制到Web工程的“WebContent/jars/”目录下。也可以指定目标路径（{@link #setJarTargetPath(String)}）
	 * @param appletProjects
	 */
	public void setProject4Applet(String[] projectes){
		
		this.appletProjects.clear();
		this.allPubToApplet = false;
		
		int size = projectes != null ? projectes.length : 0;
		if(size > 0){
			
			for(String p : projectes){
				this.appletProjects.add(p);
			}
		}
	}
	
	/**
	 * 否全部发布为Applet工程。默认：true
	 * @param allPubToApplet
	 */
	public void setAllPubToApplet(boolean allPubToApplet) {
		this.allPubToApplet = allPubToApplet;
	}

	/**
	 * 设置Applet目标文件(.jar)存放的路径。
	 * @param path
	 */
	public void setJarTargetPath(String path){
		this.jarTargetPath = path;
	}

	public void addSqlPath(String sqlPath){
		this.sqlPaths.add(sqlPath);
	}
}
