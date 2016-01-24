package net.fakult.triviatour;

import android.location.Location;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by jfakult on 1/23/16.
 */
public class TriviaPackage implements Serializable
{
	//Should we have ratings too?

	private String name;
	private String description;
	private String image;
	private int difficulty; //1-5
	private int numStages; //-1 means secret
	private ArrayList<Location> locations;
	private ArrayList<String> stageNames;
	private ArrayList<String> stageDescriptions;
	private int id;
	private String author;
	private long creationDate;
	private boolean hideLocations;
	private int stagesCompleted;
	private int numDownloads;

	public TriviaPackage()
	{
		name = "No name";
		description = "No description given";
		image = "No image";
		stagesCompleted = 0;
		numStages = 0;
	}

	public TriviaPackage(String newName, String newDescription, String newAuthor, long newCreationDate, int newDifficulty, int newNumStages, int newId, int newNumDownloads, boolean newHideLocations)
	{
		super();

		name = newName;
		description = newDescription;
		author = newAuthor;
		creationDate = newCreationDate;
		difficulty = newDifficulty;
		numStages = newNumStages;
		id = newId;
		hideLocations = newHideLocations;
		numDownloads = newNumDownloads;
	}

	public String getName()
	{
		return name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setImage(String newImage)
	{
		image = newImage;
	}

	public void setStages(ArrayList<Location> newLocations, ArrayList<String> newStageNames, ArrayList<String> newStageDescs)
	{
		locations = newLocations;
		stageNames = newStageNames;
		stageDescriptions = newStageDescs;
	}
}
