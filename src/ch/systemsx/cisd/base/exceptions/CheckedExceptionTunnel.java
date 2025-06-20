package ch.systemsx.cisd.base.exceptions;

public final class CheckedExceptionTunnel
{
    private CheckedExceptionTunnel(){}

    public static RuntimeException wrapIfNecessary(Exception ex)
    {
        if (ex instanceof RuntimeException)
        {
            return (RuntimeException) ex;
        }
        return new RuntimeException(ex);
    }
}
