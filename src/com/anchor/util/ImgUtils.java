package com.anchor.util;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;



//ͼƬ������
public class ImgUtils {
	// ͼƬ�������ķ���·��
	private static String IMG_SERVICE_URL = "127.0.0.1:9099";
	// Ҳ���Դ������ļ��ж�ȡ
	// private String IMG_SERVICE_URL= Global.getConfig("server.imgserver.url");
	// ���ش洢����Ե�ַ��ͼƬ�������ķ��ʵ�ַ��
	private static String IMG_SAVE_PATH = "C:\\imgserver\\upload";
	// Ҳ���Դ������ļ��ж�ȡ
	// private String IMG_SAVE_PATH=Global.getConfig("service.saveImg.path");

	// ͼƬ���У�����ѹ������ת�����У�
	// srcImage:Ҫ���е�ͼƬ
	// ѹ������ͼƬ��С��imgW�?imgH��
	// Ҫ���еĿ�ʼ��꣨x,y��
	// Ҫ���еĳ��͸ߣ�width��height��
	// ��ת�Ƕ�
	public static BufferedImage shearImage(BufferedImage srcImage, Integer imgW, Integer imgH, Integer x, Integer y,
			Integer width, Integer height, Integer angel) {
		if (imgW == null) {
			imgW = srcImage.getWidth();
		}
		if (imgH == null) {
			imgH = srcImage.getHeight();
		}
		// ѹ��ͼƬ
		Image image = scaledImage(srcImage, imgW, imgH);
		// ��תͼƬ
		BufferedImage bufferedImage = rotateImage(image, angel);
		// ����ͼƬ
		Image image2 = cropImage(bufferedImage, x, y, width, height);
		BufferedImage shearBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2d = (Graphics2D) shearBufferedImage.getGraphics();
		graphics2d.drawImage(image2, 0, 0, null);
		graphics2d.dispose();
		// return bufferedImage;
		return shearBufferedImage;
	}

	// ����ͼƬ
	public static Image cropImage(BufferedImage bufferedImage, Integer x, Integer y, Integer width, Integer height) {
		// CropImageFilter:���ڲü�ͼ��� ImageFilter ��
		// x - Ҫ��ȡ�ľ��ζ����� x λ��
		// y - Ҫ��ȡ�ľ��ζ����� y λ��
		// w - Ҫ��ȡ�ľ��ο��
		// h - Ҫ��ȡ�ľ��θ߶�
		CropImageFilter cropImageFilter = new CropImageFilter(x, y, width, height);
		// FilteredImageSource:ʹ�����е�ͼ��͹�����������Ϊ����ʹ������Ϊԭͼ����¹��˰汾����ͼ����ݡ�
		// FilteredImageSource(ImageProducer orig, ImageFilter imgf)
		FilteredImageSource filteredImageSource = new FilteredImageSource(bufferedImage.getSource(), cropImageFilter);
		// createImage(ImageProducer producer):ʹ��ָ����ͼ�����������һ��ͼ��
		return Toolkit.getDefaultToolkit().createImage(filteredImageSource);
	}

	// ѹ��ͼƬ
	public static Image scaledImage(BufferedImage srcImage, Integer imgW, Integer imgH) {
		// getScaledInstance(int width, int height, int hints):������ͼ������Ű汾��
		// hints- ָʾ����ͼ������ȡ����㷨���͵ı�־
		return srcImage.getScaledInstance(imgW, imgH, Image.SCALE_SMOOTH);
	}

	// ��תͼƬ
	public static BufferedImage rotateImage(Image src, int angel) {
		int src_width = src.getWidth(null);
		int src_height = src.getHeight(null);
		Rectangle rect_des = calcRotatedSize(new Rectangle(new Dimension(src_width, src_height)), angel);
		BufferedImage bufferedImage = new BufferedImage(rect_des.width, rect_des.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = bufferedImage.createGraphics();
		// ƽ��
		g2.translate((rect_des.width - src_width) / 2, (rect_des.height - src_height) / 2);
		// ��ת�任
		g2.rotate(Math.toRadians(angel), src_width / 2, src_height / 2);
		g2.drawImage(src, null, null);
		g2.dispose();
		return bufferedImage;
	}

	// ������ת���ͼƬ��С
	private static Rectangle calcRotatedSize(Rectangle src, int angel) {
		// �����ת�ĽǶȴ���90�ȣ���Ҫ����ת��
		if (angel >= 90) {
			if (angel / 90 % 2 == 1) {
				int temp = src.height;
				src.height = src.width;
				src.width = temp;
			}
			angel = angel % 90;
		}
		double r = Math.sqrt(src.height * src.height + src.width * src.width) / 2;
		double len = 2 * Math.sin(Math.toRadians(angel) / 2) * r;
		double angel_alpha = (Math.PI - Math.toRadians(angel)) / 2;
		double angel_dalta_width = Math.atan((double) src.height / src.width);
		double angel_dalta_height = Math.atan((double) src.width / src.height);

		int len_dalta_width = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_width));
		int len_dalta_height = (int) (len * Math.cos(Math.PI - angel_alpha - angel_dalta_height));
		int des_width = src.width + len_dalta_width * 2;
		int des_height = src.height + len_dalta_height * 2;
		return new Rectangle(new Dimension(des_width, des_height));
	}

	

	// ��������ļ���
	private static String createFileName() {
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String timeStr = sdf.format(new Date());
		String newFileName = timeStr + "_" + rannum;
		return newFileName;
	}

	// ���������ļ�·��
	private static String createSavePath() {
		Random random = new Random();
		int rannum = (int) (random.nextDouble() * (99999 - 10000 + 1)) + 10000;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String datePath = sdf.format(new Date());
		return datePath;
	}



}