package com.company.han;

import sun.java2d.cmm.Profile;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        boolean createFile = true;
        int year = 2019;
        String filedir = "D:\\Musky\\";
        String androidfilename = "Android Report-" + year + ".xls";
        String iOSfilename = "iOS Report-" + year + ".xls";
        if (createFile) {
            CreateBaseReportFile.create(year, filedir, androidfilename);
            CreateBaseReportFile.create(year, filedir, iOSfilename);
        } else {
            new ASOUI();
        }
    }

    private static void writeFile(String src, String dst) {
        if (src.contains("next_trucking-app")) {
            ReadiOSInsatllCSVFile.IOSCSVData ioscsvData = ReadiOSInsatllCSVFile.read(src);
            WriteDataToReport.write(ioscsvData.data, dst);
        } else {
            ReadAndroidInstallCSVFile.AndroidCSVData androidCSVData = ReadAndroidInstallCSVFile.read(src);
            WriteDataToReport.write(androidCSVData.data, dst);
        }
    }

    public static class ASOUI extends JFrame {
        private Profile profile;
        private JFileChooser csvFileChoose = new JFileChooser();
        private JTextArea csvpathText = new JTextArea();
        private JButton csvButton = new JButton("Choose CSV");


        private JFileChooser reportFileChoose = new JFileChooser();
        private JTextArea reportpathText = new JTextArea();
        private JButton reportButton = new JButton("Choose XLS");

        private JButton workButton = new JButton("Done");
        private JPanel jPanel = new JPanel();

        public ASOUI() {
            initView();
            this.setTitle("ASO Report Tools");
            this.setSize(300, 400);
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setVisible(true);
            this.setLayout(new GridLayout(1, 1));
            this.add(jPanel);
            this.setContentPane(jPanel);
            this.setResizable(false);
        }

        public void initView() {
            this.setLayout(new BorderLayout());
            jPanel.setLayout(new GridLayout(7, 1));
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Choose File", "xls", "csv");
            csvFileChoose.setFileFilter(filter);
            csvFileChoose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(csvFileChoose.getSelectedFile().getPath());
                    csvpathText.setText(csvFileChoose.getSelectedFile().getPath());
                }
            });

            reportFileChoose.setFileFilter(filter);
            reportFileChoose.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(reportFileChoose.getSelectedFile().getPath());
                    reportpathText.setText(reportFileChoose.getSelectedFile().getPath());
                }
            });

            csvButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    csvFileChoose.showOpenDialog(null);
                }
            });
            reportButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    reportFileChoose.showOpenDialog(null);
                }
            });

            jPanel.add(new JLabel("Choose online csv file:"));
            jPanel.add(csvButton);
            jPanel.add(csvpathText);
            csvpathText.setEditable(false);

            jPanel.add(new JLabel("Choose report xls file:"));
            jPanel.add(reportButton);
            jPanel.add(reportpathText);
            reportpathText.setEditable(false);

            jPanel.add(workButton);
            workButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String src = csvpathText.getText();
                    String dst = reportpathText.getText();
                    System.out.println("src file = " + src);
                    System.out.println("dst file = " + dst);
                    writeFile(src, dst);
                    File resultFile = new File(reportpathText.getText());
                    if (resultFile.exists()) {
                        try {
                            Runtime.getRuntime().exec("cmd /c start " + resultFile.getParentFile().getAbsolutePath());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
            });
        }

    }
}
