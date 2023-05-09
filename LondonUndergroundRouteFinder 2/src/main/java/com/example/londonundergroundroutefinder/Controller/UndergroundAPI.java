package com.example.londonundergroundroutefinder.Controller;

import com.example.londonundergroundroutefinder.GraphNode;
import com.example.londonundergroundroutefinder.SearchGraph;
import com.example.londonundergroundroutefinder.Station;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.io.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;

public class UndergroundAPI implements Initializable {


    public ImageView mapView;
    // public  start;
    public ComboBox destination;
    public ComboBox stationBox;
    public ComboBox destBox;
    public TextField routeView;
    private List<GraphNode<Station>> stationsList;


    Station selected;

    public UndergroundAPI() {
//        this.stationsList = new LinkedList<>();
//        readCSV();
//        oneToOneConnection();
    }

//    public void populateTextView() {
//        String src = stationBox.getSelectionModel().getSelectedItem().toString();
//        GraphNode<Station> srcnode = findGraphNodeByName(src);
//        String dest = destBox.getSelectionModel().getSelectedItem().toString();
//        GraphNode<Station> destnode = findGraphNodeByName(dest).data;
//
//        if (stationBox.getItems().toString().equals(stationsList) && destBox.getItems().toString().equals(stationsList)){
//
//
//        }
//
//
//    }


    public void initialise() {
        this.stationsList = new LinkedList<>();
        readCSV();
        // oneToOneConnection();
        // ChoiceBox<String> stationBox = new ChoiceBox<>();
        //for loop
        //add each station name
        //add node data station name from node data
        //stationBox.getItems().addAll(stationsList);
// Add event listeners to combo boxes


    }

    public void oneToOneConnection() {
        System.out.println("Solution path from Acton Town to node containing AldgateEast");
        System.out.println("--------------------------------------------------------");
        List<GraphNode<?>> path = SearchGraph.findPathDepthFirst(stationsList.get(0), null, stationsList.get(2).data);
        for (GraphNode<?> n : path) System.out.println(n.data);
    }
/*
    public void oneToOneConnection(String startStation, String endStation) {
        System.out.println("Solution path from " + startStation + " to " + endStation);
        System.out.println("--------------------------------------------------------");

        // Find the corresponding GraphNodes for the selected stations
        GraphNode<?> startNode = null;
        GraphNode<?> endNode = null;
        for (GraphNode<?> node : stationsList) {
            if (node.data.equals(startStation)) {
                startNode = node;
            }
            if (node.data.equals(endStation)) {
                endNode = node;
            }
            if (startNode != null && endNode != null) {
                break;
            }
        }

        // Find the path between the selected stations using depth-first search
        List<GraphNode<?>> path = SearchGraph.findPathDepthFirst(startNode, null, endNode.data);

        // Print out the stations in the path
        for (GraphNode<?> n : path) {
            System.out.println(n.data);
        }
    }
*/
    private void readCSV() {
        String stationFileLine;
        File stationsFile = new File("src/main/resources/com/example/londonundergroundroutefinder/CSV/LondonUndergroundData.csv");
        try (BufferedReader br = new BufferedReader(new FileReader(stationsFile))) {
            while ((stationFileLine = br.readLine()) != null) {
                String[] elements = stationFileLine.split(",");
                Station s = new Station(Integer.parseInt(elements[0]), Double.parseDouble(elements[1]), Double.parseDouble(elements[2]), elements[3], elements[4], elements[5], Integer.parseInt(elements[6]), Integer.parseInt(elements[7]));
                GraphNode<Station> stations = new GraphNode<>(s);
                stationsList.add(stations);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        String routesFileLine;
        File routesFile = new File("src/main/resources/com/example/londonundergroundroutefinder/CSV/test.txt");
        try (BufferedReader br = new BufferedReader(new FileReader(routesFile))) {
            while ((routesFileLine = br.readLine()) != null) {
                String[] elements = routesFileLine.split(",");
                System.out.println(elements[0]);
                GraphNode<Station> src = findGraphNodeById(Integer.parseInt(elements[0].trim()));
                GraphNode<Station> dest = findGraphNodeById(Integer.parseInt(elements[1].trim()));
                if (src != null && dest != null)
                    src.connectToNodeUndirected(dest);
                //Routes r = new Routes(Integer.parseInt(elements[0]), elements[1], elements[2], elements[3]);
                // GraphNode<Routes> routes = new GraphNode<>(r);
                //routesList.add(routes);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        printAllGraphNodes();
    }

    public GraphNode<Station> findGraphNodeById(int id) {

        for (GraphNode<Station> node : stationsList) {
            if (((Station) node.data).getId() == id) {
                return node;
            }
        }
        return null;
    }

    public GraphNode<Station> findGraphNodeByName(String n) {

        for (GraphNode<Station> node : stationsList) {
            if (((Station) node.data).getStationName().equals(n)) {
                return node;
            }
        }
        return null;
    }

    private void printAllGraphNodes() {
        for (GraphNode<Station> node : stationsList) {

            System.out.println(node.data);
            System.out.println("Next Stations");
            System.out.println("_______________________________________________________________");
            for (GraphNode<Station> otherStations : node.adjlist) {

                System.out.println(otherStations.data);
            }
            System.out.println("_________________________________________________________________________");

        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.stationsList = new LinkedList<>();
        readCSV();
        oneToOneConnection();
        // ChoiceBox<String> stationBox = new ChoiceBox<>();
        //for loop
        //add each station name
        //add node data station name from node data
        //stationBox.getItems().addAll(stationsList);

        for (int i = 0; i < stationsList.size(); i++) {
            stationBox.getItems().add(stationsList.get(i).data.getStationName());
        }

        for (int i = 0; i < stationsList.size(); i++) {
            destBox.getItems().add(stationsList.get(i).data.getStationName());
        }




        //if (destBox.getItems().toString().equals(stationsList))
    }
    }


