package telran.net.common;

public interface Protocol {
	ProtocolResponse getResponse(ProtocolRequest request);
}