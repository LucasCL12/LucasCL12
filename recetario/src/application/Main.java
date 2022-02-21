package application;
	
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import controlsfx.CheckListView;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.CheckBoxListCell;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import java.util.concurrent.ThreadLocalRandom;


public class Main extends Application {
	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Recetario");

        Path listaRecetasPath=Paths.get("R:\\Programming\\EclipsePrograms\\recetario\\src\\application\\ListadoDeRecetas.txt");
        Scanner scanner;
		
        ListView<String> recipeListView=new ListView<>();
        recipeListView.setPrefHeight(575);
        
        String recipeAux;
        ArrayList<String> recipeList=new ArrayList<>();
        try {
			scanner = new Scanner(listaRecetasPath);
			while(scanner.hasNext()) {
				recipeAux=scanner.nextLine();
	        	recipeListView.getItems().add(recipeAux);
	        	//System.out.println(recipeAux);
	        	recipeList.add(recipeAux);
	        }
	        scanner.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
        boolean[] visibleRecipes = new boolean[recipeList.size()];
        for(int i=0;i<visibleRecipes.length;i++) {
        	visibleRecipes[i]=true;
        }
        
        ArrayList<ArrayList<ArrayList<String>>> allRecipesDetails=new ArrayList<>();
        
        ArrayList<String> ingredientsList=new ArrayList<>();
        Set<String> ingredientsSet=new HashSet<>();
        String auxString;
        List<String> auxList;
        List<String> auxList2;
        ArrayList<ArrayList<String>> auxArrayArray;
        for(int i=0;i<recipeList.size();i++) {
        	Path recipeToRip = Paths.get("R:\\Programming\\EclipsePrograms\\recetario\\src\\application\\Recetas\\"+recipeList.get(i));
        	System.out.println(recipeToRip);
        	try {
        		auxArrayArray=new ArrayList<>();
				scanner=new Scanner(recipeToRip);
				
				auxString=scanner.nextLine();
				auxList = Arrays.asList(auxString.split(",", -1));
				auxArrayArray.add(new ArrayList<String>(auxList));
				ingredientsSet.addAll(auxList);
				auxList2=new ArrayList<>();
				auxList2.addAll(auxList);
				
				auxString=scanner.nextLine();
				auxList = Arrays.asList(auxString.split(",", -1));
				auxArrayArray.add(new ArrayList<String>(auxList));
				ingredientsSet.addAll(auxList);
				auxList2.addAll(auxList);
				
				auxString=scanner.nextLine();
				auxArrayArray.add(new ArrayList<String>(Arrays.asList(auxString.split(",", -1))));
				
				auxString=scanner.nextLine();
				auxArrayArray.add(new ArrayList<String>(Arrays.asList(auxString.split(",", -1))));
				
				auxString=scanner.nextLine();
				auxArrayArray.add(new ArrayList<String>(Arrays.asList(auxString.split(",", -1))));
				
				auxArrayArray.add(new ArrayList<String>(auxList2));
				
				allRecipesDetails.add(auxArrayArray);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        /*for(int i=0;i<allRecipesDetails.size();i++) {
        	for(int j=0;j<allRecipesDetails.get(i).size();j++) {
                	System.out.println(allRecipesDetails.get(i).get(j).toString());
                
            }
        }*/
        ingredientsList.addAll(ingredientsSet);
        ingredientsList.sort(null);
        //System.out.println(ingredientsList.toString());
        ArrayList<String> ingredientsIncluded=new ArrayList<>();
        CheckListView<Ingredient> ingredientsIncludedCheckList = new CheckListView<Ingredient>();

        ObservableList<Ingredient> ingredientList = FXCollections.observableArrayList();
        for (int i = 0; i <ingredientsList.size(); i++) {
        	ingredientList.add(new Ingredient(ingredientsList.get(i), i));
        }
        ingredientsIncludedCheckList.setItems(ingredientList);
        ingredientsIncludedCheckList.setPrefHeight(575);
        ingredientsIncludedCheckList.setPrefWidth(200);

        ingredientsIncludedCheckList.setCellFactory(lv -> new CheckBoxListCell<Ingredient>(ingredientsIncludedCheckList::getItemBooleanProperty) {
            @Override
            public void updateItem(Ingredient ing, boolean empty) {
                super.updateItem(ing, empty);
                setText(ing == null ? "" : String.format(ing.getName(), ing.getEno()));
            }
        });
        

        CheckListView<Ingredient> ingredientsExcludedCheckList = new CheckListView<Ingredient>();
        ArrayList<String> ingredientsExcluded=new ArrayList<>();
        ingredientsExcludedCheckList.setItems(ingredientList);
        ingredientsExcludedCheckList.setPrefHeight(575);
        ingredientsExcludedCheckList.setPrefWidth(200);

        ingredientsExcludedCheckList.setCellFactory(lv -> new CheckBoxListCell<Ingredient>(ingredientsExcludedCheckList::getItemBooleanProperty) {
            @Override
            public void updateItem(Ingredient ing, boolean empty) {
                super.updateItem(ing, empty);
                setText(ing == null ? "" : String.format(ing.getName(), ing.getEno()));
            }
        });
        
        
        
        BorderPane borderPane=new BorderPane();
        
        HBox ingHBox=new HBox();
        VBox ingVBox1=new VBox();
        VBox ingVBox2=new VBox();
        Label ingLabel1=new Label("Incluir ingredientes");
        Label ingLabel2=new Label("No incluir ingredientes");
        Button ingButton1 =new Button("Desmarcar todo");
        ingButton1.setPrefWidth(200);
        Button ingButton2 =new Button("Desmarcar todo");
        ingButton2.setPrefWidth(200);
        
        ingButton1.setOnMouseClicked(event->{
        	ingredientsIncludedCheckList.getCheckModel().clearChecks();
        });
        
        ingButton2.setOnMouseClicked(event->{
        	ingredientsExcludedCheckList.getCheckModel().clearChecks();
        });
        
        ingVBox1.getChildren().add(ingLabel1);
        ingVBox1.getChildren().add(ingredientsIncludedCheckList);
        ingVBox1.getChildren().add(ingButton1);
        ingVBox2.getChildren().add(ingLabel2);
        ingVBox2.getChildren().add(ingredientsExcludedCheckList);
        ingVBox2.getChildren().add(ingButton2);
        ingHBox.getChildren().add(ingVBox1);
        ingHBox.getChildren().add(ingVBox2);
        borderPane.setLeft(ingHBox);
        
        VBox midVBox=new VBox();
        HBox midHBox=new HBox();
        VBox midVBox0=new VBox();
        VBox midVBox1=new VBox();
        VBox midVBox2=new VBox();
        VBox midVBox3=new VBox();
        HBox tiempoHBox=new HBox();
        VBox tiempoVBox1=new VBox();
        VBox tiempoVBox2=new VBox();
        Label midLabel1=new Label("Opciones");
        Label midLabel2=new Label("Tiempo (min)");
        Label midLabel3=new Label("Plato");
        
        CheckBox opcCheckBox1=new CheckBox("Vegetariano");
        opcCheckBox1.setPrefSize(90, 25);
        
        CheckBox opcCheckBox2=new CheckBox("Vegano");
        opcCheckBox2.setPrefSize(80,25);
        
        CheckBox opcCheckBox3=new CheckBox("No gluten");
        opcCheckBox3.setPrefSize(80,25);
        
        CheckBox midCheckBoxTotal=new CheckBox("Total");
        midCheckBoxTotal.setPrefSize(90, 25);
        CheckBox midCheckBoxActivo=new CheckBox("Activo");
        midCheckBoxActivo.setPrefSize(90, 25);
        CheckBox midCheckBoxPrevio=new CheckBox("Antelación");
        midCheckBoxPrevio.setPrefSize(90, 25);
        TextField textFieldTiempo1=new TextField("1000");
        textFieldTiempo1.setMaxWidth(60);
        TextField textFieldTiempo2=new TextField("1000");
        textFieldTiempo2.setMaxWidth(60);
        TextField textFieldTiempo3=new TextField("1000");
        textFieldTiempo3.setMaxWidth(60);
        
        CheckBox platoCheckBox1=new CheckBox("Primero");
        platoCheckBox1.setPrefHeight(19);
        CheckBox platoCheckBox2=new CheckBox("Segundo");
        platoCheckBox2.setPrefHeight(19);
        CheckBox platoCheckBox3=new CheckBox("Acompañamiento");
        platoCheckBox3.setPrefHeight(19);
        CheckBox platoCheckBox4=new CheckBox("Repostería");
        platoCheckBox4.setPrefHeight(19);
        TextArea recetaTexto= new TextArea("Receta");
        recetaTexto.setPrefSize(570,480);
        
        midVBox1.getChildren().add(midLabel1);
        midVBox1.getChildren().add(opcCheckBox1);
        midVBox1.getChildren().add(opcCheckBox2);
        midVBox1.getChildren().add(opcCheckBox3);
        
        midVBox2.getChildren().add(midLabel2);
        tiempoVBox1.getChildren().add(midCheckBoxTotal);
        tiempoVBox1.getChildren().add(midCheckBoxActivo);
        tiempoVBox1.getChildren().add(midCheckBoxPrevio);
        tiempoVBox2.getChildren().add(textFieldTiempo1);
        tiempoVBox2.getChildren().add(textFieldTiempo2);
        tiempoVBox2.getChildren().add(textFieldTiempo3);
        tiempoHBox.getChildren().add(tiempoVBox1);
        tiempoHBox.getChildren().add(tiempoVBox2);
        tiempoHBox.setSpacing(5);
        midVBox2.getChildren().add(tiempoHBox);
        
        midVBox3.getChildren().add(midLabel3);
        midVBox3.getChildren().add(platoCheckBox1);
        midVBox3.getChildren().add(platoCheckBox2);
        midVBox3.getChildren().add(platoCheckBox3);
        midVBox3.getChildren().add(platoCheckBox4);
        midHBox.getChildren().add(midVBox0);
        midHBox.getChildren().add(midVBox1);
        midHBox.getChildren().add(midVBox2);
        midHBox.getChildren().add(midVBox3);
        midHBox.setSpacing(50);
        midVBox.getChildren().add(midHBox);
        midVBox.setSpacing(20);
        midVBox.getChildren().add(recetaTexto);
        borderPane.setCenter(midVBox);

        VBox rightVBox=new VBox();
        Label rightLabel1=new Label("Recetas");
                  
        
        recipeListView.setOnMouseClicked(event -> {
            ObservableList<String> selectedItems = recipeListView.getSelectionModel().getSelectedItems();

            recetaTexto.clear();
            recetaTexto.appendText(selectedItems.toString()+"\n\n");
            Path recipePath=Paths.get("R:\\Programming\\EclipsePrograms\\recetario\\src\\application\\Recetas\\"+selectedItems.get(0));
            Scanner scanner2;
            try {
            	scanner2 = new Scanner(recipePath);
        		while(scanner2.hasNext()) {
        			recetaTexto.appendText(scanner2.nextLine());
        			recetaTexto.appendText("\n");
        	    }
        	    scanner2.close();
        	} catch (IOException e) {
        		e.printStackTrace();
        	}
        });
        
        Button selectRandomRecipe = new Button("Receta aleatoria");
        selectRandomRecipe.setOnAction(event -> {
            int maxRandom = recipeListView.getItems().size();
            if (maxRandom ==0) {
            	return;
            }
            int randomNum = ThreadLocalRandom.current().nextInt(0, maxRandom);
            recetaTexto.clear();
            recetaTexto.appendText("["+recipeListView.getItems().get(randomNum)+"]\n\n");
            Path recipePath=Paths.get("R:\\Programming\\EclipsePrograms\\recetario\\src\\application\\Recetas\\"+recipeListView.getItems().get(randomNum));
            Scanner scanner2;
            try {
    			scanner2 = new Scanner(recipePath);
    			while(scanner2.hasNext()) {
    				recetaTexto.appendText(scanner2.nextLine());
    				recetaTexto.appendText("\n");
    	        }
    	        scanner2.close();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}
        });
        
        
        
        rightVBox.getChildren().add(rightLabel1);
        rightVBox.getChildren().add(recipeListView);
        HBox rightHBox=new HBox();
        rightHBox.getChildren().add(selectRandomRecipe);
        rightVBox.getChildren().add(rightHBox);
        
        borderPane.setRight(rightVBox);
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        
        
        
        ingredientsIncludedCheckList.getCheckModel().getCheckedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Integer> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (int i : c.getAddedSubList()) {
                            ingredientsIncluded.add(ingredientsIncludedCheckList.getItems().get(i).getName());
                            for(int m=0;m<allRecipesDetails.size();m++) {
                                if(visibleRecipes[m]
                                		&& !allRecipesDetails.get(m).get(5).contains(ingredientsIncludedCheckList.getItems().get(i).getName())){
                                	visibleRecipes[m]=false;
                                	recipeListView.getItems().remove(recipeList.get(m));
                                }
                            }
                        }
                    }
                    if (c.wasRemoved()) {
                    	boolean flag;
                        for (int i : c.getRemoved()) {
                            ingredientsIncluded.remove(ingredientsIncludedCheckList.getItems().get(i).getName());
                            for(int m=0;m<allRecipesDetails.size();m++) {
                                if(!visibleRecipes[m]
                                		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                                	flag=false;
                                	for(int j=0;j<ingredientsExcluded.size();j++) {
                                		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                                			flag=true;
                                			break;
                                		}
                                	}
                                	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                                		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                                		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                                		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                                		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                                		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                                		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                                		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                                		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                                		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                                			){
                    					flag=true;
                    				}
                                	if(!flag) {
                                	recipeListView.getItems().add(recipeList.get(m));
                                	visibleRecipes[m]=true;}
                                }
                            }
                        }
                        recipeListView.getItems().sort(null);
                    }
                }
            }
        });
        

        ingredientsExcludedCheckList.getCheckModel().getCheckedIndices().addListener(new ListChangeListener<Integer>() {
            @Override
            public void onChanged(javafx.collections.ListChangeListener.Change<? extends Integer> c) {
                while (c.next()) {
                    if (c.wasAdded()) {
                        for (int i : c.getAddedSubList()) {
                            ingredientsExcluded.add(ingredientsExcludedCheckList.getItems().get(i).getName());
                            for(int m=0;m<allRecipesDetails.size();m++) {
                                if(visibleRecipes[m]
                                		&& allRecipesDetails.get(m).get(0).contains(ingredientsExcludedCheckList.getItems().get(i).getName())){
                                	visibleRecipes[m]=false;
                                	recipeListView.getItems().remove(recipeList.get(m));
                                }
                            }
                        }
                    }
                    if (c.wasRemoved()) {
                        for (int i : c.getRemoved()) {
                        	boolean flag;
                        	ingredientsExcluded.remove(ingredientsExcludedCheckList.getItems().get(i).getName());
                            for(int m=0;m<allRecipesDetails.size();m++) {
                                if(!visibleRecipes[m]
                                		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                                	flag=false;
                                    for(int j=0;j<ingredientsExcluded.size();j++) {
                                    	if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                                    		flag=true;
                                            	break;
                                            }
                                    }
                                	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                                		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                                		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                                		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                                		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                                		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                                		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                                		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                                		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                                		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                                			){
                    					flag=true;
                    				}
                                    if(!flag) {
                                    	recipeListView.getItems().add(recipeList.get(m));
                                    	visibleRecipes[m]=true;
                                    }
                                }
                            }
                        }
                        recipeListView.getItems().sort(null);
                    }
                }
            }
        });
               
        
        opcCheckBox1.setOnAction(event->{
        	if(opcCheckBox1.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(2).contains("NoVegetariano")){
                    	visibleRecipes[m]=false;
                    	recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                    		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                    		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                    		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                    		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                    		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                    		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                    		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                    		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                    		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                    			){
        					flag=true;
        				}
                    	
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        opcCheckBox2.setOnAction(event->{
        	if(opcCheckBox2.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(2).contains("NoVegano")){
                    	visibleRecipes[m]=false;
                    	recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        opcCheckBox3.setOnAction(event->{
        	if(opcCheckBox3.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(2).contains("Gluten")){
                    	visibleRecipes[m]=false;
                    	recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                    		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                    		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                    		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                    		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                    		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                    		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                    		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                    		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                    		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                    			){
        					flag=true;
        				}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        midCheckBoxTotal.setOnAction(event->{
        	if(midCheckBoxTotal.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(visibleRecipes[m]
                    		&& (Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText()))){
                    	visibleRecipes[m]=false;
                    	recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        midCheckBoxActivo.setOnAction(event->{
        	if(midCheckBoxActivo.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(visibleRecipes[m]
                    		&& (Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText()))){
                    	visibleRecipes[m]=false;
                    	recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        midCheckBoxPrevio.setOnAction(event->{
        	if(midCheckBoxPrevio.isSelected()) {
            		for(int m=0;m<allRecipesDetails.size();m++) {
                        if(visibleRecipes[m]
                        		&& (Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText()))){
                        	visibleRecipes[m]=false;
                        	recipeListView.getItems().remove(recipeList.get(m));
                        }
                    }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        platoCheckBox1.setOnAction(event->{
        	if(platoCheckBox1.isSelected()) {
            	for(int m=0;m<allRecipesDetails.size();m++) {
            		if(visibleRecipes[m]
            				&& !allRecipesDetails.get(m).get(4).contains("1")){
                       visibleRecipes[m]=false;
                       recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        platoCheckBox2.setOnAction(event->{
        	if(platoCheckBox2.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
            		if(visibleRecipes[m]
            				&& !allRecipesDetails.get(m).get(4).contains("2")){
                       visibleRecipes[m]=false;
                       recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        platoCheckBox3.setOnAction(event->{
        	if(platoCheckBox3.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
            		if(visibleRecipes[m]
            				&& !allRecipesDetails.get(m).get(4).contains("3")){
                       visibleRecipes[m]=false;
                       recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        platoCheckBox4.setOnAction(event->{
        	if(platoCheckBox4.isSelected()) {
        		for(int m=0;m<allRecipesDetails.size();m++) {
            		if(visibleRecipes[m]
            				&& !allRecipesDetails.get(m).get(4).contains("4")){
                       visibleRecipes[m]=false;
                       recipeListView.getItems().remove(recipeList.get(m));
                    }
                }
        	} else {
        		boolean flag;
        		for(int m=0;m<allRecipesDetails.size();m++) {
                    if(!visibleRecipes[m]
                    		&& allRecipesDetails.get(m).get(5).containsAll(ingredientsIncluded)){
                    	flag=false;
                    	for(int j=0;j<ingredientsExcluded.size();j++) {
                    		if (allRecipesDetails.get(m).get(0).contains(ingredientsExcluded.get(j))){
                    			flag=true;
                    			break;
                    		}
                    	}
                    	if((opcCheckBox1.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegetariano"))
                        		|| (opcCheckBox2.isSelected()&&allRecipesDetails.get(m).get(2).contains("NoVegano"))
                        		|| (opcCheckBox3.isSelected()&&allRecipesDetails.get(m).get(2).contains("Gluten"))
                        		|| (midCheckBoxTotal.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(0))>Integer.valueOf(textFieldTiempo1.getText())))
                        		|| (midCheckBoxActivo.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(1))>Integer.valueOf(textFieldTiempo2.getText())))
                        		|| (midCheckBoxPrevio.isSelected()&&(Integer.valueOf(allRecipesDetails.get(m).get(3).get(2))>Integer.valueOf(textFieldTiempo3.getText())))
                        		|| (platoCheckBox1.isSelected()&&!allRecipesDetails.get(m).get(4).contains("1"))
                        		|| (platoCheckBox2.isSelected()&&!allRecipesDetails.get(m).get(4).contains("2"))
                        		|| (platoCheckBox3.isSelected()&&!allRecipesDetails.get(m).get(4).contains("3"))
                        		|| (platoCheckBox4.isSelected()&&!allRecipesDetails.get(m).get(4).contains("4"))
                        			){
            				flag=true;
            			}
                    	if(!flag) {
                    	recipeListView.getItems().add(recipeList.get(m));
                    	visibleRecipes[m]=true;}
                    }
                }
        		recipeListView.getItems().sort(null);
        	}
        });
        
        
    }

    
    public static class Ingredient {
        private final StringProperty name = new SimpleStringProperty();
        private final IntegerProperty eno = new SimpleIntegerProperty();
        public Ingredient(String name, int eno) {
            setName(name) ;
            setEno(eno);
        }
        public final StringProperty nameProperty() {
            return this.name;
        }

        public final String getName() {
            return this.nameProperty().get();
        }

        public final void setName(final String name) {
            this.nameProperty().set(name);
        }

        public final IntegerProperty enoProperty() {
            return this.eno;
        }

        public final int getEno() {
            return this.enoProperty().get();
        }

        public final void setEno(final int eno) {
            this.enoProperty().set(eno);
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}
