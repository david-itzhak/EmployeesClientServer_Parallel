package telran.net.common;

import java.io.Serializable;

public class ProtocolRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	public String requestType;
	public Serializable requestData;

	public ProtocolRequest(String requestType, Serializable requestData) {
		this.requestType = requestType;
		this.requestData = requestData;
	}
}
