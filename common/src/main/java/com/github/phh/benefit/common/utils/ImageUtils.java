package com.github.phh.benefit.common.utils;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Position;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 图片处理辅助类
 */
public final class ImageUtils {

    /**
     * 图片尺寸不变，压缩图片文件大小
     *
     * @param source
     * @param output
     * @param quality 范围：0.0~1.0，1为最高质量
     */
    public static void imgQuality(String source, String output, float quality) throws IOException {
        Thumbnails.of(source)
                .scale(1f)
                .outputQuality(quality)
                .toFile(output);
    }

    /**
     * 图片尺寸不变，压缩图片文件大小
     *
     * @param source
     * @param output
     * @param quality 范围：0.0~1.0，1为最高质量
     * @param scale   缩放范围 0.1~1.0
     */
    public static void imgQuality(String source, String output, float scale, float quality) throws IOException {
        Thumbnails.of(source)
                .scale(scale)
                .outputQuality(quality)
                .toFile(output);
    }

    /**
     * 指定大小进行缩放 若图片横比width小，高比height小，不变 若图片横比width小，高比height大，高缩小到height，图片比例不变
     * 若图片横比width大，高比height小，横缩小到width，图片比例不变
     * 若图片横比width大，高比height大，图片按比例缩小，横为width或高为height
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public static void imgThumb(String source, String output, int width, int height) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .toFile(output);
    }

    /**
     * 指定大小进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     */
    public static void imgThumb(File source, String output, int width, int height) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .toFile(output);
    }

    /**
     * 按照比例进行缩放
     *
     * @param source 输入源
     * @param output 输出源
     */
    public static void imgScale(String source, String output, double scale) throws IOException {
        Thumbnails.of(source)
                .scale(scale)
                .toFile(output);
    }

    public static void imgScale(File source, String output, double scale) throws IOException {
        Thumbnails.of(source)
                .scale(scale)
                .toFile(output);
    }

    /**
     * 不按照比例，指定大小进行缩放
     *
     * @param source          输入源
     * @param output          输出源
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void imgNoScale(String source, String output, int width, int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .keepAspectRatio(keepAspectRatio)
                .toFile(output);
    }

    public static void imgNoScale(File source, String output, int width, int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .keepAspectRatio(keepAspectRatio)
                .toFile(output);
    }

    /**
     * 旋转 ,正数：顺时针 负数：逆时针
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param rotate 角度
     */
    public static void imgRotate(String source, String output, int width, int height, double rotate) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .rotate(rotate)
                .toFile(output);
    }

    public static void imgRotate(File source, String output, int width, int height, double rotate) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .rotate(rotate)
                .toFile(output);
    }

    /**
     * 原图平铺加满水印
     *
     * @param source       原图路径
     * @param output       转换后图片存放路径
     * @param watermark    水印图片路径
     * @param transparency 透明度
     * @param quality      图片质量
     */
    public static final void imgWatermark(String source, String output, String watermark,
                                          float transparency, float quality) {
        imgWatermarkScaleWidth(source, 0, output, watermark, transparency, quality);
    }

    /**
     * 按高度缩放并平铺加满水印
     *
     * @param source       原图路径
     * @param targetHeight 目标高度
     * @param output       转换后图片存放路径
     * @param watermark    水印图片路径
     * @param transparency 透明度
     * @param quality      图片质量
     */
    public static final void imgWatermarkScaleHeight(String source, int targetHeight, String output, String watermark,
                                                     float transparency, float quality) {
        imgWatermarkScale(source, true, targetHeight, output, watermark, transparency, quality);
    }

    /**
     * 按宽度缩放并平铺加满水印
     *
     * @param source       原图路径
     * @param targetWidth  目标宽度
     * @param output       转换后图片存放路径
     * @param watermark    水印图片路径
     * @param transparency 透明度
     * @param quality      图片质量
     */
    public static final void imgWatermarkScaleWidth(String source, int targetWidth, String output, String watermark,
                                                    float transparency, float quality) {
        imgWatermarkScale(source, false, targetWidth, output, watermark, transparency, quality);
    }

    /**
     * @param source       原图路径
     * @param scaleFlag    宽高缩放标记，true 按高度缩放，false按宽度缩放
     * @param targetSize   宽高目标大小
     * @param output       转换后图片存放路径
     * @param watermark    水印图片路径
     * @param transparency 透明度
     * @param quality      图片质量
     */
    private static final void imgWatermarkScale(String source, boolean scaleFlag, int targetSize, String output, String watermark,
                                                float transparency, float quality) {
        int[] wh1 = getWidthHeight(source);
        int width1 = wh1[0];
        int height1 = wh1[1];
        double scale = 1.00d;
        if (targetSize > 0) {
            // scaleFlag缩放标记，true 按高度缩放，false按宽度缩放
            if (scaleFlag) {
                // 计算高度的缩放比例
                scale = targetSize * 1.00 / height1;
                width1 = (int) (width1 * scale);
                height1 = targetSize;
            } else {
                // 计算宽度的缩放比例
                scale = targetSize * 1.00 / width1;
                width1 = targetSize;
                height1 = (int) (height1 * scale);
            }
        }
        int[] wh2 = getWidthHeight(watermark);
        int width2 = wh2[0];
        int height2 = wh2[1];
        int xrepeat = width1 / width2;
        int yrepeat = height1 / height2;
        try {
            Thumbnails.Builder<File> builder = Thumbnails.of(source).scale(scale);
            for (int y = 0; y < yrepeat; y++) {
                for (int x = 0; x < xrepeat; x++) {
                    final int finalX = x * width2;
                    final int finalY = y * height2;
                    builder = builder
                            .watermark(new Position() {
                                @Override
                                public Point calculate(int enclosingWidth, int enclosingHeight,
                                                       int width, int height, int insetLeft,
                                                       int insetRight, int insetTop, int insetBottom) {
                                    return new Point(finalX, finalY);
                                }
                            }, ImageIO.read(new File(watermark)), transparency);
                }
            }
            File pf = new File(output).getParentFile();
            if (!pf.exists()) {
                pf.mkdirs();
            }
            builder.outputQuality(quality).toFile(output);
        } catch (IOException e) {
            throw new RuntimeException("image[" + source + "] watermark error!", e);
        }
    }

    /**
     * 原图加单个水印
     *
     * @param source
     * @param output
     * @param position
     * @param watermark
     * @param transparency
     * @param quality
     */
    public static void imgWatermark(String source, String output, Position position,
                                    String watermark, float transparency, float quality) throws IOException {
        Thumbnails.of(source)
                .watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(quality)
                .scale(1).toFile(output);
    }

    /**
     * 水印
     *
     * @param source       输入源
     * @param output       输入源
     * @param width        宽
     * @param height       高
     * @param position     水印位置 Positions.BOTTOM_RIGHT o.5f
     * @param watermark    水印图片地址
     * @param transparency 透明度 0.5f
     * @param quality      图片质量 0.8f
     */
    public static void imgWatermark(String source, String output, int width, int height, Position position,
                                    String watermark, float transparency, float quality) throws IOException {
        Thumbnails.of(source).size(width, height)
                .watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(quality)
                .toFile(output);
    }

    public static void imgWatermark(File source, String output, int width, int height, Position position,
                                    String watermark, float transparency, float quality) throws IOException {
        Thumbnails.of(source).size(width, height)
                .watermark(position, ImageIO.read(new File(watermark)), transparency).outputQuality(quality)
                .toFile(output);
    }

    /**
     * 裁剪图片
     *
     * @param source          输入源
     * @param output          输出源
     * @param position        裁剪位置
     * @param x               裁剪区域x
     * @param y               裁剪区域y
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void imgSourceRegion(String source, String output, Position position, int x, int y, int width,
                                       int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source)
                .sourceRegion(position, x, y)
                .size(width, height)
                .keepAspectRatio(keepAspectRatio)
                .toFile(output);
    }

    public static void imgSourceRegion(File source, String output, Position position, int x, int y, int width,
                                       int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source)
                .sourceRegion(position, x, y)
                .size(width, height)
                .keepAspectRatio(keepAspectRatio)
                .toFile(output);
    }

    /**
     * 按坐标裁剪
     *
     * @param source          输入源
     * @param output          输出源
     * @param x               起始x坐标
     * @param y               起始y坐标
     * @param x1              结束x坐标
     * @param y1              结束y坐标
     * @param width           宽
     * @param height          高
     * @param keepAspectRatio 默认是按照比例缩放的,值为false 时不按比例缩放
     */
    public static void imgSourceRegion(String source, String output, int x, int y, int x1, int y1, int width,
                                       int height, boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source)
                .sourceRegion(x, y, x1, y1)
                .size(width, height)
                .keepAspectRatio(keepAspectRatio)
                .toFile(output);
    }

    public static void imgSourceRegion(File source, String output, int x, int y, int x1, int y1, int width, int height,
                                       boolean keepAspectRatio) throws IOException {
        Thumbnails.of(source)
                .sourceRegion(x, y, x1, y1)
                .size(width, height)
                .keepAspectRatio(keepAspectRatio)
                .toFile(output);
    }

    /**
     * 转化图像格式
     *
     * @param source 输入源
     * @param output 输出源
     * @param width  宽
     * @param height 高
     * @param format 图片类型，gif、png、jpg
     */
    public static void imgFormat(String source, String output, int width, int height, String format) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .outputFormat(format)
                .toFile(output);
    }

    public static void imgFormat(File source, String output, int width, int height, String format) throws IOException {
        Thumbnails.of(source)
                .size(width, height)
                .outputFormat(format)
                .toFile(output);
    }


    /**
     * * 转换图片大小，不变形
     *
     * @param img    图片文件
     * @param width  图片宽
     * @param height 图片高
     */
    public static final void changeImge(File img, int width, int height) throws IOException {
        Thumbnails.of(img)
                .size(width, height)
                .keepAspectRatio(false)
                .toFile(img);
    }

    /**
     * 根据比例缩放图片
     *
     * @param orgImg     源图片路径
     * @param scale      比例
     * @param targetFile 缩放后的图片存放路径
     * @throws IOException
     */
    public static final void scale(BufferedImage orgImg, double scale, String targetFile) throws IOException {
        new File(targetFile).getParentFile().mkdirs();
        Thumbnails.of(orgImg).scale(scale).toFile(targetFile);
    }

    public static final void scale(String orgImgFile, double scale, String targetFile) throws IOException {
        new File(targetFile).getParentFile().mkdirs();
        Thumbnails.of(orgImgFile).scale(scale).toFile(targetFile);
    }

    /**
     * 根据宽度或高度同比缩放，采用宽度或高度的缩放比率最小值做缩放
     *
     * @param orgImgFile
     * @param targetFile
     * @param targetWidth
     * @param targetHeight
     * @throws IOException
     */
    public static final void scale(String orgImgFile, String targetFile, int targetWidth, int targetHeight) {
        if (0 > targetWidth || 0 > targetHeight) {
            String msg = String.format("argument[targetWidth|targetHeight] cannot be less than 0 ! " +
                            "targetWidth:%s,targetHeight:%s",
                    targetWidth, targetHeight);
            throw new IllegalArgumentException(msg);
        }
        if (0 == targetWidth && 0 == targetHeight) {
            String msg = String.format("argument[targetWidth|targetHeight] cannot be 0 at the same time! " +
                            "targetWidth:%s,targetHeight:%s",
                    targetWidth, targetHeight);
            throw new IllegalArgumentException(msg);
        }
        int[] wh1 = getWidthHeight(orgImgFile);
        try {
            double scale = 1.0d;
            int orgWidth = wh1[0];
            int orgHeight = wh1[1];
            if (targetWidth > 0 && targetHeight == 0) {
                scale = targetWidth * 1.00 / orgWidth;
            } else if (targetWidth == 0 && targetHeight > 0) {
                scale = targetHeight * 1.00 / orgHeight;
            } else {
                double scaleWidth = targetWidth * 1.00 / orgWidth;
                double scaleHeight = targetHeight * 1.00 / orgHeight;
                // 计算宽度的缩放比例
                scale = scaleWidth;
                if (scaleHeight < scaleWidth) {
                    scale = scaleHeight;
                }
            }
            // 裁剪
            scale(orgImgFile, scale, targetFile);
        } catch (IOException e) {
            throw new RuntimeException("image[" + orgImgFile + "] scale error!", e);
        }
    }

    /**
     * 根据宽度或高度同比缩放，采用宽度或高度的缩放比率最小值做缩放
     *
     * @param orgImg
     * @param targetFile
     * @param targetWidth
     * @param targetHeight
     * @return
     * @throws IOException
     */
    public static final double scale(BufferedImage orgImg, String targetFile, int targetWidth, int targetHeight)
            throws IOException {
        if (0 > targetWidth || 0 > targetHeight) {
            String msg = String.format("argument[targetWidth|targetHeight] cannot be less than 0 ! " +
                            "targetWidth:%s,targetHeight:%s",
                    targetWidth, targetHeight);
            throw new IllegalArgumentException(msg);
        }
        if (0 == targetWidth && 0 == targetHeight) {
            String msg = String.format("argument[targetWidth|targetHeight] cannot be 0 at the same time! " +
                            "targetWidth:%s,targetHeight:%s",
                    targetWidth, targetHeight);
            throw new IllegalArgumentException(msg);
        }
        double scale = 1.0d;
        int orgWidth = orgImg.getWidth();
        int orgHeight = orgImg.getHeight();
        if (targetWidth > 0 && targetHeight == 0) {
            scale = targetWidth * 1.00 / orgWidth;
        } else if (targetWidth == 0 && targetHeight > 0) {
            scale = targetHeight * 1.00 / orgHeight;
        } else {
            double scaleWidth = targetWidth * 1.00 / orgWidth;
            double scaleHeight = targetHeight * 1.00 / orgHeight;
            // 计算宽度的缩放比例
            scale = scaleWidth;
            if (scaleHeight < scaleWidth) {
                scale = scaleHeight;
            }
        }
        // 裁剪
        scale(orgImg, scale, targetFile);
        return scale;
    }

    /**
     * 根据宽度同比缩放
     *
     * @param orgImg      源图片
     * @param targetWidth 缩放后的宽度
     * @param targetFile  缩放后的图片存放路径
     * @throws IOException
     */
    public static final double scaleWidth(BufferedImage orgImg, int targetWidth, String targetFile) throws IOException {
        int orgWidth = orgImg.getWidth();
        // 计算宽度的缩放比例
        double scale = targetWidth * 1.00 / orgWidth;
        // 裁剪
        scale(orgImg, scale, targetFile);
        return scale;
    }

    /**
     * 根据宽度同比缩放
     *
     * @param orgImgFile
     * @param targetWidth
     * @param targetFile
     * @throws IOException
     */
    public static final void scaleWidth(String orgImgFile, int targetWidth, String targetFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
        scaleWidth(bufferedImage, targetWidth, targetFile);
    }

    /**
     * 根据高度同比缩放
     *
     * @param targetHeight //缩放后的高度
     * @param targetFile   //缩放后的图片存放地址
     * @throws IOException
     */
    public static final double scaleHeight(BufferedImage orgImg, int targetHeight, String targetFile)
            throws IOException {
        int orgHeight = orgImg.getHeight();
        double scale = targetHeight * 1.00 / orgHeight;
        scale(orgImg, scale, targetFile);
        return scale;
    }

    /**
     * 根据高度同比缩放
     *
     * @param orgImgFile   原文件地址
     * @param targetHeight 缩放后的高度
     * @param targetFile   缩放后的图片存放地址
     * @throws IOException
     */
    public static final void scaleHeight(String orgImgFile, int targetHeight, String targetFile) throws IOException {
        BufferedImage bufferedImage = ImageIO.read(new File(orgImgFile));
        scaleHeight(bufferedImage, targetHeight, targetFile);
    }

    // 原始比例缩放
    public static final void scaleWidth(File file, Integer width) throws IOException {
        String fileName = file.getName();
        String filePath = file.getAbsolutePath();
        String postFix = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
        // 缩放
        BufferedImage bufferedImg = ImageIO.read(file);
        String targetFile = filePath + "_s" + postFix;
        scaleWidth(bufferedImg, width, targetFile);
        String targetFile2 = filePath + "@" + width;
        new File(targetFile).renameTo(new File(targetFile2));
    }

    /**
     * 获取图片的宽、高数组，array[0] 为宽， array[1] 为高，
     *
     * @param source
     * @return
     */
    public static final int[] getWidthHeight(String source) {
        File picture = new File(source);
        if (!picture.exists()) {
            throw new RuntimeException("file[" + source + "] not found!");
        }
        BufferedImage sourceImg = null;
        int[] widthHeight = new int[2];
        try {
            sourceImg = ImageIO.read(new FileInputStream(picture));
            widthHeight[0] = sourceImg.getWidth();
            widthHeight[1] = sourceImg.getHeight();
        } catch (IOException e) {
            throw new RuntimeException("file[" + source + "] read error!");
        }
        return widthHeight;
    }

    public static final int[] getScaleWidthHeight(String source, int targetWidth, int targetHeight) {
        if (0 > targetWidth || 0 > targetHeight) {
            String msg = String.format("argument[targetWidth|targetHeight] cannot be less than 0 ! " +
                            "targetWidth:%s,targetHeight:%s",
                    targetWidth, targetHeight);
            throw new IllegalArgumentException(msg);
        }
        if (0 == targetWidth && 0 == targetHeight) {
            String msg = String.format("argument[targetWidth|targetHeight] cannot be 0 at the same time! " +
                            "targetWidth:%s,targetHeight:%s",
                    targetWidth, targetHeight);
            throw new IllegalArgumentException(msg);
        }
        int[] wh1 = getWidthHeight(source);
        int[] widthHeight = new int[2];

        double scale = 1.0d;
        int orgWidth = wh1[0];
        int orgHeight = wh1[1];
        if (targetWidth > 0 && targetHeight == 0) {
            scale = targetWidth * 1.00 / orgWidth;
        } else if (targetWidth == 0 && targetHeight > 0) {
            scale = targetHeight * 1.00 / orgHeight;
        } else {
            double scaleWidth = targetWidth * 1.00 / orgWidth;
            double scaleHeight = targetHeight * 1.00 / orgHeight;
            // 计算宽度的缩放比例
            scale = scaleWidth;
            if (scaleHeight < scaleWidth) {
                scale = scaleHeight;
            }
        }
        widthHeight[0] = (int) (orgWidth * scale);
        widthHeight[1] = (int) (orgHeight * scale);
        return widthHeight;
    }


    /**
     * 缩小Image，此方法返回源图像按给定宽度、高度限制下缩放后的图像
     *
     * @param inputImage
     * @param newWidth   压缩后宽度
     * @param newHeight  压缩后高度
     * @throws IOException return
     */
    public static BufferedImage scaleByPercentage(BufferedImage inputImage, int newWidth, int newHeight) throws Exception {
        // 获取原始图像透明度类型
        int type = inputImage.getColorModel().getTransparency();
        int orignWidth = inputImage.getWidth();
        int orignHeight = inputImage.getHeight();

        // 开启抗锯齿
        RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 使用高质量压缩
        renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        renderingHints.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_DEFAULT);

        BufferedImage image = new BufferedImage(newWidth, newHeight, type);
        Graphics2D g2 = image.createGraphics();
        g2.setRenderingHints(renderingHints);
        g2.drawImage(inputImage, 0, 0, newWidth, newHeight, 0, 0, orignWidth, orignHeight, null);
        g2.dispose();
        return image;
    }

    /**
     * 传入的图像必须是正方形的才会圆形,如果是长方形的比例则会变成椭圆的
     *
     * @param image 用户头像
     * @return
     * @throws IOException
     */
    public static BufferedImage convertCircular(BufferedImage image) throws IOException {
        // 透明底的图片
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        Ellipse2D.Double shape = new Ellipse2D.Double(0, 0, image.getWidth(), image.getHeight());
        Graphics2D g2 = newImage.createGraphics();
        g2.setClip(shape);
        g2.drawImage(image, 0, 0, null);
        g2.dispose();
        return newImage;
    }

    /**
     * <p>
     * 画圆：
     * 通过画圆角距形实现
     * </p>
     *
     * @param file         需要画成圆形的源图片位置
     * @param cornerRadius 圆角半径
     * @return BufferedImage
     * @throws Exception if error
     * @author phh
     * @date 2018/9/25
     * @version V1.0
     */
    public static BufferedImage drawCircle(String file, int cornerRadius) throws Exception {
        //水印图片，要圆角处理
        BufferedImage bi = Thumbnails.of(new File(file))
                .scale(1.0f)
                .asBufferedImage();
        return drawCircle(bi, cornerRadius);
    }

    /**
     * <p>
     * 画圆：
     * 通过画圆角距形实现
     * </p>
     *
     * @param bi           需要画成圆形的源图片
     * @param cornerRadius 圆角半径
     * @return BufferedImage
     * @throws Exception if error
     * @author phh
     * @date 2018/9/25
     * @version V1.0
     */
    public static BufferedImage drawCircle(BufferedImage bi, int cornerRadius) throws Exception {
        int w = bi.getWidth() > bi.getHeight() ? bi.getHeight() : bi.getWidth();
        int h = bi.getHeight();
        //如果图片太大，先缩放
        if (w > 1.3 * cornerRadius) {
            bi = Thumbnails.of(bi)
                    .scale((cornerRadius * 1.0) / (w * 1.0))
                    .asBufferedImage();
            w = bi.getWidth() > bi.getHeight() ? bi.getHeight() : bi.getWidth();
            h = w;
        } else if (w < cornerRadius) {
            //放大
            bi = Thumbnails.of(bi)
                    .scale(2.5)
                    .asBufferedImage();
            w = bi.getWidth() > bi.getHeight() ? bi.getHeight() : bi.getWidth();
            h = w;
        }
        BufferedImage targetImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = targetImage.createGraphics();
        g2.setComposite(AlphaComposite.Src);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.WHITE);
        g2.fill(new RoundRectangle2D.Float(0, 0, w, h, cornerRadius, cornerRadius));
        // using the white shape from above as alpha source
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(bi, 0, 0, null);
        g2.dispose();
        return targetImage;
    }
}
