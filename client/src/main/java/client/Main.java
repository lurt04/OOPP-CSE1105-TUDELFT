/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import static com.google.inject.Guice.createInjector;
import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    public static final MyFXML FXML = new MyFXML(INJECTOR);

    /**
     * Main method
     * @param args arguments
     * @throws URISyntaxException exception
     * @throws IOException        exception
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     * Starts application
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException exception
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        var mainCtrl = INJECTOR.getInstance(MainCtrl.class);
//        var eventOverview = FXML.load(EventOverviewCtrl.class, "client",
//                "scenes", "EventOverview.fxml");

//        var addExpenseTags = FXML.load(AddExpenseTagCtrl.class, "client",
//                "scenes", "AddExpenseTag.fxml");
        mainCtrl.initialize(primaryStage);
//        mainCtrl.overviewInitialize(eventOverview);

//        mainCtrl.invitationsInitialize(invitationsOverview);
//        mainCtrl.expenseTagsInitialize(addExpenseTags);
//        mainCtrl.adminInitialize(adminOverview, adminEventInfo);

    }
}