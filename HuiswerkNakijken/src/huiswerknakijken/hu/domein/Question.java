package huiswerknakijken.hu.domein;

import huiswerknakijken.hu.domein.QuestionTypes.*;


public class Question 
{
	
	protected int nr;
	protected String description;
	private MultipleChoice MC;
	private OpenQuestion OQ;
	private Picture pic;
	private ShortWord SW;
	
	public Question(){
		
	}
	
	public int getNr() {
		return nr;
	}
	public void setNr(int nr) {
		this.nr = nr;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public MultipleChoice getMC() {
		return MC;
	}
	public void setMC(MultipleChoice mC) {
		MC = mC;
	}
	public OpenQuestion getOQ() {
		return OQ;
	}
	public void setOQ(OpenQuestion oQ) {
		OQ = oQ;
	}
	public Picture getPic() {
		return pic;
	}
	public void setPic(Picture pic) {
		this.pic = pic;
	}
	public ShortWord getSW() {
		return SW;
	}
	public void setSW(ShortWord sW) {
		SW = sW;
	}
}
