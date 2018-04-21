
public class SampleRelation implements Comparable {
	private Sample target;
	private double distance;
	
	public Sample getTarget() {
		return target;
	}
	public void setTarget(Sample target) {
		this.target = target;
	}
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}
	
	public int compareTo(Object relation) {
		if(this.distance < ((SampleRelation) relation).getDistance()){
			return -1;
		}
		if(this.distance > ((SampleRelation) relation).getDistance()){
			return 1;
		}
		return 0;
	}
}
