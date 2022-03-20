package test;

import algorithms.BranchAndBound;
import algorithms.Greedy;
import entity.CAS;
import entity.CASListForInstallOnManagementBody;
import entity.FunctionalTask;
import entity.ManagementBody;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;

import javax.swing.*;
import javax.swing.plaf.PanelUI;
import java.net.URL;
import java.util.*;

public class TestController implements Initializable {
    public LineChart efficiencyChart;
    public LineChart timeChart;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeChart.getXAxis().setLabel("Количество КСА и ОУ");
        timeChart.getYAxis().setLabel("Время (наносекунды)");
        test3();
    }

    public void test1(){
        XYChart.Series<Integer, Long> series1 = new XYChart.Series<>();
        XYChart.Series<Integer, Long> series2 = new XYChart.Series<>();
        XYChart.Series<Integer, Long> series3 = new XYChart.Series<>();

        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        ArrayList<ManagementBody> managementBodies = new ArrayList<>();
        ArrayList<CAS> casArrayList = new ArrayList<>();
        casArrayList.add(new CAS("1", functionalTasks, 1.0));
        for(int i = 0; i < 60; i++){
            FunctionalTask functionalTaskL = new FunctionalTask("ft" + functionalTasks.size(),1);
            FunctionalTask functionalTaskH = new FunctionalTask("ft" + (functionalTasks.size() + 1), 3);
            functionalTasks.add(functionalTaskL);
            functionalTasks.add(functionalTaskH);
            managementBodies.add(new ManagementBody(String.valueOf(managementBodies.size()), new ArrayList<>(Collections.singleton(functionalTaskL))));
            managementBodies.add(new ManagementBody(String.valueOf(managementBodies.size()), new ArrayList<>(Collections.singleton(functionalTaskH))));
            casArrayList.get(0).setFunctionalTasks(functionalTasks);
            long time1 = 0;
            long time2 = 0;
            long time3 = 0;
            long start;
            for(int t = 0; t < 1000; t++){
                ArrayList<ManagementBody> managementBodies1 = cloneArrayList(managementBodies);
                ArrayList<ManagementBody> managementBodies2 = cloneArrayList(managementBodies);
                ArrayList<ManagementBody> managementBodies3 = cloneArrayList(managementBodies);
//                start = System.nanoTime();
//                BranchAndBoundTest.start(managementBodies1, (double) i + 3, functionalTasks, casArrayList);
//                time1 += System.nanoTime() - start;
                start = System.nanoTime();
                GreedyTest.start(managementBodies2, (double) i + 3, functionalTasks, casArrayList);
                time2 += System.nanoTime() - start;
                start = System.nanoTime();
                CASListForInstallOnManagementBody casListForInstallOnManagementBody = BranchAndBound.start(managementBodies3, (double) i + 3, functionalTasks, casArrayList);
                Greedy.start(managementBodies3, casListForInstallOnManagementBody, (double) i + 3);
                time3 += System.nanoTime() - start;
            }
            series1.setName("Метод ветвей и границ");
            series2.setName("Жадный алгоритм");
            series3.setName("Разработанный алгоритм");
            series1.getData().add(new XYChart.Data<>(i + 1 ,time1/1000));
            series2.getData().add(new XYChart.Data<>(i + 1 ,time2/1000));
            series3.getData().add(new XYChart.Data<>(i + 1 ,time3/1000));
        }

        timeChart.getData().addAll( series2 ,series3);

    }

    public void test2(){
        XYChart.Series<Integer, Long> series1 = new XYChart.Series<>();
        XYChart.Series<Integer, Long> series2 = new XYChart.Series<>();
        XYChart.Series<Integer, Long> series3 = new XYChart.Series<>();

        ArrayList<FunctionalTask> functionalTasks1 = new ArrayList<>(Collections.singleton(new FunctionalTask("ft1", 1)));
        ArrayList<FunctionalTask> functionalTasks2 = new ArrayList<>(Collections.singleton(new FunctionalTask("ft2", 3)));
        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>(Arrays.asList(functionalTasks1.get(0), functionalTasks2.get(0)));
        ManagementBody managementBody1 = new ManagementBody("1", functionalTasks1);
        ManagementBody managementBody2 = new ManagementBody("2", functionalTasks2);
        ArrayList<ManagementBody> managementBodies = new ArrayList<>(Arrays.asList(managementBody1, managementBody2));
        ArrayList<CAS> casArrayList = new ArrayList<>();
        for(int i = 0; i < 9; i++){
            casArrayList.add(new CAS(String.valueOf(casArrayList.size()), functionalTasks, 1.0));
            long time1 = 0;
            long time2 = 0;
            long time3 = 0;
            long start;
            for(int t = 0; t < 1000; t++){
                ArrayList<ManagementBody> managementBodies1 = cloneArrayList(managementBodies);
                ArrayList<ManagementBody> managementBodies2 = cloneArrayList(managementBodies);
                ArrayList<ManagementBody> managementBodies3 = cloneArrayList(managementBodies);
                start = System.nanoTime();
                BranchAndBoundTest.start(managementBodies1, (double) i + 3, functionalTasks, casArrayList);
                time1 += System.nanoTime() - start;
                start = System.nanoTime();
                GreedyTest.start(managementBodies2, (double) i + 3, functionalTasks, casArrayList);
                time2 += System.nanoTime() - start;
                start = System.nanoTime();
                CASListForInstallOnManagementBody casListForInstallOnManagementBody = BranchAndBound.start(managementBodies3, (double) i + 3, functionalTasks, casArrayList);
                Greedy.start(managementBodies3, casListForInstallOnManagementBody, (double) i + 3);
                time3 += System.nanoTime() - start;
            }
            series1.setName("Метод ветвей и границ");
            series2.setName("Жадный алгоритм");
            series3.setName("Разработанный алгоритм");
            series1.getData().add(new XYChart.Data<>(i + 1,time1/1000));
            series2.getData().add(new XYChart.Data<>(i + 1,time2/1000));
            series3.getData().add(new XYChart.Data<>(i + 1,time3/1000));
        }

        timeChart.getData().addAll(series1, series2 ,series3);

    }

    public void test3(){
        XYChart.Series<Integer, Long> series1 = new XYChart.Series<>();
        XYChart.Series<Integer, Long> series2 = new XYChart.Series<>();
        XYChart.Series<Integer, Long> series3 = new XYChart.Series<>();

        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        ArrayList<ManagementBody> managementBodies = new ArrayList<>();
        ArrayList<CAS> casArrayList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            System.out.println(i);
            FunctionalTask functionalTaskL = new FunctionalTask("ft" + functionalTasks.size(),1);
            FunctionalTask functionalTaskH = new FunctionalTask("ft" + (functionalTasks.size() + 1), 3);
            functionalTasks.add(functionalTaskL);
            functionalTasks.add(functionalTaskH);
            managementBodies.add(new ManagementBody(String.valueOf(managementBodies.size()), new ArrayList<>(Collections.singleton(functionalTaskL))));
            managementBodies.add(new ManagementBody(String.valueOf(managementBodies.size()), new ArrayList<>(Collections.singleton(functionalTaskH))));
            for(CAS cas : casArrayList){
                cas.setFunctionalTasks(functionalTasks);
            }
            casArrayList.add(new CAS(String.valueOf(casArrayList.size()), functionalTasks, 1.0));
            long time1 = 0;
            long time2 = 0;
            long time3 = 0;
            long start;
            for(int t = 0; t < 1000; t++){
                ArrayList<ManagementBody> managementBodies1 = cloneArrayList(managementBodies);
                ArrayList<ManagementBody> managementBodies2 = cloneArrayList(managementBodies);
                ArrayList<ManagementBody> managementBodies3 = cloneArrayList(managementBodies);
//                start = System.nanoTime();
//                BranchAndBoundTest.start(managementBodies1, (double) i + 3, functionalTasks, casArrayList);
//                time1 += System.nanoTime() - start;
                start = System.nanoTime();
                GreedyTest.start(managementBodies2, (double) i + 3, functionalTasks, casArrayList);
                time2 += System.nanoTime() - start;
                start = System.nanoTime();
                CASListForInstallOnManagementBody casListForInstallOnManagementBody = BranchAndBound.start(managementBodies3, (double) i + 3, functionalTasks, casArrayList);
                Greedy.start(managementBodies3, casListForInstallOnManagementBody, (double) i + 3);
                time3 += System.nanoTime() - start;
            }
            series1.getData().add(new XYChart.Data<>(i + 1,time1/1000));
            series2.getData().add(new XYChart.Data<>(i + 1,time2/1000));
            series3.getData().add(new XYChart.Data<>(i + 1,time3/1000));
            System.out.println((time3 - time2)/1000);
        }
        series1.setName("Метод ветвей и границ");
        series2.setName("Жадный алгоритм");
        series3.setName("Разработанный алгоритм");
        timeChart.getData().addAll(series2 ,series3);

    }

    public ArrayList<FunctionalTask> generateFunctionalTask(){
        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        int[] priorityArray = new int[]{1, 3};
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            functionalTasks.add(new FunctionalTask("ft" + functionalTasks.size(), priorityArray[random.nextInt(2)]));
        }
        return functionalTasks;
    }

    public ArrayList<ManagementBody> generateManagementBodies(ArrayList<FunctionalTask> functionalTasks){
        ArrayList<ManagementBody> managementBodies = new ArrayList<>();
        int numberAvailableFunctionalTask = functionalTasks.size();
        Random random = new Random();
        int start = 0;
        int end;
        while(numberAvailableFunctionalTask > 0){
            end = start + random.nextInt(numberAvailableFunctionalTask) + 1;
            ArrayList<FunctionalTask> functionalTasks1 = new ArrayList<>();
            for(int i = start; i < end; i++){
                functionalTasks1.add(functionalTasks.get(i));
            }
            managementBodies.add(new ManagementBody("ОУ" + managementBodies.size(), functionalTasks1));
            numberAvailableFunctionalTask -= (end - start);
            start = end;
        }
        return managementBodies;
    }

    public ArrayList<CAS> generateCASList(ArrayList<FunctionalTask> functionalTasks){
        ArrayList<CAS> casList = new ArrayList<>();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            ArrayList<FunctionalTask> functionalTasks1 = new ArrayList<>();
            for(FunctionalTask functionalTask : functionalTasks){
                if(random.nextBoolean()){
                    functionalTasks1.add(functionalTask);
                }
            }
            casList.add(new CAS("cas" + casList.size(), functionalTasks1, (double) random.nextInt(10) + 1));
        }
        return casList;
    }

    public void test4(){
        double efficiency1 = 0;
        double efficiency2 = 0;
        double efficiency3 = 0;
        Random random = new Random();
        int num = 0;
        for(int i = 0; i < 10000; i++){
            ArrayList<FunctionalTask> functionalTasks = generateFunctionalTask();
            ArrayList<ManagementBody> managementBodies = generateManagementBodies(functionalTasks);
            ArrayList<CAS> casArrayList = generateCASList(functionalTasks);

            ArrayList<ManagementBody> managementBodies1 = cloneArrayList(managementBodies);
            ArrayList<ManagementBody> managementBodies2 = cloneArrayList(managementBodies);
            ArrayList<ManagementBody> managementBodies3 = cloneArrayList(managementBodies);
            Double budget = (double) (random.nextInt(30) + 1);

            CASListForInstallOnManagementBody c1 = BranchAndBoundTest.start(managementBodies1, budget, functionalTasks, casArrayList);
            CASListForInstallOnManagementBody c2 = GreedyTest.start(managementBodies2, budget, functionalTasks, casArrayList);
            CASListForInstallOnManagementBody c3 = BranchAndBound.start(managementBodies3, budget, functionalTasks, casArrayList);
            if(c3 != null) {
                c3 = Greedy.start(managementBodies3, c3, budget);
            }

            if(c3 != null && c1 != null && c2 != null){
                num++;
                efficiency1 += c1.getEfficiency();
                efficiency2 += c2.getEfficiency();
                efficiency3 += c3.getEfficiency();
            }

        }
        System.out.println(efficiency1 / num);
        System.out.println(efficiency2 / num);
        System.out.println(efficiency3 / num);
    }

    public ArrayList<ManagementBody> cloneArrayList(ArrayList<ManagementBody> managementBodies){
        ArrayList<ManagementBody> managementBodies1 = new ArrayList<>();
        for(ManagementBody managementBody : managementBodies){
            try {
                managementBodies1.add(managementBody.clone());
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        }
        return managementBodies1;
    }
}
