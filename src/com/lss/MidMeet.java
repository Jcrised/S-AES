package com.lss;

import com.ymy.Conttoller.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MidMeet extends JFrame {


    private java.util.List<String> MimOriginal = new ArrayList<>();
    private List<String> MimSecret = new ArrayList<>();

    private MIMT attacker = new MIMT(); // 假设这是你的攻击者类
    JPanel Mmt;
    JLabel title,lab_txt,lab_sec,lab_res,lab_out,lab_show;
    JTextField JT_txt,JT_sec;
    JTextArea Jt_res,JT_out,JT_show;
    JButton back,out,sure,deci,show;
    public MidMeet() {
        Mmt = new JPanel();
        setContentPane(Mmt);
        setLayout(null);
        setTitle("S-AES 中间相遇");

        //UI标题
        title = new JLabel("中间相遇模式");
        title.setBounds(150, 75, 300, 40);
        title.setFont(new Font("楷体", Font.BOLD, 35));
        title.setForeground(Color.blue);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        Mmt.add(title);

        //输入明文
        lab_txt = new JLabel("输入明文：");
        lab_txt.setBounds(65, 160, 95, 30);
        lab_txt.setFont(new Font("宋体", Font.BOLD, 18));
        lab_txt.setForeground(Color.gray);
        lab_txt.setHorizontalAlignment(SwingConstants.LEFT);
        Mmt.add(lab_txt);

        //明文输入框
        JT_txt = new JTextField();
        JT_txt.setBounds(160, 160, 120, 30);
        JT_txt.setFont(new Font("宋体", Font.BOLD, 12));
        JT_txt.setHorizontalAlignment(SwingConstants.LEFT);
        Mmt.add(JT_txt);

        //输入密文
        lab_sec = new JLabel("输入密文：");
        lab_sec.setBounds(310, 160, 95, 30);
        lab_sec.setFont(new Font("宋体", Font.BOLD, 18));
        lab_sec.setForeground(Color.gray);
        lab_sec.setHorizontalAlignment(SwingConstants.LEFT);
        Mmt.add(lab_sec);

        //密文输入框
        JT_sec = new JTextField();
        JT_sec.setBounds(405, 160, 120, 30);
        JT_sec.setFont(new Font("宋体", Font.BOLD, 12));
        JT_sec.setHorizontalAlignment(SwingConstants.LEFT);
        Mmt.add(JT_sec);

        //明文、密钥存储
        lab_show = new JLabel("明密存储：");
        lab_show.setBounds(310, 210, 95, 30);
        lab_show.setFont(new Font("宋体", Font.BOLD, 18));
        lab_show.setForeground(Color.gray);
        lab_show.setHorizontalAlignment(SwingConstants.LEFT);
        Mmt.add(lab_show);

        //明文、密钥存储展示
        Jt_res=new JTextArea();
        JT_show=new JTextArea();
        JT_show.setEditable(false);
        JT_show.setBounds(310,250,215,180);
        JT_show.setFont(new Font("宋体",Font.BOLD,12));
        Mmt.add(JT_show);


        //点击存储
        show = new JButton("存储");
        show.setBounds(445,210,80,30);
        show.setFont(new Font("黑体",Font.BOLD,20));
        show.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String input = JT_txt.getText();
                String key = JT_sec.getText();
                String result = "";
                if (input.length() == 16 && key.length() == 16) {
                    JT_show.append("明文：" + input + "\n");
                    JT_show.append("密文：" + key + "\n\n");
                    MimOriginal.add(input);
                    MimSecret.add(key);
                    JT_txt.setText("");
                    JT_sec.setText("");
                } else {
                    JOptionPane.showMessageDialog(Mmt, "请输入16位明文和16位密文", "错误", JOptionPane.ERROR_MESSAGE);
                }

                Jt_res.setText(result);
            }
        });
        Mmt.add(show);

        //破解结果
        lab_out = new JLabel("破解：");
        lab_out.setBounds(65, 210, 95, 30);
        lab_out.setFont(new Font("宋体", Font.BOLD, 18));
        lab_out.setForeground(Color.gray);
        lab_out.setHorizontalAlignment(SwingConstants.LEFT);
        Mmt.add(lab_out);

        //解密结果展示
        JT_out = new JTextArea();
        JT_out.setBounds(65, 250, 215, 180);
        JT_out.setFont(new Font("宋体", Font.BOLD, 12));
        Mmt.add(JT_out);

        //破解按钮
        sure = new JButton("破解");
        sure.setBounds(200, 210, 80, 30);
        sure.setFont(new Font("黑体", Font.BOLD, 20));
        sure.setHorizontalAlignment(SwingConstants.CENTER);
        sure.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JT_out.setText("");
                if ( JT_show.getText().isEmpty() ) {
                    JT_out.setText("请至少输入一对明密文对");
                }else if (MimOriginal.size() == 1) {
                    String iniOri = MimOriginal.get(0);
                    String iniSec = MimSecret.get(0);
                    List<String> keyArray = attacker.SingleMsgAttack(iniOri, iniSec);
                    JT_out.append("密钥数量过于庞大，只展示以下十条\n");
                    for (int i = 0; i < Math.min(10, keyArray.size()); i++) {
                        JT_out.append(keyArray.get(i) + "\n");
                    }
                }else {
                    String iniOri = MimOriginal.get(0);
                    String iniSec = MimSecret.get(0);
                    List<String> keyArray = attacker.SingleMsgAttack(iniOri, iniSec);
                    keyArray = attacker.MultiMsgAttack(MimOriginal, MimSecret, keyArray);
                    if (keyArray.isEmpty()) {
                        JT_out.setText("该组明密文对不存在可行密钥");
                    } else {
                        JT_out.append("可能的密钥：\n");
                        for (String key : keyArray) {
                            JT_out.append(key + "\n");
                        }
                    }
                }
            }


        });
      Mmt.add(sure);

        //返回主页面
        back = new JButton("返回");
        back.setBounds(310, 450, 100, 30);
        back.setFont(new Font("黑体", Font.BOLD, 20));
        back.setHorizontalAlignment(SwingConstants.CENTER);
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new home();
                dispose();
            }
        });
        Mmt.add(back);

        //退出系统
        out = new JButton("退出");
        out.setBounds(180, 450, 100, 30);
        out.setFont(new Font("黑体", Font.BOLD, 20));
        out.setHorizontalAlignment(SwingConstants.CENTER);
        out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        Mmt.add(out);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350, 100, 600, 600);
        setVisible(true);





    }

}
