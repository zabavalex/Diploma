package algorithms;;
import entity.*;
import lombok.Data;
import lombok.SneakyThrows;

import java.util.ArrayList;


public final class BranchAndBound {
    public static CASListForInstallOnManagementBody start(ArrayList<ManagementBody> managementBodies,
                                                   double budget, ArrayList<FunctionalTask> functionalTasks,
                                                          ArrayList<CAS> casList){
        ArrayList<ManagementBody> managementBodyForAutomatization = DataPreparation.
                selectedManagementBodyForHigherFunctionalTasks(managementBodies, functionalTasks);
        DataPreparation.selectedCASForManagementBodyList(casList, managementBodies);
        if(managementBodyForAutomatization.size() == 0){
            return null;
        }
        return recursion(managementBodyForAutomatization, new CASListForInstallOnManagementBody(), 0,
                0, budget);
    }
    @SneakyThrows
    private static CASListForInstallOnManagementBody recursion(ArrayList<ManagementBody> managementBodyForAutomatization,
                                                        CASListForInstallOnManagementBody casListForInstallOnManagementBody,
                                                        int numManagementBody, int numCAS, double budget){
        if(managementBodyForAutomatization.size() <= numManagementBody){
            return casListForInstallOnManagementBody;
        }
        if(budget - casListForInstallOnManagementBody.getPrice() <= 0){
            return null;
        }

        ManagementBody managementBody = managementBodyForAutomatization.get(numManagementBody);

        if(managementBody.getAvailableCASList().size() == 0){
            if(managementBody.getFunctionalTasksNeedResolved().size() != 0){
                return null;
            }
            return recursion(managementBodyForAutomatization, casListForInstallOnManagementBody.clone(), numManagementBody + 1, 0, budget);
        }

        CAS cas = managementBody.getAvailableCASList().get(numCAS).getCas();
        if(casListForInstallOnManagementBody.getManagementBodyCASHashMap().containsKey(managementBody)){
            casListForInstallOnManagementBody.deleteCAS(managementBody,
                    casListForInstallOnManagementBody.getManagementBodyCASHashMap().get(managementBody));
        }

        CASListForInstallOnManagementBody casListForInstallOnManagementBodyWithInclusion =
                casListForInstallOnManagementBody.clone();
        CASListForInstallOnManagementBody casListForInstallOnManagementBodyWithoutInclusion =
                casListForInstallOnManagementBody.clone();

        if(!addCASForAllPreviousManagementBody(casListForInstallOnManagementBodyWithInclusion, managementBody) ||
                (budget - casListForInstallOnManagementBodyWithInclusion.getPrice()) < 0){
            return null;
        }

        casListForInstallOnManagementBodyWithInclusion = getCASListForWithInclusion(
                casListForInstallOnManagementBodyWithInclusion, cas, managementBody,
                managementBodyForAutomatization, numManagementBody, numCAS, budget);
        if(numCAS == managementBody.getAvailableCASList().size() - 1){
            return casListForInstallOnManagementBodyWithInclusion;
        }

        casListForInstallOnManagementBodyWithoutInclusion = getCASListForWithoutInclusion(
                casListForInstallOnManagementBodyWithoutInclusion,
                managementBodyForAutomatization, numManagementBody, numCAS, budget);

        return getBestCASList(casListForInstallOnManagementBodyWithInclusion,
                casListForInstallOnManagementBodyWithoutInclusion);

    }

    private static boolean addCASForAllPreviousManagementBody(CASListForInstallOnManagementBody casListForInstallOnManagementBody,
                                                    ManagementBody managementBody){
        if(managementBody.getPreviousManagementBody() != null){
            ManagementBody previousManagementBody = managementBody.getPreviousManagementBody();
            while(previousManagementBody != null){
                if(!casListForInstallOnManagementBody.getManagementBodyCASHashMap().containsKey(previousManagementBody)){
                    CASForManagementBody casForManagementBody = getCheapCAS(previousManagementBody.getAvailableCASList());
                    if (casForManagementBody == null) {
                        return false;
                    }
                    casListForInstallOnManagementBody.addCAS(previousManagementBody, casForManagementBody.getCas());
                }
                previousManagementBody = previousManagementBody.getPreviousManagementBody();
            }
        }
        return true;
    }

    private static CASForManagementBody getCheapCAS(ArrayList<CASForManagementBody> casForManagementBodyList){
        if(casForManagementBodyList == null || casForManagementBodyList.size() == 0){
            return null;
        }
        CASForManagementBody cheapCasForManagementBody = casForManagementBodyList.get(0);
        for(CASForManagementBody casForManagementBody : casForManagementBodyList){
            if((casForManagementBody.getCas().getPrice() < cheapCasForManagementBody.getCas().getPrice()) ||
                    (casForManagementBody.getCas().getPrice().equals(cheapCasForManagementBody.getCas().getPrice()) &&
                            casForManagementBody.getEfficiency() > cheapCasForManagementBody.getEfficiency())){
                cheapCasForManagementBody = casForManagementBody;
            }
        }
        return cheapCasForManagementBody;
    }

    private static CASListForInstallOnManagementBody getCASListForWithInclusion(CASListForInstallOnManagementBody casList, CAS cas,
                                                                        ManagementBody managementBody,
                                                                        ArrayList<ManagementBody> managementBodyForAutomatization,
                                                                        int numManagementBody, int numCAS, double budget){
        double budgetWithInclusion = budget - cas.getPrice() - casList.getPrice();

        if(budgetWithInclusion >= 0){
            casList.addCAS(managementBody, cas);
            return recursion(managementBodyForAutomatization,
                    casList, numManagementBody + 1,
                    0, budget);
        }
        return null;
    }

    private static CASListForInstallOnManagementBody getCASListForWithoutInclusion(CASListForInstallOnManagementBody casList,
                                                                        ArrayList<ManagementBody> managementBodyForAutomatization,
                                                                        int numManagementBody, int numCAS, double budget){
        return recursion(managementBodyForAutomatization, casList, numManagementBody, numCAS + 1, budget);
    }

    private static CASListForInstallOnManagementBody getBestCASList(CASListForInstallOnManagementBody casList1,
                                                            CASListForInstallOnManagementBody casList2){
        if(casList1 == null){
            return casList2;
        }
        if(casList2 == null){
            return casList1;
        }

        if(casList1.getEfficiency() > casList2.getEfficiency()){
            return casList1;
        }
        if(casList1.getEfficiency() < casList2.getEfficiency()){
            return casList2;
        }
        if(casList1.getEfficiency().equals(casList2.getEfficiency())){
            if(casList1.getSumOfPriorityOfFunctionalTasks() >
                    casList2.getSumOfPriorityOfFunctionalTasks()){
                return casList1;
            } else{
                return casList2;
            }
        }
        return null;
    }
}
