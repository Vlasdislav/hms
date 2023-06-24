package com.hms.view;

import com.hms.entity.Doctor;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DoctorForm {
    private static JFrame frame;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private JTextField expField;
    private JTextField telField;
    private JTextField idField;
    private JTextField passField;
    private JScrollPane scrollTableDoctors;
    private JTable tableDoctors;
    private JButton homeButton;
    private JPanel doctorPanel;
    private JTextField nameField;
    private JTextField specialityField;
    private Doctor selectedDoctor;
    private int selectedIndexDoctor;
    private final String pathFileDoctors = "C:\\Vlad\\Projects\\IntelliJIDEA\\hms\\src\\com\\hms\\db\\doctors";
    public void fileReader(List<Doctor> doctors, String pathFile) {
        try {
            File file = new File(pathFile);
            FileReader fr = new FileReader(file);
            BufferedReader reader = new BufferedReader(fr);
            do {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                String[] parseLine = line.split("# ");
                Doctor doctor = new Doctor(
                        Integer.parseInt(parseLine[0]),
                        parseLine[1],
                        parseLine[2],
                        parseLine[3],
                        Integer.parseInt(parseLine[4]),
                        parseLine[5]
                );
                doctors.add(doctor);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fileWriter(List<Doctor> doctors, String pathFile) {
        try {
            FileWriter writer = new FileWriter(pathFile, false);
            for (Doctor doctor : doctors) {
                String id = String.valueOf(doctor.getId());
                String name = doctor.getName();
                String pass = doctor.getPass();
                String speciality = doctor.getSpeciality();
                String exp = String.valueOf(doctor.getExp());
                String tel = doctor.getTel();
                writer.write(id + "# " + name + "# " + pass + "# " + speciality + "# " + exp + "# " + tel + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public DoctorForm() {
        List<Doctor> doctors = new ArrayList<>();
        fileReader(doctors, pathFileDoctors);
        DoctorTableModel doctorTableModel = new DoctorTableModel(doctors);
        tableDoctors.setPreferredScrollableViewportSize(new Dimension(450,100));
        tableDoctors.setModel(doctorTableModel);
        tableDoctors.setAutoCreateRowSorter(true);
        idField.setEnabled(false);
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        clearButton.setEnabled(true);
        addButton.addActionListener(e -> {
            Doctor doctor = new Doctor(
                    nameField.getText(),
                    passField.getText(),
                    specialityField.getText(),
                    Integer.parseInt(expField.getText()),
                    telField.getText()
            );
            doctors.add(doctor);
            fileWriter(doctors, pathFileDoctors);
            doctorTableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(scrollTableDoctors, "Доктор добавлен!");
            clearAndReset();
        });
        updateButton.addActionListener(e -> {
            selectedDoctor.setId(Integer.parseInt(idField.getText()));
            selectedDoctor.setName(nameField.getText());
            selectedDoctor.setPass(passField.getText());
            selectedDoctor.setTel(telField.getText());
            selectedDoctor.setExp(Integer.parseInt(expField.getText()));
            selectedDoctor.setSpeciality(specialityField.getText());
            doctors.set(selectedIndexDoctor, selectedDoctor);
            fileWriter(doctors, pathFileDoctors);
            doctorTableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(scrollTableDoctors, "Данные доктора ID=" + selectedDoctor.getId() + " успешно обновлены!");
            clearAndReset();
        });
        deleteButton.addActionListener(e -> {
            doctors.remove(selectedIndexDoctor);
            fileWriter(doctors, pathFileDoctors);
//            if (doctors.size() != 0) {
//                doctorTableModel.fireTableDataChanged();
//            } else {
//                tableDoctors.setModel(new DoctorTableModel());
//            }
            doctorTableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(scrollTableDoctors, "Данные доктора ID=" + selectedDoctor.getId() + " успешно удалены!");
            clearAndReset();
        });
        clearButton.addActionListener(e -> clearAndReset());
        tableDoctors.getSelectionModel().addListSelectionListener(e -> {
            if (!tableDoctors.getSelectionModel().isSelectionEmpty()) {
                selectedIndexDoctor = tableDoctors.convertRowIndexToModel(tableDoctors.getSelectedRow());
                selectedDoctor = doctors.get(selectedIndexDoctor);
                if (selectedDoctor != null) {
                    idField.setText(String.valueOf(selectedDoctor.getId()));
                    nameField.setText(selectedDoctor.getName());
                    passField.setText(selectedDoctor.getPass());
                    telField.setText(selectedDoctor.getTel());
                    expField.setText(String.valueOf(selectedDoctor.getExp()));
                    specialityField.setText(selectedDoctor.getSpeciality());
                    addButton.setEnabled(false);
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                    clearButton.setEnabled(false);
                }
            }
        });
        homeButton.addActionListener(e -> {
            HomeForm homeForm = new HomeForm();
            homeForm.createLayout();
            frame.setVisible(false);
        });
    }

    private void clearAndReset() {
        idField.setText("");
        nameField.setText("");
        passField.setText("");
        telField.setText("");
        expField.setText("");
        specialityField.setText("");
        selectedIndexDoctor = -1;
        selectedDoctor = null;
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        clearButton.setEnabled(true);
    }

    public void createLayout() {
        frame = new JFrame("DoctorForm");
        frame.setContentPane(new DoctorForm().doctorPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }

    private static class DoctorTableModel extends AbstractTableModel {
        private final String[] COLUMNS = { "ID", "Имя", "Пароль", "Специальность", "Опыт", "Телефон" };
        private final List<Doctor> doctors;
        public DoctorTableModel(List<Doctor> doctors) {
            this.doctors = doctors;
        }

        @Override
        public int getRowCount() {
            return doctors.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> doctors.get(rowIndex).getId();
                case 1 -> doctors.get(rowIndex).getName();
                case 2 -> doctors.get(rowIndex).getPass();
                case 3 -> doctors.get(rowIndex).getSpeciality();
                case 4 -> doctors.get(rowIndex).getExp();
                case 5 -> doctors.get(rowIndex).getTel();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int column) {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}
