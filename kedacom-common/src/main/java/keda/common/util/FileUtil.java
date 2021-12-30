package keda.common.util;


import org.apache.log4j.Logger;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * 文件操作工具类
 * 
 * @author LinChaoYu
 *
 */
public class FileUtil {
	private static final Logger log = Logger.getLogger(RuntimeUtil.class);
	private static final long DIFFDAY = 1000 * 60 * 60 * 24;
	private static final int  BUFFER_SIZE = 2 * 1024;

	/**
	 * 删除文件
	 * 
	 * @param path
	 *            文件所在的目录
	 * @param maxDay
	 *            超过的天数
	 * @param prefixs
	 *            所要删除的文件前缀
	 */
	public static void deleteFile(String path, int maxDay, String... prefixs) {
		File file = new File(path);
		if (file.exists()) {
			long curTime = System.currentTimeMillis();
			log.debug("===》deleteFile curTime : " + curTime);
			
			for (String prefix : prefixs) {
				File[] files = file.listFiles();
				if (files != null) {
					for (File temp : files) {
						if (temp.exists()) {
							String name = temp.getName();
							log.debug("===》deleteFile fileName : " + name);
							
							if (name.startsWith(prefix)) {
								long fileTime = temp.lastModified();
								log.debug("===》deleteFile lastModified : " + fileTime);
								long diffDay = (curTime - fileTime) / DIFFDAY;
								log.debug("===》deleteFile diffDay : " + diffDay);
								
								if (diffDay > maxDay) {
									log.debug("delete file " + name
											+ ", isExists : " + temp.exists());
									temp.delete();
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * 删除指定文件夹下的所有文件
	 * 
	 * @param path
	 *            文件所在的目录
	 */
	public static void deleteAllFile(String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				File[] files = file.listFiles();
				if (files != null) {
					for (File temp : files) {
						if (temp.exists()) {
							boolean res = temp.delete();
							if(!res)
								log.error("删除失败："+temp.getPath());
						}
					}
				}
				
				boolean res = file.delete();
				if(!res)
					log.error("删除失败："+file.getPath());
			}
		} catch (Exception e) {
			log.error(e);
		}
	}
	
	/**
	 * 删除指定路径的文件或文件夹（包含所有子文件和文件夹）
	 * 
	 * @param path
	 *            文件所在的目录
	 */
	public static void deleteFile(String path) {
		try {
			File file = new File(path);
			if (file.exists()) {
				
				// 赋权限
				Runtime.getRuntime().exec("chmod -R 777 "+path);
				
				// 执行删除命令
				String[] cmd = new String[]{"/bin/sh","-c","rm -rf "+path};
				Runtime.getRuntime().exec(cmd);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 文件拷贝
	 * @param srcPath  源文件
	 * @param tergetPath 目标文件
	 */
	public static void Copy(String srcPath, String tergetPath) {
		InputStream fileIn = null;
		FileOutputStream fileOut = null;
		try {
			int byteread = 0;
			
			File oldfile = new File(srcPath);
			if (oldfile.exists()) {
				
				// 校验目标文件夹
				File tPath = new File(tergetPath);
				File parentFile = tPath.getParentFile();
				
				// 如果目标文件所在目录不存在，这里创建文件夹
				if(!parentFile.exists()){
					parentFile.mkdirs();
				}
				
				fileIn = new FileInputStream(srcPath);
				fileOut = new FileOutputStream(tergetPath);
				byte[] buffer = new byte[1444];
				while ((byteread = fileIn.read(buffer)) != -1) {
					fileOut.write(buffer, 0, byteread);
				}
			} else {
				throw new Exception("源文件未找到，" + srcPath);
			}
		} catch (Exception e) {
			log.error("Copy", e);
		}finally{
			if(fileIn != null){
				try {
            	fileIn.close();
            } catch (IOException e) {
            	log.error("fileIn close",e);
            	}
            }
			if(fileOut != null){
				try {
					fileOut.close();
            } catch (IOException e) {
            	log.error("fileOut close",e);
            	}
            }
        }
	}
	
	/**
	 * 拷贝文件夹
	 * @param sourcePath
	 * @param newPath
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static void copyDir(String sourcePath, String newPath) throws IOException {
		try{
			File file = new File(sourcePath);
			String[] filePath = file.list();
	        
			if (!(new File(newPath)).exists()) {
				(new File(newPath)).mkdir();
	        }
	        
			for (int i = 0; i < filePath.length; i++) {
            if ((new File(sourcePath + file.separator + filePath[i])).isDirectory()) {
                copyDir(sourcePath  + file.separator  + filePath[i], newPath  + file.separator + filePath[i]);
            	}
            
            if (new File(sourcePath  + file.separator + filePath[i]).isFile()) {
            	Copy(sourcePath + file.separator + filePath[i], newPath + file.separator + filePath[i]);
            	}
	        }
		} catch (Exception e) {
			log.error("copyDir", e);
		}
    }

    /**
     * 压缩成ZIP
    * @param srcFile 待压缩的【文件夹】路径 
    * @param tergetFile 目标压缩文件路径（/xx/xx/xx.zip）
    * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构;
    *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
    * @throws RuntimeException 压缩失败会抛出运行时异常
     */
    public static void toZip(String srcFile, String tergetFile, boolean KeepDirStructure) throws RuntimeException{
    	long start = System.currentTimeMillis();
    	ZipOutputStream zos = null;
    	OutputStream out = null;
    	try {
    		
    		// 如果目标文件所在目录不存在，这里创建文件夹
			File tPath = new File(tergetFile);
			File parentFile = tPath.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs();
			}
    		
    		out = new FileOutputStream(tergetFile);
        	zos = new ZipOutputStream(out);
        	
        	log.debug("===》开始压缩，请稍等片刻...");
        	
        	File sourceFile = new File(srcFile);
        	compress(sourceFile,zos,sourceFile.getName(),KeepDirStructure);

        	long end = System.currentTimeMillis();
        	log.debug("===》压缩完成，耗时：" + (end - start) +" ms");

    	} catch (Exception e) {
    		throw new RuntimeException("FileUtil zip",e);
    	}finally{
    		if(zos != null){
    			try {
    				zos.close();
    			} catch (IOException e) {
    				log.error("zos close",e);
                }
            }
    		if(out != null){
    			try {
    				out.close();
    			} catch (IOException e) {
    				log.error("out close",e);
                }
            }
        }
    }

    /**
     * 递归压缩方法
     * @param sourceFile 源文件
     * @param zos   zip输出流
     * @param name  压缩后的名称
     * @param KeepDirStructure  是否保留原来的目录结构,true:保留目录结构; 
     *                          false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名文件,会压缩失败)
     * @throws Exception
     */
    private static void compress(File sourceFile, ZipOutputStream zos, String name, boolean KeepDirStructure) throws Exception{
    	byte[] buf = new byte[BUFFER_SIZE];
    	if(sourceFile.isFile()){
    		// 向zip输出流中添加一个zip实体，构造器中name为zip实体的文件的名字
    		zos.putNextEntry(new ZipEntry(name));

    		// copy文件到zip输出流中
    		int len;
    		FileInputStream in = new FileInputStream(sourceFile);
    		while ((len = in.read(buf)) != -1){
            zos.write(buf, 0, len);
            }
    		zos.closeEntry();
    		in.close();
    	} else {
    		File[] listFiles = sourceFile.listFiles();
    		if(listFiles == null || listFiles.length == 0){
    			// 需要保留原来的文件结构时,需要对空文件夹进行处理
    			if(KeepDirStructure){
               zos.putNextEntry(new ZipEntry(name + "/"));// 空文件夹的处理
               zos.closeEntry(); // 没有文件，不需要文件的copy
                }
    		}else {
    			for (File file : listFiles) {
                    // 判断是否需要保留原来的文件结构
               if (KeepDirStructure) {
                       // 注意：file.getName()前面需要带上父文件夹的名字加一斜杠,
                       // 不然最后压缩包中就不能保留原来的文件结构,即：所有文件都跑到压缩包根目录下了
                 compress(file, zos, name + "/" + file.getName(),KeepDirStructure);
               } else {
                 compress(file, zos, file.getName(),KeepDirStructure);
                    }
                }
            }
        }
    }

	public static void main(String[] args) {
		deleteFile("/opt/ivshome/apache/logs", 2, "catalina", "host-manager",
				"localhost", "manager");
	}
}
