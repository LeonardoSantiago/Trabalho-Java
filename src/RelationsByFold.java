import java.util.ArrayList;
import java.util.List;

public class RelationsByFold {
	private int targetFold;
	private List<SampleRelation> sampleRelationList = new ArrayList();
	

	public int getTargetFold() {
		return targetFold;
	}

	public void setTargetFold(int targetFold) {
		this.targetFold = targetFold;
	}
	
	public List<SampleRelation> getSampleRelationList() {
		return sampleRelationList;
	}

	public void setSampleRelationList(List<SampleRelation> sampleRelationList) {
		this.sampleRelationList = sampleRelationList;
	}
}
