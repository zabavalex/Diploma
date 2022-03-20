package gui;

import algorithms.BranchAndBound;
import algorithms.Greedy;
import entity.CAS;
import entity.CASListForInstallOnManagementBody;
import entity.FunctionalTask;
import entity.ManagementBody;


import java.awt.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class Controller implements Initializable {


    public Button fTAddButton;
    public CheckBox fTPriorityCheckBox;
    public TextField fTNameField;
    public TextArea fTTextArea;
    public TextField managementBodyNameField;
    public TextField managementBodyFTListField;
    public Button managementBodyAddButton;
    public TextArea managementBodyArea;
    public TextField cASNameField;
    public TextField cASFTListField;
    public TextField cASPriceField;
    public Button cASAddButton;
    public TextArea cASTextArea;
    public TextArea answerTextArea;
    public Label errorLabel;
    public ComboBox<String> managementBodyPreviousBox;
    public Button calculateButton;
    public TextField budgetField;

    private ArrayList<FunctionalTask> functionalTasks;
    private ArrayList<String> functionalTaskNames;
    private ArrayList<ManagementBody> managementBodies;
    private ArrayList<String> managementBodyNames;
    private ArrayList<CAS> casList;
    private ArrayList<String> casListNames;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fTNameField.setText("1");
        managementBodyNameField.setText("1");
        cASNameField.setText("1");
        managementBodyPreviousBox.getItems().add("");
        functionalTaskNames = new ArrayList<>();
        functionalTasks = new ArrayList<>();
        managementBodies = new ArrayList<>();
        managementBodyNames = new ArrayList<>();
        casList = new ArrayList<>();
        casListNames = new ArrayList<>();
        cASPriceField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d\\d*.\\d*")) {
                    cASPriceField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        budgetField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d\\d*.\\d*")) {
                    cASPriceField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });

    }


    public void addFunctionalTask(ActionEvent actionEvent) {
        errorLabel.setVisible(false);
        if(fTNameField.getText().equals("")){
            error("Ошибка при добавлении ФЗ", "пустое поле");
            return;
        }
        if(functionalTaskNames.contains(fTNameField.getText())){
            error("Ошибка при добавлении ФЗ", "такое имя уже используется");
            return;
        }
        FunctionalTask functionalTask = new FunctionalTask(fTNameField.getText(), 1);
        if(fTPriorityCheckBox.isSelected()){
            functionalTask.setPriority(3);
        }

        functionalTasks.add(functionalTask);
        functionalTaskNames.add(functionalTask.getName());
        if(fTTextArea.getText().equals("")){
            fTTextArea.setText(fTTextArea.getText() + "ФЗ: Имя: " + functionalTask.getName() + ", приоритет: " + functionalTask.getPriority());
        } else {
            fTTextArea.setText(fTTextArea.getText() + "\nФЗ: Имя: " + functionalTask.getName() + ", приоритет: " + functionalTask.getPriority());
        }
        fTNameField.setText(String.valueOf(functionalTasks.size() + 1));
    }

    public void addManagementBody(ActionEvent actionEvent) {
        errorLabel.setVisible(false);
        if(managementBodyNameField.getText().equals("") || managementBodyFTListField.getText().equals("")){
            error("Ошибка при добавлении ОУ", "пустое поле");
            return;
        }
        if(checkFunctionalTaskText(managementBodyFTListField.getText())){
            error("Ошибка при добавлении ОУ", "указаны неправильные ФЗ");
            return;
        }
        if(managementBodyNames.contains(managementBodyNameField.getText())){
            error("Ошибка при добавлении ОУ", "такое имя уже используется");
            return;
        }

        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        String[] functionalTaskStringList = managementBodyFTListField.getText().split(", ");
        for(String name : functionalTaskStringList) {
            FunctionalTask functionalTask = this.functionalTasks.get(functionalTaskNames.indexOf(name));
            if(functionalTasks.contains(functionalTask)){
                error("Ошибка при добавлении ОУ", "ФЗ не должны повторяться");
                return;
            }
            functionalTasks.add(functionalTask);
        }
        if(checkManagementBodyFTForUse(functionalTasks)){
            error("Ошибка при добавлении ОУ", "заданные ФЗ уже используются в других ОУ");
            return;
        }

        ManagementBody managementBody = new ManagementBody(managementBodyNameField.getText(), functionalTasks);

        if(managementBodyPreviousBox.getValue() != null && !managementBodyPreviousBox.getValue().equals("")) {
            ManagementBody previousManagementBody = managementBodies.
                    get(managementBodyNames.indexOf(managementBodyPreviousBox.getValue()));
            managementBody.setPreviousManagementBody(previousManagementBody);
            previousManagementBody.getNextManagementBodyList().add(managementBody);
        }

        managementBodies.add(managementBody);
        managementBodyNames.add(managementBodyNameField.getText());
        managementBodyPreviousBox.getItems().add(managementBody.getName());
        refreshManagementBodyArea();
        managementBodyNameField.setText(String.valueOf(managementBodies.size() + 1));
    }


    public void addCAS(ActionEvent actionEvent) {
        errorLabel.setVisible(false);
        if(cASNameField.getText().equals("") || cASFTListField.getText().equals("") ||
                cASPriceField.getText().equals("")){
            error("Ошибка при добавлении КСА", "пустое поле");
            return;
        }
        if(checkFunctionalTaskText(cASFTListField.getText())){
            error("Ошибка при добавлении КСА", "указаны неправильные ФЗ");
            return;
        }
        if(casListNames.contains(cASNameField.getText())){
            error("Ошибка при добавлении КСА", "такое имя уже используется");
            return;
        }

        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        String[] functionalTaskStringList = cASFTListField.getText().split(", ");
        for(String name : functionalTaskStringList) {
            FunctionalTask functionalTask = this.functionalTasks.get(functionalTaskNames.indexOf(name));
            if(functionalTasks.contains(functionalTask)){
                error("Ошибка при добавлении КСА", "ФЗ не должны повторяться");
                return;
            }
            functionalTasks.add(functionalTask);
        }

        CAS cas = new CAS(cASNameField.getText(), functionalTasks, Double.valueOf(cASPriceField.getText()));
        casList.add(cas);
        casListNames.add(cas.getName());
        if(cASTextArea.getText().equals("")){
            cASTextArea.setText(cASTextArea.getText() + "КСА: Имя: " + cas.getName() +
                    ", ФЗ: " + cASFTListField.getText() + ", Цена: " + cASPriceField.getText());
        } else {
            cASTextArea.setText(cASTextArea.getText() + "\nКСА: Имя: " + cas.getName() +
                    ", ФЗ: " + cASFTListField.getText() + ", Цена: " + cASPriceField.getText());
        }
        cASNameField.setText(String.valueOf(casList.size() + 1));
    }

    private void error(String errorTag, String errorText){
        errorLabel.setText(errorTag + ": " + errorText);
        errorLabel.setVisible(true);
    }


    public void calculateBody(ActionEvent actionEvent) {
        CASListForInstallOnManagementBody casListForInstallOnManagementBody = BranchAndBound.start(managementBodies,
                Double.parseDouble(budgetField.getText()), functionalTasks, casList);
        casListForInstallOnManagementBody = Greedy.start(managementBodies, casListForInstallOnManagementBody,
                Double.parseDouble(budgetField.getText()));
        if(casListForInstallOnManagementBody == null || casListForInstallOnManagementBody.getResolvedFunctionalTask().size() == 0){
            error("Ошибка при вычислении", "Невозможно осуществить данный план");
            return;
        }

        StringBuilder text = new StringBuilder("");
        text.append("Цена решения: ").append(casListForInstallOnManagementBody.getPrice()).append("\n");
        text.append("Решенные ФЗ: ");
        for(FunctionalTask functionalTask : casListForInstallOnManagementBody.getResolvedFunctionalTask()){
            text.append(functionalTask.getName()).append(" ");
        }
        text.append("\n\n");
        for(ManagementBody managementBody : managementBodies){
            if(managementBody.getPreviousManagementBody() == null) {
                CAS cas = casListForInstallOnManagementBody.getManagementBodyCASHashMap().get(managementBody);
                if(cas != null) {
                    text.append("ОУ: ").append("Имя: ").append(managementBody.getName()).append(", КСА: ").append(cas.getName());
                    text.append("\n");
                    if (managementBody.getNextManagementBodyList().size() != 0) {
                        text.append(getTextForAnswerManagementBody(managementBody.getNextManagementBodyList(), 4, casListForInstallOnManagementBody));
                    }
                }

            }
        }
        answerTextArea.setText(text.toString());
    }


    private void refreshManagementBodyArea(){
        StringBuilder text = new StringBuilder("");
        for(ManagementBody managementBody : managementBodies){
            if(managementBody.getPreviousManagementBody() == null) {
                text.append("ОУ: ").append("Имя: ").append(managementBody.getName()).append(", ФЗ: ");
                for (FunctionalTask functionalTask : managementBody.getFunctionalTasks()) {
                    text.append(functionalTask.getName()).append(" ");
                }
                text.append("\n");
                if (managementBody.getNextManagementBodyList().size() != 0) {
                    text.append(getTextForManagementBody(managementBody.getNextManagementBodyList(), 4));
                }
            }
        }
        managementBodyArea.setText(text.toString());
    }

    private String getTextForManagementBody(ArrayList<ManagementBody> managementBodyList, int numSpace){
        StringBuilder text = new StringBuilder("");
        String space = new String(new char[numSpace]).replaceAll("\0", " ");
        for(ManagementBody managementBody : managementBodyList){
            text.append(space).append("ОУ: ").append("Имя: ").append(managementBody.getName()).append(", ФЗ: ");
            for(FunctionalTask functionalTask : managementBody.getFunctionalTasks()){
                text.append(functionalTask.getName()).append(" ");
            }
            text.append("\n");
            if(managementBody.getNextManagementBodyList().size() !=0){
                text.append(getTextForManagementBody(managementBody.getNextManagementBodyList(), numSpace + 4));
            }
        }
        return text.toString();
    }

    private String getTextForAnswerManagementBody(ArrayList<ManagementBody> managementBodyList, int numSpace, CASListForInstallOnManagementBody casListForInstallOnManagementBody){
        StringBuilder text = new StringBuilder("");
        String space = new String(new char[numSpace]).replaceAll("\0", " ");
        for(ManagementBody managementBody : managementBodyList){
            CAS cas = casListForInstallOnManagementBody.getManagementBodyCASHashMap().get(managementBody);
            if(cas != null) {
                text.append(space).append("ОУ: ").append("Имя: ").append(managementBody.getName()).append(", КСА: ").append(cas.getName());
                text.append("\n");
                if (managementBody.getNextManagementBodyList().size() != 0) {
                    text.append(getTextForAnswerManagementBody(managementBody.getNextManagementBodyList(), numSpace + 4, casListForInstallOnManagementBody));
                }
            }
        }
        return text.toString();
    }

    private boolean checkFunctionalTaskText(String functionalTaskText){
        String[] functionalTaskTextList = functionalTaskText.split(", ");
        for(String functionalTaskName : functionalTaskTextList){
            if(!functionalTaskNames.contains(functionalTaskName)){
                return true;
            }
        }
        return false;
    }

    public boolean checkManagementBodyFTForUse(ArrayList<FunctionalTask> functionalTasks){
        for(FunctionalTask functionalTask : functionalTasks){
            for(ManagementBody managementBody : managementBodies){
                if(managementBody.getFunctionalTasks().contains(functionalTask)){
                    return true;
                }
            }
        }
        return false;
    }

}
