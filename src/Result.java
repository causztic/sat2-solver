
public class Result {
	
	private boolean[] variables;
	private String[] clauses;
	
	public boolean[] getVariables() {
		return variables;
	}

	public void setVariables(boolean[] variables) {
		this.variables = variables;
	}

	public String[] getClauses() {
		return clauses;
	}

	public void setClauses(String[] clauses) {
		this.clauses = clauses;
	}

	public Result(boolean[] variables, String[] clauses){
		this.variables = variables;
		this.clauses = clauses;
	}
}
