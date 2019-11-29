package de.adorsys.xs2a.adapter.service.util.adjuster;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ParamAdjustingResultHolder {
    private Map<String, String> parametersMap;
    private Set<String> missingParameters;

    public ParamAdjustingResultHolder() {
        this.parametersMap = new HashMap<>();
        this.missingParameters = new HashSet<>();
    }

    public Map<String, String> getParametersMap() {
        return parametersMap;
    }

    public Set<String> getMissingParameters() {
        return missingParameters;
    }

    public boolean containsMissingParams() {
        return !missingParameters.isEmpty();
    }

    public void addAdjustedParam(String paramName, String paramValue) {
        parametersMap.put(paramName, paramValue);
    }

    public void addMissingParam(String paramName) {
        missingParameters.add(paramName);
    }
}
