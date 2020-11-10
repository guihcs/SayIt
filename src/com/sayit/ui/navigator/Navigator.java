package com.sayit.ui.navigator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sayit.di.Injector;
import com.sayit.ui.control.FXMLManager;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;

public class Navigator {

    private final Stage context;
    private static Map<String, SceneRoute> sceneRouteMap;
    private static final Stack<String> routeStack = new Stack<>();
    private static Stage lastModal;
    private static Consumer<Object> lastConsumer;

    private Navigator(Stage context) {
        this.context = context;
    }


    public static Navigator of(Stage context) {
        return new Navigator(context);
    }

    public void pushNamed(String path) {
        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());

        loader.setControllerFactory(this::instantiateInjected);

        Parent node = (Parent) FXMLManager.loadFromLoader(loader);

        if (node == null) throw new RuntimeException("Path not registered.");
        if (sceneRoute.getStyle() != null)
            Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());

        if (sceneRoute.getTitle() != null)
            context.setTitle(sceneRoute.getTitle());

        Platform.runLater(() -> {
            context.setScene(new Scene(node));
            context.show();
            centerWindow();
            Navigator.routeStack.push(path);
        });
    }

    private Object instantiateInjected(Class<?> type){
        try {
            Object o = type.getConstructor().newInstance();
            Injector.inject(o);
            return o;
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    private void centerWindow() {
        var screenBounds = Screen.getPrimary().getVisualBounds();
        context.setX(screenBounds.getMaxX() * 0.5 - context.getWidth() * 0.5);
        context.setY(screenBounds.getMaxY() * 0.5 - context.getHeight() * 0.5);
    }

    public <T> void pushNamedModal(String path, int width, int height, Consumer<T> resultCallback) {
        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());

        Stage requestWindow = createModal(node, width, height);
        if (sceneRoute.getTitle() != null)
            requestWindow.setTitle(sceneRoute.getTitle());

        Navigator.lastModal = requestWindow;
        Navigator.routeStack.push(path);
        requestWindow.showAndWait();
    }

    public static Node buildNamed(String path) {
        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        var loader = FXMLManager.getLoader(sceneRoute.getLayout());
        Parent node = (Parent) FXMLManager.loadFromLoader(loader);
        if (sceneRoute.getStyle() != null)
            Objects.requireNonNull(node).getStylesheets().add(sceneRoute.getStyle());

        return node;
    }

    public void pop() {
        Navigator.routeStack.pop();
        String peek = Navigator.routeStack.pop();
        pushNamed(peek);
    }

    public <T> void popResult(T result) {
        pop();

        if (lastModal != null) {
            lastModal.close();
            lastModal = null;
        }

        if (lastConsumer != null) {
            lastConsumer.accept(result);
            lastConsumer = null;
        }
    }

    private Stage createModal(Node root, double width, double height) {
        Stage stage = new Stage();
        Scene scene = new Scene((Parent) root, width, height);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        return stage;
    }


    public static void loadFrom(String path) {
        InputStream resource = Navigator.class.getClassLoader().getResourceAsStream(path);
        if (resource == null) throw new RuntimeException("Resource not found.");
        Reader reader = new InputStreamReader(resource);

        Type listOfMyClassObject = new TypeToken<ArrayList<SceneRoute>>() {
        }.getType();

        List<SceneRoute> sceneRoutes = new Gson().fromJson(reader, listOfMyClassObject);
        Map<String, SceneRoute> sceneRouteMap = new HashMap<>();

        sceneRoutes.forEach(r -> sceneRouteMap.put(r.getRoute(), r));

        Navigator.sceneRouteMap = sceneRouteMap;
    }
}
