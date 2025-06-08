package ch.supsi.omega.openbis;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.io.IOUtils;

import ch.systemsx.cisd.base.exceptions.CheckedExceptionTunnel;

/**
 * Helper class for retrieving and locally storing SSL certificates from a server.
 * @author Chandrasekhar Ramakrishnan
 */
public class SslCertificateHelper
{
	private final String	serviceURL;

	private final File	keystoreFile;

	private final String	certificateEntryName;

	/**
	 * Create a helper that retrieves a certificate from the serviceURL, and stores it in a keystore file with the name "keystore" in the configDirectory.
	 * @param serviceURL The URL to retrieve the certificate from.
	 * @param keystoreFile The file where to store the certificate in.
	 * @param certificateEntryName The name in the keystore the certificate is stored under.
	 */
	public SslCertificateHelper(String serviceURL, File keystoreFile, String certificateEntryName)
	{
		this.serviceURL 			  = serviceURL;
		this.keystoreFile 		  = keystoreFile;
		this.certificateEntryName = certificateEntryName;
	}

	public void setUpKeyStore()
	{
		if (serviceURL.startsWith("https"))
		{
			Certificate[] certificates = getServerCertificate();
			KeyStore keyStore;
			try
			{
				keyStore = KeyStore.getInstance("JKS");
				keyStore.load(null, null);
				for (int i = 0; i < certificates.length; i++)
				{
					keyStore.setCertificateEntry(certificateEntryName + i, certificates[i]);
				}
			}
			catch (Exception ex)
			{
				throw CheckedExceptionTunnel.wrapIfNecessary(ex);
			}
			FileOutputStream fileOutputStream = null;
			try
			{
				fileOutputStream = new FileOutputStream(keystoreFile);
				keyStore.store(fileOutputStream, "changeit".toCharArray());
				fileOutputStream.close();
				System.setProperty("javax.net.ssl.trustStore", keystoreFile.getAbsolutePath());
			}
			catch (Exception ex)
			{
				throw CheckedExceptionTunnel.wrapIfNecessary(ex);
			}
			finally
			{
				IOUtils.closeQuietly(fileOutputStream);
			}
		}
	}

	private Certificate[] getServerCertificate()
	{
		workAroundABugInJava6();

		// Create a trust manager that does not validate certificate chains
		setUpAllAcceptingTrustManager();
		SSLSocket socket = null;
		try
		{
			URL url = new URL(serviceURL);
                        int port = url.getPort();
                        if (port == -1)
                        {
                                port = 443; // standard port for https
                        }
			String hostname = url.getHost();
			SSLSocketFactory factory = HttpsURLConnection.getDefaultSSLSocketFactory();
			socket = (SSLSocket) factory.createSocket(hostname, port);
			socket.startHandshake();
			return socket.getSession().getPeerCertificates();
		}
		catch (Exception e)
		{
			throw CheckedExceptionTunnel.wrapIfNecessary(e);
		}
		finally
		{
			if (socket != null)
			{
				try
				{
					socket.close();
				}
				catch (IOException ex)
				{
					// ignored
				}
			}
		}
	}

	private void setUpAllAcceptingTrustManager()
	{
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager()
		{
			public java.security.cert.X509Certificate[] getAcceptedIssuers()
			{
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
			{
			}
		} };
		// Install the all-trusting trust manager
		try
		{
			SSLContext sc = SSLContext.getInstance("SSL");
			sc.init(null, trustAllCerts, new java.security.SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		}
		catch (Exception e)
		{
		}
	}

	// WORKAROUND: see comment submitted on 31-JAN-2008 for
	// http://bugs.sun.com/bugdatabase/view_bug.do?bug_id=6514454
	private void workAroundABugInJava6()
	{
		try
		{
			SSLContext.getInstance("SSL").createSSLEngine();
		}
		catch (Exception ex)
		{
			// Ignore this one.
		}
	}

	public static void trustAnyCertificate(String url)
	{
		if (url.startsWith("https://"))
		{
			try
			{
				File tempKeyStore = File.createTempFile("cert", "keystore");
				tempKeyStore.deleteOnExit();
				SslCertificateHelper helper = new SslCertificateHelper(url, tempKeyStore, "cert");
				helper.setUpKeyStore();
			}
			catch (IOException ioex)
			{
				throw CheckedExceptionTunnel.wrapIfNecessary(ioex);
			}
		}
	}
}