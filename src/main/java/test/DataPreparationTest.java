package test;

import entity.CAS;
import entity.FunctionalTask;
import entity.ManagementBody;

import java.util.ArrayList;

public final class DataPreparationTest {
    public static ArrayList<ManagementBody> selectedManagementBodyForHigherFunctionalTasks(
            ArrayList<ManagementBody> managementBodies,
            ArrayList<FunctionalTask> functionalTasks
    ){
        ArrayList<ManagementBody> priorityManagementBodies = new ArrayList<>();
        for(FunctionalTask functionalTask : functionalTasks){
            if(functionalTask.getPriority() > 1) {
                for (ManagementBody managementBody : managementBodies) {
                    if (managementBody.getFunctionalTasks().contains(functionalTask)) {
                        if (!priorityManagementBodies.contains(managementBody)) {
                            priorityManagementBodies.add(managementBody);
                        }
                        managementBody.getFunctionalTasksNeedResolved().add(functionalTask);
                        break;
                    }
                }
            }
        }
        return priorityManagementBodies;
    }

    public static boolean selectedCASForManagementBodyList(ArrayList<CAS> casList, ArrayList<ManagementBody> managementBodies){
        for(ManagementBody managementBody : managementBodies){
            for(CAS cas : casList){
                if(checkCASForFunctionalTasks(cas, managementBody)){
                    managementBody.addAvailableCAS(cas);
                }
            }
            if(managementBody.getFunctionalTasksNeedResolved() != null &&
                    managementBody.getFunctionalTasksNeedResolved().size() != 0 &&
                    managementBody.getAvailableCASList() == null || managementBody.getAvailableCASList().size() == 0){
                return false;
            }
            managementBody.sortAvailableCAS();
        }
        return true;
    }

    private static boolean checkCASForFunctionalTasks(CAS cas, ManagementBody managementBody){
        if(managementBody.getFunctionalTasksNeedResolved().size() != 0) {
            for (FunctionalTask functionalTask : managementBody.getFunctionalTasksNeedResolved()) {
                if (!cas.getFunctionalTasks().contains(functionalTask)) {
                    return false;
                }
            }
            return true;
        }
        else{
            for (FunctionalTask functionalTask : managementBody.getFunctionalTasks()) {
                if (cas.getFunctionalTasks().contains(functionalTask)) {
                    return true;
                }
            }
            return false;
        }
    }

    private static void calculateManagementBodyEfficiency(ArrayList<ManagementBody> managementBodies){
        for(ManagementBody managementBody : managementBodies){
            managementBody.calculateEfficiency();
        }
    }

    public static ArrayList<ManagementBody> getManagementBodyWithoutPreviouslyAndWithEfficiency(ArrayList<ManagementBody> managementBodies){
        ArrayList<ManagementBody> answer = new ArrayList<>();
        for(ManagementBody managementBody : managementBodies){
            if(managementBody.getPreviousManagementBody() == null) {
                answer.add(managementBody);
            }
        }
        calculateManagementBodyEfficiency(answer);
        return answer;
    }

    public static void sortManagementBodyByEfficiency(ArrayList<ManagementBody> managementBodies){
        managementBodies.sort((o1, o2) -> o2.getEfficiency().compareTo(o1.getEfficiency()));
        for(ManagementBody managementBody : managementBodies){
            if(managementBody.getNextManagementBodyList().size() != 0){
                sortManagementBodyByEfficiency(managementBody.getNextManagementBodyList());
            }
        }
    }
}
