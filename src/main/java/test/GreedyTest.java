package test;

import algorithms.DataPreparation;
import entity.*;

import java.util.ArrayList;

public final class GreedyTest {
    public static CASListForInstallOnManagementBody start(ArrayList<ManagementBody> managementBodies,
                                                          double budget, ArrayList<FunctionalTask> functionalTasks,
                                                          ArrayList<CAS> casList){
        DataPreparationTest.selectedManagementBodyForHigherFunctionalTasks(managementBodies, functionalTasks);
        DataPreparationTest.selectedCASForManagementBodyList(casList, managementBodies);
        ArrayList<ManagementBody> managementBodyForAutomatization = DataPreparationTest.getManagementBodyWithoutPreviouslyAndWithEfficiency(managementBodies);
        DataPreparationTest.sortManagementBodyByEfficiency(managementBodyForAutomatization);
        if(managementBodyForAutomatization.size() == 0){
            return null;
        }
        return recursion(managementBodyForAutomatization, new CASListForInstallOnManagementBody(), budget);
    }

    private static CASListForInstallOnManagementBody recursion(ArrayList<ManagementBody> managementBodyForAutomatization,
                                                               CASListForInstallOnManagementBody casListForInstallOnManagementBody,
                                                               double budget){
        for(ManagementBody managementBody : managementBodyForAutomatization){
                if(casListForInstallOnManagementBody.getManagementBodyCASHashMap().containsKey(managementBody)){
                    casListForInstallOnManagementBody.deleteCAS(managementBody,
                            casListForInstallOnManagementBody.getManagementBodyCASHashMap().get(managementBody));
                }
                for(CASForManagementBody casForManagementBody : managementBody.getAvailableCASList()){
                    if(budget - casForManagementBody.getCas().getPrice() -
                            casListForInstallOnManagementBody.getPrice() >= 0){
                        casListForInstallOnManagementBody.addCAS(managementBody, casForManagementBody.getCas());
                        break;
                    }
                }

            if(casListForInstallOnManagementBody.getManagementBodyCASHashMap().containsKey(managementBody) &&
                    managementBody.getNextManagementBodyList().size() != 0){
                casListForInstallOnManagementBody = recursion(managementBody.getNextManagementBodyList(), casListForInstallOnManagementBody, 0);
            }
        }
        return casListForInstallOnManagementBody;
    }
}
