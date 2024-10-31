package com.lss;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class home extends JFrame {
    JPanel Login;
    JLabel title;
    JButton out,main_comCipher,main_deCipher,main_Must_deCipher,db_comCipher,tri_comCipher,cbc_comCipher;
    public home(){
        Login = new JPanel();
        setContentPane(Login);
        setLayout(null);
        setTitle("S-AES/S-DES 加解密集成系统");

        title = new JLabel("S-AES/S-DES 加解密集成系统");
        title.setBounds(50,50,500,40);
        title.setFont(new Font("楷体",Font.BOLD,35));
        title.setForeground(Color.blue);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        Login.add(title);

        //加密
        main_comCipher= new JButton("加密模式");
        main_comCipher.setBounds(120,120,110,40);
        main_comCipher.setFont(new Font("黑体",Font.BOLD,18));
        main_comCipher.setHorizontalAlignment(SwingConstants.CENTER);
        main_comCipher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Comcipher();
                dispose();
            }
        });
        Login.add(main_comCipher);

        //解密
        main_deCipher= new JButton("解密模式");
        main_deCipher.setBounds(245,120,110,40);
        main_deCipher.setFont(new Font("黑体",Font.BOLD,18));
        main_deCipher.setHorizontalAlignment(SwingConstants.CENTER);
        main_deCipher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Decipher();
                dispose();
            }
        });
        Login.add(main_deCipher);

        //暴力破解
        main_Must_deCipher= new JButton("中间相遇");
        main_Must_deCipher.setBounds(370,120,110,40);
        main_Must_deCipher.setFont(new Font("黑体",Font.BOLD,18));
        main_Must_deCipher.setHorizontalAlignment(SwingConstants.CENTER);
        main_Must_deCipher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new MidMeet();
                dispose();
            }
        });
        Login.add(main_Must_deCipher);

        //双重加密
        db_comCipher=new JButton("双重加密");
        db_comCipher.setBounds(120,170,110,40);
        db_comCipher.setFont(new Font("黑体",Font.BOLD,18));
        db_comCipher.setHorizontalAlignment(SwingConstants.CENTER);
        db_comCipher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Doublecomcipher();
                dispose();
            }
        });
        Login.add(db_comCipher);

        //三重加密
        tri_comCipher=new JButton("三重加密");
        tri_comCipher.setBounds(245,170,110,40);
        tri_comCipher.setFont(new Font("黑体",Font.BOLD,18));
        tri_comCipher.setHorizontalAlignment(SwingConstants.CENTER);
        tri_comCipher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Triplecomcipher();
                dispose();
            }
        });
        Login.add(tri_comCipher);

        //cbc加密
        cbc_comCipher=new JButton("CBC加密");
        cbc_comCipher.setBounds(370,170,110,40);
        cbc_comCipher.setFont(new Font("黑体",Font.BOLD,18));
        cbc_comCipher.setHorizontalAlignment(SwingConstants.CENTER);
        cbc_comCipher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CBCcomcipher();
                dispose();
            }
        });
        Login.add(cbc_comCipher);

        //退出按钮
        out=new JButton("退出");
        out.setBounds(200,220,200,40);
        out.setFont(new Font("黑体",Font.BOLD,20));
        out.setHorizontalAlignment(SwingConstants.CENTER);
        out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        Login.add(out);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(350,200,600,400);
        setVisible(true);
    }

    public static void main(String[] args) {
        new home();
    }
}