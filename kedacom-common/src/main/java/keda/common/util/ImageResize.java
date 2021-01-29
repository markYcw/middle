package keda.common.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 图片编辑工具类
 * @author root
 *
 */
public class ImageResize {

	/**  
     * 实现图像的等比缩放  
	 * @param source  
	 * @param targetW  
	 * @param targetH  
	 * @return  
   */
	public static BufferedImage resize(BufferedImage source, int targetW,int targetH) {
	  // targetW，targetH分别表示目标长和宽   
	  int type = source.getType();
	  BufferedImage target = null;
	  double sx = (double) targetW / source.getWidth();
	  double sy = (double) targetH / source.getHeight();
	  	  // 这里想实现在targetW，targetH范围内实现等比缩放。如果不需要等比缩放   
	  	// 则将下面的if else语句注释即可   
	//  if (sx < sy) {
	//      sx = sy;
	//      targetW = (int) (sx * source.getWidth());
	//  } else {
	//      sy = sx;
	//      targetH = (int) (sy * source.getHeight());
	//  		}
	  sy = sx;
	  targetH = (int) (sx * source.getHeight());
	  if (type == BufferedImage.TYPE_CUSTOM) { // handmade   
	      ColorModel cm = source.getColorModel();
	      WritableRaster raster = cm.createCompatibleWritableRaster(targetW,targetH);
	      boolean alphaPremultiplied = cm.isAlphaPremultiplied();
	      target = new BufferedImage(cm, raster, alphaPremultiplied, null);
	  } else
	      target = new BufferedImage(targetW, targetH, type);
	  
	  Graphics2D g = target.createGraphics();
	  // smoother than exlax:   
	  g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BICUBIC);
	  g.drawRenderedImage(source, AffineTransform.getScaleInstance(sx, sy));
	  g.dispose();
	  return target;
  }

  /**  
      * 实现图像的等比缩放和缩放后的截取  
	 * @param inFilePath 要截取文件的路径  
	 * @param outFilePath 截取后输出的路径  
	 * @param width 要截取宽度  
	 * @param hight 要截取的高度  
	 * @param proportion  
	 * @throws Exception  
   */
	public static void saveImageAsJpg(String inFilePath, String outFilePath,
	        int width, int hight, boolean proportion) throws Exception {
		File file = new File(inFilePath);
		InputStream in = new FileInputStream(file);
		File saveFile = new File(outFilePath);

		BufferedImage srcImage = ImageIO.read(in);
		if (width > 0 || hight > 0) {
	        // 原图的大小   
			int sw = srcImage.getWidth();
			int sh = srcImage.getHeight();
	        // 如果原图像的大小小于要缩放的图像大小，直接将要缩放的图像复制过去   
			if (sw > width && sh > hight) {
				srcImage = resize(srcImage, width, hight);
			} else {
				String fileName = saveFile.getName();
				String formatName = fileName.substring(fileName
	                .lastIndexOf('.') + 1);
				ImageIO.write(srcImage, formatName, saveFile);
				return;
	        }
	    }
	    // 缩放后的图像的宽和高   
		int w = srcImage.getWidth();
		int h = srcImage.getHeight();
	    // 如果缩放后的图像和要求的图像宽度一样，就对缩放的图像的高度进行截取   
		if (w == width) {
			int x = 0;// 计算X轴坐标 
			int y = h / 2 - hight / 2;
			saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
	    }
	    // 否则如果是缩放后的图像的高度和要求的图像高度一样，就对缩放后的图像的宽度进行截取   
		else if (h == hight) {
			int x = w / 2 - width / 2;// 计算X轴坐标  
			int y = 0;
			saveSubImage(srcImage, new Rectangle(x, y, width, hight), saveFile);
	    }
		in.close();
	}

	/**  
      * 实现缩放后的截图  
	 * @param image 缩放后的图像  
	 * @param subImageBounds 要截取的子图的范围  
	 * @param subImageFile 要保存的文件  
	 * @throws IOException  
   */
	private static void saveSubImage(BufferedImage image,
          Rectangle subImageBounds, File subImageFile) throws IOException {
		if (subImageBounds.x < 0 || subImageBounds.y < 0 || subImageBounds.width - subImageBounds.x > image.getWidth() 
				|| subImageBounds.height - subImageBounds.y > image.getHeight()) {
		    return;
		}
		BufferedImage subImage = image.getSubimage(subImageBounds.x,
		        subImageBounds.y, subImageBounds.width, subImageBounds.height);
		String fileName = subImageFile.getName();
		String formatName = fileName.substring(fileName.lastIndexOf('.') + 1);
		ImageIO.write(subImage, formatName, subImageFile);
	}
	
	
	 /** 
     * 截图指定大小的图片 
     * @param srcPath 源图片路径
     * @param tarPath 截图保存路径
     * @param imgType 图片类型：jpg、png、gif
     * @param startX  截图X坐标
     * @param startY  截图Y坐标
     * @param width  截图宽度
     * @param height  截图高度
     */
    public void cut(String srcPath,String tarPath,String imgType,int startX,int startY,int width,int height){ 
        try { 
        	if(imgType == null || imgType.length() == 0)
        		imgType = "jpg";
        	
            BufferedImage bufImg = ImageIO.read(new File(srcPath)); 
            BufferedImage  subImg = bufImg.getSubimage(startX, startY, width, height); 

            if(subImg.getWidth()!=width || subImg.getHeight()!=height){ 
                BufferedImage tempImg = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB); 
                tempImg.getGraphics().drawImage(subImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0,null); 
                ImageIO.write(tempImg, imgType, new File(tarPath)); 
            }else{ 
                ImageIO.write(subImg,imgType,new File(tarPath)); 
            } 
        } catch (Exception e) { 
            e.printStackTrace(); 
        } 
    } 
    
    /**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		ImageResize r = new ImageResize();
//		String srcPath = "/var/ftphome/ipcpersonpic/imgcj/ladybug_ant.jpg";
//		String tarPath = "/var/ftphome/ipcpersonpic/imgcj/ladybug_ant_m.jpg";
//		
//		int startX = 120;
//		int startY = 32;
//		
//		int width = 192;
//		int height = 224;
//		
//		
//		r.cut(srcPath, tarPath, "jpg",startX, startY, width, height);
	}
}
