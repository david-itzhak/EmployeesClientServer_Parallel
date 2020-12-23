package telran.net.common;

import java.io.Serializable;

public class ProtocolResponse implements Serializable {
	private static final long serialVersionUID = 1L;
	public ResponseCode code;
	public Serializable responseData;

	public ProtocolResponse(ResponseCode code, Serializable responseData) {
		this.code = code;
		this.responseData = responseData;
	}
}
