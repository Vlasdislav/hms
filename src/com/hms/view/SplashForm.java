package com.hms.view;

import javax.swing.*;

public class SplashForm {
    private static JFrame frame;
    private JProgressBar progressLoading;
    private JPanel splashPanel;
    private JLabel progressStatus;

    public SplashForm() {
        frame = new JFrame("SplashForm");
        frame.setContentPane(splashPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        SplashForm splashForm = new SplashForm();
        for (int i = 0; i <= 100; ++i) {
            try {
                Thread.sleep(20);
                splashForm.progressStatus.setText(i + "%");
                splashForm.progressLoading.setValue(i);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        LoginForm loginForm = new LoginForm();
        loginForm.createLayout();
        frame.setVisible(false);
    }
}
