package com.lx.yeb.utils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.util.Date;

/**
 * @ClassName VerificationCode
 * @Description 验证码生成工具
 * @Author lipan
 * @Date 2021/3/25 16:31
 * @Version 1.0
 */
public class VerificationCode{
    private int          width     = 100; //生成验证码图片的宽度
    private int          height    = 30; //生成验证码图片的高度
    private String[]     fontNames = {"宋体", "楷体", "隶书", "微软雅黑"};
    private Color        bgColor   = new Color(255, 255, 255); //定义验证码图片的背景颜色为白色
    private SecureRandom random    = new SecureRandom();
    private String       codes     = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private String       text      = ""; //记录随机字符串

    //生成一个随机颜色
    private Color randomColor(){
        int red   = random.nextInt(256);
        int green = random.nextInt(256);
        int blue  = random.nextInt(256);
        return new Color(red, green, blue);
    }

    //生成一个随机字体
    private Font randomFont(){
        String name  = fontNames[random.nextInt(fontNames.length)];
        int    style = random.nextInt(4);
        int    size  = random.nextInt(5) + 24;
        return new Font(name, style, size);
    }

    //获取一个随机字符
    private char randomChar(){
        return codes.charAt(random.nextInt(codes.length()));
    }

    //绘制干扰线
    private void drawLine(BufferedImage image){
        Graphics2D graphics2D = (Graphics2D) image.getGraphics();
        for(int i = 0; i < 5; i++){
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            graphics2D.setColor(randomColor());
            graphics2D.setStroke(new BasicStroke(1.5f));
            graphics2D.drawLine(x1, y1, x2, y2);
        }
    }

    //生成四位的验证码图片
    public BufferedImage createBufferedImage(){
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D    graphics2D    = (Graphics2D) bufferedImage.getGraphics();
        graphics2D.setColor(bgColor);
        graphics2D.fillRect(0, 0, width, height);

        StringBuilder stringBuffer = new StringBuilder();
        for(int i = 0; i < 4; i++){
            String s = randomChar() + "";
            stringBuffer.append(s);
            graphics2D.setColor(randomColor());
            graphics2D.setFont(randomFont());
            graphics2D.drawString(s, i * width * 1.0f / 4, height - 8);
        }
        text = stringBuffer.toString();
        drawLine(bufferedImage);
        return bufferedImage;
    }

    //向response中写验证码图片
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception{
        HttpSession   session = request.getSession();
        BufferedImage image   = createBufferedImage();
        response.setHeader("verifyCode", text);
        session.removeAttribute("verifyCode");
        session.setAttribute("verifyCode", text);
        session.setAttribute("codeTime", System.currentTimeMillis());
        // 将内存中的图片通过流动形式输出到客户端
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }
}
