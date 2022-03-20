package algorithms;

import entity.CAS;
import entity.CASForManagementBody;
import entity.CASListForInstallOnManagementBody;
import entity.ManagementBody;

import java.util.ArrayList;

public final class Greedy {
    public static CASListForInstallOnManagementBody start(ArrayList<ManagementBody> managementBody,
                                                          CASListForInstallOnManagementBody casListForInstallOnManagementBody,
                                                          double budget){
        ArrayList<ManagementBody> managementBodyForAutomatization = DataPreparation.getManagementBodyWithoutPreviouslyAndWithEfficiency(managementBody);
        DataPreparation.sortManagementBodyByEfficiency(managementBodyForAutomatization);
        if(managementBodyForAutomatization.size() == 0){
            return null;
        }
        if(casListForInstallOnManagementBody == null){
            return recursion(managementBodyForAutomatization, new CASListForInstallOnManagementBody(), budget);
        }
        return recursion(managementBodyForAutomatization, casListForInstallOnManagementBody, budget);
    }

    private static CASListForInstallOnManagementBody recursion(ArrayList<ManagementBody> managementBodyForAutomatization,
                                                               CASListForInstallOnManagementBody casListForInstallOnManagementBody,
                                                               double budget){
        for(ManagementBody managementBody : managementBodyForAutomatization){
            if(managementBody.getFunctionalTasksNeedResolved().size() == 0){
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
            }
            if(casListForInstallOnManagementBody.getManagementBodyCASHashMap().containsKey(managementBody) &&
                    managementBody.getNextManagementBodyList().size() != 0){
                casListForInstallOnManagementBody = recursion(managementBody.getNextManagementBodyList(), casListForInstallOnManagementBody, budget);
            }
        }
        return casListForInstallOnManagementBody;
    }
}
