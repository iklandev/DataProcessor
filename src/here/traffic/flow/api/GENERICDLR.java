package here.traffic.flow.api;

/**
 * This element is included if the RWS reference type is GENERIC_DLR. The
 * element is intended as a multi-purpose, self-defining format, basically a
 * string with two elements of metadata describing the string.
 * 
 * @author ivan
 *
 */
public class GENERICDLR {
	
	/**
	 * The value of the element is a string, interpreted according to the
	 * attributes below.
	 * Required.
	 */
	private String String;
	
	/**
	 * Type of the location reference. 
	 * Required.
	 */
	private String TY;
	
	/**
	 * Version of the type specification governing this location reference.
	 * Required.
	 */
	private String VER;

	public String getString() {
		return String;
	}

	public String getTY() {
		return TY;
	}

	public String getVER() {
		return VER;
	}
}
