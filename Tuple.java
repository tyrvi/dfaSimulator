public class Tuple {
	private String x;
	private String y;

	public Tuple(String x, String y) {
		this.x = x;
		this.y = y;
	}

	public String getValue(int i) {
		if (i == 0)	return this.x;
		else if (i == 1) return this.y;
		else return null;
	}
}
