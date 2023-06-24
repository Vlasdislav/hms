package com.hms.view;

import com.hms.entity.Admin;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoginForm {
    private static JFrame frame;
    private JTextField nameField;
    private JPasswordField passField;
    private JButton enterButton;
    private JButton clearButton;
    private JPanel loginPanel;
    private final String pathFileAdmins = "C:\\Vlad\\Projects\\IntelliJIDEA\\hms\\src\\com\\hms\\db\\admins";
    public void fileReader(List<Admin> admins, String pathFile) {
        try {
            File file = new File(pathFile);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            do {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                String[] parseLine = line.split(", ");
                Admin admin = new Admin(
                        Integer.parseInt(parseLine[0]),
                        parseLine[1],
                        parseLine[2]
                );
                admins.add(admin);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fileWriter(List<Admin> admins, String pathFile) {
        try {
            FileWriter writer = new FileWriter(pathFile, false);
            for (Admin admin : admins) {
                String id = String.valueOf(admin.getId());
                String name = admin.getName();
                String pass = admin.getPass();
                writer.write(id + ", " + name + ", " + pass);
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public LoginForm() {
        List<Admin> admins = new ArrayList<>();
        fileReader(admins, pathFileAdmins);
        enterButton.addActionListener(e -> {
            String enterName = nameField.getText();
            String enterPass = String.valueOf(passField.getPassword());
            boolean nameAndPassTrue = false;
            for (Admin admin : admins) {
                if (enterName.equals(admin.getName()) && enterPass.equals(admin.getPass())) {
                    HomeForm homeForm = new HomeForm();
                    homeForm.createLayout();
                    frame.setVisible(false);
                    nameAndPassTrue = true;
                    break;
                }
            }
            if (!nameAndPassTrue) {
                JOptionPane.showMessageDialog(loginPanel, "Неверное имя или пароль!");
            }
        });
        clearButton.addActionListener(e -> {
            nameField.setText("");
            passField.setText("");
        });
    }

    public void createLayout() {
        frame = new JFrame("LoginForm");
        frame.setContentPane(new LoginForm().loginPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }
}
