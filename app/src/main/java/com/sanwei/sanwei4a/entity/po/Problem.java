package com.sanwei.sanwei4a.entity.po;

public class Problem {
    private Integer problemId;

    private String problemQq;

    private String problemPhone;

    private Integer problemAccid;

    private Boolean problemIsfix;

    /**
     * 此参数不能为空 其他皆可为空
     */
    private String problemDescribe;

    public Integer getProblemId() {
        return problemId;
    }

    public void setProblemId(Integer problemId) {
        this.problemId = problemId;
    }

    public String getProblemQq() {
        return problemQq;
    }

    public void setProblemQq(String problemQq) {
        this.problemQq = problemQq;
    }

    public String getProblemPhone() {
        return problemPhone;
    }

    public void setProblemPhone(String problemPhone) {
        this.problemPhone = problemPhone;
    }

    public Integer getProblemAccid() {
        return problemAccid;
    }

    public void setProblemAccid(Integer problemAccid) {
        this.problemAccid = problemAccid;
    }

    public Boolean getProblemIsfix() {
        return problemIsfix;
    }

    public void setProblemIsfix(Boolean problemIsfix) {
        this.problemIsfix = problemIsfix;
    }

    public String getProblemDescribe() {
        return problemDescribe;
    }

    public void setProblemDescribe(String problemDescribe) {
        this.problemDescribe = problemDescribe;
    }
}
