package sample;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;

public class Controller {

    @FXML private TableView<StudentRecord> tableView;
    @FXML private TableColumn studentID;
    @FXML private TableColumn midterm;
    @FXML private TableColumn assignments;
    @FXML private TableColumn finalExam;
    @FXML private TableColumn finalMark;
    @FXML private TableColumn letterGrade;
    @FXML private TextField studentIdField;
    @FXML private TextField assignmentsField;
    @FXML private TextField midtermField;
    @FXML private TextField finalExamField;

    private Stage mainStage;
    private String currentFilename;
    private ObservableList<StudentRecord> data = DataSource.getAllMarks();

    @FXML
    public void initialize() {
        studentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        midterm.setCellValueFactory(new PropertyValueFactory<>("midterm"));
        assignments.setCellValueFactory(new PropertyValueFactory<>("assignments"));
        finalExam.setCellValueFactory(new PropertyValueFactory<>("finalExam"));
        finalMark.setCellValueFactory(new PropertyValueFactory<>("finalMark"));
        letterGrade.setCellValueFactory(new PropertyValueFactory<>("letterGrade"));

        tableView.setItems(data);
    }

    @FXML
    public void newPressed() {
        data.clear();
    }

    @FXML
    public void openPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open StudentRecord csv File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.csv"));
        currentFilename = fileChooser.showOpenDialog(mainStage).getName();

        // load up the data
        load();
    }

    @FXML
    public void savePressed() {
        // save to currentFilename
        String line = "";
        try {
            BufferedWriter br = new BufferedWriter(new FileWriter(currentFilename));
            for (StudentRecord sr : data) {
                String studentID = sr.getStudentID();
                float assignments = sr.getAssignments();
                float midterm = sr.getMidterm();
                float finalExam = sr.getFinalExam();
                br.write(studentID + "," + assignments + "," + midterm + "," + finalExam + "\n");
            }
            // finish writing
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void saveAsPressed() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as csv File");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.csv"));
        currentFilename = fileChooser.showOpenDialog(mainStage).getName();

        // save to file
        savePressed();
    }

    @FXML
    public void exitPressed() {
        System.exit(0);
    }

    @FXML
    public void load() {
        data.clear();
        // loads from currentFilename
        String line = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(currentFilename));
            while ((line = br.readLine()) != null) {
                String[] columns = line.split(",");
                // add the new student record
                String studentID = columns[0];
                float assignments = Float.parseFloat(columns[1]);
                float midterm = Float.parseFloat(columns[2]);
                float finalExam = Float.parseFloat(columns[3]);

                StudentRecord newStudentRecord = new StudentRecord(studentID, assignments, midterm, finalExam);
                data.add(newStudentRecord);
            }
            // finish reading
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void addStudentRecord() {
        String studentID = studentIdField.getText();
        float assignments = Float.parseFloat(assignmentsField.getText());
        float midterm = Float.parseFloat(midtermField.getText());
        float finalExam = Float.parseFloat(finalExamField.getText());

        StudentRecord newStudentRecord = new StudentRecord(studentID, assignments, midterm, finalExam);
        data.add(newStudentRecord);
    }

    public void setMainStage(Stage mainStage) {
        this.mainStage = mainStage;
    }
}
