package com.hms.view;

import com.hms.entity.Patient;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PatientForm {
    private static JFrame frame;
    private JPanel patientPanel;
    private JButton homeButton;
    private JTable tablePatients;
    private JScrollPane scrollTableDoctors;
    private JComboBox genderComboBox;
    private JTextField idField;
    private JTextField addressField;
    private JTextField nameField;
    private JTextField telField;
    private JTextField ageField;
    private JTextField diagnosisField;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton clearButton;
    private Patient selectedPatient;
    private int selectedIndexPatient;
    private final String[] itemsGenderComboBox = { "-", "M", "F" };
    private final String pathFilePatients = "C:\\Vlad\\Projects\\IntelliJIDEA\\hms\\src\\com\\hms\\db\\patients";
    public void fileReader(List<Patient> patients, String pathFile) {
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
                Patient patient = new Patient(
                        Integer.parseInt(parseLine[0]),
                        parseLine[1],
                        parseLine[2],
                        parseLine[3],
                        Integer.parseInt(parseLine[4]),
                        parseLine[5],
                        parseLine[6]
                );
                patients.add(patient);
            } while (true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void fileWriter(List<Patient> patients, String pathFile) {
        try {
            FileWriter writer = new FileWriter(pathFile, false);
            for (Patient patient : patients) {
                String id = String.valueOf(patient.getId());
                String name = patient.getName();
                String address = patient.getAddress();
                String tel = patient.getTel();
                String age = String.valueOf(patient.getAge());
                String gender = patient.getGender();
                String diagnosis = patient.getDiagnosis();
                writer.write(id + "# " + name + "# " + address + "# " + tel + "# " + age + "# " + gender + "# " + diagnosis + "\n");
            }
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public PatientForm() {
        List<Patient> patients = new ArrayList<>();
        fileReader(patients, pathFilePatients);

        PatientForm.PatientTableModel doctorTableModel = new PatientForm.PatientTableModel(patients);
        tablePatients.setPreferredScrollableViewportSize(new Dimension(450,100));
        tablePatients.setModel(doctorTableModel);
        tablePatients.setAutoCreateRowSorter(true);
        genderComboBox.setSelectedItem(itemsGenderComboBox[0]);
        idField.setEnabled(false);
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        clearButton.setEnabled(true);
        addButton.addActionListener(e -> {
            Patient patient = new Patient(
                    nameField.getText(),
                    addressField.getText(),
                    telField.getText(),
                    Integer.parseInt(ageField.getText()),
                    genderComboBox.getEditor().getItem().toString(),
                    diagnosisField.getText()
            );
            patients.add(patient);
            fileWriter(patients, pathFilePatients);
            doctorTableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(scrollTableDoctors, "Пациент добавлен!");
            clearAndReset();
        });
        updateButton.addActionListener(e -> {
            selectedPatient.setId(Integer.parseInt(idField.getText()));
            selectedPatient.setName(nameField.getText());
            selectedPatient.setAddress(addressField.getText());
            selectedPatient.setTel(telField.getText());
            selectedPatient.setAge(Integer.parseInt(ageField.getText()));
            selectedPatient.setGender(genderComboBox.getEditor().getItem().toString());
            selectedPatient.setDiagnosis(diagnosisField.getText());
            patients.set(selectedIndexPatient, selectedPatient);
            fileWriter(patients, pathFilePatients);
            doctorTableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(scrollTableDoctors, "Данные пациента ID=" + selectedPatient.getId() + " успешно обновлены!");
            clearAndReset();
        });
        deleteButton.addActionListener(e -> {
            patients.remove(selectedIndexPatient);
            fileWriter(patients, pathFilePatients);
//            if (doctors.size() != 0) {
//                doctorTableModel.fireTableDataChanged();
//            } else {
//                tableDoctors.setModel(new DoctorTableModel());
//            }
            doctorTableModel.fireTableDataChanged();
            JOptionPane.showMessageDialog(scrollTableDoctors, "Данные пациента ID=" + selectedPatient.getId() + " успешно удалены!");
            clearAndReset();
        });
        clearButton.addActionListener(e -> clearAndReset());
        tablePatients.getSelectionModel().addListSelectionListener(e -> {
            if (!tablePatients.getSelectionModel().isSelectionEmpty()) {
                selectedIndexPatient = tablePatients.convertRowIndexToModel(tablePatients.getSelectedRow());
                selectedPatient = patients.get(selectedIndexPatient);
                if (selectedPatient != null) {
                    idField.setText(String.valueOf(selectedPatient.getId()));
                    nameField.setText(selectedPatient.getName());
                    addressField.setText(selectedPatient.getAddress());
                    telField.setText(selectedPatient.getTel());
                    ageField.setText(String.valueOf(selectedPatient.getAge()));
                    genderComboBox.setSelectedItem(selectedPatient.getGender());
                    diagnosisField.setText(selectedPatient.getDiagnosis());
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
        addressField.setText("");
        telField.setText("");
        ageField.setText("");
        diagnosisField.setText("");
        genderComboBox.setSelectedItem(itemsGenderComboBox[0]);
        selectedIndexPatient = -1;
        selectedPatient = null;
        addButton.setEnabled(true);
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
        clearButton.setEnabled(true);
    }

    public void createLayout() {
        frame = new JFrame("PatientForm");
        frame.setContentPane(new PatientForm().patientPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setSize(600, 500);
        frame.setVisible(true);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
        createGenderComboBox();
    }

    private void createGenderComboBox() {
        genderComboBox = new JComboBox(itemsGenderComboBox);
    }

    private static class PatientTableModel extends AbstractTableModel {
        private final String[] COLUMNS = { "ID", "Имя", "Адрес", "Телефон", "Возраст", "Пол", "Диагноз" };
        private final List<Patient> patients;
        public PatientTableModel(List<Patient> patients) {
            this.patients = patients;
        }

        @Override
        public int getRowCount() {
            return patients.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> patients.get(rowIndex).getId();
                case 1 -> patients.get(rowIndex).getName();
                case 2 -> patients.get(rowIndex).getAddress();
                case 3 -> patients.get(rowIndex).getTel();
                case 4 -> patients.get(rowIndex).getAge();
                case 5 -> patients.get(rowIndex).getGender();
                case 6 -> patients.get(rowIndex).getDiagnosis();
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