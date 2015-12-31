package com.ibs.zj.qrcode.swetake;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;

public class SwetakeHandler {

	/**
	 * 处理二维码
	 * 
	 * @param content
	 * @param response
	 */
	public static void handSwetake(String content) {
		try {
			Qrcode qrcode = new Qrcode();
			qrcode.setQrcodeErrorCorrect('M');
			qrcode.setQrcodeEncodeMode('B');
			qrcode.setQrcodeVersion(7);

			// 内容
			byte[] contentBytes = content.getBytes("gbk");
			
			BufferedImage bufImg = new BufferedImage(90, 90,BufferedImage.TYPE_INT_RGB);
			Graphics2D gs = bufImg.createGraphics();

			gs.setBackground(Color.WHITE);
			gs.clearRect(0, 0, 90, 90);
			gs.setColor(Color.BLACK);

			// 限制最大字节数为119
			if (contentBytes.length > 0 && contentBytes.length < 120) {
				boolean[][] s = qrcode.calQrcode(contentBytes);
				for (int i = 0; i < s.length; i++) {
					for (int j = 0; j < s.length; j++) {
						if (s[j][i]) {
							gs.fillRect(j * 2, i * 2, 2, 2);
						}
					}
				}
			}
			gs.dispose();
			bufImg.flush();
			File f = new File("E:\\a.jpg");
			if (!f.exists())
				f.createNewFile();
			// 生成本地图片
			ImageIO.write(bufImg, "jpg", f);  
			// 生成servlet图片
            // ImageIO.write(bufImg, "jpg", response.getOutputStream());  
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	public static void main(String[] args) {
		
		String content = "http://www.hao123.com/?tn=94855055_hao_pg";
		handSwetake(content);
		
	}
	
}
