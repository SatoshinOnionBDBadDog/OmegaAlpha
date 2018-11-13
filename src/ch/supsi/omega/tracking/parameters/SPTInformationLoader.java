package ch.supsi.omega.tracking.parameters;

public abstract class SPTInformationLoader
{
	protected SPTExecutionInfoHandler	executionInfoHandler	= null;

	public abstract void initLoader();

	public abstract SPTExecutionInfoHandler loadInformation();

	public abstract void closeLoader();
}
