package utils;

public class Action{
	
	// the name of the action (prism)
	String action_name;
	// the var name to update
	String update_var_name;
	// the new value to set to variable
	String update_value;
	// enable the action, initially 0
	Boolean enable;
	// num transition
	String json_update;
	
	
	public Action(String action_name, String update_var_name, String update_value) {
		this.action_name = action_name;
		this.update_var_name = update_var_name;
		this.update_value = update_value;
		this.enable = false;
		this.json_update = "";
	}
	
	public void setEnable(Boolean x){
		this.enable = x;
	}
	
	public Boolean isEnable() {
		return this.enable;
	}
	
	public String getActionName() {
		return action_name;
	}
	
	public String getUpdateVarName() {
		return update_var_name;
	}
	
	public void setUpdateVarName(String var_name) {
		this.update_var_name = var_name;
	}
	
	public void appendUpdateVarName(String v) {
		if(this.update_var_name == "") {
			this.update_var_name =  v;
			
		}else {
			this.update_var_name += ';' + v;
		}
		
	}
	
	public String getUpdateValue() {
		return update_value;
	}
	
	public void setUpdateValue(String update_value) {
		this.update_value = update_value;
	}
	
	public void appendUpdateValue(String v) {
		if(this.update_value == "") {
			this.update_value = v;
		}else {
			this.update_value += ';' + v;
		}
		
	}
	
	public String getJsonUpdate() {
		return json_update;
	}
	
	public void setJsonUpdate(String json) {
		this.json_update = json;
	}
}