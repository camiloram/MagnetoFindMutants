package co.com.magneto.magnetofindmutants.business;

import co.com.magneto.magnetofindmutants.business.exception.BusinessException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ejb.Stateless;

/**
 * Clase negocio que contiene la logica del servicio
 * @author Camilo Andres Ramirez Pava
 * @versión 1.0
 */
@Stateless
public class BusinessService implements IBusiness {

    private int numberSequencesMutant;
    private String[] validSequences = {"AAAA", "TTTT", "CCCC", "GGGG"};
    private String[] dnaBusiness;
    private List<RegistryDna> dataBaseDna = new ArrayList<>();
    
    /**
     * Metodo que determina si el request de la operacion mutant
     * corresponde a un mutante o un humano
     * @param dnaRequest
     * @return boolean
     * @throws BusinessException 
     */
    @Override
    public boolean isMutant(DnaRequest dnaRequest) throws BusinessException {
        numberSequencesMutant = 2;
        dnaBusiness = dnaRequest.getDna();
        verticalHorizontalValidation();
        obliqueValidation();
        registrarBD(dnaRequest);
        return numberSequencesMutant <= 0;
    }
    
    /**
     * Metodo que busca las secuencias de forma horizontal y vertical
     */
    public void verticalHorizontalValidation() {
        int index = 0;
        for (String dnaRowRequest : dnaBusiness) {
            
            if(containsValidSequences(dnaRowRequest)){
                numberSequencesMutant--;
            }
            
            int index_V = 0;
            String DnaRequest_V = "";
            for (int j = 0; j <= dnaBusiness.length - 1; j++) {
                DnaRequest_V += dnaBusiness[index_V++].charAt(index);
            }
            
            if(containsValidSequences(DnaRequest_V)){
                numberSequencesMutant--;
            }
            index++;
        }
    }

    /**
     * Metodo que busca las secuencias en un String
     * @param dnaRowRequest
     * @return boolean
     */
    public boolean containsValidSequences(String dnaRowRequest) {
        //testValidatePrint(dnaRowRequest);
        return Arrays.stream(validSequences).anyMatch(dnaRowRequest::contains);
    }
    
    /**
     * Metodo que busca las secuencias de forma oblicua o diagonal
     */
    public void obliqueValidation() {
        int referenceSearch = dnaBusiness.length - validSequences.length;

        for (int i = referenceSearch; i >= 0; i--) {
            String dnaRowRequest_Do_Up = "";
            for (int j = 0; j < dnaBusiness.length - i; j++) {
                dnaRowRequest_Do_Up += dnaBusiness[i + j].charAt(j);
            }

            if(containsValidSequences(dnaRowRequest_Do_Up)){
                numberSequencesMutant--;
            }
        }
        
        for (int i = 1; i <= referenceSearch; i++) {
            String dnaRowRequest_Do_Up = "";
            for (int j = 0; j < dnaBusiness.length - i; j++) {
                dnaRowRequest_Do_Up += dnaBusiness[j].charAt(i + j);
            }

            if(containsValidSequences(dnaRowRequest_Do_Up)){
                numberSequencesMutant--;
            }
        }
        
        for (int i = referenceSearch + 1; i < dnaBusiness.length; i++) {
            String dnaRowRequest_Up_Do = "";
            for (int j = 0; j <= i; j++) {
                dnaRowRequest_Up_Do += dnaBusiness[i - j].charAt(j);
            }

            if(containsValidSequences(dnaRowRequest_Up_Do)){
                numberSequencesMutant--;
            }
        }

        for (int i = 0; i < referenceSearch; i++) {
            String dnaRowRequest_Up_Do = "";
            for (int j = i + 1; j < dnaBusiness.length; j++) {
                dnaRowRequest_Up_Do += dnaBusiness[dnaBusiness.length - j + i].charAt(j);
            }

            if(containsValidSequences(dnaRowRequest_Up_Do)){
                numberSequencesMutant--;
            }
        }
    }

    /**
     * metodo de control
     * @param dnaRequest 
     */
    private void testValidatePrint(String dnaRequest) {
        System.out.println("base validate - " + dnaRequest);
        if(Arrays.stream(validSequences).anyMatch(dnaRequest::contains))
            System.out.println("-- base find - " + dnaRequest);
    }

    private void registrarBD(DnaRequest dnaRequest) {
        RegistryDna registryDna = new RegistryDna(dnaRequest, numberSequencesMutant <= 0);
        dataBaseDna.add(registryDna);
        
        System.err.println("  ---------  " + dataBaseDna.size());
        
        for (RegistryDna registryDna1 : dataBaseDna) {
            System.err.println("  -  " + registryDna1.isIsMutant() + "  -  " + registryDna1.getDnaRequest().getDna());
            for (String string : registryDna1.getDnaRequest().getDna()) {
                //System.err.print("  -  " + string);
            }
            System.err.println("  ---------  ");
        }
    }
    
    @Override
    public StatsResponse getStat() throws BusinessException {
        StatsResponse statsResponse = new StatsResponse();
        for (RegistryDna registryDna : dataBaseDna) {
            if(registryDna.isIsMutant()){
                statsResponse.setCount_mutant_dna(statsResponse.getCount_mutant_dna() + 1);
            } else {
                statsResponse.setCount_human_dna(statsResponse.getCount_human_dna() + 1);
            }
        }
        long humanCount = statsResponse.getCount_human_dna();
        long mutantCount = statsResponse.getCount_mutant_dna();
        
        if(mutantCount == humanCount){
            statsResponse.setRatio(50.0);
            return statsResponse;
        } else if(mutantCount == 0 || humanCount == 0){
            statsResponse.setRatio(100.0);
            return statsResponse;
        } else if(humanCount < mutantCount){
            statsResponse.setRatio(new Double((double)humanCount / (double)mutantCount));
        } else if(humanCount > mutantCount){
            statsResponse.setRatio(new Double((double)mutantCount / (double)humanCount));
        }
        
        return statsResponse;
    }
}
