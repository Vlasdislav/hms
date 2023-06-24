package com.hms.view;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HomeForm {
    private static JFrame frame;
    private JPanel homePanel;
    private JLabel doctorLink;
    private JLabel patientLink;

    public HomeForm() {
        doctorLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                DoctorForm doctorForm = new DoctorForm();
                doctorForm.createLayout();
                frame.setVisible(false);
            }
        });
        patientLink.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                PatientForm patientForm = new PatientForm();
                patientForm.createLayout();
                frame.setVisible(false);
            }
        });
    }

    public void createLayout() {
        frame = new JFrame("HomeForm");
        frame.setContentPane(new HomeForm().homePanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }
}
