package com.app.dmm.core.utils.QR;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.CharacterSetECI;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;


public class ZxingUtils {

    //图片格式
    private static final String FORMAT = "JPG";
    //二维码宽度,单位：像素pixels
    private static final int QRCODE_WIDTH = 300;
    //二维码高度,单位：像素pixels
    private static final int QRCODE_HEIGHT = 300;
    //LOGO宽度,单位：像素pixels
    private static final int LOGO_WIDTH = 100;
    //LOGO高度,单位：像素pixels
    private static final int LOGO_HEIGHT = 100;

    //自定义宽高
    /**
     * // 二维码生成
     * @param contents 说明
     * @param width 宽
     * @param height 高
     * @param margin 边框
     * @return BufferedImage
     * @throws Exception
     */
    public static BufferedImage createQRImage(String contents, int width, int height, int margin) throws Exception {
        BufferedImage qRImage = null;

        if (contents == null || "".equals(contents)) {
            throw new Exception("content说明不能为空");
        }

        // 二维码参数设置
        HashMap<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, CharacterSetECI.UTF8); // 编码设置
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H); // 安全等级，最高h
        hints.put(EncodeHintType.MARGIN, margin); // 设置margin=0-10

        // 二维码图片的生成
        BarcodeFormat format = BarcodeFormat.QR_CODE;

        // 创建矩阵容器

        BitMatrix matrix = null;

        try {
            matrix = new MultiFormatWriter().encode(contents, format, width, height, hints);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        // 设置矩阵转为图片的参数
        MatrixToImageConfig toImageConfig = new MatrixToImageConfig(Color.black.getRGB(), Color.white.getRGB());

        // 矩阵转换图像
        qRImage = MatrixToImageWriter.toBufferedImage(matrix, toImageConfig);

        return qRImage;
    }

    // 二维码添加logo,这样用工具类来写，之后使用我们可以采用加或者不加logo
    /**
     * @param qrImage
     * @param width
     * @param height
     * @param logoPath
     * @param logoSize
     * @return BufferedImage
     * @throws Exception
     */

    public static BufferedImage addQRImagelogo(BufferedImage qrImage, int width, int height, String logoPath, int logoSize) throws Exception {
        BufferedImage qRImageWithLogo = null;
        File logoFile = new File(logoPath);
        if (!logoFile.exists() || !logoFile.isFile()) {
            throw new Exception("指定的logo图片不存在");
        }

        // 处理logo
        BufferedImage image = ImageIO.read(logoFile);
        // 设置logo的高和宽
        int logoHeight = qrImage.getHeight()/logoSize;
        int logoWidth = qrImage.getWidth()/logoSize;
        // 设置放置位置
        int x = (qrImage.getHeight() - logoHeight) / 2;
        int y = (qrImage.getWidth() - logoWidth) / 2;

        // 新建画板
        qRImageWithLogo = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 新建画笔
        Graphics2D g = (Graphics2D) qRImageWithLogo.getGraphics();
        // 将二维码绘制到画板
        g.drawImage(qrImage, 0, 0, null);
        // 设置头透明度，可设置范围0.0f-1.0f
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
        // 绘制logo
        g.drawImage(image, x, y, logoWidth, logoHeight, null);

        return qRImageWithLogo;
    }

    //采用默认宽高
    /**
     * 生成二维码图片
     * @param content 二维码内容
     * @param logoPath 图片地址
     * @param needCompress 是否压缩
     * @return
     * @throws Exception
     */
    private static BufferedImage createImage(String content, String logoPath, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hints.put(EncodeHintType.CHARACTER_SET,"utf-8");
        hints.put(EncodeHintType.MARGIN, 1);
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_WIDTH, QRCODE_HEIGHT,
                hints);
        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (logoPath == null || "".equals(logoPath)) {
            return image;
        }
        // 插入图片
        ZxingUtils.insertImage(image, logoPath, needCompress);
        return image;
    }

    /**
     * 插入LOGO
     * @param source 二维码图片
     * @param logoPath LOGO图片地址
     * @param needCompress 是否压缩
     * @throws Exception
     */
    private static void insertImage(BufferedImage source, String logoPath, boolean needCompress) throws Exception {
        File file = new File(logoPath);
        if (!file.exists()) {
            throw new Exception("logo file not found.");
        }
        Image src = ImageIO.read(new File(logoPath));
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        if (needCompress) { // 压缩LOGO
            if (width > LOGO_WIDTH) {
                width = LOGO_WIDTH;
            }
            if (height > LOGO_HEIGHT) {
                height = LOGO_HEIGHT;
            }
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics g = tag.getGraphics();
            g.drawImage(image, 0, 0, null); // 绘制缩小后的图
            g.dispose();
            src = image;
        }

        // 插入LOGO
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_WIDTH - width) / 2;
        int y = (QRCODE_HEIGHT - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        graph.setStroke(new BasicStroke(3f));
        graph.draw(shape);
        graph.dispose();
    }

    /**
     * 生成二维码(内嵌LOGO)
     * 调用者指定二维码文件名
     * @param content 二维码的内容
     * @param logoPath 中间图片地址
     * @param destPath 存储路径
     * @param fileName 文件名称
     * @param needCompress 是否压缩
     * @return
     * @throws Exception
     */
    public static String encode(String content, String logoPath, String destPath, String fileName, boolean needCompress) throws Exception {
        BufferedImage image = ZxingUtils.createImage(content, logoPath, needCompress);
        mkdirs(destPath);
        //文件名称通过传递
        fileName = fileName.substring(0, fileName.indexOf(".")>0?fileName.indexOf("."):fileName.length())
                + "." + FORMAT.toLowerCase();
        ImageIO.write(image, FORMAT, new File(destPath + "/" + fileName));
        return fileName;
    }

    /**
     * 创建文件夹， mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
     * @param destPath
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        if (!file.exists() && !file.isDirectory()) {
            file.mkdirs();
        }
    }

    /**
     * 解析二维码
     * @param path 二维码图片路径
     * @return String 二维码内容
     * @throws Exception
     */
    public static String decode(String path) throws Exception {
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable<DecodeHintType, Object> hints = new Hashtable<DecodeHintType, Object>();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

    /**
     * 测试
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //String text = "http://localhost:8001/login/aaa?bbb=ccc";
        //String text = "https://gitee.com/zoomLee/picture-store/blob/master/jk/lyt.jpg";
        //String text = "https://gitee.com/zoomLee/picture-store/blob/master/jk/lyt.jpg";
        String text = "http://dmmgee.nat300.top/index.html";

        //String logoPath = "C:\\Users\\windy\\Desktop\\005XSXmNly1glvjgaasj2j30go0gjdho.jpg";
        String logoPath = "C:\\Users\\windy\\Desktop\\df.jpg";

        //不含Logo
        ZxingUtils.encode(text, null, "C:\\Users\\windy\\Desktop\\", "qrcode", true);
        //含Logo，指定二维码图片名
        ZxingUtils.encode(text, logoPath, "C:\\Users\\windy\\Desktop\\", "qrcode1", true);
        System.out.println(ZxingUtils.decode("C:\\Users\\windy\\Desktop\\qrcode1.jpg"));
    }
}

