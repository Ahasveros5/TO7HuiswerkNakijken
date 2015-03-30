package actions.brules;

import services.RuleService;
import services.TargetService;

import com.opensymphony.xwork2.ActionSupport;

@SuppressWarnings("serial")
public class Push extends ActionSupport {
	
	private String generatedCode;
	private int id;

	public String execute(){
		RuleService RS = new RuleService();
		generatedCode = RS.Generate(id);
		System.out.println(generatedCode);
		TargetService ts = new TargetService();
		ts.PushToTarget(generatedCode);
		
		return SUCCESS;
	}
//create or replace trigger {trigger}<br/> before insert or update on {table}#1<br/> for each row<br/> begin<br/> if {column}#1 {operator}#1 {comperator}#1 and {comperator}#2 then<br/> SEQ_PASNUMMER.nextval into :new.PASNUMMER from dual;<br/> end if;<br/> end;
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
