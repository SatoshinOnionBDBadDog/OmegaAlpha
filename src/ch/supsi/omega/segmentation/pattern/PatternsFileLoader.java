package ch.supsi.omega.segmentation.pattern;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import com.galliva.gallibrary.GLogManager;

public class PatternsFileLoader extends PatternsLoader
{
	private String			workingDirectory	= null;
	private String			filesExtension		= null;

	private List<String>	files					= new ArrayList<String>();

	public String getWorkingDirectory()
	{
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory)
	{
		this.workingDirectory = workingDirectory;
	}

	public String getFilesExtension()
	{
		return filesExtension;
	}

	public void setFilesExtension(String filesExtension)
	{
		this.filesExtension = filesExtension;
	}

	public PatternsFileLoader()
	{
	}

	public PatternsFileLoader(String path, String extension)
	{
		this.workingDirectory = path;
		this.filesExtension = extension;
	}

	/**
	 * Loads all the Patterns in the workingDirectory directory.
	 */
	@Override
	public void loadPatterns()
	{
		getFiles();

		for (String file : files)
		{
			Pattern pattern = processFile(workingDirectory + file);

			if (pattern != null)
				patterns.add(pattern);
		}
	}

	/**
	 * Gets all the files in the working directory.
	 */
	private void getFiles()
	{
		try
		{
			File dir = new File(workingDirectory);

			FilenameFilter filter = new FilenameFilter()
			{
				public boolean accept(File dir, String name)
				{
					return name.endsWith(filesExtension);
				}
			};

			String[] children = dir.list(filter);

			if (children == null)
			{
				// nothing we can do
			}
			else
			{
				for (int i = 0; i < children.length; i++)
				{
					files.add(children[i]);
				}
			}

		}
		catch (Exception e)
		{
			GLogManager.log("error loading patterns: " + e.toString(), Level.SEVERE);
			// nothing we can do, files.size() will be 0 and no patterns will be loaded
		}
	}

	/**
	 * Process a Pattern file.
	 * @param file the file to be processed
	 * @return a Pattern object
	 */
	private Pattern processFile(String file)
	{
		Pattern pattern = null;

		try
		{
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));

			String temp;

			// name of the Pattern
			String name = br.readLine().trim();
			// id of the Pattern
			temp = br.readLine();
			int id = Integer.parseInt(temp.trim());
			// window widht of the Pattern
			temp = br.readLine();
			int ww = Integer.parseInt(temp.trim());
			// features of the Pattern
			temp = br.readLine();
			int fe = Integer.parseInt(temp.trim());

			pattern = new Pattern(id, name, ww, fe, patternsColor[id - 1]);

			in.close();

			return pattern;
		}
		catch (Exception e)
		{
			GLogManager.log("pattern skipped: " + e.toString(), Level.WARNING);
			return null;
		}
	}
}
