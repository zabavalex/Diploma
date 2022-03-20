package entity;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;

@Data
public class CASListForInstallOnManagementBody implements Cloneable{
    HashMap<ManagementBody, CAS> managementBodyCASHashMap;
    ArrayList<FunctionalTask> resolvedFunctionalTask;
    double sumOfPriorityOfFunctionalTasks;
    double price;

    public CASListForInstallOnManagementBody() {
        managementBodyCASHashMap = new HashMap<>();
        resolvedFunctionalTask = new ArrayList<>();
        price = 0.0;
        sumOfPriorityOfFunctionalTasks = 0.0;
    }

    public void addCAS(ManagementBody managementBody, CAS cas){
        managementBodyCASHashMap.put(managementBody, cas);
        price += cas.getPrice();
        for(FunctionalTask functionalTask : cas.getFunctionalTasks()){
            if(managementBody.getFunctionalTasks().contains(functionalTask)){
                resolvedFunctionalTask.add(functionalTask);
                sumOfPriorityOfFunctionalTasks += functionalTask.getPriority();
            }
        }
    }

    public void deleteCAS(ManagementBody managementBody, CAS cas){
        managementBodyCASHashMap.remove(managementBody);
        price -= cas.getPrice();
        for(FunctionalTask functionalTask : cas.getFunctionalTasks()){
            if(managementBody.getFunctionalTasks().contains(functionalTask)){
                resolvedFunctionalTask.remove(functionalTask);
                sumOfPriorityOfFunctionalTasks -= functionalTask.getPriority();
            }
        }
    }

    public Double getEfficiency(){
        return sumOfPriorityOfFunctionalTasks / price;
    }

    public CASListForInstallOnManagementBody clone() throws CloneNotSupportedException {
        CASListForInstallOnManagementBody newList = (CASListForInstallOnManagementBody) super.clone();
        newList.setManagementBodyCASHashMap((HashMap<ManagementBody, CAS>) managementBodyCASHashMap.clone());
        newList.setResolvedFunctionalTask((ArrayList<FunctionalTask>) resolvedFunctionalTask.clone());
        return newList;
    }
}
