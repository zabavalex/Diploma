import algorithms.DataPreparation;
import entity.CAS;
import entity.FunctionalTask;
import entity.ManagementBody;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DataPreparationTest {
    @Test
    void selectedManagementBodyForHigherFunctionalTasksTest(){
        ArrayList<ManagementBody> managementBodies = new ArrayList<>();
        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        FunctionalTask functionalTask1 = new FunctionalTask("1", 0);
        FunctionalTask functionalTask2 = new FunctionalTask("2", 1);
        FunctionalTask functionalTask3 = new FunctionalTask("3", 1);
        FunctionalTask functionalTask4 = new FunctionalTask("4", 1);
        FunctionalTask functionalTask5 = new FunctionalTask("5", 1);
        FunctionalTask functionalTask6 = new FunctionalTask("6", 1);
        FunctionalTask functionalTask7 = new FunctionalTask("7", 0);
        FunctionalTask functionalTask8 = new FunctionalTask("8", 0);
        FunctionalTask functionalTask9 = new FunctionalTask("9  ", 0);
        ManagementBody managementBody1 = new ManagementBody("mb1", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9)));
        ManagementBody managementBody2 = new ManagementBody("mb2", new ArrayList<>(Arrays.asList(functionalTask3, functionalTask7)));
        ManagementBody managementBody3 = new ManagementBody("mb3", new ArrayList<>(Arrays.asList(functionalTask8, functionalTask2)));
        ManagementBody managementBody4 = new ManagementBody("mb4", new ArrayList<>(Arrays.asList(functionalTask6, functionalTask5)));
        managementBodies.add(managementBody1);
        managementBodies.add(managementBody2);
        managementBodies.add(managementBody3);
        managementBodies.add(managementBody4);
        functionalTasks.add(functionalTask1);
        functionalTasks.add(functionalTask2);
        functionalTasks.add(functionalTask3);
        functionalTasks.add(functionalTask4);
        functionalTasks.add(functionalTask5);
        functionalTasks.add(functionalTask6);
        functionalTasks.add(functionalTask7);
        functionalTasks.add(functionalTask8);
        functionalTasks.add(functionalTask9);
        ArrayList<ManagementBody> managementBodiesAnswer = new ArrayList<>();
        managementBodiesAnswer.add(managementBody3);
        managementBodiesAnswer.add(managementBody2);
        managementBodiesAnswer.add(managementBody4);
        ArrayList<ManagementBody> priorityManagementBody = DataPreparation.selectedManagementBodyForHigherFunctionalTasks(managementBodies, functionalTasks);
        assertEquals(priorityManagementBody, managementBodiesAnswer);
    }
    @Test
    void selectedCASForManagementBodyListTest(){
        ArrayList<ManagementBody> managementBodies = new ArrayList<>();
        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        FunctionalTask functionalTask1 = new FunctionalTask("1", 1);
        FunctionalTask functionalTask2 = new FunctionalTask("2", 2);
        FunctionalTask functionalTask3 = new FunctionalTask("3", 2);
        FunctionalTask functionalTask4 = new FunctionalTask("4", 2);
        FunctionalTask functionalTask5 = new FunctionalTask("5", 2);
        FunctionalTask functionalTask6 = new FunctionalTask("6", 2);
        FunctionalTask functionalTask7 = new FunctionalTask("7", 1);
        FunctionalTask functionalTask8 = new FunctionalTask("8", 1);
        FunctionalTask functionalTask9 = new FunctionalTask("9", 1);
        ManagementBody managementBody1 = new ManagementBody("mb1", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9)));
        ManagementBody managementBody2 = new ManagementBody("mb2", new ArrayList<>(Arrays.asList(functionalTask3, functionalTask7)));
        ManagementBody managementBody3 = new ManagementBody("mb3", new ArrayList<>(Arrays.asList(functionalTask8, functionalTask2)));
        ManagementBody managementBody4 = new ManagementBody("mb4", new ArrayList<>(Arrays.asList(functionalTask6, functionalTask5)));
        managementBodies.add(managementBody1);
        managementBodies.add(managementBody2);
        managementBodies.add(managementBody3);
        managementBodies.add(managementBody4);

        functionalTasks.add(functionalTask1);
        functionalTasks.add(functionalTask2);
        functionalTasks.add(functionalTask3);
        functionalTasks.add(functionalTask4);
        functionalTasks.add(functionalTask5);
        functionalTasks.add(functionalTask6);
        functionalTasks.add(functionalTask7);
        functionalTasks.add(functionalTask8);
        functionalTasks.add(functionalTask9);

        ArrayList<CAS> cas = new ArrayList<>();
        cas.add(new CAS("CAS1", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask2)), 1.0));
        cas.add(new CAS("CAS2", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9)), 2.0));
        cas.add(new CAS("CAS3", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9)), 3.0));
        cas.add(new CAS("CAS4", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9, functionalTask2, functionalTask3, functionalTask5)), 15.0));
        cas.add(new CAS("CAS5", new ArrayList<>(Arrays.asList(functionalTask2, functionalTask3, functionalTask4)), 20.0));
        cas.add(new CAS("CAS6", new ArrayList<>(Arrays.asList(functionalTask5, functionalTask6)), 2.0));
        cas.add(new CAS("CAS7", new ArrayList<>(Arrays.asList(functionalTask5, functionalTask6)), 10.0));
        assertTrue(DataPreparation.selectedCASForManagementBodyList(cas, managementBodies));

    }

    @Test
    void getManagementBodyWithoutPreviouslyAndWithEfficiencyTest(){
        ArrayList<ManagementBody> managementBodies = new ArrayList<>();
        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        FunctionalTask functionalTask1 = new FunctionalTask("1", 1);
        FunctionalTask functionalTask2 = new FunctionalTask("2", 2);
        FunctionalTask functionalTask3 = new FunctionalTask("3", 2);
        FunctionalTask functionalTask4 = new FunctionalTask("4", 2);
        FunctionalTask functionalTask5 = new FunctionalTask("5", 2);
        FunctionalTask functionalTask6 = new FunctionalTask("6", 2);
        FunctionalTask functionalTask7 = new FunctionalTask("7", 1);
        FunctionalTask functionalTask8 = new FunctionalTask("8", 1);
        FunctionalTask functionalTask9 = new FunctionalTask("9", 1);
        ManagementBody managementBody1 = new ManagementBody("mb1", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9)));
        ManagementBody managementBody2 = new ManagementBody("mb2", new ArrayList<>(Arrays.asList(functionalTask3, functionalTask7)));
        ManagementBody managementBody3 = new ManagementBody("mb3", new ArrayList<>(Arrays.asList(functionalTask8, functionalTask2)));
        ManagementBody managementBody4 = new ManagementBody("mb4", new ArrayList<>(Arrays.asList(functionalTask6, functionalTask5)));
        managementBody3.getNextManagementBodyList().add(managementBody4);
        managementBody4.setPreviousManagementBody(managementBody3);
        managementBodies.add(managementBody1);
        managementBodies.add(managementBody2);
        managementBodies.add(managementBody3);
        managementBodies.add(managementBody4);

        ArrayList<ManagementBody> answer = new ArrayList<>();
        answer.add(managementBody1);
        answer.add(managementBody2);
        answer.add(managementBody3);

        functionalTasks.add(functionalTask1);
        functionalTasks.add(functionalTask2);
        functionalTasks.add(functionalTask3);
        functionalTasks.add(functionalTask4);
        functionalTasks.add(functionalTask5);
        functionalTasks.add(functionalTask6);
        functionalTasks.add(functionalTask7);
        functionalTasks.add(functionalTask8);
        functionalTasks.add(functionalTask9);

        ArrayList<CAS> cas = new ArrayList<>();
        cas.add(new CAS("CAS1", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask2)), 1.0));
        cas.add(new CAS("CAS2", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9)), 2.0));
        cas.add(new CAS("CAS3", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9)), 3.0));
        cas.add(new CAS("CAS4", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask9, functionalTask2, functionalTask3, functionalTask5)), 15.0));
        cas.add(new CAS("CAS5", new ArrayList<>(Arrays.asList(functionalTask2, functionalTask3, functionalTask4)), 20.0));
        cas.add(new CAS("CAS6", new ArrayList<>(Arrays.asList(functionalTask5, functionalTask6)), 2.0));
        cas.add(new CAS("CAS7", new ArrayList<>(Arrays.asList(functionalTask5, functionalTask6)), 10.0));
        assertEquals(DataPreparation.getManagementBodyWithoutPreviouslyAndWithEfficiency(managementBodies), answer);
    }
}
