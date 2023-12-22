import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

public class CreateWindow extends javax.swing.JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;

    private JComboBox<String> projectNamesComboBox;
    private JComboBox<String> programConstructsComboBox;
    private JComboBox<String> packageNamesComboBox;
    private JComboBox<String> classNamesComboBox;
    private javax.swing.JButton nextButton;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel selectProjectNameLabel;
    private javax.swing.JLabel selectConstructLabel;
    private javax.swing.JLabel selectPackageLabel;
    private javax.swing.JLabel selectClassLabel;
    private ProgramInfo programInfo;
    private ProjectInfo project;
    private int flag = 0;

    public CreateWindow() {
        initComponents();
    }

    private void initComponents() {
        initializeDesign();
        setGroupLayout();
        pack();
    }

    private void initializeDesign() {
        selectProjectNameLabel = new javax.swing.JLabel();
        projectNamesComboBox = new javax.swing.JComboBox<String>();
        programConstructsComboBox = new javax.swing.JComboBox<String>();
        packageNamesComboBox = new javax.swing.JComboBox<String>();
        classNamesComboBox = new javax.swing.JComboBox<String>();
        selectConstructLabel = new javax.swing.JLabel();
        selectPackageLabel = new javax.swing.JLabel();
        selectClassLabel = new javax.swing.JLabel();
        nextButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        selectProjectNameLabel.setText("Select Project Name");
      projectNamesComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>());
        projectNamesComboBox.addItem("<Select Project>");
        populateProjects(projectNamesComboBox);
        projectNamesComboBox.addActionListener(this);
        selectConstructLabel.setText("Select Program Construct");
        programConstructsComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Relation", "Call Sequence", "Count Program Artifact","Exceptions" }));
        programConstructsComboBox.setSelectedIndex(-1);
        programConstructsComboBox.addActionListener(this);
        selectPackageLabel.setText("Select Package");
        packageNamesComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>());
        packageNamesComboBox.addItem("<Select Package");
        populatePackages(packageNamesComboBox);
        packageNamesComboBox.addActionListener(this);
        selectClassLabel.setText("Select Class");
        classNamesComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>());
        classNamesComboBox.addItem("<Select Class");
        populateClasses(classNamesComboBox);
        classNamesComboBox.addActionListener(this);
        nextButton.setText("Next>");
        nextButton.addActionListener(this);
        cancelButton.setText("Cancel");
        cancelButton.addActionListener(this);
        setResizable(false);
    }

    // private void setGroupLayout() {
    //     // ... (other GroupLayout code)
    // }

    	private void setGroupLayout() {
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(nextButton)
                    .addComponent(selectConstructLabel)
                    .addComponent(selectProjectNameLabel))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(projectNamesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(programConstructsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(54, 54, 54)
                        .addComponent(cancelButton)))
                .addContainerGap(104, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectProjectNameLabel)
                    .addComponent(projectNamesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(selectConstructLabel)
                    .addComponent(programConstructsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nextButton)
                    .addComponent(cancelButton))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        selectConstructLabel.setVisible(false);
        programConstructsComboBox.setVisible(false);
        nextButton.setVisible(false);
        cancelButton.setVisible(false);
	}

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == projectNamesComboBox && projectNamesComboBox.getSelectedIndex() != 0) {
            handleProjectSelection();
        } else if (e.getSource() == programConstructsComboBox) {
            handleConstructSelection();
        } else if (e.getSource() == nextButton) {
            handleNextButton();
        } else if (e.getSource() == cancelButton) {
            handleCancelButton();
        }
    }

    private void handleProjectSelection() {
        try {
            flag = 1;
            InfoObject infoObject = new InfoObject();
            programInfo = infoObject.createInfoObjects(projectNamesComboBox.getSelectedItem().toString());
            project = programInfo.getProject();
            selectConstructLabel.setVisible(true);
            programConstructsComboBox.setVisible(true);
        } catch (Exception exception) {
            handleException("There are build errors in your project!! Could not create InfoObjects!!");
            exception.printStackTrace();
        }
    }

    private void handleConstructSelection() {
        if (programConstructsComboBox.getSelectedIndex() == -1 || flag == 0) {
            showMessage("Select a project and then select Construct");
        } else {
            flag = 1;
            nextButton.setVisible(true);
            cancelButton.setVisible(true);
        }
    }

    private void handleNextButton() {
        if (programConstructsComboBox.getSelectedIndex() == -1 || flag == 0) {
            showMessage("Select a Project/Construct and then Click \"Next\"");
        } else {
            int selectedIndex = programConstructsComboBox.getSelectedIndex();
            handleSelectedIndex(selectedIndex);
        }
    }

    private void handleSelectedIndex(int selectedIndex) {
        this.setVisible(false);
        switch (selectedIndex) {
            case 0:
                handleLabelObject();
                break;
            case 1:
                handleCallSequence();
                break;
            case 2:
                handleProgramArtifact();
                break;
            case 3:
                handleExceptionGui();
                break;
        }
    }

    private void handleLabelObject() {
        LabelObject obj = new LabelObject();
        obj.init(project, false, "-1");
        flag = 0;
    }

    private void handleCallSequence() {
        CallSequence cs = new CallSequence();
        cs.init(project, false, "-1", false);
        flag = 0;
    }

    private void handleProgramArtifact() {
        ProgramArtifact pa = new ProgramArtifact();
        pa.init(project, false, "-1");
        flag = 0;
    }

    private void handleExceptionGui() {
        ExceptionGui exceptionGui = new ExceptionGui();
        exceptionGui.init(project);
        flag = 0;
    }

    private void handleCancelButton() {
        this.setVisible(false);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Alert!", JOptionPane.PLAIN_MESSAGE);
    }

    private void handleException(String message) {
        showMessage(message);
    }
}
