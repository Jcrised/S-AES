package com.lss;
import com.ymy.Conttoller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Doublecomcipher extends JFrame {

    JPanel Dbl,De;
    JLabel title,lab_cate,lab_txt,lab_sec,lab_res,lab_out;
    JComboBox JC_cate;
    JTextField JT_txt,JT_sec;
    JTextArea Jt_res,JT_out;
    JButton back,out,sure,deci;
    public Doublecomcipher() {
        Dbl = new JPanel();
        setContentPane(Dbl);
        setLayout(null);
        setTitle("S-AES 双重加密");

        //UI标题
        title = new JLabel("双重加密模式");
        title.setBounds(90, 60, 220, 40);
        title.setFont(new Font("楷体", Font.BOLD, 35));
        title.setForeground(Color.blue);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        Dbl.add(title);

        //输入明文
        lab_txt = new JLabel("明文/密文：");
        lab_txt.setBounds(80, 135, 150, 30);
        lab_txt.setFont(new Font("宋体", Font.BOLD, 18));
        lab_txt.setForeground(Color.gray);
        lab_txt.setHorizontalAlignment(SwingConstants.LEFT);
        Dbl.add(lab_txt);

        //明文输入框
        JT_txt = new JTextField();
        JT_txt.setBounds(190, 135, 130, 30);
        JT_txt.setFont(new Font("宋体", Font.BOLD, 12));
        JT_txt.setHorizontalAlignment(SwingConstants.LEFT);
        Dbl.add(JT_txt);



        //输入密钥
        lab_sec = new JLabel("输入密钥：");
        lab_sec.setBounds(80, 170, 100, 30);
        lab_sec.setFont(new Font("宋体", Font.BOLD, 18));
        lab_sec.setForeground(Color.gray);
        lab_sec.setHorizontalAlignment(SwingConstants.LEFT);
        Dbl.add(lab_sec);

        //密钥输入框
        JT_sec = new JTextField();
        JT_sec.setBounds(190, 170, 130, 30);
        JT_sec.setFont(new Font("宋体", Font.BOLD, 12));
        JT_sec.setHorizontalAlignment(SwingConstants.LEFT);
        Dbl.add(JT_sec);

        //加密类型
        lab_cate = new JLabel("加密类型：");
        lab_cate.setBounds(80, 205, 100, 30);
        lab_cate.setFont(new Font("宋体", Font.BOLD, 18));
        lab_cate.setForeground(Color.gray);
        lab_cate.setHorizontalAlignment(SwingConstants.LEFT);
        Dbl.add(lab_cate);

        //加密类型选择
        JC_cate = new JComboBox();
        JC_cate.addItem("ASCII码");
        JC_cate.addItem("Binary");
        JC_cate.setBounds(190, 205, 130, 30);
        JC_cate.setFont(new Font("楷体", Font.BOLD, 12));
        JC_cate.setBackground(Color.white);
        Dbl.add(JC_cate);

        //加密结果
        lab_res = new JLabel("加密结果：");
        lab_res.setBounds(80, 240, 100, 30);
        lab_res.setFont(new Font("宋体", Font.BOLD, 18));
        lab_res.setForeground(Color.gray);
        lab_res.setHorizontalAlignment(SwingConstants.LEFT);
        Dbl.add(lab_res);

        //加密结果展示
        Jt_res = new JTextArea();
        Jt_res.setBounds(190, 240, 130, 30);
        Jt_res.setFont(new Font("宋体", Font.BOLD, 12));
        Dbl.add(Jt_res);

        //解密结果
        lab_out = new JLabel("解密结果：");
        lab_out.setBounds(80, 275, 100, 30);
        lab_out.setFont(new Font("宋体", Font.BOLD, 18));
        lab_out.setForeground(Color.gray);
        lab_out.setHorizontalAlignment(SwingConstants.LEFT);
        Dbl.add(lab_out);

        //解密结果展示
        JT_out = new JTextArea();
        JT_out.setBounds(190, 275, 130, 30);
        JT_out.setFont(new Font("宋体", Font.BOLD, 12));
        Dbl.add(JT_out);

        //加密按钮
        sure = new JButton("加密");
        sure.setBounds(80, 330, 110, 30);
        sure.setFont(new Font("黑体", Font.BOLD, 20));
        sure.setHorizontalAlignment(SwingConstants.CENTER);
        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JT_txt.getText();
                String key = JT_sec.getText();
                String inputType = (String) JC_cate.getSelectedItem();
                String result = "";
                if (inputType.equals("ASCII码")) {
                    result = Encode.AscDencode(input, key);
                } else {
                    if (input.length() == 16 && key.length() == 32) {
                        result = Encode.AscDencode(input, key);
                    } else {
                        JOptionPane.showMessageDialog(Dbl, "请输入16位明文和32位密钥", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
                Jt_res.setText(result);
            }
        });
        Dbl.add(sure);

        //解密按钮
        deci= new JButton("解密");
        deci.setBounds(210, 330, 110, 30);
        deci.setFont(new Font("黑体", Font.BOLD, 20));
        deci.setHorizontalAlignment(SwingConstants.CENTER);
        deci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    String input = JT_txt.getText();
                    String key= JT_sec.getText();
                    String inputType=(String) JC_cate.getSelectedItem();
                    String result="";
                    if(inputType.equals("ASCII码")){
                        result=Decode.AscDdecode(input,key);
                    }else {
                        if (input.length() == 16 && key.length() == 32) {
                            result = Decode.Ddecode(input,key);
                        } else {
                            JOptionPane.showMessageDialog(Dbl, "请输入16位明文和32位密钥", "错误", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    Jt_res.setText(result);

            }
        });
        Dbl.add(deci);

        //返回主页面
        back = new JButton("返回");
        back.setBounds(210, 370, 110, 30);
        back.setFont(new Font("黑体", Font.BOLD, 20));
        back.setHorizontalAlignment(SwingConstants.CENTER);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home();
                dispose();
            }
        });
        Dbl.add(back);

        //退出系统
        out = new JButton("退出");
        out.setBounds(80, 370, 110, 30);
        out.setFont(new Font("黑体", Font.BOLD, 20));
        out.setHorizontalAlignment(SwingConstants.CENTER);
        out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        Dbl.add(out);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(450, 150, 400, 500);
        setVisible(true);
    }
}
