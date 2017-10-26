import java.util.List;

public class Result {
	
	private boolean[] variables;
	private List<int[]> clauses;
	
	public boolean[] getVariables() {
		return variables;
	}

	public void setVariables(boolean[] variables) {
		this.variables = variables;
	}

	public Result(boolean[] variables, List<int[]> clauses){
		this.variables = variables;
		this.clauses = clauses;
	}

	public List<int[]> getClauses() {
		return clauses;
	}

	public void setClauses(List<int[]> clauses) {
		this.clauses = clauses;
	}
}
