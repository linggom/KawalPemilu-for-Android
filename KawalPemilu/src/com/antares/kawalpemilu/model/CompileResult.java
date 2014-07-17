package com.antares.kawalpemilu.model;

public class CompileResult{


	public static int toInt(String a){
		try {
			return Integer.parseInt(a);
		} catch (Exception e) {
			return 0; //data not valid
		}
	}

	public CompileResult(String [] r) {
		setId(toInt(r[0]));
		setNames(r[1]);
		setResultCandidate1(toInt(r[2]));
		setResultCandidate2(toInt(r[3]));
		setValidCount(toInt(r[4]));
		setInvalidCount(toInt(r[5]));
		setErrorTPS(toInt(r[6]));
		setEnteredCount(toInt(r[7]));
		setPending(toInt(r[8]));
		setTotal(toInt(r[9]));
	}

	private Integer id;
	private String names;
	private Integer resultCandidate1;
	private Integer resultCandidate2;
	private Integer validCount;
	private Integer invalidCount;
	private Integer errorCount;
	private Integer enteredCount;
	private Integer pending;
	private Integer total;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNames() {
		return names;
	}

	public void setNames(String names) {
		this.names = names;
	}

	public Integer getResultCandidate1() {
		return resultCandidate1;
	}

	public void setResultCandidate1(Integer resultCandidate1) {
		this.resultCandidate1 = resultCandidate1;
	}

	public Integer getResultCandidate2() {
		return resultCandidate2;
	}

	public void setResultCandidate2(Integer resultCandidate2) {
		this.resultCandidate2 = resultCandidate2;
	}

	public Integer getValidCount() {
		return validCount;
	}

	public void setValidCount(Integer validCount) {
		this.validCount = validCount;
	}

	public Integer getInvalidCount() {
		return invalidCount;
	}

	public void setInvalidCount(Integer invalidCount) {
		this.invalidCount = invalidCount;
	}

	public Integer getErrorTPS() {
		return errorCount;
	}

	public void setErrorTPS(Integer errorTPS) {
		this.errorCount = errorTPS;
	}

	public Integer getEnteredCount() {
		return enteredCount;
	}

	public void setEnteredCount(Integer enteredCount) {
		this.enteredCount = enteredCount;
	}

	public Integer getPending() {
		return pending;
	}

	public void setPending(Integer pending) {
		this.pending = pending;
	}

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

}
