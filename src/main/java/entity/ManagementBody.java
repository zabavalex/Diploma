package entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

@Data
@EqualsAndHashCode(exclude = "previousManagementBody")
@ToString(exclude = "previousManagementBody")
public class ManagementBody implements Cloneable{
    private String name;
    private CAS cas;
    private ArrayList<FunctionalTask> functionalTasks;
    private ArrayList<FunctionalTask> functionalTasksNeedResolved;
    private ArrayList<CASForManagementBody> availableCASList;
    private ManagementBody previousManagementBody;
    private ArrayList<ManagementBody> nextManagementBodyList;
    private Double efficiency;

    public ManagementBody(String name, ArrayList<FunctionalTask> functionalTasks) {
        this.name = name;
        this.functionalTasks = functionalTasks;
        functionalTasksNeedResolved = new ArrayList<>();
        availableCASList = new ArrayList<>();
        nextManagementBodyList = new ArrayList<>();
    }

    public void addAvailableCAS (CAS cas){
        double efficiency = 0;
        for(CASForManagementBody casForManagementBody : availableCASList){
            if(casForManagementBody.getCas() == cas){
                return;
            }
        }
        for(FunctionalTask functionalTask : cas.getFunctionalTasks()){
            if(functionalTasks.contains(functionalTask)){
                efficiency += functionalTask.getPriority();
            }
        }
        efficiency = efficiency / cas.getPrice();
        availableCASList.add(new CASForManagementBody(cas, efficiency));
    }
    public void sortAvailableCAS(){
        availableCASList.sort((o1, o2) -> o2.getEfficiency().compareTo(o1.getEfficiency()));
    }

    public Double calculateEfficiency(){
        efficiency = 0.0;
        for(CASForManagementBody cas : availableCASList){
            efficiency += cas.getEfficiency();
        }
        efficiency = efficiency / availableCASList.size();

        if(nextManagementBodyList.size() != 0){
            for(ManagementBody managementBody : nextManagementBodyList){
                efficiency += 0.5 * managementBody.calculateEfficiency();
            }
        }
        return efficiency;
    }

    public ManagementBody clone() throws CloneNotSupportedException {
        ManagementBody newMB = (ManagementBody) super.clone();
        newMB.setAvailableCASList(new ArrayList<>());
        newMB.setFunctionalTasksNeedResolved(new ArrayList<>());
        return newMB;
    }

}
