package actions.brules;

import services.RuleService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class Generate extends ActionSupport {
	
	private String generatedCode;
	private int id;

	public String execute(){
		RuleService RS = new RuleService();
		generatedCode = RS.Generate(id);
		return SUCCESS;
	}

	public void validate(){
	}

	public String getGeneratedCode() {
		return generatedCode;
	}

	public void setGeneratedCode(String generatedCode) {
		this.generatedCode = generatedCode;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
}
