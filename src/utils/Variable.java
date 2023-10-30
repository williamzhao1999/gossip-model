package utils;

public 	// Class that store information about the state, like transitions, probability distributions...
class Variable{
	
	// var name
	private String var_name;
	// type of variable
	private String var_type;
	// lower bound of int
	private int lower_bound;
	private int upper_bound;
	private int initial_value;
	
	// current value
	private int value;
	
	
	public Variable(String var_name, String var_type, int lower_bound,
			int upper_bound, int initial_value)
	{
		this.var_name = var_name;
		this.var_type = var_type;
		this.lower_bound = lower_bound;
		this.upper_bound = upper_bound;
		this.initial_value = initial_value;
	}
	
	// get the current value of variable
	public int getCurrentValue() {
		return value;
	}
	
	public void setCurrentValue(int v) {
		if(v > upper_bound) {
			this.value = this.upper_bound;
		}else {
			this.value = v;
		}
		
	}
	
	public String getVarName() {
		return var_name;
	}
	
	public String getVarType() {
		return var_type;
	}
	
	public int getLowerBound() {
		return lower_bound;
	}
	
	public int getUpperBound() {
		return upper_bound;
	}
	
	public int getInitialValue() {
		return initial_value;
	}
	
	
	
	

}
