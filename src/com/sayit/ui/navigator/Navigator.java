package com.sayit.ui.navigator;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Consumer;

public class Navigator {

    private static final Stack<RenderedRoute> routeStack = new Stack<>();
    private static Map<String, SceneRoute> sceneRouteMap;
    private final Stage context;


    private Navigator(Stage context) {
        this.context = context;
    }


    public static Navigator of(Stage context) {
        return new Navigator(context);
    }

    public static RenderedNode buildNamed(String path, Consumer<Object> resultCallback) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.BUILD, resultCallback));
        return getRenderedNode(path);
    }

    public static RenderedNode buildNamed(String path, Object param, Consumer<Object> resultCallback) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.BUILD, resultCallback, param));
        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        return new RenderedNode(sceneRoute, param);
    }

    public static RenderedNode buildNamed(String path) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.BUILD));
        return getRenderedNode(path);
    }

    public static RenderedNode getRenderedNode(String path){
        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);
        return new RenderedNode(sceneRoute);
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

    public static void clearStack() {
        Navigator.routeStack.clear();
    }

    public void pushNamed(String path) {
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.NORMAL));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);

        RenderedNode renderedNode = new RenderedNode(sceneRoute);

        if (sceneRoute.getTitle() != null)
            context.setTitle(sceneRoute.getTitle());

        Platform.runLater(() -> {
            context.setScene(new Scene(renderedNode.getParent()));
            context.show();
            centerWindow();
        });
    }

    private void centerWindow() {
        var screenBounds = Screen.getPrimary().getVisualBounds();
        context.setX(screenBounds.getMaxX() * 0.5 - context.getWidth() * 0.5);
        context.setY(screenBounds.getMaxY() * 0.5 - context.getHeight() * 0.5);
    }

    public void pushNamedModal(String path, int width, int height, Consumer<Object> resultCallback) {
        Stage requestWindow = new Stage();

        Navigator.routeStack.push(new RenderedRoute(path, RouteType.MODAL, requestWindow, resultCallback));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);

        RenderedNode renderedNode = new RenderedNode(sceneRoute);

        requestWindow.initModality(Modality.APPLICATION_MODAL);
        Scene scene = new Scene(renderedNode.getParent(), width, height);

        requestWindow.setScene(scene);

        if (sceneRoute.getTitle() != null)
            requestWindow.setTitle(sceneRoute.getTitle());


        requestWindow.showAndWait();
    }

    public void pushNamedModal(String path, int width, int height, Object param, Consumer<Object> resultCallback) {
        Stage requestWindow = new Stage();
        Navigator.routeStack.push(new RenderedRoute(path, RouteType.MODAL, requestWindow, resultCallback, param));

        SceneRoute sceneRoute = Navigator.sceneRouteMap.get(path);

        RenderedNode renderedNode = new RenderedNode(sceneRoute, param);

        requestWindow.initModality(Modality.APPLICATION_MODAL);

        Scene scene = new Scene(renderedNode.getParent(), width, height);
        requestWindow.setScene(scene);
        if (sceneRoute.getTitle() != null)
            requestWindow.setTitle(sceneRoute.getTitle());

        requestWindow.showAndWait();
    }

    public <T> void popResult(T result) {

        RenderedRoute lastRoute = Navigator.routeStack.pop();

        if (lastRoute.getModal() != null) lastRoute.getModal().close();
        if (lastRoute.getConsumer() != null) lastRoute.getConsumer().accept(result);

        if (lastRoute.getRouteType() == RouteType.NORMAL
                &&
                Navigator.routeStack.peek().getRouteType() == RouteType.NORMAL) {
            rebuildLastRoute();
        }

    }

    public void rebuildLastRoute() {
        pushNamed(Navigator.routeStack.pop().getPath());
    }
}
