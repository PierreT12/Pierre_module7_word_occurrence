/** 
@author Trevor Pierre
//Date:  6/21/2020
@version Pierre_module7_word_occurrence
@param Making a GUI Version of Text Analyzer
 * 
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class Pierre_module7_word_occurrence extends Application
{
	 /**
	* Declaring all of the variables that will be used including the file that will be read.
	* Along with that, a number of List and Maps are created to handle the export from
	* the stream. 
	 * 
	 */
	static Map<String, Integer> textTest = new LinkedHashMap<String, Integer>();
	ArrayList<String> wordOcc = new ArrayList<String>();
	static ArrayList<Integer> numbOcc= new ArrayList<Integer>();
	HashMap<String, Integer> freq = new HashMap<String, Integer>();
	ArrayList<String> freq2 = new ArrayList<String>();
	

	public static void main(String[] args) 
	{
		/**
		 * Launches the GUI
		 */
		launch(args);
	}
	
		
	


	/**
	 * 
	 * @param freq2 
	 * An ArrayList with all of the words without the occurrences set yet
	 * @param freq
	 * The HashMap that will have the words and their correct occurrences 
	
	 * This method loops through an ArrayList with all of the words taken from the play
	 * and puts them in a HashMap, the key being the word, and the value being the 
	 * Occurrences.
	 */
	public void addToHM(ArrayList<String> freq2, HashMap<String, Integer> freq)
	{
		
		
		for(int i = 0; i < freq2.size();)
		{
			/**
			 * Checks to see if the word exists in the HashMap already
			 */
			if(freq.containsKey(freq2.get(i)))
			{
				/**
				 * If it does n is created and adds 1 to the value
				 */
				int n = freq.get(freq2.get(i));
				freq.put(freq2.get(i), ++n);

				i++;
				
			}
			else
			{
				/**
				 * Otherwise a new entry is created with the value of 1
				 */
				freq.put(freq2.get(i), 1);
				i++;
			}
			
		
			
		}
		
		

		
	}
	
	
	/**
	 * 
	 * @param <K>
	 * @param <V>
	 * @param freq
	 * The unsorted HashMap
	 * @param textTest
	 *  Contains the sorted HashMap
	 * @param wordOcc
	 *  Contains all the sorted words/keys
	 * @param numbOcc
	 *  Contains all the sorted values
	 * @return 
	 * returns a HashMap 
	
	 * Uses a java stream to process the data from the frequency HashMap, because it is
	 * based off of collections, the Map can be sorted, limited, and then pushed out to 
	 * a LinkedHashMap so that the order is kept
	 */
	
	 
	public <K, V> Map<String, Integer> sort20(HashMap<String, Integer> freq, Map<String, Integer> textTest,
			ArrayList<String> wordOcc, ArrayList<Integer> numbOcc)
	{
		
		/**
		 * The stream that does all of the sorting and limiting
		 */
		Map<String, Integer> owo = freq.entrySet() 
		 .stream()
		 .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
		 .limit(20)
		 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, LinkedHashMap::new));
		/**
		 * Puts the sorted collection into a few different Lists and Maps
		 * @return
		 */
		textTest.putAll(owo);
		wordOcc.addAll(owo.keySet()); 
		numbOcc.addAll(owo.values());
		return owo;
		
		
		
		
		
	}
	
	
	/*
	 * The method that sets up the GUI
	 */
	public void start(Stage stage) throws Exception 
	{
		/////////////////////////////////////////////////////////////////
		///////////////Code that gathers all the words and stuff//////////////
		////////////////////////////////////////////////////////////////
		
			/**
			 * Declaring all of the variables for reading the file
			 * 
			 */
				File playfile = new File("playfile.txt");
				Scanner input = new Scanner(playfile);
				Scanner userinput = new Scanner(System.in);
				/**
				 * A while loop that reads the play text file
				 */
				while(input.hasNextLine())
				{
					//This makes all words lowecase and removes punctuation so duplicate entries aren't created
					String words = input.nextLine().replaceAll("[^A-Za-z]+", "").toLowerCase();
					freq2.add(words);
				}
			

				/**
				 * Runs the two major methods that get the program working
				 */
				addToHM(freq2, freq);
				sort20(freq, textTest, wordOcc, numbOcc );
				
				
		
		///////////////////////////////////////////////////////////////
		///////Javafx UI Making/////////////////////////////////////
		/////////////////////////////////////////////////////////////
		/**
		 * Setting up how the list will appear.
		 */
		Text topText = new Text("The Top 20 Word by Ocurrences");
		Label wordLabel = new Label();
		//Converting hashmap to a more readable format
		String test = textTest.toString();
		test =  test.replace("=", ": ");
		test = test.replace("{", "");
		test = test.replace("}", "");
		test = test.replace(",", System.getProperty("line.separator"));
		
		wordLabel.setText(test);
		//Setting font type and size
		wordLabel.setFont(Font.font("verdana", FontPosture.REGULAR, 12));	
		topText.setFont(Font.font("verdana",FontWeight.BOLD, FontPosture.REGULAR, 14));
		
		/**
		 *  Getting the rest of the GUI set up.
		 */
		HBox hbox = new HBox(wordLabel);
		VBox vbox1 = new VBox(topText, hbox);
		
		hbox.setAlignment(Pos.TOP_CENTER);
		vbox1.setAlignment(Pos.BASELINE_CENTER);
		//Set up for Scene
		Scene scene = new Scene(vbox1, 350, 350);
		stage.setTitle("GUI for Word Occurrences");
		stage.setScene(scene);
		stage.show();
		
		
	}
}