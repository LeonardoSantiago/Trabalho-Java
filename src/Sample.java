import java.util.ArrayList;

public class Sample implements Comparable {
	private double attrs[];
	private Integer classNumber;
	//private Integer supposedClassNumber;
	private Integer foldNumber; 
	private RelationsByFold[] relationsByFold;
	public Sample(double attrs [], int classNumber){
		this.attrs = attrs;
		this.classNumber = classNumber;
				/*new double[attrNumber];*/
	}
	public double[] getAttrs() {
		return attrs;
	}
	public void setAttrs(double[] attrs) {
		this.attrs = attrs;
	}
	public Integer getClassNumber() {
		return classNumber;
	}
	public void setClassNumber(Integer classNumber) {
		this.classNumber = classNumber;
	}
	/*public Integer getSupposedClassNumber() {
		return supposedClassNumber;
	}
	public void setSupposedClassNumber(Integer supposedClassNumber) {
		this.supposedClassNumber = supposedClassNumber;
	}*/
	public Integer getFoldNumber() {
		return foldNumber;
	}
	public void setFoldNumber(Integer foldNumber) {
		this.foldNumber = foldNumber;
	}

	public RelationsByFold[] getRelationsByFold() {
		return relationsByFold;
	}
	public void setRelationsByFold(RelationsByFold[] relationsByFold) {
		this.relationsByFold = relationsByFold;
	}
	
	public int compareTo(Object sample) {
		if(this.classNumber < ((Sample) sample).getClassNumber()){
			return -1;
		}
		if(this.classNumber > ((Sample) sample).getClassNumber()){
			return 1;
		}
		return 0;
	}
}
