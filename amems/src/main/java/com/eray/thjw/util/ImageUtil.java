package com.eray.thjw.util;

import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * 图片裁剪压缩工具类
 * @author Admin_PC
 *
 */
public class ImageUtil {
	 /**
	  *   
	  * @param src
	  * @param dest
	  * @param w
	  * @param h
	  * @throws Exception
	  */
    public static void zoomImage(File srcFile,File destFile,int w,int h) throws Exception {  
        double wr=0,hr=0;  
        BufferedImage bufImg = ImageIO.read(srcFile);  
        Image Itemp = bufImg.getScaledInstance(w, h, bufImg.SCALE_SMOOTH);  
        wr=w*1.0/bufImg.getWidth();  
        hr=h*1.0 / bufImg.getHeight();  
        AffineTransformOp ato = new AffineTransformOp(AffineTransform.getScaleInstance(wr, hr), null);  
        Itemp = ato.filter(bufImg, null);  
        try {  
            ImageIO.write((BufferedImage) Itemp, destFile.getName().substring(destFile.getName().lastIndexOf(".")+1), destFile);  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        }  
    }  
	
}
