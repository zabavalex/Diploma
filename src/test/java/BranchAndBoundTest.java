import algorithms.BranchAndBound;
import algorithms.DataPreparation;
import entity.CAS;
import entity.CASListForInstallOnManagementBody;
import entity.FunctionalTask;
import entity.ManagementBody;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

public class BranchAndBoundTest {
    @Test
    void test1(){
        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        FunctionalTask functionalTask1 = new FunctionalTask("ft1", 1);
        FunctionalTask functionalTask2 = new FunctionalTask("ft2", 3);
        functionalTasks.add(functionalTask1);
        functionalTasks.add(functionalTask2);

        CAS cas1 = new CAS("cas1", functionalTasks, 2.0);
        CAS cas2 = new CAS("cas2", functionalTasks, 3.0);
        CAS cas3 = new CAS("cas3", new ArrayList<>(Collections.singletonList(functionalTask1)), 1.0);
        CAS cas4 = new CAS("cas4", new ArrayList<>(Collections.singletonList(functionalTask2)), 1.0);
        ArrayList<CAS> casArrayList = new ArrayList<>(Arrays.asList(cas1, cas2, cas3, cas4));

        ManagementBody managementBody1 = new ManagementBody("second", new ArrayList<>(Collections.singletonList(functionalTask1)));
        ManagementBody managementBody2 = new ManagementBody("first", new ArrayList<>(Collections.singletonList(functionalTask2)));
        managementBody2.setPreviousManagementBody(managementBody1);
        ArrayList<ManagementBody> managementBodies = new ArrayList<>(Arrays.asList(managementBody1, managementBody2));

        CASListForInstallOnManagementBody answer = BranchAndBound.start(managementBodies,
                2, functionalTasks, casArrayList);
        assertEquals(answer.getPrice(), 2.0);
        assertEquals(answer.getManagementBodyCASHashMap().get(managementBody2), cas4);
        assertEquals(answer.getManagementBodyCASHashMap().get(managementBody1), cas3);
        assertEquals(answer.getResolvedFunctionalTask().get(0), functionalTask1);
        assertEquals(answer.getResolvedFunctionalTask().get(1), functionalTask2);
        assertEquals(answer.getResolvedFunctionalTask().size(), 2);
        assertNull(BranchAndBound.start(managementBodies,
                1, functionalTasks, casArrayList));
        assertNull(BranchAndBound.start(managementBodies,
                0, functionalTasks, casArrayList));
    }

    @Test
    void test2(){
        ArrayList<FunctionalTask> functionalTasks = new ArrayList<>();
        FunctionalTask functionalTask1 = new FunctionalTask("ft1", 3);
        FunctionalTask functionalTask2 = new FunctionalTask("ft2", 3);
        FunctionalTask functionalTask3 = new FunctionalTask("ft3", 1);
        FunctionalTask functionalTask4 = new FunctionalTask("ft4", 1);
        functionalTasks.add(functionalTask1);
        functionalTasks.add(functionalTask2);
        functionalTasks.add(functionalTask3);
        functionalTasks.add(functionalTask4);

        CAS cas1 = new CAS("cas1", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask3)), 2.5);
        CAS cas2 = new CAS("cas2", new ArrayList<>(Arrays.asList(functionalTask2, functionalTask4)), 2.5);
        CAS cas3 = new CAS("cas3", new ArrayList<>(Collections.singletonList(functionalTask1)), 2.0);
        CAS cas4 = new CAS("cas4", new ArrayList<>(Collections.singletonList(functionalTask2)), 2.0);
        ArrayList<CAS> casArrayList = new ArrayList<>(Arrays.asList(cas1, cas2, cas3, cas4));

        ManagementBody managementBody1 = new ManagementBody("first", new ArrayList<>(Arrays.asList(functionalTask1, functionalTask3)));
        ManagementBody managementBody2 = new ManagementBody("second", new ArrayList<>(Arrays.asList(functionalTask2, functionalTask4)));
        managementBody1.setPreviousManagementBody(managementBody2);
        ArrayList<ManagementBody> managementBodies = new ArrayList<>(Arrays.asList(managementBody1, managementBody2));

        CASListForInstallOnManagementBody answer = BranchAndBound.start(managementBodies,
                5, functionalTasks, casArrayList);

        assertEquals(answer.getManagementBodyCASHashMap().get(managementBody1), cas1);
        assertEquals(answer.getManagementBodyCASHashMap().get(managementBody2), cas2);
    }



}
