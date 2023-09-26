package com.surveyking.apigateway.authentication.constants;

import org.springframework.stereotype.Component;

@Component("Privilege")
public final class PrivilegeConstants {
    public static final String PROJECT_INFO = "Project Info";
    public static final String PROJECT_CREATE = "Project Create";
    public static final String PROJECT_DELETE = "Project Delete";
    public static final String PROJECT_UPDATE = "Project Update";
    public static final String UPLOAD_SCRIPT = "Upload Script";
    public static final String CHECK_SCRIPT = "Check Script";
    public static final String INTERVIEWER_ASSIGNMENT = "Interviewer Assignment";
    public static final String INTERVIEWER_INFO = "Interviewer Info";
    public static final String INTERVIEWER_CREATE = "Interviewer Create";
    public static final String INTERVIEWER_DELETE = "Interviewer Delete";
    public static final String INTERVIEWER_UPDATE = "Interviewer Update";
    public static final String FIELD_STATUS = "Field Status";
    public static final String DASHBOARD = "Dashboard";
    public static final String DATA_BY_RESPONDENT = "Data by Respondent";
    public static final String QUALITY_CONTROL = "Quality Control";
    public static final String QC_REPORT = "QC Report";
    public static final String COMMERCIAL_REPORT = "Commercial Report";
    public static final String DATA_DOWNLOAD = "Data download";
    public static final String TELEPHONIC_INTERVIEW = "Telephonic Interview";

    private PrivilegeConstants(){

    }
}
