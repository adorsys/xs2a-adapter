package de.adorsys.xs2a.adapter.crealogix.model;

import java.util.List;
import java.util.Objects;

/**
 * custom DKB model, that the bank returns for 'Login / Credentials validation' request
 * i.e. POST /psd2-auth/v1/auth/token
 */
public class CrealogixValidationResponse {
    private CrealogixReturnCode returnCode;

    private CrealogixActionCode actionCode;

    private String returnMsg;

    private String lastSuccessfulLoginTimestamp;

    private String authOptionIdLastUsed;

    private CrealogixAuthType authTypeSelected;

    private String authOptionIdSelected;

    private String challenge;

    private Integer timeout;

    private List<CrealogixAuthItem> authSelectionList;

    private String accessToken;

    public CrealogixReturnCode getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(CrealogixReturnCode returnCode) {
        this.returnCode = returnCode;
    }

    public CrealogixActionCode getActionCode() {
        return actionCode;
    }

    public void setActionCode(CrealogixActionCode actionCode) {
        this.actionCode = actionCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getLastSuccessfulLoginTimestamp() {
        return lastSuccessfulLoginTimestamp;
    }

    public void setLastSuccessfulLoginTimestamp(String lastSuccessfulLoginTimestamp) {
        this.lastSuccessfulLoginTimestamp = lastSuccessfulLoginTimestamp;
    }

    public String getAuthOptionIdLastUsed() {
        return authOptionIdLastUsed;
    }

    public void setAuthOptionIdLastUsed(String authOptionIdLastUsed) {
        this.authOptionIdLastUsed = authOptionIdLastUsed;
    }

    public CrealogixAuthType getAuthTypeSelected() {
        return authTypeSelected;
    }

    public void setAuthTypeSelected(CrealogixAuthType authTypeSelected) {
        this.authTypeSelected = authTypeSelected;
    }

    public String getAuthOptionIdSelected() {
        return authOptionIdSelected;
    }

    public void setAuthOptionIdSelected(String authOptionIdSelected) {
        this.authOptionIdSelected = authOptionIdSelected;
    }

    public String getChallenge() {
        return challenge;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public Integer getTimeout() {
        return timeout;
    }

    public void setTimeout(Integer timeout) {
        this.timeout = timeout;
    }

    public List<CrealogixAuthItem> getAuthSelectionList() {
        return authSelectionList;
    }

    public void setAuthSelectionList(List<CrealogixAuthItem> authSelectionList) {
        this.authSelectionList = authSelectionList;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrealogixValidationResponse that = (CrealogixValidationResponse) o;
        return Objects.equals(returnCode, that.returnCode) &&
            Objects.equals(actionCode, that.actionCode) &&
            Objects.equals(returnMsg, that.returnMsg) &&
            Objects.equals(lastSuccessfulLoginTimestamp, that.lastSuccessfulLoginTimestamp) &&
            Objects.equals(authOptionIdLastUsed, that.authOptionIdLastUsed) &&
            Objects.equals(authTypeSelected, that.authTypeSelected) &&
            Objects.equals(authOptionIdSelected, that.authOptionIdSelected) &&
            Objects.equals(challenge, that.challenge) &&
            Objects.equals(timeout, that.timeout) &&
            Objects.equals(authSelectionList, that.authSelectionList) &&
            Objects.equals(accessToken, that.accessToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(returnCode, actionCode, returnMsg, lastSuccessfulLoginTimestamp, authOptionIdLastUsed, authTypeSelected, authOptionIdSelected, challenge, timeout, authSelectionList, accessToken);
    }
}
