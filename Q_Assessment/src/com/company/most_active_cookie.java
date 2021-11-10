package com.company;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


//Main class, adopted classname in lowercase based on command line
public class most_active_cookie {

    public static void main(String[] args) throws IOException {
	// write your code here
        //check the length of args
        if (args.length != 3) {
            System.out.println ("Usage: Your command line input format is not in proper");
            System.exit (1);
        }

        String filename = args[0];  //filename in cmd line
        String _d = args[1];  //-d symbol

        String day = args[2];  //The date in cmd line that user want to track
        if(!day.matches("\\d{4}-\\d{2}-\\d{2}")){ //format check
            System.out.println("Your date input is not in valid format");
            System.out.println("Correct format: YYYY-MM-DD");
            System.exit(1);
        }


        //This arraylist is used to store values that matched selected days
        List<String> logs_storage= new ArrayList<>();

        //read the csv file
        String line = "";
        try{
            BufferedReader br = new BufferedReader(new FileReader(filename));
            while ((line = br.readLine()) != null){
                //skip the header
                if(line.contains("cookie,timestamp"))
                    continue;
                //call the format_string method to remove time but keep day in timestamp
                String line_after_format = format_string(line);

                String[] key_value = line_after_format.split(","); //This array stored the log and timestamp for a single line
                if(key_value[1].equals(day))
                    logs_storage.add(key_value[0]); //if the day is matched store the log into the arraylist
            }
        }catch(IOException e){
            System.err.println("This is not a valid file name or the file does not existed!");
            e.printStackTrace();
        }

        //Put all of nodes into a Hashmap to count frequency
        Map<String, Integer> map = new HashMap<>();
        if(logs_storage.isEmpty()){
            System.out.println("No logs activated in " + day);
            System.exit(1);
        }
        //count the frequency
        for (String s : logs_storage) {
            Integer count = map.get(s);
            if (count == null)
                count = 0;
            map.put(s, ++count);
        }


        //find most activity logs and save them into another Linkedlist
        int most_frequent_logs = Collections.max(map.values());
        List<String> results = new LinkedList<>();
        for (Map.Entry<String, Integer> en : map.entrySet()) {
            if (en.getValue()==most_frequent_logs) {
                results.add(en.getKey());
            }
        }

        //print them out
        System.out.println(results.toString());
    }

    //Format method
    public static String format_string(String a){
        //Here, I removed the time in each line for further steps, I removed them by following steps:
        //reverse line, remove the part before first T, reverse back.
        //Ex: 2018-12-09T14:19:00+00:00 => 2018-12-09
        StringBuilder sb_line=new StringBuilder(a);
        sb_line.reverse();
        String reverted_remove_time = (sb_line.toString()).split("T")[1];
        StringBuilder sb_line2=new StringBuilder(reverted_remove_time);
        String removed_time = (sb_line2.reverse()).toString();
        return removed_time;
    }
}
