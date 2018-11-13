package ch.supsi.omega.tracking.parameters;

/**
 * Abstract class, to be overridden when writing SPT information to file or DB.
 * @author galliva
 */
public abstract class SPTInformationWriter
{
	public abstract void initWriter();

	public abstract String writeInformation(SPTExecutionInfoHandler executionInfoHandler);

	public abstract void closeWriter();
}
